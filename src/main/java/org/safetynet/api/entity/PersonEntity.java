package org.safetynet.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 *
 * To get the JSON data about persons
 *
 */

@Data
@AllArgsConstructor
public class PersonEntity extends IdentityBasisEntity {

    private String zip;
    private String city;
    private String eMail;
    private String phone;
    //private Date birthDate;
    private String idFireStation;
    private String idMedicalRecord;

    public PersonEntity(){

    }

    public PersonEntity(String id, String firstName, String lastName, String address, String zip, String city, String eMail, String phone, Date birthDate, String idFireStation,String idMedicalRecord){
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = firstName+lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.eMail = eMail;
        this.phone = phone;
        this.birthDate = birthDate;
        this.idFireStation = idFireStation;
        this.idMedicalRecord = idMedicalRecord;
    }

}
