package org.safetynet.api.entity;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MedicalRecordEntity extends IdentityBasisEntity{

    private List<String> medications;
    private List<String> allergies;

    public MedicalRecordEntity(String id, String firstName, String lastName, Date birthdate, List<String> medications, List<String> allergies){

        this.id = firstName+lastName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthdate;
        this.medications = medications;
        this.allergies = allergies;
    }

}
