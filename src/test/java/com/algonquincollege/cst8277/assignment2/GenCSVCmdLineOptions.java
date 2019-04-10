/**********************************************************************w***a******l******r*******us***********
 * File: SPMCCmdLineOptions.java
 * Course materials (19W) CST 8277
 * @author (original) Mike Norman
 */
package com.algonquincollege.cst8277.assignment2;

import static com.algonquincollege.cst8277.assignment2.SPMCCmdLineOptions.CSV_PATH_LONGOPT;
import static com.algonquincollege.cst8277.assignment2.SPMCCmdLineOptions.CSV_PATH_META;
import static com.algonquincollege.cst8277.assignment2.SPMCCmdLineOptions.CSV_PATH_SHORTOPT;
import static com.algonquincollege.cst8277.assignment2.SPMCCmdLineOptions.CSV_PATH_USAGE;
import static com.algonquincollege.cst8277.assignment2.SPMCCmdLineOptions.DASH;
import static com.algonquincollege.cst8277.assignment2.SPMCCmdLineOptions.DASHDASH;
import static com.algonquincollege.cst8277.assignment2.SPMCCmdLineOptions.HELP_LONGOPT;
import static com.algonquincollege.cst8277.assignment2.SPMCCmdLineOptions.HELP_SHORTOPT;
import static com.algonquincollege.cst8277.assignment2.SPMCCmdLineOptions.HELP_USAGE;

import org.kohsuke.args4j.Option;

/**
 *
 * <b>Description</b></br></br>
 * Helper class that holds annotated member fields that represent cmdLine args for {@link GenerateContactsCSV}
 *
 * @date 2019 02
 *
 * @author mwnorman
 *
 */
public class GenCSVCmdLineOptions {

    protected static final int DEFAULT_GEN_COUNT = 1000; //default number of Contacts to write CSV file
    protected static final String COUNT_SHORTOPT = "c";
    protected static final String COUNT_LONGOPT = "count";
    protected static final String COUNT_USAGE = "number of random Contacts to write to CSV file";
    protected static final String COUNT_META = "<count>";

    @Option(
        name = DASH + HELP_SHORTOPT,
        aliases = {DASHDASH + HELP_LONGOPT},
        help = true,
        usage = HELP_USAGE
    )
    public boolean help;

    @Option(
        name = DASH + COUNT_SHORTOPT,
        aliases = {DASHDASH + COUNT_LONGOPT},
        usage = COUNT_USAGE,
        metaVar = COUNT_META
    )
    public int genCount = DEFAULT_GEN_COUNT;

    @Option(
        required = true,
        name = DASH + CSV_PATH_SHORTOPT,
        aliases = {DASHDASH + CSV_PATH_LONGOPT},
        usage = CSV_PATH_USAGE,
        metaVar = CSV_PATH_META
    )
    protected String csvPath;

}