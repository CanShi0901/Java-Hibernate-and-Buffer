/**********************************************************************w***a******l******r*******us***********
 * File: BlockingQueueBuffer.java
 * Course materials (19W) CST 8277
 * @author (original) Mike Norman, derived from code by Deitel & Associates, Inc.
 *         (Notes: Fig. 23.18: CircularBuffer.java Synchronizing access to a shared bounded buffer)
 *
 * (C) Copyright 1992-2015 by Deitel & Associates, Inc. and
 * Pearson Education,Inc.
 * All Rights Reserved.
 *
 * DISCLAIMER: The authors and publisher of this book have used their
 * best efforts in preparing the book. These efforts include the
 * development, research, and testing of the theories and programs to determine their
 * effectiveness. The authors and publisher make no warranty of any kind,
 * expressed or implied, with regard to these programs or to the
 * documentation contained in these books. The authors and publisher
 * shall not be liable in any event for incidental or
 * consequential damages in connection with, or arising out of, the
 * furnishing, performance, or use of these programs.
 *
 *************************************************************************/
package com.algonquincollege.cst8277.assignment2;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.ArrayBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: Implements the {@link com.algonquincollege.cst8277.assignment2.Buffer} interface using {@link ArrayBlockingQueue}
 *
 * @date  (modified) 2019 02 20
 *
 * @author Nan Jiang 040-886-436, Can Shi 040-806-036
 *
 * @param <E> the element type held in the buffer
 */
public class BlockingQueueBuffer<E> implements Buffer<E> {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    protected ArrayBlockingQueue<E> bufArray; // buffer


    /**
     * Constructor builds a buffer of the specified size
     * @param capacity
     */
    public BlockingQueueBuffer(int capacity) {
        bufArray = new ArrayBlockingQueue<E>(capacity);
    }

    /**
     * Add element to buffer (thread-safe)
     * @param element
     */
    public void blockingPut(E element) throws InterruptedException {

        bufArray.put(element); // set new buffer value
        logger.info( "Producer writes " + element.toString());
    }

    /**
     * Remove element from buffer (thread-safe)
     *
     * @return element
     */
    public E blockingGet() throws InterruptedException {
        
        E readValue = null; // initialize value read from buffer
        readValue = (E) bufArray.take(); // read value from buffer

        logger.info( "Consumer reads " + readValue.toString() );

        return readValue;
    }
}
