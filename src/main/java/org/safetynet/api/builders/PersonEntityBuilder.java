package org.safetynet.api.builders;

import org.safetynet.api.entity.PersonEntity;
import org.safetynet.api.model.FireStation;
import org.safetynet.api.model.MedicalRecord;

import java.util.Date;

public class PersonEntityBuilder {
    public String id;
    //public String idMedicalRecord;
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

    public PersonEntityBuilder withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }

    public PersonEntityBuilder withLastName(String lastName){
        this.lastName = lastName;
        return this;
    }

    public PersonEntityBuilder withId(String firstName, String lastName){
        this.id = firstName+lastName;
        return this;
    }

    public PersonEntityBuilder withAddress(String address){
        this.address = address;
        return this;
    }

    public PersonEntityBuilder withZip(String zip){
        this.zip = zip;
        return this;
    }

    public PersonEntityBuilder withCity(String city){
        this.city = city;
        return this;
    }

    public PersonEntityBuilder withEMail(String eMail){
        this.eMail = eMail;
        return this;
    }

    public PersonEntityBuilder withPhone(String phone){
        this.phone = phone;
        return this;
    }

    public PersonEntityBuilder withBirthDateDate (Date birthDate){
        this.birthDate = birthDate;
        return this;
    }

    public PersonEntityBuilder withFireStation(String fireStation){
        //this.fireStation.setStation(fireStation);
        this.idFireStation = fireStation;
        return this;
    }

    public PersonEntityBuilder withMedicalRecord(String firstName, String lastName){
        this.idMedicalRecord = firstName+lastName;
        return this;
    }

    public PersonEntity build(){
     PersonEntity personEntity = new PersonEntity(this.id,this.firstName,this.lastName,this.address, this.zip,this.city,this.eMail,this.phone,this.birthDate,this.idFireStation,this.idMedicalRecord);
     return personEntity;
    }


}
