/**********************************************************************w***a******l******r*******us***********
 * File: ContactProducer.java
 * Course materials (19W) CST 8277
 * @author (original) Mike Norman
 */
package com.algonquincollege.cst8277.assignment2;

import static com.algonquincollege.cst8277.assignment2.Utils.buildStreamFromScanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.algonquincollege.cst8277.assignment2.model.Contact;
/**
 *
 * <b>Description</b></br></br>
 * Implements the {@link com.algonquincollege.cst8277.assignment2.Producer} interface </br>
 *
 * @date  (modified) 2019 02 20
 *
 * @author Nan Jiang 040-886-436, Can Shi 040-806-036
 *
 */
public class ContactProducer implements Producer<Optional<Contact>> {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Override
    public Logger getLogger() {
        return logger;
    }

    protected static final String COMMA = ",";
    protected static final String SOMETHING_WENT_WRONG_READING_CSV_FILE = "something went wrong reading CSV file {}";
    protected static final String SOMETHING_WENT_WRONG_CREATING_CONTACT = "something went wrong creating contact or adding to buffer";

    protected Buffer<Optional<Contact>> threadSafeBuffer;
    protected int numProduced = 0;
    protected String csvPath; // path to CSV file containing Contacts

    public ContactProducer(Buffer<Optional<Contact>> threadSafeBuffer, String pathToCSV) {
        this.threadSafeBuffer = threadSafeBuffer;
        this.csvPath = pathToCSV;
    }

    /**
     * Iterate through the csv file, building {@link Contact}'s and adding them to the buffer
     *
     * @return int number of Contacts build from csv file
     */
    @Override
    public int produce() {
        int count = 0;
        try (Scanner scanner = new Scanner(new File(csvPath));
            Stream<String> csvStream = buildStreamFromScanner(scanner)) {
            List<Contact> newContacts = csvStream
                .skip(1) //skip header record
                .map(csvLine -> buildNewContacts(csvLine))
                .collect(Collectors.toList());
            //add a 'marker' object buffer (empty Optional) so that consumer(s) know to stop
            threadSafeBuffer.blockingPut(Optional.empty());
            count = newContacts.size();
        }
        catch (FileNotFoundException | InterruptedException e) {
            logger.error(SOMETHING_WENT_WRONG_READING_CSV_FILE,csvPath,e);
        }
        return count;
    }

    /**
     * Extract values from csvLine and build a Contact object
     * and add to the buffer
     *
     * @param csvLine
     * @return newly built Contact
     */
    protected Contact buildNewContacts(String csvLine) {
        
        Contact contact = new Contact();
        contact.setStreet(csvLine.split(COMMA)[0]);
        contact.setCity(csvLine.split(COMMA)[1]);
        contact.setState(csvLine.split(COMMA)[2]);
        contact.setPostal(csvLine.split(COMMA)[3]);
        contact.setCountry(csvLine.split(COMMA)[4]);
        contact.setFirstName(csvLine.split(COMMA)[5]);
        contact.setLastName(csvLine.split(COMMA)[6]);
        contact.setEmail(csvLine.split(COMMA)[7]);
        try {
            threadSafeBuffer.blockingPut(Optional.of(contact));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return contact;
    }
}