package org.safetynet.api.entity;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

//@NoArgsConstructor
//@Setter
@Data
public class MedicalRecordEntity extends IdentityBasisEntity{
    //private String id;
    //private String firstName;
    //private String lastName;
    //private Date birthDate;

    //@Getter
    private String medications;
    //@Getter
    private String allergies;

    public MedicalRecordEntity(String id,String firstName,String lastName,Date birthdate,String medications,String allergies){

        this.id = firstName+lastName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthdate;
        this.medications = medications;
        this.allergies = allergies;
    }

}
