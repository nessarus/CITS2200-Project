import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.lang.*;

/**
 * Joshua Ng 20163079, Khang Siang Tay 21884623
 * 30/5/18
 * Project takes in a file as standard in.
 * For each component, it returns the top 5 nodes according to each centrality measures
 * If two nodes have the same value, it'll return both the nodes
 */
public class Project
{
    private int [] indexNode; //index of node
    private int [] nodeEdgeNum; //number of edges on each node
    private int [][] adj; // adjacency matrix
    private int [] components;
    private int component = 0;

    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);
        LinkedList list = new LinkedList();
        
        //read through file and parse nodes for variable arrays
        while (sc.hasNextLine())
        {
            String st = sc.nextLine();
            String []temp = st.split(" "); 
            String st2 = temp[1] + " " + temp[0]; //checks for duplicate strings by swapping nodes
            if(list.contains(st) == false && list.contains(st2)==false) //avoids duplicates
            {
                list.add(st);
            }
        }

        Project prog = new Project(list);
        double alpha = Double.parseDouble(args[0]);

        ArrayList [] degCen = prog.degreeCentrality();
        System.out.println("Degree Centrality:");
        for (int i = 0; i < prog.getComponent(); i++)
        {   
            System.out.println("Component :" + (i+1));
            for (Object j : degCen[i])
            {          
                System.out.println(j);
            }        
        }

        ArrayList [] closeCen = prog.closenessCentrality();
        System.out.println("Closeness Centrality:");
        for (int i = 0; i < prog.getComponent(); i++)
        {   
            System.out.println("Component :" + (i+1));
            for (Object j : closeCen[i])
            {          
                System.out.println(j);
            }        
        }

        ArrayList [] betCen = prog.betweennessCentrality();
        System.out.println("Betweenness Centrality:");
        for (int i = 0; i < prog.getComponent(); i++)
        {   
            System.out.println("Component :" + (i+1));
            for (Object j : betCen[i])
            {          
                System.out.println(j);
            }        
        }

        ArrayList [] katzCen = prog.katzCentral(alpha);
        System.out.println("Katz Centrality: (alpha=" + alpha + ")");
        for (int i = 0; i < prog.getComponent(); i++)
        {   
            System.out.println("Component :" + (i+1));
            for (Object j : katzCen[i])
            {          
                System.out.println(j);
            }        
        }
    }

    public int getComponent()
    {
        return component;
    }

    /**
     * Constructor for objects of class Project
     */
    public Project(LinkedList list)
    {
        HashMap<Integer, Integer> nodeIndex = new HashMap();

        LinkedList list2 = (LinkedList) list.clone();

        //read through file and parse nodes for variable arrays
        while (list.isEmpty() == false)
        {
            String st = (String) list.pop();

            int leftNode = Integer.parseInt(st.split(" ")[0]);
            int rightNode = Integer.parseInt(st.split(" ")[1]);

            //indexing nodes
            nodeIndex.put(leftNode,nodeIndex.getOrDefault(leftNode, nodeIndex.size()));
            nodeIndex.put(rightNode,nodeIndex.getOrDefault(rightNode, nodeIndex.size()));
        }

        indexNode = new int[nodeIndex.size()];
        //converting (node to index) to (index to node)
        for(Object i : nodeIndex.keySet())
        {
            indexNode[nodeIndex.get(i)] = (int) i;
        }

        nodeEdgeNum = new int[indexNode.length];
        adj = new int[indexNode.length][indexNode.length];

        //read through file and parse nodes for static arrays
        //counting of number of edges that a node is connected to is done here
        while (list2.isEmpty() == false)
        {
            String st = (String) list2.pop();
            int leftNode = Integer.parseInt(st.split(" ")[0]);
            int rightNode = Integer.parseInt(st.split(" ")[1]);

            //counting number of edges per node
            nodeEdgeNum[nodeIndex.get(leftNode)]++;
            nodeEdgeNum[nodeIndex.get(rightNode)]++;

            //building adjacency matrix
            adj[nodeIndex.get(leftNode)][nodeIndex.get(rightNode)] = 1;
            adj[nodeIndex.get(rightNode)][nodeIndex.get(leftNode)] = 1;
        }

        // split into components
        components = new int [indexNode.length]; 
        SearchImp search = new SearchImp();

        int negative = 0;
        int start = 0;
        do 
        {
            component++;
            negative = 0;
            int [] bfs = search.getDist(adj, start);
            for(int i=0; i<bfs.length; i++)
            {
                if(components[i] != 0)
                {
                    continue;
                }
                if(bfs[i] != -1)
                {
                    components[i] = component;
                }
                else
                {
                    start = i;
                    negative++;
                }
            }

        } while(negative != 0);
    }

     /**
     * Method degreeCentrality
     *
     * @return an array of arraylist 
     * each arraylist contains the top 5 nodes with highest degree value
     * each element in the array represents a different component
     */
    public ArrayList [] degreeCentrality()
    {
        double[] edgeCount = new double [nodeEdgeNum.length];
        for (int i = 0; i < nodeEdgeNum.length; i++)
        {
            edgeCount[i] = nodeEdgeNum[i];
        }
        ArrayList[] comp = getMax(edgeCount);

        return comp; 
    }

    // returns the closest node using breath first search.
    /**
     * Method closenessCentrality
     *
     * @return an array of arraylist 
     * each arraylist contains the top 5 nodes with highest closeness value
     * each element in the array represents a different component
     */
    public ArrayList [] closenessCentrality()
    {
        SearchImp search = new SearchImp();
        int [] sum = new int[indexNode.length];

        // find the shortest paths per node to every other node
        for(int i=0; i<indexNode.length; i++)
        {
            int [] bfs = search.getDist(adj, i);
            
            // sum the paths for each search
            for(int j=0; j<indexNode.length; j++)
            {
                if(j!=i)
                {
                    sum[j] += bfs[j];
                }
            }
        }

        // changes the far to close
        double [] close = new double[indexNode.length];
        for(int i=0; i<sum.length; i++)
        {
            close[i] = 1.0 / sum[i];
        }

        // return the largest value in close array     
        ArrayList[] comp = getMax(close);

        return comp; 
    }

     /**
     * Method betweennessCentrality
     *
     * @return an array of arraylist 
     * each arraylist contains the top 5 nodes with highest betweenness value
     * each element in the array represents a different component
     */
    public ArrayList[] betweennessCentrality()
    {
        Stack S;
        QueueLinked Q;
        LinkedList<Integer>[] P = new LinkedList[indexNode.length]; //each vertex has a list of their parents
        int[] d; //distance
        int[] sig; //shortest path

        if(indexNode.length < 1)
        {
            return null; 
        }

        double[] bc = new double[indexNode.length]; // Betweenness centrality
        for(int s=0; s<indexNode.length; s++)
        {
            S = new Stack();
            for(int w=0; w<indexNode.length; w++)
            {
                P[w] = new LinkedList<Integer>();
            }
            sig = new int[indexNode.length];
            sig[s] = 1;
            d = new int[indexNode.length];
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
                for(int w=0; w<indexNode.length; w++)
                {
                    // if w is not a neighbor of v, continue.
                    if(w==v || adj[w][v]==0)
                    {
                        continue;
                    }
                    // w found for the first time?
                    if(d[w] < 0)
                    {
                        Q.enqueue(w);
                        d[w] = d[v] + 1;
                    }
                    // shortest path to w via v?
                    if(d[w] == d[v] +1)
                    {
                        sig[w] = sig[w] + sig[v];
                        P[w].add(v);
                    }
                }
            }

            double [] del = new double[indexNode.length];
            // S returns vertices in order of non-increasing distance from s
            while(S.empty() == false)
            {
                int w = (int) S.pop();
                for(Object i : P[w].toArray())
                {
                    int v = (int) i;
                    del[v] = del[v] + (double) sig[v]/sig
                    [w]*(1 + del[w]);
                }
                if(w != s)
                {
                    bc[w] = bc[w] + del[w];
                }
            }
        }
        ArrayList[] comp = getMax(bc);

        return comp;        
    }

    /**
     * Method katzCentral
     *
     * @param alpha - number between 0 - 1
     * @return an array of arraylist 
     * each arraylist contains the top 5 nodes with highest Katz value
     * each element in the array represents a different component
     */
    public ArrayList[] katzCentral(double alpha) 
    {
        SearchImp search = new SearchImp();
        double [] sum = new double[indexNode.length];

        // find the shortest paths per node to every other node
        for(int i=0; i<indexNode.length; i++)
        {
            int [] k = search.getDist(adj, i); //array of hops counts

            for (int j = 0; j < k.length; j++)
            {
                if (k[j] == -1 )
                {
                    continue;
                }
                sum[i] += Math.pow(alpha,k[j]+1)*adjCount(j);
            }
        }
        ArrayList[] comp = getMax(sum);

        return comp;   
    }

    private int adjCount(int v) //returns the number of nodes connected to node v
    {   
        int count = 0;

        for (int i = 0; i < adj.length; i++)
        {
            if(i == v)
            {
                continue;
            }
            count += adj[v][i];
        }

        return count;
    }

    /**returns and array which contains an arraylist
     * each array represents a different component 
     * each arraylist contains the node ID of the top 5 max values of the list
     */
    private ArrayList[] getMax(double[] list) 
    {
        double [] copy = list.clone();
        Arrays.sort(copy);
        double [] topFive = new double[component+1];
        int [] count = new int[component+1];

        //gets the value of the 5th largest element in the list for each component
        for (int j = 1; j<component+1; j++)
        {
            for (int i = copy.length-1; i > -1; i--)
            {
                if (j==components[i])
                {
                    count[j]++;
                    if(count[j]==5) 
                    {
                        topFive[j] = copy[i];
                    }
                }
                if(count[j]>5) 
                {
                    break;
                }
            }
        }

        ArrayList [] comp = new ArrayList[component];

        //adds nodeID into the list when values are larger or equal to the 5 largest value
        for(int j=1; j<component+1; j++)
        {
            ArrayList<Integer> comp_array = new ArrayList();
            PriorityQueueLinked maxUser = new PriorityQueueLinked();
            //use a priority queue to sort the index of the 5 highest values

            for(int i=0; i<list.length; i++)
            {
                if(topFive[j] <= list[i] && components[i] == j) 
                {
                    maxUser.enqueue(i, list[i]);
                }
            }
            while (maxUser.isEmpty() == false)
            {
                comp_array.add(indexNode[ (int) maxUser.dequeue()]);
            }
            comp[j-1] = comp_array;
        }

        return comp;
    }
}
