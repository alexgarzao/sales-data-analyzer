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
            SalesFile t = new SalesFile("samples/data/in/base.dat", AppConfig.fieldDelimiter);
            t.read();
            assertEquals(t.getTotalSalesman(), 2);
            assertEquals(t.getTotalCustomers(), 2);
            assertEquals(t.getMostExpensiveSaleId(), "10");
            assertEquals(t.getWorstSalesman(), "Renato");
        } catch(RecordInvalidTokenException ex) {
            assertTrue("This file is well formed, but something is wrong!", false);
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
            SalesFile t = new SalesFile("samples/data/in/base_2.dat", AppConfig.fieldDelimiter);
            t.read();
            assertEquals(t.getTotalSalesman(), 2);
            assertEquals(t.getTotalCustomers(), 3);
            assertEquals(t.getMostExpensiveSaleId(), "08");
            assertEquals(t.getWorstSalesman(), "Diego");
        } catch(RecordInvalidTokenException ex) {
            assertTrue("This file is well formed, but something is wrong!", false);
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
            SalesFile t = new SalesFile("samples/data/in/base_3.dat", AppConfig.fieldDelimiter);
            t.read();
            assertEquals(t.getTotalSalesman(), 3);
            assertEquals(t.getTotalCustomers(), 4);
            assertEquals(t.getMostExpensiveSaleId(), "08");
            assertEquals(t.getWorstSalesman(), "Alex");
        } catch(RecordInvalidTokenException ex) {
            assertTrue("This file is well formed, but something is wrong!", false);
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
            SalesFile t = new SalesFile("samples/data/in/base_4.dat", AppConfig.fieldDelimiter);
            t.read();
            assertEquals(t.getTotalSalesman(), 2);
            assertEquals(t.getTotalCustomers(), 4);
            assertEquals(t.getMostExpensiveSaleId(), "10");
            assertEquals(t.getWorstSalesman(), "Renato");
        } catch(RecordInvalidTokenException ex) {
            assertTrue("This file is well formed, but something is wrong!", false);
        } catch(FileNotFoundException e) {
            assertTrue("File must exist to complete the test!", false);
        } catch(IOException e) {
            assertTrue("IO exception!", false);
        }
    }

    /**
     * Interpreting file with empty lines.
     */
    public void testFileWithEmptyLines()
    {
        try {
            SalesFile t = new SalesFile("samples/data/in/base_with_empty_lines.dat", AppConfig.fieldDelimiter);
            t.read();
            assertEquals(t.getTotalSalesman(), 2);
            assertEquals(t.getTotalCustomers(), 2);
            assertEquals(t.getMostExpensiveSaleId(), "10");
            assertEquals(t.getWorstSalesman(), "Renato");
        } catch(RecordInvalidTokenException ex) {
            assertTrue("This file is well formed, but something is wrong!", false);
        } catch(FileNotFoundException e) {
            assertTrue("File must exist to complete the test!", false);
        } catch(IOException e) {
            assertTrue("IO exception!", false);
        }
    }

    /**
     * Empty file.
     */
    public void testEmptyFile()
    {
        try {
            SalesFile t = new SalesFile("samples/data/in/empty_file.dat", AppConfig.fieldDelimiter);
            t.read();
            assertEquals(t.getTotalSalesman(), 0);
            assertEquals(t.getTotalCustomers(), 0);
            assertEquals(t.getMostExpensiveSaleId(), "");
            assertEquals(t.getWorstSalesman(), "");
        } catch(RecordInvalidTokenException ex) {
            assertTrue("This file is well formed, but something is wrong!", false);
        } catch(FileNotFoundException e) {
            assertTrue("File must exist to complete the test!", false);
        } catch(IOException e) {
            assertTrue("IO exception!", false);
        }
    }

    /**
     * Invalid customer record.
     */
    public void testInvalidCustomerRecord()
    {
        try {
            SalesFile t = new SalesFile("samples/data/in/invalid_customer_record.dat", AppConfig.fieldDelimiter);
            t.read();
            assertTrue("Invalid file must trigger an exception", false);
        } catch(RecordInvalidTokenException ex) {
        } catch(FileNotFoundException e) {
            assertTrue("File must exist to complete the test!", false);
        } catch(IOException e) {
            assertTrue("IO exception!", false);
        }
    }

    /**
     * File with DOS EOL .
     */
    public void testFileWithDosEol()
    {
        try {
            SalesFile t = new SalesFile("samples/data/in/base_dos_eol.dat", AppConfig.fieldDelimiter);
            t.read();
            assertEquals(t.getTotalSalesman(), 2);
            assertEquals(t.getTotalCustomers(), 2);
            assertEquals(t.getMostExpensiveSaleId(), "10");
            assertEquals(t.getWorstSalesman(), "Renato");
        } catch(RecordInvalidTokenException ex) {
            assertTrue("This file is well formed, but something is wrong!", false);
        } catch(FileNotFoundException e) {
            assertTrue("File must exist to complete the test!", false);
        } catch(IOException e) {
            assertTrue("IO exception!", false);
        }
    }
}
