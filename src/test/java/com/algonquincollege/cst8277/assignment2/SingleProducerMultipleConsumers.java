/**********************************************************************w***a******l******r*******us***********
 * File: SingleProducerMultipleConsumers.java
 * Course materials (19W) CST 8277
 * @author (original) Mike Norman
 */
package com.algonquincollege.cst8277.assignment2;

import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.ParserProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.algonquincollege.cst8277.assignment2.LoggingOutputStream.LogLevel;
import com.algonquincollege.cst8277.assignment2.model.Contact;

/**
 *
 * <b>Description</b></br></br>
 * Driver class that demonstrates the Single-Producer-&-Multiple-Consumer solution using two different buffer implementations
 *
 * @date  (modified) 2019 02 20
 *
 * @author Nan Jiang 040-886-436, Can Shi 040-806-036
 *
 */
public class SingleProducerMultipleConsumers {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    protected static final int FIRST_AWAIT_TERMINATION_TIME_MINUTES = 3;
    protected static final int SECOND_AWAIT_TERMINATION_TIME_MINUTES = 1;
    protected static final String CIRCULAR_BUFTYPE = "c";
    protected static final String BLOCKING_BUFTYPE = "b";
    protected static final String USE_CIRCULAR_BUFFER_MSG = "Using Circular Buffer";
    protected static final String USE_BLOCKING_BUFFER_MSG = "Using Blocking Buffer";
    protected static final String CMDLINE_PARSING_ERROR_MSG = "cmdLine parsing error: {}";
    protected static final String TOTAL_TIME_MSG = "Total time = {} ms";

    public static void main(String[] args) {

        CmdLineParser cmdLineParser = null;
        SPMCCmdLineOptions cmdLineOptions = new SPMCCmdLineOptions();
        try {
            ParserProperties parserProperties = ParserProperties
                .defaults()
                .withOptionSorter(null)
              //.withUsageWidth(100)
            ;
            cmdLineParser = new CmdLineParser(cmdLineOptions, parserProperties);
            cmdLineParser.parseArgument(args);
        }
        catch (CmdLineException e) {
            // if there's a problem in the command line, you'll get this exception
            // this will report an error message
            logger.error(CMDLINE_PARSING_ERROR_MSG, e.getLocalizedMessage());
            LoggingOutputStream los = new LoggingOutputStream(logger, LogLevel.ERROR);
            logCmdLineUsage(cmdLineParser, los);
            System.exit(-1);
        }
        if (cmdLineOptions.help) {
            LoggingOutputStream los = new LoggingOutputStream(logger, LogLevel.INFO);
            logCmdLineUsage(cmdLineParser, los);
            return;
        }

        Buffer<Optional<Contact>> threadSafeBuffer = null;
        if (BLOCKING_BUFTYPE.equalsIgnoreCase(cmdLineOptions.bufType)) {
            logger.info(USE_BLOCKING_BUFFER_MSG);
            threadSafeBuffer = new BlockingQueueBuffer<>(cmdLineOptions.bufSize);
        }
        else if (CIRCULAR_BUFTYPE.equalsIgnoreCase(cmdLineOptions.bufType)) {
            logger.info(USE_CIRCULAR_BUFFER_MSG);
            threadSafeBuffer = new CircularBuffer<>(cmdLineOptions.bufSize);
        }

        Instant startTime = Instant.now();

        ExecutorService pool = Executors.newCachedThreadPool();
        for (int cnt = 0, numConsumerThreads = cmdLineOptions.numberOfConsumerThreads; cnt < numConsumerThreads; cnt++) {
            ContactConsumer cThread = new ContactConsumer(threadSafeBuffer, HibernateHelperImpl.INSTANCE);
            pool.execute(cThread);
        }
        ContactProducer pThread = new ContactProducer(threadSafeBuffer, "C:\\Users\\Can\\Desktop\\contacts.csv");
        pool.execute(pThread);

        shutdownAndAwaitTermination(pool);

        Instant endTime = Instant.now();
        long elapsedTime = Duration.between(startTime, endTime).toMillis();
        logger.info(TOTAL_TIME_MSG, elapsedTime);
    }

    protected static void logCmdLineUsage(CmdLineParser cmdLineParser, LoggingOutputStream los) {
        
        PrintWriter pw = new PrintWriter(los); // print the list of available options
        pw.println("\nUsage:");
        pw.flush();
        cmdLineParser.printUsage(los);
        los.line();
    }

    protected static void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(FIRST_AWAIT_TERMINATION_TIME_MINUTES, TimeUnit.MINUTES)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(SECOND_AWAIT_TERMINATION_TIME_MINUTES, TimeUnit.MINUTES))
                    logger.error("thread pool {} did not properly terminate", pool.toString());
            }
        }
        catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }

}