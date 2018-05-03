import java.io.*;
import java.net.*;
import java.nio.file.*;

/**
 * Write a description of class Read here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Fileman
{
    private BufferedReader br;
    
    /**
     * Constructor for objects of class Read
     */
    public Fileman(String path)
    {
        File file = new File(path);
        try {
            br = new BufferedReader(new FileReader(file));
        } catch(IOException e)
        {
            System.err.println("Caught IOException: " + e.getMessage());
        }
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public String readline()
    {
        try {
            return br.readLine();
        } catch(IOException e)
        {
            System.err.println("Caught IOException: " + e.getMessage());
            return null;
        }
    }
}
