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
import java.util.logging.Logger;

/**
* SalesFile is responsible to parser the file.
*
* @author  Alex S. Garzão
*/
public class SalesFile
{
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String RECORD_ID = "001";
    private static final int RECORD_SIZE = RECORD_ID.length();

    private String inFilename;
    private Salesman salesmanData;
    private Customer customerData;
    private Sales salesData;
    private long spentTimeToProcess;
    private long fileLines;

    /**
    * SalesFile is responsible to define the file to be parsed.
    */
    public SalesFile(String inFilename, String fieldDelimiter)
    {
        this.inFilename = inFilename;
        salesmanData = new Salesman(fieldDelimiter);
        customerData = new Customer(fieldDelimiter);
        salesData = new Sales(fieldDelimiter);
    }

    /**
    * read is responsible to extract all the data from a file.
    */
    public void read()
        throws FileNotFoundException, IOException, RecordInvalidTokenException
    {
        long startTime = System.currentTimeMillis();

        LOGGER.info("Start process file " + inFilename);

        BufferedInputStream bf = new BufferedInputStream(new FileInputStream(inFilename));
        BufferedReader in = new BufferedReader(new InputStreamReader(bf, StandardCharsets.UTF_8));
        Map<String, Record> recordTypes = mapAllRecordTypes();
        String recordLine;

        recordLine = in.readLine();

        while(recordLine != null) {
            fileLines += 1;

            if (recordLine.isEmpty()) {
                recordLine = in.readLine();
                continue;
            }

            recordParser(recordLine, recordTypes);

            recordLine = in.readLine();
        }

        bf.close();
        in.close();

        spentTimeToProcess = System.currentTimeMillis() - startTime;
    }

    /**
    * mapAllRecordTypes is responsible to map all record types.
    *     The main purpouse is to speed up the discovery of record type.
    *
    * @return all record types mapped.
    */
    private Map<String, Record> mapAllRecordTypes()
    {
        Map<String, Record> recordTypes = new HashMap<String, Record>();

        recordTypes.put(salesmanData.getId(), salesmanData);
        recordTypes.put(customerData.getId(), customerData);
        recordTypes.put(salesData.getId(), salesData);

        return recordTypes;
    }

    private void recordParser(String recordLine, Map<String, Record> recordTypes)
        throws RecordInvalidTokenException
    {
        try {
            String formatId = recordLine.substring(0, RECORD_SIZE);
            Record record = recordTypes.get(formatId);
            record.parser(recordLine);
            if (formatId.equals(RECORD_ID)) {
                salesData.addNewSalesman(salesmanData.getName());
            }
        } catch(Exception ex) {
            throw new RecordInvalidTokenException();
        }
    }

    /**
    * getTotalSalesman is responsible to return the total of salesman.
    *
    * @return the total of salesman.
    */
    public int getTotalSalesman()
    {
        return salesmanData.getTotal();
    }

    /**
    * getTotalCustomers is responsible to return the total of customers.
    *
    * @return the total of customers.
    */
    public int getTotalCustomers()
    {
        return customerData.getTotal();
    }

    /**
    * getTotalSales is responsible to return the total of sales.
    *
    * @return the total of sales.
    */
    public int getTotalSales()
    {
        return salesData.getTotal();
    }

    /**
    * getMostExpensiveSaleId is responsible to return the most expensive sale id.
    *
    * @return the most expensive sale id.
    */
    public String getMostExpensiveSaleId()
    {
        return salesData.getMostExpensiveSaleId();
    }

    /**
    * getWorstSalesman is responsible to return the worst salesman.
    *
    * @return the worst salesman.
    */
    public String getWorstSalesman()
    {
        return salesData.getWorstSalesman();
    }

    public long getSpentTimeToProcess()
    {
        return spentTimeToProcess;
    }

    public long getFileLines()
    {
        return fileLines;
    }
}
