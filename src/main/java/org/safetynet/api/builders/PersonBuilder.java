package org.safetynet.api.builders;

import org.safetynet.api.model.Person;

import java.util.Date;
/**
 *
 * To make easy the instantiation of a Person Object
 *
 */
public class PersonBuilder {
    public String id;
    public String firstName;
    public String lastName;
    public String address;
    public String zip;
    public String city;
    public String eMail;
    public String phone;
    public Date birthDate;
    public String idFireStation;
    public String idMedicalRecord;

    public PersonBuilder withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }

    public PersonBuilder withLastName(String lastName){
        this.lastName = lastName;
        return this;
    }

    public PersonBuilder withId(String firstName, String lastName){
        this.id = firstName+lastName;
        return this;
    }

    public PersonBuilder withAddress(String address){
        this.address = address;
        return this;
    }

    public PersonBuilder withZip(String zip){
        this.zip = zip;
        return this;
    }

    public PersonBuilder withCity(String city){
        this.city = city;
        return this;
    }

    public PersonBuilder withEMail(String eMail){
        this.eMail = eMail;
        return this;
    }

    public PersonBuilder withPhone(String phone){
        this.phone = phone;
        return this;
    }

    public PersonBuilder withBirthDateDate (Date birthDate){
        this.birthDate = birthDate;
        return this;
    }

    public PersonBuilder withFireStation(String fireStation){

        this.idFireStation = fireStation;
        return this;
    }

    public PersonBuilder withMedicalRecord(String firstName, String lastName){//(String medications, String allergies){
        this.idMedicalRecord = firstName+lastName;
        return this;
    }

    public Person build(){

        Person person = new Person(this.id,this.firstName,this.lastName,this.address, this.zip,this.city,this.eMail,this.phone,this.birthDate,this.idFireStation,this.idMedicalRecord);
        return person;
    }


}
