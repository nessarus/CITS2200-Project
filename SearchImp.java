import CITS2200.*;

/**
 * SearchImp proves the implementation using methods based on a 
 * Breadth First Search and Depth First Search over a directed Graph.
 *
 * Joshua Ng 20163079, Khang Siang Tay 21884623
 * 30/5/18
 */
public class SearchImp implements Search
{
    private static final int WHITE = 0;
    private static final int GREY = 1;
    private static final int BLACK = 2;
    private static final int DISCOVERY = 0;
    private static final int FINISH = 1;

    private int[][] t;
    private int[][] em;
    private int[] parent;
    private int[] colour;
    private int time;

    /**
     * Runs a BFS on a given directed, unweighted graph to 
     * find the distances of vertices from the start vertex.
     *
     * @param em - the matrix to be searched
     * @param startVertex - the vertex on which to start the search
     * @return an array listing the distance of each vertex from 
     * the start vertex of each, or -1 is the vertex is not reachable 
     * from the start vertex.
     */
    public int[] getAllShortDist(int[][] em, int startVertex)
    {

        colour = new int[em.length];
        QueueLinked q = new QueueLinked();
        colour[startVertex] = GREY;
        parent = new int[em.length];

        int[] distance = new int[em.length];
        for(int i=0; i<distance.length; i++) { distance[i] = -1;}
        distance[startVertex] = 0;
        q.enqueue(startVertex);
        int [] count = new int[em.length];

        while(!q.isEmpty())
        {
            int w = (int) q.dequeue();
            for(int x=0; x<em[w].length; x++)
            {
                if(w!=x && colour[x]==WHITE && em[w][x]==1)
                {
                    distance[x] = distance[w] + 1;
                    parent[x] = w;
                    q.enqueue(x);
                }
            }
            colour[w] = BLACK;
        }

        return distance;
    }
    
    /**
     * Runs a BFS on a given directed, unweighted graph to 
     * find the distances of vertices from the start vertex.
     *
     * @param em - the matrix to be searched
     * @param startVertex - the vertex on which to start the search
     * @return an array listing the distance of each vertex from 
     * the start vertex of each, or -1 is the vertex is not reachable 
     * from the start vertex.
     */
    public int[] getDist(int[][] em, int startVertex)
    {

        colour = new int[em.length];
        QueueLinked q = new QueueLinked();
        colour[startVertex] = GREY;
        parent = new int[em.length];

        int[] distance = new int[em.length];
        for(int i=0; i<distance.length; i++) { distance[i] = -1;}
        distance[startVertex] = 0;
        q.enqueue(startVertex);

        while(!q.isEmpty())
        {
            int w = (int) q.dequeue();
            for(int x=0; x<em[w].length; x++)
            {
                if(w!=x && colour[x]==WHITE && em[w][x]==1)
                {
                    distance[x] = distance[w] + 1;
                    parent[x] = w;
                    colour[x] = GREY;
                    q.enqueue(x);
                }
            }
            colour[w] = BLACK;
        }

        return distance;
    }

    /**
     * Runs a BFS on a given directed, unweighted graph.
     *
     * @param g - the Graph to be Searched
     * @param start - the vertex on which to start the search
     * @return an array listing the parent of each vertex in 
     *  the spanning tree, or -1 is the vertex is not reachable 
     *  from the start vertex.
     */
    public int[] getConnectedTree(Graph g, int startVertex)
    {
        em = g.getEdgeMatrix();
        QueueLinked q = new QueueLinked();
        q.enqueue(startVertex);
        colour = new int[g.getNumberOfVertices()];
        parent = new int[g.getNumberOfVertices()];
        colour[startVertex] = GREY;
        for(int i=0; i<parent.length; i++) { parent[i] = -1;} 

        while(!q.isEmpty())
        {
            int w = (int) q.dequeue();
            for(int x=0; x<em[w].length; x++)
            {
                if(w!=x && colour[x]==WHITE && em[w][x]==1)
                {
                    parent[x] = w;
                    colour[x] = GREY;
                    q.enqueue(x);
                }
            }
            colour[w] = BLACK;
        }
        return parent;
    }

    /**
     * Runs a BFS on a given directed, unweighted graph to 
     * find the distances of vertices from the start vertex.
     *
     * @param g - the Graph to be searched
     * @param startVertex - the vertex on which to start the search
     * @return an array listing the distance of each vertex from 
     * the start vertex of each, or -1 is the vertex is not reachable 
     * from the start vertex.
     */
    public int[] getDistances(Graph g, int startVertex)
    {
        em = g.getEdgeMatrix();
        colour = new int[g.getNumberOfVertices()];
        QueueLinked q = new QueueLinked();
        colour[startVertex] = GREY;
        parent = new int[g.getNumberOfVertices()];
        //for(int i=0; i<parent.length; i++) { parent[i] = -1;}
        int[] distance = new int[g.getNumberOfVertices()];
        for(int i=0; i<distance.length; i++) { distance[i] = -1;}
        distance[startVertex] = 0;
        q.enqueue(startVertex);

        while(!q.isEmpty())
        {
            int w = (int) q.dequeue();
            for(int x=0; x<em[w].length; x++)
            {
                if(w!=x && colour[x]==WHITE && em[w][x]==1)
                {
                    distance[x] = distance[w] + 1;
                    parent[x] = w;
                    colour[x] = GREY;
                    q.enqueue(x);
                }
            }
            colour[w] = BLACK;
        }

        return distance;
    }

    /**
     * Runs a DFS on a given directed, unweighted graph to find the 
     * start time and finish time for each vertex
     *
     * @param g - the Graph to be searched
     * @param startVertex - the vertex on which to start the search
     * @return a 2-diimensional array, where each sub-array has two 
     * elements: the first is the start time, the second is the end time.
     */
    public int[][] getTimes(Graph g, int startVertex)
    {
        t = new int[g.getNumberOfVertices()][2];
        em = g.getEdgeMatrix();
        parent = new int[g.getNumberOfVertices()];
        colour = new int[g.getNumberOfVertices()];
        for(int i=0; i<t.length; i++) { t[i][DISCOVERY] = -1; }
        for(int i=0; i<t.length; i++) { t[i][FINISH] = -1; }
        time = 0;

        getTimes(startVertex);

        return t;
    }

    private void getTimes(int w)
    {
        colour[w] = GREY;
        t[w][DISCOVERY] = time;
        time++;

        for(int x=0; x<em[w].length; x++)
        {
            if(w!=x && colour[x] == WHITE && em[w][x]==1)
            {
                parent[x] = w;
                getTimes(x);
            }
        }
        colour[w] = BLACK;
        t[w][FINISH] = time;
        time++;
    }

}
