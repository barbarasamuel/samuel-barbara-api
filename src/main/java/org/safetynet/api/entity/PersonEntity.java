package org.safetynet.api.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.safetynet.api.model.FireStation;
import org.safetynet.api.model.IdentityBasis;
import org.safetynet.api.model.MedicalRecord;

import java.util.Date;

@Data
//@JsonFilter("filtreDynamiquePersonEntity")
@AllArgsConstructor
//@EqualsAndHashCode(callSuper=false)
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
