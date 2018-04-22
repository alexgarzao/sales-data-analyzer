package sales_data_analyzer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * Unit test for SalesFile.
 */
public class SalesFileTest
        extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SalesFileTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SalesFileTest.class );
    }

    /**
     * Interpreting basic example 1.
     */
    public void testInterpretingBasicExample1()
    {
        try {
            SalesFile t = new SalesFile("samples/data/in/base.dat", "samples/data/out/base.done.dat", "samples/data/proc/base.dat");
            t.process();
            assertEquals(t.getTotalSalesman(), 2);
            assertEquals(t.getTotalClients(), 2);
            assertEquals(t.getMostExpensiveSaleId(), "10");
            assertEquals(t.getWorstSalesman(), "Renato");
        } catch(FileNotFoundException e) {
            assertTrue("File must exist to complete the test!", false);
        } catch(IOException e) {
            assertTrue("IO exception!", false);
        }
    }

    /**
     * Interpreting basic example 2.
     */
    public void testInterpretingBasicExample2()
    {
        try {
            SalesFile t = new SalesFile("samples/data/in/base_2.dat", "samples/data/out/base_2.done.dat", "samples/data/proc/base_2.dat");
            t.process();
            assertEquals(t.getTotalSalesman(), 2);
            assertEquals(t.getTotalClients(), 3);
            assertEquals(t.getMostExpensiveSaleId(), "08");
            assertEquals(t.getWorstSalesman(), "Diego");
        } catch(FileNotFoundException e) {
            assertTrue("File must exist to complete the test!", false);
        } catch(IOException e) {
            assertTrue("IO exception!", false);
        }
    }

    /**
     * Interpreting example where a salesman doesn't have sales.
     */
    public void testSalesmanWithoutSales()
    {
        try {
            SalesFile t = new SalesFile("samples/data/in/base_3.dat", "samples/data/out/base_3.done.dat", "samples/data/proc/base_3.dat");
            t.process();
            assertEquals(t.getTotalSalesman(), 3);
            assertEquals(t.getTotalClients(), 4);
            assertEquals(t.getMostExpensiveSaleId(), "08");
            assertEquals(t.getWorstSalesman(), "Alex");
        } catch(FileNotFoundException e) {
            assertTrue("File must exist to complete the test!", false);
        } catch(IOException e) {
            assertTrue("IO exception!", false);
        }
    }

    /**
     * Interpreting example where a salesman has more than one sale.
     */
    public void testSalesmanWithTwoSales()
    {
        try {
            SalesFile t = new SalesFile("samples/data/in/base_4.dat", "samples/data/out/base_4.done.dat", "samples/data/proc/base_4.dat");
            t.process();
            assertEquals(t.getTotalSalesman(), 2);
            assertEquals(t.getTotalClients(), 4);
            assertEquals(t.getMostExpensiveSaleId(), "10");
            assertEquals(t.getWorstSalesman(), "Renato");
        } catch(FileNotFoundException e) {
            assertTrue("File must exist to complete the test!", false);
        } catch(IOException e) {
            assertTrue("IO exception!", false);
        }
    }

    // TODO: example with a lot of data.
}
