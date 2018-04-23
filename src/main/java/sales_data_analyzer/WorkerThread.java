package sales_data_analyzer;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

/**
* @author: Alex S. Garzão
*
* WorkerThread class. A worker thread.
*/
public class WorkerThread implements Runnable
{
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private String inFilename;

    public WorkerThread(String inFilename)
    {
        this.inFilename = inFilename;
    }

    @Override
    public void run() {
        try {
            processCommand();
        } catch(Exception ex) {
            LOGGER.severe(ex.toString());
        }
    }

    private void processCommand()
        throws FileNotFoundException, IOException
    {
        String outFilename = AppConfig.getOutFilename(inFilename);
        String bkpFilename = AppConfig.getBkpFilename(inFilename);

        try {
            ProcessSalesFile t = new ProcessSalesFile(inFilename, outFilename, bkpFilename, AppConfig.fieldDelimiter);
            t.process();
        } catch(RecordInvalidTokenException ex) {
            LOGGER.severe(String.format("Invalid record processing file %s: %s", inFilename, ex));
        } catch(FileNotFoundException ex) {
            LOGGER.severe(String.format("File not found: %s", inFilename));
        } catch(IOException ex) {
            LOGGER.severe(String.format("I/O exception when processing file %s: %s", inFilename, ex));
        }
    }

    @Override
    public String toString(){
        return inFilename;
    }
}
