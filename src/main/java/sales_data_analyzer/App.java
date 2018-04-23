package sales_data_analyzer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.nio.file.Paths;
import java.util.logging.Logger;
import java.nio.file.Files;
import java.io.IOException;

/**
* Main class. The data analyzer start here.
*
* @author Alex S. Garzão
*/
public class App
{
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
    * main... where the magic start... well, with some bugs too :-)
    */
    public static void main(String[] args)
    {
        createDefaultDataDirs();
        loggerConfig();

        LOGGER.info("Starting Sales Data Analyzer 0.1");
        LOGGER.info("Current config is " + AppConfig.toLog());

        startFileWatcher();
    }

    /**
    * If necessary, create the default data dirs.
    */
    private static void createDefaultDataDirs()
    {
        try {
            Files.createDirectories(Paths.get(AppConfig.inPath));
            Files.createDirectories(Paths.get(AppConfig.outPath));
            Files.createDirectories(Paths.get(AppConfig.procPath));
            Files.createDirectories(Paths.get(AppConfig.logPath));
        } catch(IOException ex) {
            System.out.println("ERROR: When trying to create default paths: " + ex.toString());
        }
    }

    /**
    * Config the logger.
    */
    private static void loggerConfig()
    {
        new LoggerConfig(AppConfig.logPath + AppConfig.logFilename);
    }

    /**
    * Start the file watcher.
    */
    private static void startFileWatcher()
    {
        ExecutorService executor = Executors.newFixedThreadPool(AppConfig.maxWorkers);
        FileWatcher fileWatcher = new FileWatcher(Paths.get(AppConfig.inPath), AppConfig.fileSuffixToProcess, executor);
        fileWatcher.start();
    }
}
