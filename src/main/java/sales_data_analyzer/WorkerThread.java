package sales_data_analyzer;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

public class WorkerThread implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private WorkerConfig config;

    public WorkerThread(WorkerConfig config)
    {
        this.config = config;
    }

    @Override
    public void run() {
        try {
            processCommand();
        } catch(Exception ex) {
            LOGGER.severe(ex.toString());
        }
    }

    private void processCommand() throws FileNotFoundException, IOException {

        try {
            LOGGER.info("Start file " + config.inFilename);

            SalesFile t = new SalesFile(config.inFilename, config.outFilename, config.bkpFilename);
            t.process();

            LOGGER.info("End file " + config.inFilename);
            LOGGER.info("Stats file created in " + config.bkpFilename);
        } catch(RecordInvalidTokenException ex) {
            LOGGER.severe(String.format("Invalid record processing file %s: %s", config.inFilename, ex));
        } catch(FileNotFoundException ex) {
            LOGGER.severe(String.format("File not found: %s", config.inFilename));
        } catch(IOException ex) {
            LOGGER.severe(String.format("I/O exception when processing file %s: %s", config.inFilename, ex));
        }
    }

    @Override
    public String toString(){
        return config.inFilename;
    }
}
