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
* SalesFile class is responsible to read, parser and keep the stats data.
*
* @author  Alex S. Garzão
* @version 0.1
*/
public class SalesFile
{
    private String filename;
    private BufferedReader in;
    private Salesman salesmanData = new Salesman();
    private Customer customerData = new Customer();
    private Sales salesData = new Sales();

    public SalesFile(String filename)
    {
        this.filename = filename;
    }

    private void open() throws FileNotFoundException
    {
        BufferedInputStream bf = new BufferedInputStream(new FileInputStream(filename));
        in = new BufferedReader(new InputStreamReader(bf, StandardCharsets.UTF_8));
    }

    private void close() throws IOException
    {
        in.close();
    }

    /**
    * readLine is responsible to read a line from the file.
    *
    * @return line readed.
    */
    private String readLine() throws IOException
    {
        return in.readLine();
    }

    private Map<String, Record> registryAllRecordTypes()
    {
        Map<String, Record> recordTypes = new HashMap<String, Record>();
        recordTypes.put(salesmanData.getId(), salesmanData);
        recordTypes.put(customerData.getId(), customerData);
        recordTypes.put(salesData.getId(), salesData);

        return recordTypes;
    }

    public void process() throws FileNotFoundException, IOException
    {
        open();

        Map<String, Record> recordTypes = registryAllRecordTypes();
        String recordLine = readLine();
        while(recordLine != null) {
            Record record = recordTypes.get(recordLine.substring(0, 3));
            record.parser(recordLine);
            recordLine = readLine();
        }

        close();
    }

    public int getTotalSalesman()
    {
        return salesmanData.getTotal();
    }

    public int getTotalClients()
    {
        return customerData.getTotal();
    }

    public String getFilename()
    {
        return filename;
    }

    public String getMostExpensiveSaleId()
    {
        return salesData.getMostExpensiveSaleId();
    }

    public String getWorstSalesman()
    {
        return salesData.getWorstSalesman();
    }
}
