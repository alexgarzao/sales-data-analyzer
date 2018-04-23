package sales_data_analyzer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for AppConfig.
 */
public class AppConfigTest
        extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppConfigTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppConfigTest.class );
    }

    /**
     * Validating getOutFilename with valid data.
     */
    public void testGetOutFilenameWithValidData()
    {
        assertEquals(AppConfig.getOutFilename("abc.dat"), "./data/out/abc.done.dat");
        assertEquals(AppConfig.getOutFilename("./data/in/abc.dat"), "./data/out/abc.done.dat");
    }

    /**
     * Validating getBkpFilename with valid data.
     */
    public void testGetBkpFilenameWithValidData()
    {
        assertEquals(AppConfig.getBkpFilename("abc.dat"), "./data/proc/abc.dat");
        assertEquals(AppConfig.getBkpFilename("./data/in/abc.dat"), "./data/proc/abc.dat");
    }
}
