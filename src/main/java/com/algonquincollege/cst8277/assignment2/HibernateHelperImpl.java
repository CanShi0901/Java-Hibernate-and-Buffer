/**********************************************************************w***a******l******r*******us***********
 * File: HibernateHelper.java
 * Course materials (19W) CST 8277
 * @authors (original) Stanley Pieda, Mike Norman
 */
package com.algonquincollege.cst8277.assignment2;

import java.lang.invoke.MethodHandles;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.algonquincollege.cst8277.assignment2.model.Contact;


/**
 * <b>Description</b></br></br>
 * Enforce the singleton property with a private constructor for enum type
 *
 * @see <a href="https://www.pearson.com/us/higher-education/program/Bloch-Effective-Java-2nd-Edition/PGM310651.html">J. Bloch, EffectiveJava, Addison-Wesly, 2008 (ISBN-13: 978-0-321-35668-0)</a>
 *
 * @authors Stanley Pieda, mwnorman
 *
 * @date (modified) 2018 11
 *
 */
public enum HibernateHelperImpl implements HibernateHelper {

    /** Only use one enum type for Singleton Design Pattern */
    INSTANCE;

    /**
     * member field to hold onto SLF4J logger
     * @authors Stanley Pieda, Mike Norman
     */
    private Logger logger;

    /**
     * member field to hold onto Hibernate factory
     * @authors Stanley Pieda, Mike Norman
     */
    private SessionFactory factory;

    private HibernateHelperImpl() {
        logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
        try {
             Configuration config = new Configuration()
                    .addAnnotatedClass(Contact.class) // load the entities
                    .configure("hibernate.cfg.xml");   // load additional settings

              ServiceRegistry sR = new StandardServiceRegistryBuilder()
                    .applySettings(config.getProperties())
                    .build();
              factory = config.buildSessionFactory(sR);    
        }
        catch (Throwable ex) {
            factory = null;
            logger.error("Building SessionFactory failed:", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Insert Contact in the database<br/>
     * Checks factory to prevent NullPointerException<br/>
     * Checks contact, skip DB round-trip if null
     *
     * @param contact
     *
     * @authors Stanley Pieda, Mike Norman
     *
     * @see com.algonquincollege.cst8277.assignment2.HibernateHelper#saveContact()
     */
    @Override
    public void saveContact(Contact contact) {
        if (factory == null) {
            logger.error("Hibernate factory is null");
            return;
        }
        if (contact == null) {
            logger.warn("contact is null; don't bother trying to insert into the database");
            return;
        }
        Session s = null;
        Transaction tx = null;
        try {
            logger.trace("attempting to save contact {} to database", contact.toString());
            s= factory.getCurrentSession();
            tx=s.getTransaction();
            tx.begin();
            s.save(contact);
            tx.commit();
            logger.debug("contact {} successfully saved to database", contact.toString());
        }
        catch (Exception e) {
            logger.error("Something went wrong trying to insert contact in the database:", e);
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
        finally {
            s.close();
        }
    }

    /* (non-Javadoc)
     * @see com.algonquincollege.cst8277.assignment2.HibernateHelper#shutdown()
     */
    @Override
    public void shutdown() {
        if (factory != null) {
            factory.close();
            factory = null;
            logger.info("Hibernate factory shutdown");
        }
    }

}