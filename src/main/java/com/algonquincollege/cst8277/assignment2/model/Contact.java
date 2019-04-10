/**********************************************************************w***a******l******r*******us***********
 * File: Contact.java
 * Course materials (19W) CST 8277
 * @author (original) Mike Norman
 */
package com.algonquincollege.cst8277.assignment2.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="contacts")
/**
 *
 * <b>Description</b></br></br>
 * Model class for contacts table (from Assignment 1)
 *
 * @date 2019 02
 *
 * @author mwnorman
 *
 */
public class Contact implements Serializable {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;

    protected int id;
    protected String street;
    protected String city;
    protected String state;
    protected String postal;
    protected String country;
    protected String firstName;
    protected String lastName;
    protected String email;

    @Id                       // the getter of the primary key is annotated, so Hibernate will use property access
                              // H2 will automatically generate the primary key values
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getPostal() {
        return postal;
    }
    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name="first_name")
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    @Column(name="last_name")
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Pattern(regexp="")
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format(
            "Contact [id=%s, street=%s, city=%s, state=%s, postal=%s, country=%s, firstName=%s, lastName=%s, email=%s]",
            id, street, city, state, postal, country, firstName, lastName, email);
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + id;
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((postal == null) ? 0 : postal.hashCode());
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        result = prime * result + ((street == null) ? 0 : street.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Contact)) {
            return false;
        }
        Contact other = (Contact)obj;
        if (city == null) {
            if (other.city != null) {
                return false;
            }
        }
        else if (!city.equals(other.city)) {
            return false;
        }
        if (country == null) {
            if (other.country != null) {
                return false;
            }
        }
        else if (!country.equals(other.country)) {
            return false;
        }
        if (email == null) {
            if (other.email != null) {
                return false;
            }
        }
        else if (!email.equals(other.email)) {
            return false;
        }
        if (firstName == null) {
            if (other.firstName != null) {
                return false;
            }
        }
        else if (!firstName.equals(other.firstName)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (lastName == null) {
            if (other.lastName != null) {
                return false;
            }
        }
        else if (!lastName.equals(other.lastName)) {
            return false;
        }
        if (postal == null) {
            if (other.postal != null) {
                return false;
            }
        }
        else if (!postal.equals(other.postal)) {
            return false;
        }
        if (state == null) {
            if (other.state != null) {
                return false;
            }
        }
        else if (!state.equals(other.state)) {
            return false;
        }
        if (street == null) {
            if (other.street != null) {
                return false;
            }
        }
        else if (!street.equals(other.street)) {
            return false;
        }
        return true;
    }
}