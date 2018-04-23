package sales_data_analyzer;

/**
* Record class is responsible to parser data records.
*
* @author  Alex S. Garzão
*/
public class Record
{
    private String recordId;
    private int dataNumber;
    private String fieldsDelimiter;
    protected String[] tokens;
    private int total;

    public Record(String recordId, int dataNumber, String fieldsDelimiter)
    {
        this.recordId = recordId;
        this.dataNumber = dataNumber;
        this.fieldsDelimiter = fieldsDelimiter;
    }

    /**
    * parser is responsible to extract the data from a file line.
    *
    * @param data contains the data to be parsed.
    * @return true if the data could be parsed,
    *         false otherwise.
    */
    public void parser(String data) throws RecordInvalidTokenException
    {
        tokens = data.split(fieldsDelimiter);

        if (tokens.length != dataNumber) {
            throw new RecordInvalidTokenException();
        }

        if (tokens[0].equals(recordId) == false) {
            throw new RecordInvalidTokenException();
        }

        total++;
    }

    public int length()
    {
        return tokens.length;
    }

    public String getToken(int index) throws RecordInvalidTokenException
    {
        if (index < 0 || index > length() - 1) {
            throw new RecordInvalidTokenException(Integer.toString(index));
        }

        return tokens[index];
    }

    public String getId()
    {
        return recordId;
    }

    public int getTotal()
    {
        return total;
    }
}
