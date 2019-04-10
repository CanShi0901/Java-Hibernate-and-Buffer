/**********************************************************************w***a******l******r*******us***********
 * File: CircularBuffer.java
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

/**
 *
 * Description: Implements the {@link com.algonquincollege.cst8277.assignment2.Buffer} interface using a CircularBuffer </br>
 *
 * @date  (modified) 2019 02 20
 *
 * @author Nan Jiang 040-886-436, Can Shi 040-806-036
 *
 * @param <E> the element type held in the buffer
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.invoke.MethodHandles;

public class CircularBuffer<E> implements Buffer<E> {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
    protected E[] bufArray; // buffer
    private int occupiedBuffers = 0; // count number of buffers used
    private int writeIndex = 0; // index to write next value
    private int readIndex = 0; // index to read next value

    /**
     * Constructor builds a buffer of the specified size
     * @param capacity
     */
    @SuppressWarnings("unchecked")
    public CircularBuffer(int capacity) {
        bufArray = (E[]) new Object[capacity];      
    }

    @Override
    /**
     * Add element to buffer (thread-safe); if no room, block
     * @param element
     */
    public synchronized void blockingPut(E element) throws InterruptedException {

           while (occupiedBuffers == bufArray.length)
           {
              logger.info( "All buffers full. Producer waits." );
              wait();
           } 
               bufArray[ writeIndex ] = element; // set new buffer value                  
               writeIndex = ( writeIndex + 1 ) % bufArray.length; // update circular write index    
               occupiedBuffers++; // one more buffer element is full
               logger.info( "Producer writes " + element.toString());
               notify();
    }

    @Override
    /**
     * Remove element from buffer (thread-safe); if none, block
     *
     * @return element
     */
    public synchronized E blockingGet() throws InterruptedException {

        E readValue = null; // initialize value read from buffer

           while ( occupiedBuffers == 0 )
           {
              logger.info( "All buffers empty. Consumer waits." );
              wait();
           } 
               readValue = bufArray[ readIndex ]; // read value from buffer             
               readIndex = ( readIndex + 1 ) % bufArray.length; // update circular read index   
               occupiedBuffers--; // one more buffer element is empty
               logger.info( "Consumer reads " + readValue.toString() );
               notify();
           return readValue;
    }
}