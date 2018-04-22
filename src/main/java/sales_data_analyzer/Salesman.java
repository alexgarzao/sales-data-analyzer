package sales_data_analyzer;

/**
* Salesman class is responsible to parser and keep the salesman data.
*
* @author  Alex S. Garzão
* @version 0.1
*/
public class Salesman extends Record
{
    private String CPF;
    private String name;
    private float salary;

    public Salesman()
    {
        super("001", 4);
    }

    /**
    * parser is responsible to extract the data from a file line.
    *
    * @param data contains the data to be parsed. Example: "001çCPFçNameçSalary"
    * @return true if the data could be parsed,
    *         false otherwise.
    */
    public boolean parser(String data)
    {
        CPF = "";
        name = "";
        salary = 0;

        if (super.parser(data) == false) {
            return false;
        }

        CPF = tokens[1];
        name = tokens[2];

        try {
            salary = Float.parseFloat(tokens[3]);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public String getCPF()
    {
        return CPF;
    }

    public String getName()
    {
        return name;
    }

    public float getSalary()
    {
        return salary;
    }
}
