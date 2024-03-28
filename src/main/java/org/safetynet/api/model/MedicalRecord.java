package org.safetynet.api.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@JsonFilter("filtreDynamiqueMedicalRecord")
@Data
public class MedicalRecord extends IdentityBasis {

    private List<String> medications;
    private List<String> allergies;

    public MedicalRecord(String id, String firstName, String lastName, Date birthdate, List<String> medications, List<String> allergies){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthdate;
        this.medications = medications;
        this.allergies = allergies;
    }
}
