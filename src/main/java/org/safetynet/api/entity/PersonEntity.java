package org.safetynet.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.safetynet.api.model.FireStation;
import org.safetynet.api.model.IdentityBasis;
import org.safetynet.api.model.MedicalRecord;

import java.util.Date;

@Data
//@AllArgsConstructor
//@EqualsAndHashCode(callSuper=false)
public class PersonEntity extends IdentityBasisEntity {
//public class PersonEntity extends IdentityBasis implements GenericEntity{

    //private String id;
    //private String firstName;
    //private String lastName;
    //private String address;
    private String zip;
    private String city;
    private String eMail;
    private String phone;
    //private Date birthDate;
    private String idFireStation;
    private String idMedicalRecord;

    /*public PersonEntity(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = firstName+lastName;
    }

    */public PersonEntity(){

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
    /*public String getId() {
        return id;
    }*/
}
