package org.safetynet.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
//@AllArgsConstructor
public class Person extends IdentityBasis {
    //private String id;
    /*private String firstName;
    private String lastName;
    private String address;*/
    private String zip;
    private String city;
    private String eMail;
    private String phone;
    //private Date birthDate;
    private String idFireStation;
    private String idMedicalRecord;

    public Person(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = firstName+lastName;
    }

    public Person(String id, String firstName, String lastName, String address, String zip, String city, String eMail, String phone, Date birthDate, String idFireStation,String idMedicalRecord){
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.eMail = eMail;
        this.phone = phone;
        this.birthDate = birthDate;
        this.idFireStation = idFireStation;
        this.idMedicalRecord = idMedicalRecord;
    }
    public Person(){}

}
