import CITS2200.*;

/**
 * A Cyclic Linked Implementation of a Queue.
 *
 * @author Joshua Ng
 * @version 22.03.2018
 */
public class QueueLinked implements Queue
{
    Link last;

    /**
     * Constructor for objects of class QueueLinked
     */
    public QueueLinked()
    {
        last = null;
    }

    /**
     * insert an item at the back of the queue
     *
     * @param  a  the item to insert
     */
    public void enqueue(Object a)
    {
        if(isEmpty())
        {
            last = new Link(a, null);
            last.successor = last;
        } 
        else 
        {
            Link temp = last.successor;
            last.successor = new Link(a, temp);
            last = last.successor;
        }
    }

    /**
     * remove the item at the front of the queue
     *
     * @return    the removed item
     * @exception Underflow if the queue is empty
     */
    public Object dequeue() throws Underflow
    {
        if(isEmpty())
        {
            throw new Underflow("queue is empty");
        }
        Object a = last.successor.item;
        if(last.successor == last)
        {
            last = null;
        }
        else
        {
            last.successor = last.successor.successor;
        }
        return a;
    }

    /**
     * examine the item at the front of the queue
     *
     * @return    the first item
     * @exception Underflow if the queue is empty
     */
    public Object examine() throws Underflow
    {
        if(isEmpty())
        {
            throw new Underflow("examining empty queue");
        }
        return last.successor.item;
    }

    /**
     * test whether the queue is empty
     *
     * @return    true if the queue is empty, false otherwise
     */
    public boolean isEmpty()
    {
        return last == null;
    }

}
