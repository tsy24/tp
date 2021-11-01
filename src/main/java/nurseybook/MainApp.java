package nurseybook;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import nurseybook.commons.core.Config;
import nurseybook.commons.core.LogsCenter;
import nurseybook.commons.core.Version;
import nurseybook.commons.exceptions.DataConversionException;
import nurseybook.commons.util.ConfigUtil;
import nurseybook.commons.util.StringUtil;
import nurseybook.logic.Logic;
import nurseybook.logic.LogicManager;
import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.NurseyBook;
import nurseybook.model.ReadOnlyNurseyBook;
import nurseybook.model.ReadOnlyUserPrefs;
import nurseybook.model.UserPrefs;
import nurseybook.model.util.SampleDataUtil;
import nurseybook.storage.JsonNurseyBookStorage;
import nurseybook.storage.JsonUserPrefsStorage;
import nurseybook.storage.NurseyBookStorage;
import nurseybook.storage.Storage;
import nurseybook.storage.StorageManager;
import nurseybook.storage.UserPrefsStorage;
import nurseybook.ui.Ui;
import nurseybook.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 3, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing NurseyBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        NurseyBookStorage nurseyBookStorage = new JsonNurseyBookStorage(userPrefs.getNurseyBookFilePath());
        storage = new StorageManager(nurseyBookStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s nursey book and {@code userPrefs}. <br>
     * The data from the sample nursey book will be used instead if {@code storage}'s nursey book is not found,
     * or an empty nursey book will be used instead if errors occur when reading {@code storage}'s nursey book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyNurseyBook> nurseyBookOptional;
        ReadOnlyNurseyBook initialData;
        try {
            nurseyBookOptional = storage.readNurseyBook();
            if (!nurseyBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample NurseyBook");
            }
            initialData = nurseyBookOptional.orElseGet(SampleDataUtil::getSampleNurseyBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty NurseyBook");
            initialData = new NurseyBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty NurseyBook");
            initialData = new NurseyBook();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty NurseyBook");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting NurseyBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Nursey Book ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
