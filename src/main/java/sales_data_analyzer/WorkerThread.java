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
            LOGGER.info("Start file " + config.inFilename);
            processCommand();
            LOGGER.info("End file " + config.inFilename);
            LOGGER.info("Stats file created in " + config.bkpFilename);
        } catch(Exception ex) {
            LOGGER.severe(ex.toString());
        }
    }

    private void processCommand() throws FileNotFoundException, IOException {
        try {
            SalesFile t = new SalesFile(config.inFilename, config.outFilename, config.bkpFilename);
            t.process();
        } catch(FileNotFoundException e) {
            throw e;
        } catch(IOException e) {
            throw e;
        }
    }

    @Override
    public String toString(){
        return config.inFilename;
    }
}
