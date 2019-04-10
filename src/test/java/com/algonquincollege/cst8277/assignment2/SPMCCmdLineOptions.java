/**********************************************************************w***a******l******r*******us***********
 * File: SPMCCmdLineOptions.java
 * Course materials (19W) CST 8277
 * @author (original) Mike Norman
 */
package com.algonquincollege.cst8277.assignment2;

import static com.algonquincollege.cst8277.assignment2.SingleProducerMultipleConsumers.BLOCKING_BUFTYPE;

import org.kohsuke.args4j.Option;

/**
 *
 * <b>Description</b></br></br>
 * Helper class that holds annotated member fields that represent cmdLine args for {@link SingleProducerMultipleConsumers}
 *
 * @date 2019 02
 *
 * @author mwnorman
 *
 */
public class SPMCCmdLineOptions {

    protected static final int DEFAULT_BUFFER_SIZE = 20;
    protected static final int DEFAULT_PRODUCER_COUNT = 500;
    protected static final String DASH = "-";
    protected static final String DASHDASH = DASH + DASH;
    protected static final String HELP_SHORTOPT = "h";
    protected static final String HELP_LONGOPT = "help";
    protected static final String HELP_USAGE = "print this message";
    protected static final String BUFFER_LONGOPT = "buffer";
    protected static final String BUFFER_USAGE = "b=BlockingQueue Buffer, c=Circular Buffer";
    protected static final String BUFFER_META = "<Buffer Type>";
    protected static final String SIZE_SHORTOPT = "s";
    protected static final String SIZE_LONGOPT = "size";
    protected static final String SIZE_USAGE = "buffer size";
    protected static final String SIZE_META = "<size>";
    protected static final String CSV_PATH_SHORTOPT = "p";
    protected static final String CSV_PATH_LONGOPT = "path";
    protected static final String CSV_PATH_USAGE = "path to CSV file containing Contacts";
    protected static final String CSV_PATH_META = "<path>";

    @Option(
        name = DASH + HELP_SHORTOPT,
        aliases = {DASHDASH + HELP_LONGOPT},
        help = true,
        usage = HELP_USAGE
    )
    public boolean help;

    @Option(
        required = true,
        name = DASH + BLOCKING_BUFTYPE,
        aliases= {DASHDASH + BUFFER_LONGOPT},
        usage = BUFFER_USAGE,
        metaVar = BUFFER_META
    )
    public String bufType;

    @Option(
        name = DASH + SIZE_SHORTOPT,
        aliases = {DASHDASH + SIZE_LONGOPT},
        usage = SIZE_USAGE,
        metaVar = SIZE_META
    )
    public int bufSize = DEFAULT_BUFFER_SIZE;

    @Option(
        name = DASH + "con",   //reuse CIRCULAR_BUFTYPE as count's short option
        aliases = {DASHDASH + "consumers"},
        usage = "number of consumer threads"
    )
    public int numberOfConsumerThreads = 1;

    @Option(
        required = true,
        name = DASH + CSV_PATH_SHORTOPT,
        aliases = {DASHDASH + CSV_PATH_LONGOPT},
        usage = CSV_PATH_USAGE,
        metaVar = CSV_PATH_META
    )
    protected String csvPath;

}