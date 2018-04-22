package sales_data_analyzer;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;


/**
* SalesFile class is responsible to parser and keep the stats data.
*
* @author  Alex S. Garzão
* @version 0.1
*/
public class SalesFile
{
    private String filename;
    private Salesman salesmanData = new Salesman();
    private Customer customerData = new Customer();
    private Sales salesData = new Sales();

    /**
    * SalesFile is responsible to define the file to be parsed.
    */
    public SalesFile(String filename)
    {
        this.filename = filename;
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
    public void process() throws FileNotFoundException, IOException
    {
        BufferedInputStream bf = new BufferedInputStream(new FileInputStream(filename));
        BufferedReader in = new BufferedReader(new InputStreamReader(bf, StandardCharsets.UTF_8));

        Map<String, Record> recordTypes = mapAllRecordTypes();

        String recordLine = in.readLine();

        while(recordLine != null) {
            String formatId = recordLine.substring(0, 3);
            Record record = recordTypes.get(formatId);
            // TODO: and if the formatId doesn't exist?
            record.parser(recordLine);
            recordLine = in.readLine();
        }

        in.close();
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
    * getTotalClientes is responsible to return the total of clients.
    *
    * @return the total of salesman.
    */
    public int getTotalClients()
    {
        return customerData.getTotal();
    }

    /**
    * getFilename is responsible to returns the filename.
    *
    * @return the filename.
    */
    public String getFilename()
    {
        return filename;
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
}
