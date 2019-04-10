/**********************************************************************w***a******l******r*******us***********
 * File: HibernateHelper.java
 * Course materials (19W) CST 8277
 * @author (original) Stanley Pieda, Mike Norman
 */
package com.algonquincollege.cst8277.assignment2;

import com.algonquincollege.cst8277.assignment2.model.Contact;

/**
 *
 * <b>Description</b></br></br>
 * Helpful interface for saving {@link Contact}'s to the database </br>
 *
 * @date (modified) 2019 02
 *
 * @authors Stanley Pieda, Mike Norman
 *
 */
public interface HibernateHelper {

    /**
     * Save {@link Contact} to database
     *
     * @authors Stanley Pieda, Mike Norman
     */
    void saveContact(Contact contact);

    /**
     * Shutdown Hibernate factory
     *
     * @authors Stanley Pieda, Mike Norman
     */
    void shutdown();
}