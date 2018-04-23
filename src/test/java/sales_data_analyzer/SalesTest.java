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
    private Sales t;

    /**
     * Test setup.
     */
    protected void setUp()
    {
        t = new Sales();
    }

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
     * Assert that a RecordInvalidTokenException will happen.
     */
    private void assertRecordInvalidTokenException(String recordLine) {
        try {
            t.parser(recordLine);
            assertTrue("Must be an invalid record!", false);
        } catch(RecordInvalidTokenException ex) {
        }
    }

    /**
     * Basic test with valid data.
     */
    public void testWithValidData() throws RecordInvalidTokenException
    {
        t.parser("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego");
        t.parser("003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato");
    }

    /**
     * Validating correct format ID.
     */
    public void testWithInvalidFormatId()
    {
        assertRecordInvalidTokenException("001ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego");
        assertRecordInvalidTokenException("002ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego");
    }

    /**
     * Test with less data than necessary.
     */
    public void testWithLessDataThanNecessary()
    {
        assertRecordInvalidTokenException("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]ç");
        assertRecordInvalidTokenException("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]");
        assertRecordInvalidTokenException("003ç10ç");
        assertRecordInvalidTokenException("003ç10");
        assertRecordInvalidTokenException("003ç");
        assertRecordInvalidTokenException("003");
    }

    /**
     * Test with more data than necessary.
     */
    public void testWithMoreDataThanNecessary()
    {
        assertRecordInvalidTokenException("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiegoçA");
        assertRecordInvalidTokenException("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiegoçAç");
        assertRecordInvalidTokenException("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiegoçAç12");
    }

    /**
     * Test validating expected sale ID.
     */
    public void testValidatingSaleId() throws RecordInvalidTokenException
    {
        t.parser("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego");
        assertEquals(t.getSaleId(), "10");
    }

    /**
     * Test validating total sale.
     */
    public void testValidatingTotalSale() throws RecordInvalidTokenException
    {
        t.parser("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego");
        assertEquals(t.getTotalSale(), 10*100.0d + 30*2.50d + 40*3.10d);
    }

    /**
     * Test validating expected salesman.
     */
    public void testValidatingSalesman() throws RecordInvalidTokenException
    {
        t.parser("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego");
        assertTrue(t.getSalesman().equals("Diego"));
    }

    /**
     * Test validating the input example.
     */
    public void testValidatingInputExample() throws RecordInvalidTokenException
    {
        t.parser("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego");
        assertTrue(t.getSaleId().equals("10"));
        assertEquals(t.getTotalSale(), 10*100.0d + 30*2.50d + 40*3.10d);
        assertTrue(t.getSalesman().equals("Diego"));

        t.parser("003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato");
        assertTrue(t.getSaleId().equals("08"));
        assertEquals(t.getTotalSale(), 34*10.0d + 33*1.50d + 40*0.10d);
        assertTrue(t.getSalesman().equals("Renato"));
    }
}
