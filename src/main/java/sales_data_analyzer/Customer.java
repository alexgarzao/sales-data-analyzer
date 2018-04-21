package sales_data_analyzer;

/**
* Customer class is responsible to parser and keep the data.
*
* @author  Alex S. Garzão
* @version 0.1
*/
public class Customer extends Record
{
    String CNPJ;
    String name;
    String businessArea;

    public Customer()
    {
        super("002", 4);
    }

    /**
    * parser is responsible to extract the data from a file line.
    *
    * @param data contains the data to be parsed. Example: "002çCNPJçNameçBusinessArea"
    * @return true if the data could be parsed,
    *         false otherwise.
    */
    public boolean parser(String data)
    {
        CNPJ = "";
        name = "";
        businessArea = "";

        if (super.parser(data) == false) {
            return false;
        }

        CNPJ = tokens[1];
        name = tokens[2];
        businessArea = tokens[3];

        return true;
    }

    public String getCNPJ()
    {
        return CNPJ;
    }

    public String getName()
    {
        return name;
    }

    public String getBusinessArea()
    {
        return businessArea;
    }
}
