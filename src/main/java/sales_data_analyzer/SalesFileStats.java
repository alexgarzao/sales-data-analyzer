package sales_data_analyzer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
* SalesFileStats class is responsible to create the stats file.
*
* @author  Alex S. Garzão
*/
public class SalesFileStats
{
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String RECORD_ID = "003";

    private String outFilename;

    /**
    * ProcessSalesFile is responsible to define the file to be processed.
    */
    public SalesFileStats(String outFilename)
    {
        this.outFilename = outFilename;
    }

    /**
    * save is responsible to save the stats on the out filename.
    *
    * Format: 099çTotalSalesMançTotalCustomersçMostExpensiveSaleIdçWorstSalesman
    */
    public void save(SalesFileReader salesFileReader)
        throws IOException
    {
        StringBuilder str = new StringBuilder(RECORD_ID);
        str.append(AppConfig.recordDelimiter + salesFileReader.getTotalSalesman());
        str.append(AppConfig.recordDelimiter + salesFileReader.getTotalCustomers());
        str.append(AppConfig.recordDelimiter + salesFileReader.getMostExpensiveSaleId());
        str.append(AppConfig.recordDelimiter + salesFileReader.getWorstSalesman());
        str.append('\n');

        BufferedWriter writer = new BufferedWriter(new FileWriter(outFilename));
        writer.write(str.toString());

        writer.close();

        LOGGER.info("Stats file created in " + outFilename);
    }
}
