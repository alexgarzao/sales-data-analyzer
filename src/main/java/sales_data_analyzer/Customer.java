package sales_data_analyzer;

/**
* Customer class is responsible to parser the customer data.
*
* @author  Alex S. Garzão
*/
public class Customer extends Record
{
    private static final String RECORD_ID = "002";
    private static final int FIELDS_COUNT = 4;

    private String CNPJ;
    private String name;
    private String businessArea;

    public Customer(String fieldDelimiter)
    {
        super(RECORD_ID, FIELDS_COUNT, fieldDelimiter);
    }

    /**
    * parser is responsible to extract the data from a file line.
    *
    * @param data contains the data to be parsed. Example: "002çCNPJçNameçBusinessArea"
    * @return true if the data could be parsed,
    *         false otherwise.
    */
    public void parser(String data)
        throws RecordInvalidTokenException
    {
        final int CNPJ_INDEX = 1;
        final int NAME_INDEX = 2;
        final int BUSINESS_AREA_INDEX = 3;

        CNPJ = "";
        name = "";
        businessArea = "";

        super.parser(data);

        CNPJ = tokens[CNPJ_INDEX];
        name = tokens[NAME_INDEX];
        businessArea = tokens[BUSINESS_AREA_INDEX];
    }

    /**
    * Get CNPJ.
    */
    public String getCNPJ()
    {
        return CNPJ;
    }

    /**
    * Get name.
    */
    public String getName()
    {
        return name;
    }

    /**
    * Get business area.
    */
    public String getBusinessArea()
    {
        return businessArea;
    }
}
