package sales_data_analyzer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.nio.file.Paths;
import java.util.logging.Logger;

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
        new LoggerConfig("data/log/sda.log");

        LOGGER.info("Starting Sales Data Analyzer 0.1");

        // TODO: define the correct thread pool size bellow.
        ExecutorService executor = Executors.newFixedThreadPool(2);
        FileWatcher fileWatcher = new FileWatcher(Paths.get("./data/in"), executor);
        fileWatcher.start();

        LOGGER.info("Ending Sales Data Analyzer 0.1");
    }
}
