package sales_data_analyzer;

import java.util.HashMap;
import java.util.Map;


/**
* Salesman class is responsible to parser and keep the data.
*
* @author  Alex S. Garzão
* @version 0.1
*/
public class Sales extends Record
{
    private String saleId;
    private String salesman;
    private float totalSale;

    private float mostExpensiveSaleTotal;
    private String mostExpensiveSaleId;

    private Map<String, Float> salesPerSeller = new HashMap<String, Float>();

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
        salesman = "";
        totalSale = 0;

        if (super.parser(data) == false) {
            return false;
        }

        saleId = tokens[1];
        String items = tokens[2];
        salesman = tokens[3];

        totalSale = calcTotalSaleFromItems(items);

        if (totalSale > mostExpensiveSaleTotal) {
            mostExpensiveSaleTotal = totalSale;
            mostExpensiveSaleId = saleId;
        }

        float salesmanTotal = salesPerSeller.getOrDefault(salesman, 0f);
        salesmanTotal += totalSale;
        salesPerSeller.put(salesman, salesmanTotal);

        return true;
    }

    // [ItemID-ItemQuantity-ItemPrice]
    // Example: [1-10-100,2-30-2.50,3-40-3.10]
    private float calcTotalSaleFromItems(String items)
    {
        if (items.charAt(0) != '[') {
            return 0;
        }

        if (items.charAt(items.length() - 1) != ']') {
            return 0;
        }

        float totalSale = 0;
        String itemList[] = items.substring(1, items.length() - 2).split(",");

        for (int itemNumber = 0; itemNumber < itemList.length; itemNumber++) {
            String tokens[] = itemList[itemNumber].split("-");
            if (tokens.length != 3) {
                return 0;
            }

            float quantity = Float.parseFloat(tokens[1]);
            float value = Float.parseFloat(tokens[2]);

            totalSale += (quantity * value);
        }

        return totalSale;

    }

    public String getSaleId()
    {
        return saleId;
    }

    public float getTotalSale()
    {
        return totalSale;
    }

    public String getSalesman()
    {
        return salesman;
    }

    public String getMostExpensiveSaleId()
    {
        return mostExpensiveSaleId;
    }

    public String getWorstSalesman()
    {
        float worstTotal = Float.MAX_VALUE;
        String salesman = "";

        for (Map.Entry<String, Float> pair : salesPerSeller.entrySet()) {
            if (pair.getValue() < worstTotal) {
                worstTotal = pair.getValue();
                salesman = pair.getKey();
            }
        }

        return salesman;
    }
}
