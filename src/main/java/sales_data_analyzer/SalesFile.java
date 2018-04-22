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
    private Map<String, Record> recordTypes;

    public SalesFile(String filename)
    {
        this.filename = filename;
        recordTypes = new HashMap<String, Record>();
    }

    public void registryRecordType(Record record)
    {
        recordTypes.put(record.getId(), record);
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

    public void process() throws FileNotFoundException, IOException
    {
        open();

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
        return recordTypes.get("001").getTotal();
    }

    public int getTotalClients()
    {
        return recordTypes.get("002").getTotal();
    }

    public String getFilename()
    {
        return filename;
    }

    public String getMostExpensiveSaleId()
    {
        Sales sale = (Sales)recordTypes.get("003");
        return sale.getMostExpensiveSaleId();
    }

    public String getWorstSalesman()
    {
        Sales sale = (Sales)recordTypes.get("003");
        return sale.getWorstSalesman();
    }
}
