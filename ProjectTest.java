

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class ProjectTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ProjectTest
{
    /**
     * Default constructor for test class ProjectTest
     */
    public ProjectTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }


    @Test
    public void test1()
    {
        Project project1 = new Project();
        project1.degreeCentrality("short1.txt");
        
    }
    
    @Test
    public void test2()
    {
        Project project1 = new Project();
        project1.closenessCentrality("test.txt");
        
    }
}


