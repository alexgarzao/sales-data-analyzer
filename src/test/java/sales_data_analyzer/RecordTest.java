package sales_data_analyzer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Record.
 */
public class RecordTest
        extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RecordTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( RecordTest.class );
    }

    /**
     * Test validating the input example.
     */
    public void testValidatingInputExample()
    {
        Record t = new Record("001", 4);
        assertTrue(t.parser("001ç1234567891234çDiegoç50000"));
        assertTrue(t.parser("001ç3245678865434çRenatoç40000.99"));

        t = new Record("002", 4);
        assertTrue(t.parser("002ç2345675434544345çJosedaSilvaçRural"));
        assertTrue(t.parser("002ç2345675433444345çEduardoPereiraçRural"));

        t = new Record("003", 4);
        assertTrue(t.parser("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego"));
        assertTrue(t.parser("003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato"));
    }

    /**
     * Validating correct format ID.
     */
    public void testWithInvalidFormatId()
    {
        Record t = new Record("001", 4);
        assertFalse(t.parser("002ç1234567891234çDiegoç50000"));
        assertFalse(t.parser("003ç3245678865434çRenatoç40000.99"));

        t = new Record("002", 4);
        assertFalse(t.parser("001ç2345675434544345çJosedaSilvaçRural"));
        assertFalse(t.parser("003ç2345675433444345çEduardoPereiraçRural"));

        t = new Record("003", 4);
        assertFalse(t.parser("001ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego"));
        assertFalse(t.parser("002ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato"));
    }

    /**
     * Test with less data than necessary.
     */
    public void testWithLessDataThanNecessary()
    {
        Record t = new Record("001", 4);
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
        Record t = new Record("001", 4);
        assertFalse(t.parser("001ç12345678901çName 1ç123456.78ç12"));
        assertFalse(t.parser("001ç12345678901çName 1ç123456.78çAç"));
        assertFalse(t.parser("001ç12345678901çName 1ç123456.78çAç12"));
    }

    /**
     * Test parser length.
     */
    public void testParserLength()
    {
        Record t = new Record("001", 4);
        assertTrue(t.parser("001ç1234567891234çDiegoç50000"));
        assertTrue(t.length() == 4);

        t = new Record("001", 3);
        assertTrue(t.parser("001ç1234567891234çDiegoç"));
        assertTrue(t.length() == 3);
        assertTrue(t.parser("001ç1234567891234çDiego"));
        assertTrue(t.length() == 3);

        t = new Record("001", 2);
        assertTrue(t.parser("001ç1234567891234ç"));
        assertTrue(t.length() == 2);
        assertTrue(t.parser("001ç1234567891234"));
        assertTrue(t.length() == 2);

        t = new Record("001", 1);
        assertTrue(t.parser("001ç"));
        assertTrue(t.length() == 1);
        assertTrue(t.parser("001"));
        assertTrue(t.length() == 1);
    }

    /**
     * Test token data.
     */
    public void testTokenData() throws RecordInvalidTokenException
    {
        Record t = new Record("001", 4);
        assertTrue(t.parser("001ç1234567891234çDiegoç50000"));
        assertTrue(t.length() == 4);
        try {
            assertTrue(t.getToken(0).equals("001"));
            assertTrue(t.getToken(1).equals("1234567891234"));
            assertTrue(t.getToken(2).equals("Diego"));
            assertTrue(t.getToken(3).equals("50000"));
        } catch (RecordInvalidTokenException e) {
            throw e;
        }
    }

    /**
     * Test invalid token exception.
     */
    public void testInvalidTokenException()
    {
        Record t = new Record("001", 4);
        assertTrue(t.parser("001ç1234567891234çDiegoç50000"));
        assertTrue(t.length() == 4);

        try {
            t.getToken(4);
            assertTrue("RecordInvalidTokenException not occurred!", false);
        } catch (RecordInvalidTokenException e) {
        }

        try {
            t.getToken(-1);
            assertTrue("RecordInvalidTokenException not occurred!", false);
        } catch (RecordInvalidTokenException e) {
        }
    }
}
