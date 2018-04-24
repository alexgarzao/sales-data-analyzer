package sales_data_analyzer;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.logging.Logger;
import java.util.Locale;

/**
* ProcessSalesFile class is responsible to process the sale file.
*     In the stage of process, is necessary to read the file, parser, keep stats data, and so on.
*
* @author  Alex S. Garzão
*/
public class ProcessSalesFile
{
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private String inFilename;
    private String outFilename;
    private String bkpFilename;
    private String fieldDelimiter;

    private SalesFile salesFile;

    /**
    * ProcessSalesFile is responsible to define the file to be processed.
    */
    public ProcessSalesFile(String inFilename, String outFilename, String bkpFilename, String fieldDelimiter)
    {
        this.inFilename = inFilename;
        this.outFilename = outFilename;
        this.bkpFilename = bkpFilename;
        this.fieldDelimiter = fieldDelimiter;
    }

    /**
    * process is responsible to extract all the data from a file.
    */
    public void process()
        throws FileNotFoundException, IOException, RecordInvalidTokenException
    {
        processFile();
        saveFileStats();
        doFileBackup();
        logStats();
    }

    /**
    * Read the sales file.
    */
    private void processFile()
        throws FileNotFoundException, IOException, RecordInvalidTokenException
    {
        salesFile = new SalesFile(inFilename, fieldDelimiter);
        salesFile.read();
    }

    /**
    * saveFileStats responsible to save the stats on the out filename.
    *
    * Format: 099çTotalSalesMançTotalCustomersçMostExpensiveSaleIdçWorstSalesman
    */
    public void saveFileStats()
        throws IOException
    {
        SalesFileStats salesFileStats = new SalesFileStats();
        salesFileStats.save(outFilename, fieldDelimiter, salesFile);
    }

    /**
    * Move the file to backup dir.
    */
    private void doFileBackup()
    {
        Path source = Paths.get(inFilename);
        Path target = Paths.get(bkpFilename);

        try {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            LOGGER.severe("When trying to move the file: " + ex);
        }

        LOGGER.info(String.format("File moved from %s to %s", inFilename, bkpFilename));
    }

    /**
    * Log some stats about the process.
    */
    private void logStats()
    {
        LOGGER.info(String.format(
            Locale.US,
            "File: %s TimeToProcess(s): %.2f Lines: %d TotalSalesman: %d TotalCustomers: %d TotalSales: %d",
            inFilename, salesFile.getSpentTimeToProcess() / 1000.0, salesFile.getFileLines(), salesFile.getTotalSalesman(), salesFile.getTotalCustomers(), salesFile.getTotalSales()));
    }
}
