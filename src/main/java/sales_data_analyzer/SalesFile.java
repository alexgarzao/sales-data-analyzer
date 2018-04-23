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
* SalesFile class is responsible to parser and keep the stats data.
*
* @author  Alex S. Garzão
*/
public class SalesFile
{
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private String inFilename;
    private String outFilename;
    private String bkpFilename;
    private Salesman salesmanData = new Salesman();
    private Customer customerData = new Customer();
    private Sales salesData = new Sales();
    private long spentTimeToProcess;
    private long fileLines;

    /**
    * SalesFile is responsible to define the file to be parsed.
    */
    public SalesFile(String inFilename, String outFilename, String bkpFilename)
    {
        this.inFilename = inFilename;
        this.outFilename = outFilename;
        this.bkpFilename = bkpFilename;
    }

    /**
    * mapAllRecordTypes is responsible to map all record types.
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

    /**
    * process is responsible to extract all the data from a file.
    */
    public void process() throws FileNotFoundException, IOException, RecordInvalidTokenException
    {
        long startTime = System.currentTimeMillis();

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
            try {
                String formatId = recordLine.substring(0, 3);
                Record record = recordTypes.get(formatId);
                record.parser(recordLine);
                if (formatId.equals("001")) {
                    salesData.addNewSalesman(salesmanData.getName());
                }
            } catch(Exception ex) {
                throw new RecordInvalidTokenException();
            }

            recordLine = in.readLine();
        }

        bf.close();
        in.close();

        spentTimeToProcess = System.currentTimeMillis() - startTime;

        saveFileStats();

        doFileBackup();

        logStats();
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

    /**
    * TODO is responsible to return the worst salesman.
    *
    * Format: 099çTotalSalesMançTotalCustomersçMostExpensiveSaleIdçWorstSalesman
    * @return the worst salesman.
    */
    public void saveFileStats() throws IOException {
        StringBuilder str = new StringBuilder("099");
        str.append("ç" + getTotalSalesman());
        str.append("ç" + getTotalCustomers());
        str.append("ç" + getMostExpensiveSaleId());
        str.append("ç" + getWorstSalesman());
        str.append('\n');

        BufferedWriter writer = new BufferedWriter(new FileWriter(outFilename));
        writer.write(str.toString());

        writer.close();
    }

    private void doFileBackup()
    {
        Path source = Paths.get(inFilename);
        Path target = Paths.get(bkpFilename);

        try {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            LOGGER.severe("When trying to move the file: " + ex.toString());
        }
    }

    private void logStats()
    {
        LOGGER.info(String.format(
            "File: %s TimeToProcess: %d Lines: %d TotalSalesman: %d TotalCustomers: %d TotalSales: %d",
            inFilename, getSpentTimeToProcess(), getFileLines(), getTotalSalesman(), getTotalCustomers(), getTotalSales()));
    }

    private long getSpentTimeToProcess()
    {
        return spentTimeToProcess;
    }

    private long getFileLines()
    {
        return fileLines;
    }
}
