package sales_data_analyzer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.logging.Logger;

/**
* SalesFileStats class is responsible to create the stats file.
*
* @author  Alex S. Garzão
*/
public class SalesFileStats
{
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String RECORD_ID = "099";

    /**
    * save is responsible to save the stats on the out filename.
    *
    * Format: 099çTotalSalesMançTotalCustomersçMostExpensiveSaleIdçWorstSalesman
    */
    public void save(String outFilename, String fieldDelimiter, SalesFile SalesFile)
        throws IOException
    {
        StringBuilder str = new StringBuilder(RECORD_ID);
        str.append(fieldDelimiter + SalesFile.getTotalSalesman());
        str.append(fieldDelimiter + SalesFile.getTotalCustomers());
        str.append(fieldDelimiter + SalesFile.getMostExpensiveSaleId());
        str.append(fieldDelimiter + SalesFile.getWorstSalesman());
        str.append('\n');

        BufferedWriter writer = new BufferedWriter(new FileWriter(outFilename));
        writer.write(str.toString());

        writer.close();

        LOGGER.info("Stats file created in " + outFilename);
    }
}
