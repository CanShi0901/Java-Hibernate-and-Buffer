/**********************************************************************w***a******l******r*******us***********
 * File: GenerateContactsCSV.java
 * Course materials (19W) CST 8277
 * @author (original) Mike Norman
 */
package com.algonquincollege.cst8277.assignment2;

import static com.algonquincollege.cst8277.assignment2.SingleProducerMultipleConsumers.CMDLINE_PARSING_ERROR_MSG;
import static com.algonquincollege.cst8277.assignment2.SingleProducerMultipleConsumers.logCmdLineUsage;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.ParserProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.algonquincollege.cst8277.assignment2.LoggingOutputStream.LogLevel;
import com.algonquincollege.cst8277.assignment2.model.Contact;

import uk.co.jemos.podam.api.ClassInfoStrategy;
import uk.co.jemos.podam.api.DefaultClassInfoStrategy;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * <b>Description</b></br></br>
 * Helper class that generates random Contacts and writes them to the specified CSV file
 *
 * @date 2019 02
 *
 * @author mwnorman
 *
 */
public class GenerateContactsCSV {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    protected static final String[] CONTACT_CSV_HEADER = {
        // further down - csvPrinter creates a csv record:  ensure order of header and record are in synch
        "Street", "City", "State", "Postal", "Country", "Firstname", "Lastname", "Email"
        };
    protected static final String SOMETHING_WENT_WRONG_WRITING_TO_CSV_FILE =
        "something went wrong writing to CSV file {}";

    public static void main(String[] args) {
        CmdLineParser cmdLineParser = null;
        GenCSVCmdLineOptions cmdLineOptions = new GenCSVCmdLineOptions();
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
            // problem parsing the command line - report the error message
            logger.error(CMDLINE_PARSING_ERROR_MSG, e.getLocalizedMessage());
            // log usage with level ERROR
            logCmdLineUsage(cmdLineParser, new LoggingOutputStream(logger, LogLevel.ERROR));
            System.exit(-1); //nonzero code indicates abnormal termination.
        }
        if (cmdLineOptions.help) {
            // log usage with level INFO
            logCmdLineUsage(cmdLineParser, new LoggingOutputStream(logger, LogLevel.INFO));
            return;
        }

        //PODAM - POjo DAta Mocker
        PodamFactory factory =  new PodamFactoryImpl();
        ClassInfoStrategy classInfoStrategy = factory.getClassStrategy();
        //no need to generate primary key (id), database will do that for us
        ((DefaultClassInfoStrategy)classInfoStrategy).addExcludedField(Contact.class, "id");

        try (
            CSVPrinter csvPrinter = CSVFormat.DEFAULT
                .withHeader(CONTACT_CSV_HEADER)
                .print(new FileWriter(cmdLineOptions.csvPath))
            ) {
            for (int cnt = 0, numRandomContacts = cmdLineOptions.genCount; cnt < numRandomContacts; cnt++) {
                Contact randomContact = factory.manufacturePojo(Contact.class);
                logger.debug("created random contact {}", randomContact.toString());
                csvPrinter.printRecord(randomContact.getStreet(), randomContact.getCity(),
                    randomContact.getState(), randomContact.getPostal(), randomContact.getCountry(),
                    randomContact.getFirstName(), randomContact.getLastName(), randomContact.getEmail());
            }
        }
        catch (IOException ioe) {
            logger.error(SOMETHING_WENT_WRONG_WRITING_TO_CSV_FILE,cmdLineOptions.csvPath, ioe);
        }
    }
}
