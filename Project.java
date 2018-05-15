import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

/**
 * Write a description of class Project here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Project
{
    private HashMap<Integer, Integer> nodeIndex;
    private int [] nodeEdgeNum;
    private int [][] adj;
    
    /**
     * Constructor for objects of class Project
     */
    public Project(String path)
    {
        nodeIndex = new HashMap();
        Fileman br = new Fileman(path);
        String st;
        
        //read through file and parse nodes for variable arrays
        while ((st = br.readline()) != null)
        {
            int leftNode = Integer.parseInt(st.split(" ")[0]);
            int rightNode = Integer.parseInt(st.split(" ")[1]);
            
            //indexing nodes
            nodeIndex.put(leftNode,nodeIndex.getOrDefault(leftNode, nodeIndex.size()));
            nodeIndex.put(rightNode,nodeIndex.getOrDefault(rightNode, nodeIndex.size()));
        }
        
        nodeEdgeNum = new int[nodeIndex.size()];
        adj = new int[nodeIndex.size()][nodeIndex.size()];
        br = new Fileman(path);
        
        //read through file and parse nodes for static arrays
        while ((st = br.readline()) != null)
        {
            int leftNode = Integer.parseInt(st.split(" ")[0]);
            int rightNode = Integer.parseInt(st.split(" ")[1]);
            
            //counting number of edges per node
            nodeEdgeNum[nodeIndex.get(leftNode)]++;
            nodeEdgeNum[nodeIndex.get(rightNode)]++;

            //building adjacency matrix
            adj[nodeIndex.get(leftNode)][nodeIndex.get(rightNode)] = 1;
            adj[nodeIndex.get(rightNode)][nodeIndex.get(leftNode)] = 1;
        }
    }

    public int degreeCentrality()
    {
        int max = -1;
        int maxUser = -1;
        
        for(int i=0; i<nodeEdgeNum.length; i++)
        {
            if(max < nodeEdgeNum[i]) 
            {
                maxUser = nodeIndex.get(i);
                max = nodeEdgeNum[i];
            }
        }
        return maxUser; 
    }

    // returns the array of sums of the shortest path over each node using breath first search.
    public double [] closenessCentrality()
    {
        SearchImp search = new SearchImp();
        int [] sum = new int[nodeIndex.size()];
        
        // find the shortest paths per node to every other node
        for(int i=0; i<nodeIndex.size()-1; i++)
        {
            int [] bfs = search.getDist(adj, i);
            
            // sum the paths for each search
            for(int j=i; j<nodeIndex.size(); j++)
            {
                sum[j] += bfs[j];
            }
        }

        // changes the far to close
        double [] close = new double[nodeIndex.size()];
        for(int i=0; i<sum.length; i++)
        {
            close[i] = 1.0 / sum[i];
        }

        return close;
    }

    
    public int [] betweennessCentrality()
    {
        Stack S;
        QueueLinked Q;
        ArrayList<Integer>[] P = 
            new ArrayList[nodeIndex.size()]; //each vertex has a list of their parents
        int[] d; //distance
        int[] sig; //shortest path
        
        if(nodeIndex.size() < 1) return null; 
        int[] bc = new int[nodeIndex.size()]; // Betweenness centrality
        for(int s=0; s<nodeIndex.size(); s++)
        {
            S = new Stack();
            for(int w=0; w<nodeIndex.size(); w++)
            {
                P[w] = new ArrayList<Integer>();
            }
            sig = new int[nodeIndex.size()];
            sig[s] = 1;
            d = new int[nodeIndex.size()];
            for(int i=0; i<d.length; i++)
            {
                d[i] = -1;
            }
            d[s] = 0;
            Q = new QueueLinked();
            Q.enqueue(s);
            while(Q.isEmpty() == false)
            {
                int v = (int) Q.dequeue();
                S.push(v);
                for(int w=0; w<nodeIndex.size(); w++)
                {
                    // if w is not a neighbor of v, continue.
                    if(w==v || adj[w][v]==0)
                    {
                        continue;
                    }
                    
                    
                }
            }
            
            
            

            
            
            
            
            
        }
        return null;
    }
}

