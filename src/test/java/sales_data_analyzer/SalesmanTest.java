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
        Salesman t = new Salesman();
        assertTrue(t.parser("001ç12345678901çName 1ç123456.78"));
    }

    /**
     * Validating correct format ID.
     */
    public void testWithInvalidFormatId()
    {
        Salesman t = new Salesman();
        assertFalse(t.parser("002ç12345678901çName 1ç123456.78"));
    }

    /**
     * Test with less data than necessary.
     */
    public void testWithLessDataThanNecessary()
    {
        Salesman t = new Salesman();
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
        Salesman t = new Salesman();
        assertFalse(t.parser("001ç12345678901çName 1ç123456.78ç12"));
        assertFalse(t.parser("001ç12345678901çName 1ç123456.78çAç"));
        assertFalse(t.parser("001ç12345678901çName 1ç123456.78çAç12"));
    }

    /**
     * Test validating expected CPF.
     */
    public void testValidatingCPF()
    {
        Salesman t = new Salesman();
        assertTrue(t.parser("001ç12345678901çName 1ç123456.78"));
        assertTrue(t.getCPF().equals("12345678901"));
    }

    /**
     * Test validating expected name.
     */
    public void testValidatingName()
    {
        Salesman t = new Salesman();
        assertTrue(t.parser("001ç12345678901çName 1ç123456.78"));
        assertTrue(t.getName().equals("Name 1"));
    }

    /**
     * Test validating expected salary.
     */
    public void testValidatingSalary()
    {
        Salesman t = new Salesman();
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
        Salesman t = new Salesman();
        assertFalse(t.parser("001ç12345678901çName 1ç123A45"));
        assertFalse(t.parser("001ç12345678901çName 1ç123,45"));
    }
}
