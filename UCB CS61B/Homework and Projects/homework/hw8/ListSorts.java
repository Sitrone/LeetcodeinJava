package com.cs61b.hw8;

/* ListSorts.java */

import com.cs61b.hw8.list.LinkedQueue;
import com.cs61b.hw8.list.QueueEmptyException;

public class ListSorts {

  private final static int SORTSIZE = 100;

  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
 * @throws QueueEmptyException 
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) throws QueueEmptyException {
    // Replace the following line with your solution.
      LinkedQueue queues = new LinkedQueue();
      while(!q.isEmpty())
      {
          LinkedQueue queue = new LinkedQueue();
          queue.enqueue(q.dequeue());
          queues.enqueue(queue);
      }
    return queues;
  }

  /**
   *  mergeSortedQueues() merges two sorted queues into a third.  On completion
   *  of this method, q1 and q2 are empty, and their items have been merged
   *  into the returned queue.
   *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @return a LinkedQueue containing all the Comparable objects from q1 
   *   and q2 (and nothing else), sorted from smallest to largest.
   *  @throws QueueEmptyException 
   **/
    public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) throws QueueEmptyException
    {
        // Replace the following line with your solution.
        LinkedQueue result = new LinkedQueue();
        while (!q1.isEmpty() && !q2.isEmpty())
        {
            int cmp = ((Comparable) (q1.front())).compareTo(q2.front());
            if (cmp > 0)
            {
                result.enqueue(q2.dequeue());
            }
            else
            {
                result.enqueue(q1.dequeue());
            }
        }
        if (q1.isEmpty())
        {
            result.append(q2);
        }
        else
        {
            result.append(q1);
        }
        return result;
    }

  /**
   *  partition() partitions qIn using the pivot item.  On completion of
   *  this method, qIn is empty, and its items have been moved to qSmall,
   *  qEquals, and qLarge, according to their relationship to the pivot.
   *  @param qIn is a LinkedQueue of Comparable objects.
   *  @param pivot is a Comparable item used for partitioning.
   *  @param qSmall is a LinkedQueue, in which all items less than pivot
   *    will be enqueued.
   *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
   *    will be enqueued.
   *  @param qLarge is a LinkedQueue, in which all items greater than pivot
   *    will be enqueued.  
   *  @throws QueueEmptyException 
   **/   
  public static void partition(LinkedQueue qIn, Comparable pivot, 
                               LinkedQueue qSmall, LinkedQueue qEquals, 
                               LinkedQueue qLarge) throws QueueEmptyException 
  {
      // put your code here
      while(!qIn.isEmpty())
      {
          Object cur = qIn.dequeue();
          int cmp = ((Comparable) cur).compareTo(pivot);
          if(cmp < 0)
          {
              qSmall.enqueue(cur);
          }
          else if(cmp > 0)
          {
              qLarge.enqueue(cur);
          }
          else
          {
              qEquals.enqueue(cur);
          }
      }
  }

  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
   *  @throws QueueEmptyException 
   **/
    public static void mergeSort(LinkedQueue q) throws QueueEmptyException
    {
        // put your code here
        LinkedQueue queues = makeQueueOfQueues(q);
        while(queues.size() > 1)
        {
            LinkedQueue q1 = (LinkedQueue) queues.dequeue();
            LinkedQueue q2 = (LinkedQueue) queues.dequeue();
            queues.enqueue(mergeSortedQueues(q1, q2));
        }
        q.append((LinkedQueue) queues.dequeue());
    }

  /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
   *  @throws QueueEmptyException 
   **/
    public static void quickSort(LinkedQueue q) throws QueueEmptyException
    {
     // put your code here
        if (q.size() <= 1) return ;
        
        Object pivot = q.nth((int) (q.size() * Math.random() + 1));
        LinkedQueue small = new LinkedQueue();
        LinkedQueue equal = new LinkedQueue();
        LinkedQueue large = new LinkedQueue();
        
        partition(q, (Comparable) pivot, small, equal, large);
        quickSort(small);
        quickSort(large);
        
        q.append(small);
        q.append(equal);
        q.append(large);
    }

  /**
   *  makeRandom() builds a LinkedQueue of the indicated size containing
   *  Integer items.  The items are randomly chosen between 0 and size - 1.
   *  @param size is the size of the resulting LinkedQueue.
   **/
    public static LinkedQueue makeRandom(int size)
    {
        LinkedQueue q = new LinkedQueue();
        for (int i = 0; i < size; i++)
        {
            q.enqueue(new Integer((int) (size * Math.random())));
        }
        return q;
    }

  /**
   *  main() performs some tests on mergesort and quicksort.  Feel free to add
   *  more tests of your own to make sure your algorithms works on boundary
   *  cases.  Your test code will not be graded.
 * @throws QueueEmptyException 
   **/
  public static void main(String [] args) throws QueueEmptyException {

    LinkedQueue q = makeRandom(10);
    System.out.println(q.toString());
    mergeSort(q);
    System.out.println(q.toString());

    q = makeRandom(10);
    System.out.println(q.toString());
    quickSort(q);
    System.out.println(q.toString());

//     Remove these comments for Part III.
    int size = SORTSIZE;
    for(int i = 1; i < 8; i++)
    {
        Timer stopWatch = new Timer();
        q = makeRandom(size);
        stopWatch.start();
        mergeSort(q);
        stopWatch.stop();
        System.out.println("Mergesort time, " + size + " Integers:  " +
                           stopWatch.elapsed() + " msec.");

        stopWatch.reset();
        q = makeRandom(size);
        stopWatch.start();
        quickSort(q);
        stopWatch.stop();
        System.out.println("Quicksort time, " + size + " Integers:  " +
                           stopWatch.elapsed() + " msec.");
        size *= 10;
        
    }
  }

}
