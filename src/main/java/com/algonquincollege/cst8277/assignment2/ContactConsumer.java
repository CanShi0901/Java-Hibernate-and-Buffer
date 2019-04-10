/**********************************************************************w***a******l******r*******us***********
 * File: ContactConsumer.java
 * Course materials (19W) CST 8277
 * @author (original) Mike Norman
 */
package com.algonquincollege.cst8277.assignment2;

import java.lang.invoke.MethodHandles;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.algonquincollege.cst8277.assignment2.model.Contact;


/**
 *
 * <b>Description</b></br></br>
 * Implements the {@link com.algonquincollege.cst8277.assignment2.Consumer} interface </br>
 *
 * @date (modified) 2019 02 20
 *
 * @author Nan Jiang 040-886-436, Can Shi 040-806-036
 *
 */
public class ContactConsumer implements Consumer<Optional<Contact>> {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Override
    public Logger getLogger() {
        return logger;
    }
    protected static final String SOMETHING_WENT_WRONG_CONSUMING = "something went wrong consuming";

    protected Buffer<Optional<Contact>> threadSafeBuffer;
    protected HibernateHelper hibernateHelper;

    public ContactConsumer(Buffer<Optional<Contact>> threadSafeBuffer, HibernateHelper hibernateHelper) {
        this.threadSafeBuffer = threadSafeBuffer;
        this.hibernateHelper = hibernateHelper;
    }

    /**
     * Work through buffer, storing {@link Contact}'s into the database
     *
     * @return int number of Contacts (this thread) saved to the database
     */
    @Override
    public int consume() {
        int numConsumed = 0;
        boolean done = false;
        while (!done) {            
            try {
               Optional<Contact> contact = threadSafeBuffer.blockingGet();
                   if (contact.isPresent()) 
                   {
                       logger.info("Consuming random contact {}", contact.get().toString());
                       HibernateHelperImpl.INSTANCE.saveContact(contact.get());
                       numConsumed++;
                    }
                    else 
                    {
                        done = true;
                        //in case there are other consumer threads, put the empty Optional back so they know to stop, too
                        threadSafeBuffer.blockingPut(contact);
                    }
            }
            catch (InterruptedException e) {
                logger.error(SOMETHING_WENT_WRONG_CONSUMING, e);
            }
        }
        return numConsumed;
    }

}