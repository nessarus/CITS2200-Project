import CITS2200.*;

/**
 * PriorityQueueLinked archives items with the priority.
 * Items removed depend on their priority, with items of 
 * equal priority processed in chronological order.
 *
 * Joshua Ng 20163079, Khang Siang Tay 21884623
 * 30/5/18
 */
public class PriorityQueueLinked
{
    /**
     * An inner class to hold the element, the successor,
     * and the priority
     **/
    private class LinkP
    {
        Object element;
        double priority;
        LinkP next;

        public LinkP(Object e, double p, LinkP n)
        {
            this.element = e;
            this.priority = p;
            this.next = n;
        }
    }

    private LinkP front;

    public PriorityQueueLinked()
    {
        front = null;
    }

    /**
     * test whether the queue is empty
     *
     * @return true if the priority queue is empty, false otherwise
     */
    public boolean isEmpty()
    {
        return front == null;
    }

    /**
     * insert an item at the back into the queue with a given priority
     *
     * @param a - the item to insert
     * @param priority - the priority of the element
     */
    public void enqueue(Object a, double priority) 
    throws IllegalValue
    {
        if(priority < 0)
        {
            throw new IllegalValue("the priority is not in a valid range");
        }

        if(isEmpty() || priority > front.priority)
        {
            front = new LinkP(a, priority, front);
        } else {
            LinkP l = front;
            while(l.next != null && l.next.priority >= priority)
            {
                l = l.next;
            }
            l.next = new LinkP(a, priority, l.next);
        }
    }

    /**
     * examine the item at the front of the queue (the element with the 
     * highest priority that has been in the queue the longest)
     *
     * @return the first item
     */
    public Object examine()
    throws Underflow
    {
        if(!isEmpty()) 
        {
            return front.element;
        } else throw new Underflow("Empty Queue");
    }

    /**
     * remove the item at the front of the queue (the element with the 
     * highest priority that has been there the longest)
     *
     * @return the removed item
     */
    public Object dequeue()
    throws Underflow
    {
        if(!isEmpty())
        {
            Object temp = front.element;
            front = front.next;
            return temp;
        } else throw new Underflow("Empty Queue");
    }

    /**
     * return a DAT.Iterator to examine all the elements in the PriorityQueue
     *
     * @return an Iterator pointing to before the first item
     */
    public Iterator iterator()
    {
        return new PriorityQueueIterator(front);
    }

    private class PriorityQueueIterator implements Iterator
    {
        private Link front;

        public PriorityQueueIterator(LinkP start)
        {
            if(start == null)
            {
                front = null;
                return;
            }
            Link last = new Link(start.element, null);
            front = last;

            LinkP i = start.next;
            while( i != null )
            {
                last.successor = new Link(i.element, null);
                last = last.successor;
                i = i.next;
            }
        }

        /**
         * tests if there is a next item to return
         *
         * @return true if and only if there is a next item
         */
        public boolean hasNext()
        {
            return front != null;
        }

        /**
         * Returns the next element a moves the iterator to the next position.
         *
         * @return the next element in the collection
         */
        public Object next()
        throws OutOfBounds
        {
            if(!hasNext())
            {
                return new OutOfBounds("if there is no next element");
            }

            Object item = front.item;
            front = front.successor;
            return item;
        }
    }
}
