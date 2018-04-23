package sales_data_analyzer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Salesman.
 */
public class CustomerTest
        extends TestCase
{
    private Customer t;

    protected void setUp()
    {
        t = new Customer();
    }

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CustomerTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( CustomerTest.class );
    }

    private void parserWithExceptionExpected(String recordLine) {
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
        t.parser("002ç12345678901234çName 1çBusiness area 1");
    }

    /**
     * Validating correct format ID.
     */
    public void testWithInvalidFormatId()
    {
        parserWithExceptionExpected("001ç12345678901234çName 1çBusiness area 1");
        parserWithExceptionExpected("003ç12345678901234çName 1çBusiness area 1");
    }

    /**
     * Test with less data than necessary.
     */
    public void testWithLessDataThanNecessary()
    {
        parserWithExceptionExpected("002ç12345678901234çName 1ç");
        parserWithExceptionExpected("002ç12345678901234çName 1");
        parserWithExceptionExpected("002ç12345678901234ç");
        parserWithExceptionExpected("002ç12345678901234");
        parserWithExceptionExpected("002ç");
        parserWithExceptionExpected("002");
    }

    /**
     * Test with more data than necessary.
     */
    public void testWithMoreDataThanNecessary()
    {
        parserWithExceptionExpected("002ç12345678901234çName 1çName 1ç12");
        parserWithExceptionExpected("002ç12345678901234çName 1çName 1çAç");
        parserWithExceptionExpected("002ç12345678901234çName 1çName 1çAç12");
    }

    /**
     * Test validating expected CNPJ.
     */
    public void testValidatingCNPJ() throws RecordInvalidTokenException
    {
        t.parser("002ç12345678901234çName 1çBusiness area 1");
        assertTrue(t.getCNPJ().equals("12345678901234"));
    }

    /**
     * Test validating expected name.
     */
    public void testValidatingName() throws RecordInvalidTokenException
    {
        t.parser("002ç12345678901234çName 1çBusiness area 1");
        assertTrue(t.getName().equals("Name 1"));
    }

    /**
     * Test validating expected business area.
     */
    public void testValidatingBusinessArea() throws RecordInvalidTokenException
    {
        t.parser("002ç12345678901234çName 1çBusiness area 1");
        assertTrue(t.getBusinessArea().equals("Business area 1"));
    }

    /**
     * Test validating the input example.
     */
    public void testValidatingInputExample() throws RecordInvalidTokenException
    {
        t.parser("002ç2345675434544345çJosedaSilvaçRural");
        assertTrue(t.getCNPJ().equals("2345675434544345"));
        assertTrue(t.getName().equals("JosedaSilva"));
        assertTrue(t.getBusinessArea().equals("Rural"));

        t.parser("002ç2345675433444345çEduardoPereiraçRural");
        assertTrue(t.getCNPJ().equals("2345675433444345"));
        assertTrue(t.getName().equals("EduardoPereira"));
        assertTrue(t.getBusinessArea().equals("Rural"));
    }
}
