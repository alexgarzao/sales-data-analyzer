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

    /**
     * Basic test with valid data.
     */
    public void testWithValidData()
    {
        assertTrue(t.parser("001ç12345678901çName 1ç123456.78"));
    }

    /**
     * Validating correct format ID.
     */
    public void testWithInvalidFormatId()
    {
        assertFalse(t.parser("002ç12345678901çName 1ç123456.78"));
        assertFalse(t.parser("003ç12345678901çName 1ç123456.78"));
    }

    /**
     * Test with less data than necessary.
     */
    public void testWithLessDataThanNecessary()
    {
        assertFalse(t.parser("001ç12345678901çName 1ç"));
        assertFalse(t.parser("001ç12345678901çName 1"));
        assertFalse(t.parser("001ç12345678901ç"));
        assertFalse(t.parser("001ç12345678901"));
        assertFalse(t.parser("001ç"));
        assertFalse(t.parser("001"));
    }

    /**
     * Test with more data than necessary.
     */
    public void testWithMoreDataThanNecessary()
    {
        assertFalse(t.parser("001ç12345678901çName 1ç123456.78ç12"));
        assertFalse(t.parser("001ç12345678901çName 1ç123456.78çAç"));
        assertFalse(t.parser("001ç12345678901çName 1ç123456.78çAç12"));
    }

    /**
     * Test validating expected CPF.
     */
    public void testValidatingCPF()
    {
        assertTrue(t.parser("001ç12345678901çName 1ç123456.78"));
        assertTrue(t.getCPF().equals("12345678901"));
    }

    /**
     * Test validating expected name.
     */
    public void testValidatingName()
    {
        assertTrue(t.parser("001ç12345678901çName 1ç123456.78"));
        assertTrue(t.getName().equals("Name 1"));
    }

    /**
     * Test validating expected salary.
     */
    public void testValidatingSalary()
    {
        assertTrue(t.parser("001ç12345678901çName 1ç123456.78"));
        assertTrue(t.getSalary() == 123456.78f);

        assertTrue(t.parser("001ç12345678901çName 1ç12345"));
        assertTrue(t.getSalary() == 12345f);

        assertTrue(t.parser("001ç12345678901çName 1ç12345"));
        assertTrue(t.getSalary() == 12345);
    }

    /**
     * Test with invalid salary format.
     */
    public void testWithInvalidSalary()
    {
        assertFalse(t.parser("001ç12345678901çName 1ç123A45"));
        assertFalse(t.parser("001ç12345678901çName 1ç123,45"));
    }

    /**
     * Test validating the input example.
     */
    public void testValidatingInputExample()
    {
        assertTrue(t.parser("001ç1234567891234çDiegoç50000"));
        assertTrue(t.getCPF().equals("1234567891234"));
        assertTrue(t.getName().equals("Diego"));
        assertTrue(t.getSalary() == 50000);

        assertTrue(t.parser("001ç3245678865434çRenatoç40000.99"));
        assertTrue(t.getCPF().equals("3245678865434"));
        assertTrue(t.getName().equals("Renato"));
        assertTrue(t.getSalary() == 40000.99f);
    }
}
