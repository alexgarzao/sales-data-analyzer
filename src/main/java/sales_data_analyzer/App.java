package sales_data_analyzer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.nio.file.Paths;
import java.util.logging.Logger;
import java.nio.file.Files;
import java.io.IOException;

/**
* @author: Alex S. Garzão
*
* Main class. The data analyzer start here.
*/
public class App
{
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main( String[] args )
    {
        loggerConfig();

        LOGGER.info("Starting Sales Data Analyzer 0.1");
        LOGGER.info("Current config is " + AppConfig.toLog());

        createDefaultDataDirs();
        startThreadPoolAndFileWatcher();
    }

    private static void loggerConfig()
    {
        new LoggerConfig(AppConfig.logPath + AppConfig.logFilename);
    }

    private static void startThreadPoolAndFileWatcher()
    {
        ExecutorService executor = Executors.newFixedThreadPool(AppConfig.maxWorkers);
        FileWatcher fileWatcher = new FileWatcher(Paths.get(AppConfig.inPath), AppConfig.fileSuffixToProcess, executor);
        fileWatcher.start();
    }

    private static void createDefaultDataDirs()
    {
        try {
            Files.createDirectories(Paths.get(AppConfig.inPath));
            Files.createDirectories(Paths.get(AppConfig.outPath));
            Files.createDirectories(Paths.get(AppConfig.procPath));
            Files.createDirectories(Paths.get(AppConfig.logPath));
        } catch(IOException ex) {
            LOGGER.severe("When trying to create default paths: " + ex.toString());
        }
    }
}
