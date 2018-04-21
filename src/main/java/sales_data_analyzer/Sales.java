package sales_data_analyzer;

/**
* Salesman class is responsible to parser and keep the data.
*
* @author  Alex S. Garzão
* @version 0.1
*/
public class Sales extends Record
{
    private String saleId;
    private String items;
    private String salesman;

    public Sales()
    {
        super("003", 4);
    }

    /**
    * parser is responsible to extract the data from a file line.
    *
    * @param data contains the data to be parsed. Example: "003çSaleIDç[ItemID-ItemQuantity-ItemPrice]çSalesmanname"
    * @return true if the data could be parsed,
    *         false otherwise.
    */
    public boolean parser(String data)
    {
        saleId = "";
        items = "";
        salesman = "";

        if (super.parser(data) == false) {
            return false;
        }

        saleId = tokens[1];
        items = tokens[2];
        salesman = tokens[3];

        // TODO: parser items...
        //
        // try {
        //     salary = Float.parseFloat(tokens[3]);
        // } catch (NumberFormatException e) {
        //     return false;
        // }

        return true;
    }

    public String getSaleId()
    {
        return saleId;
    }

    public String getItems()
    {
        return items;
    }

    public String getSalesman()
    {
        return salesman;
    }
}
