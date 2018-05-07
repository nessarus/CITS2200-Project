import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Iterator;
import CITS2200.*;

/**
 * Write a description of class Project here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Project
{
    // instance variables - replace the example below with your own

    /**
     * Constructor for objects of class Project
     */
    public Project()
    {
        // initialise instance variables
    }

    public int degreeCentrality(String path)
    {
        HashMap<Integer,Integer> map = new HashMap();
        Fileman br = new Fileman(path);
        String st;
        int max = -1;
        int maxUser = 0;

        while ((st = br.readline()) != null)
        {
            int leftNode = Integer.parseInt(st.split(" ")[0]);
            int rightNode = Integer.parseInt(st.split(" ")[1]);
            map.put(leftNode,(int) map.getOrDefault(leftNode, 0) + 1);
            map.put(rightNode,(int) map.getOrDefault(rightNode, 0) + 1);

            Iterator it = map.keySet().iterator();
            while(it.hasNext())
            {
                int user = (int) it.next();
                if(max < (int) map.get(user)) 
                {
                    maxUser = user;
                    max = (int) map.get(user);
                }
            }
        }
        return maxUser; 
    }

    public int closenessCentrality(String path)
    {
        HashMap<Integer,Integer> map = new HashMap();
        Fileman br = new Fileman(path);
        String st;
        SearchImp s = new SearchImp();

        while ((st = br.readline()) != null)
        {
            int leftNode = Integer.parseInt(st.split(" ")[0]);
            int rightNode = Integer.parseInt(st.split(" ")[1]);
            map.put(leftNode,(int) map.getOrDefault(leftNode, map.size()));
            map.put(rightNode,(int) map.getOrDefault(rightNode, map.size()));
        }
        int [][] adj = new int[map.size()][map.size()];

        br = new Fileman(path);        
        while ((st = br.readline()) != null)
        {
            int leftNode = Integer.parseInt(st.split(" ")[0]);
            int rightNode = Integer.parseInt(st.split(" ")[1]);
            adj[map.get((int) leftNode)][map.get((int) rightNode)] = 1;
            adj[map.get((int) rightNode)][map.get((int) leftNode)] = 1;
        }
        
        int sum = 0;
        for(int i=0; i<map.size(); i++)
        {
            for(int j : s.getDist(adj, i))
            {
                sum += j;
            }
        }
        return 1/sum;
    }
}

