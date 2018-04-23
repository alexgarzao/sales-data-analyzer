package sales_data_analyzer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Salesman.
 */
public class SalesmanTest
        extends TestCase
{
    private Salesman t;

    protected void setUp()
    {
        t = new Salesman();
    }

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SalesmanTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SalesmanTest.class );
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
        t.parser("001ç12345678901çName 1ç123456.78");
    }

    /**
     * Validating correct format ID.
     */
    public void testWithInvalidFormatId()
    {
        parserWithExceptionExpected("002ç12345678901çName 1ç123456.78");
        parserWithExceptionExpected("003ç12345678901çName 1ç123456.78");
    }

    /**
     * Test with less data than necessary.
     */
    public void testWithLessDataThanNecessary()
    {
        parserWithExceptionExpected("001ç12345678901çName 1ç");
        parserWithExceptionExpected("001ç12345678901çName 1");
        parserWithExceptionExpected("001ç12345678901ç");
        parserWithExceptionExpected("001ç12345678901");
        parserWithExceptionExpected("001ç");
        parserWithExceptionExpected("001");
    }

    /**
     * Test with more data than necessary.
     */
    public void testWithMoreDataThanNecessary()
    {
        parserWithExceptionExpected("001ç12345678901çName 1ç123456.78ç12");
        parserWithExceptionExpected("001ç12345678901çName 1ç123456.78çAç");
        parserWithExceptionExpected("001ç12345678901çName 1ç123456.78çAç12");
    }

    /**
     * Test validating expected CPF.
     */
    public void testValidatingCPF() throws RecordInvalidTokenException
    {
        t.parser("001ç12345678901çName 1ç123456.78");
        assertEquals(t.getCPF(), "12345678901");
    }

    /**
     * Test validating expected name.
     */
    public void testValidatingName() throws RecordInvalidTokenException
    {
        t.parser("001ç12345678901çName 1ç123456.78");
        assertEquals(t.getName(), "Name 1");
    }

    /**
     * Test validating expected salary.
     */
    public void testValidatingSalary() throws RecordInvalidTokenException
    {
        t.parser("001ç12345678901çName 1ç123456.78");
        assertTrue(t.getSalary() == 123456.78f);

        t.parser("001ç12345678901çName 1ç12345");
        assertTrue(t.getSalary() == 12345f);

        t.parser("001ç12345678901çName 1ç12345");
        assertTrue(t.getSalary() == 12345);
    }

    /**
     * Test with invalid salary format.
     */
    public void testWithInvalidSalary()
    {
        parserWithExceptionExpected("001ç12345678901çName 1ç123A45");
        parserWithExceptionExpected("001ç12345678901çName 1ç123,45");
    }

    /**
     * Test validating the input example.
     */
    public void testValidatingInputExample() throws RecordInvalidTokenException
    {
        t.parser("001ç1234567891234çDiegoç50000");
        assertEquals(t.getCPF(), "1234567891234");
        assertEquals(t.getName(), "Diego");
        assertEquals(t.getSalary(), 50000f);

        t.parser("001ç3245678865434çRenatoç40000.99");
        assertEquals(t.getCPF(), "3245678865434");
        assertEquals(t.getName(), "Renato");
        assertEquals(t.getSalary(), 40000.99f);
    }
}
