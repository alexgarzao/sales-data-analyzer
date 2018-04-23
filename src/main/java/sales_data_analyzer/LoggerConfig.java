package sales_data_analyzer;

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.ConsoleHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.LogRecord;

/**
* LoggerConfig class. Build the logger config.
*
* @author Alex S. Garzão
*/
public class LoggerConfig {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private ConsoleHandler ch = null;
    private FileHandler fh = null;

    public LoggerConfig(String logFilename) {
        try {
            ch = new ConsoleHandler();
            fh = new FileHandler(logFilename);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ch.setFormatter(new MyFormatter());
        fh.setFormatter(new MyFormatter());

        LOGGER.setUseParentHandlers(false);

        LOGGER.addHandler(ch);
        LOGGER.addHandler(fh);
    }
}


/**
* MyFormatter class. Custom logger formatter.
*/
class MyFormatter extends SimpleFormatter{
    @Override
    public synchronized String format(LogRecord record) {
        record.setSourceClassName(MyFormatter.class.getName());
        return String.format(
            "[%1$s]: %2$s - %3$s\n",
            record.getLevel().getName(),
            Thread.currentThread().getName(),
            formatMessage(record));
    }
}
