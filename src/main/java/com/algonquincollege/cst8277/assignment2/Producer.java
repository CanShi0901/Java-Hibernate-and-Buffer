/**********************************************************************w***a******l******r*******us***********
 * File: Producer.java
 * Course materials (19W) CST 8277
 * @author (original) Mike Norman
 */
package com.algonquincollege.cst8277.assignment2;

import java.time.Duration;
import java.time.Instant;

import org.slf4j.Logger;

/**
 *
 * <b>Description</b></br></br>
 *
 * Producer interface and (new with Java 8+) default method code for the {@link #run()} method </br>
 * that sets the name of a Producer thread to {@value #PRODUCER_THREAD_NAME_FMT} and calls the {@link #produce()} method </br>
 * <p>
 * Additionally, two statistics are calculated: elapsed time and number of elements produced
 *
 * @see <a href="https://en.wikipedia.org/wiki/Producer–consumer_problem">https://en.wikipedia.org/wiki/Producer–consumer_problem</a>
 *
 * @author mwnorman
 * @date 2019 02
 *
 * @param <T> the element type processed by a Producer thread
 */
public interface Producer<T> extends Runnable {

    /**
     * {@value #PRODUCER_THREAD_NAME_FMT} is the {@link java.util.Formatter printf-style} format string for the name of the Producer thread
     * where the hex value is the {@link java.lang.System#identityHashCode identityHashCode} of the thread
     */
    final static String PRODUCER_THREAD_NAME_FMT = "%#x-Producer";

    /**
     * {@link #STATISTICS_MSG_FMT} is a <a href="https://www.slf4j.org/faq.html#string_contents">SLF4J format string </a> (with "{}" formatting anchors) that logs the number of elements produced and the elapsed
     * time
     */
    final static String STATISTICS_MSG_FMT = "number produced {} ({} ms)";

    int produce();

    /* private */Logger getLogger();

    /**
     * This method is called when an instance of this {@link Runnable} to installed in a {@link Thread}
     *
     * @author mwnorman
     */
    default void run() {
        Thread currentThread = Thread.currentThread();
        currentThread.setName(String.format(PRODUCER_THREAD_NAME_FMT, System.identityHashCode(currentThread)));
        Instant startTime = Instant.now();
        int numProduced = produce();
        long elapsedTime = Duration.between(startTime, Instant.now()).toMillis();
        getLogger().info(STATISTICS_MSG_FMT, numProduced, elapsedTime);
    }

}