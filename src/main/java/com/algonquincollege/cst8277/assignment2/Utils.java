/**********************************************************************w***a******l******r*******us***********
 * File: Utils.java
 * Course materials (19W) CST 8277
 * @author (original) Mike Norman
 */
package com.algonquincollege.cst8277.assignment2;

import java.util.Scanner;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 *
 * <b>Description</b></br></br>
 * Helper class that holds miscellaneous utility methods
 *
 * @date 2019 02
 *
 * @author mwnorman
 *
 */
public class Utils {

    //Note - in Java 9+, Scanner will have a method tokens​() that will return Steam<String>; for now ...
    public static Stream<String> buildStreamFromScanner(Scanner scanner) {
        Spliterator<String> spliterator = Spliterators.spliteratorUnknownSize(scanner,
            Spliterator.IMMUTABLE | Spliterator.ORDERED |
            Spliterator.NONNULL);
        Stream<String> streamFromScanner = StreamSupport.stream(spliterator, false);
        return streamFromScanner;
      }

}