package org.safetynet.api.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class MedicalRecord extends IdentityBasis {
    /*private String firstName;
    private String lastName;
    private Date birthdate;*/
    private String medications;
    private String allergies;

    public MedicalRecord(String id, String firstName, String lastName, Date birthdate, String medications, String allergies){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthdate;
        this.medications = medications;
        this.allergies = allergies;
    }
}
