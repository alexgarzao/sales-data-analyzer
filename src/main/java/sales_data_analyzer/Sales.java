package sales_data_analyzer;

import java.util.HashMap;
import java.util.Map;

/**
* Sales class is responsible to parser and keep the sales data.
*
* @author  Alex S. Garzão
*/
public class Sales extends Record
{
    private String saleId;
    private String salesman;
    private double totalSale;

    private double mostExpensiveSaleTotal = 0d;
    private String mostExpensiveSaleId = "";

    private Map<String, Double> salesPerSeller = new HashMap<String, Double>();

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
    public void parser(String data) throws RecordInvalidTokenException
    {
        saleId = "";
        salesman = "";
        totalSale = 0;

        super.parser(data);

        saleId = tokens[1];
        String items = tokens[2];
        salesman = tokens[3];

        totalSale = calcTotalSaleFromItems(items);

        if (totalSale > mostExpensiveSaleTotal) {
            mostExpensiveSaleTotal = totalSale;
            mostExpensiveSaleId = saleId;
        }

        double salesmanTotal = salesPerSeller.getOrDefault(salesman, 0d);
        salesmanTotal += totalSale;
        salesPerSeller.put(salesman, salesmanTotal);
    }

    // [ItemID-ItemQuantity-ItemPrice]
    // Example: [1-10-100,2-30-2.50,3-40-3.10]
    private double calcTotalSaleFromItems(String items)
    {
        if (items.charAt(0) != '[') {
            return 0;
        }

        if (items.charAt(items.length() - 1) != ']') {
            return 0;
        }

        double totalSale = 0d;
        String itemList[] = items.substring(1, items.length() - 2).split(",");

        for (int itemNumber = 0; itemNumber < itemList.length; itemNumber++) {
            String tokens[] = itemList[itemNumber].split("-");
            if (tokens.length != 3) {
                return 0;
            }

            double quantity = Double.parseDouble(tokens[1]);
            double value = Double.parseDouble(tokens[2]);

            totalSale += (quantity * value);
        }

        return totalSale;
    }

    public String getSaleId()
    {
        return saleId;
    }

    public double getTotalSale()
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

    public void addNewSalesman(String salesmanName)
    {
        salesPerSeller.putIfAbsent(salesmanName, 0d);
    }

    public String getWorstSalesman()
    {
        double worstTotal = Double.MAX_VALUE;
        String salesman = "";

        for (Map.Entry<String, Double> pair : salesPerSeller.entrySet()) {
            if (pair.getValue() < worstTotal) {
                worstTotal = pair.getValue();
                salesman = pair.getKey();
            }
        }

        return salesman;
    }
}
