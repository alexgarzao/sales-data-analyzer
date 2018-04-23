package sales_data_analyzer;

/**
* Salesman class is responsible to parser and keep the salesman data.
*
* @author  Alex S. Garzão
*/
public class Salesman extends Record
{
    private static final String RECORD_ID = "001";
    private static final int FIELDS_COUNT = 4;

    private String CPF;
    private String name;
    private double salary;

    public Salesman()
    {
        super(RECORD_ID, FIELDS_COUNT);
    }

    /**
    * parser is responsible to extract the data from a file line.
    *
    * @param data contains the data to be parsed. Example: "001çCPFçNameçSalary"
    * @return true if the data could be parsed,
    *         false otherwise.
    */
    public void parser(String data) throws RecordInvalidTokenException
    {
        CPF = "";
        name = "";
        salary = 0;

        super.parser(data);

        CPF = tokens[1];
        name = tokens[2];

        try {
            salary = Double.parseDouble(tokens[3]);
        } catch (NumberFormatException e) {
            throw new RecordInvalidTokenException();
        }
    }

    public String getCPF()
    {
        return CPF;
    }

    public String getName()
    {
        return name;
    }

    public double getSalary()
    {
        return salary;
    }
}
