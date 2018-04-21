package sales_data_analyzer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Salesman.
 */
public class SalesTest
        extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SalesTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SalesTest.class );
    }

    /**
     * Basic test with valid data.
     */
    public void testWithValidData()
    {
        Sales t = new Sales();
        assertTrue(t.parser("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego"));
        assertTrue(t.parser("003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato"));
    }

    /**
     * Validating correct format ID.
     */
    public void testWithInvalidFormatId()
    {
        Sales t = new Sales();
        assertFalse(t.parser("001ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego"));
        assertFalse(t.parser("002ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego"));
    }

    /**
     * Test with less data than necessary.
     */
    public void testWithLessDataThanNecessary()
    {
        Sales t = new Sales();
        assertFalse(t.parser("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]ç"));
        assertFalse(t.parser("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]"));
        assertFalse(t.parser("003ç10ç"));
        assertFalse(t.parser("003ç10"));
        assertFalse(t.parser("003ç"));
        assertFalse(t.parser("003"));
    }

    /**
     * Test with more data than necessary.
     */
    public void testWithMoreDataThanNecessary()
    {
        Sales t = new Sales();
        assertFalse(t.parser("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiegoçA"));
        assertFalse(t.parser("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiegoçAç"));
        assertFalse(t.parser("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiegoçAç12"));
    }

    /**
     * Test validating expected sale ID.
     */
    public void testValidatingSaleId()
    {
        Sales t = new Sales();
        assertTrue(t.parser("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego"));
        assertTrue(t.getSaleId().equals("10"));
    }

    /**
     * Test validating expected items.
     */
    public void testValidatingItems()
    {
        Sales t = new Sales();
        assertTrue(t.parser("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego"));
        assertTrue(t.getItems().equals("[1-10-100,2-30-2.50,3-40-3.10]"));
    }

    /**
     * Test validating expected salesman.
     */
    public void testValidatingSalesman()
    {
        Sales t = new Sales();
        assertTrue(t.parser("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego"));
        assertTrue(t.getSalesman().equals("Diego"));
    }

    /**
     * Test validating the input example.
     */
    public void testValidatingInputExample()
    {
        Sales t = new Sales();
        assertTrue(t.parser("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego"));
        assertTrue(t.getSaleId().equals("10"));
        assertTrue(t.getItems().equals("[1-10-100,2-30-2.50,3-40-3.10]"));
        assertTrue(t.getSalesman().equals("Diego"));

        assertTrue(t.parser("003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato"));
        assertTrue(t.getSaleId().equals("08"));
        assertTrue(t.getItems().equals("[1-34-10,2-33-1.50,3-40-0.10]"));
        assertTrue(t.getSalesman().equals("Renato"));
    }
}
