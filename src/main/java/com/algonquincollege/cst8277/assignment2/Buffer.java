/**********************************************************************w***a******l******r*******us***********
 * File:        Buffer.java
 * @author (original) Mike Norman, derived from code by Deitel & Associates, Inc.
 * Course:      CST8277
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
 * <b>Description</b></br></br>
 * This interface describes the methods for a thread-safe buffer
 *
 * @date 2019 02
 *
 * @author mwnorman
 *
 */
public interface Buffer<E> {

    /**
     * A method to add an Element to the buffer.
     *
     * @param element - the object to be added.
     * @throws InterruptedException - if the method is interuppted while wait()-ing.
     */
    public void blockingPut(E element) throws InterruptedException;

    /**
     * A method to get an Element from the buffer.
     *
     * @return - the next object in the buffer.
     * @throws InterruptedException - if the method is interuppted while wait()-ing.
     */
    public E blockingGet() throws InterruptedException;

}