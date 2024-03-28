package org.safetynet.api.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;
import java.util.List;
//@JsonFilter("filtreDynamiqueMedicalRecord")
//@NoArgsConstructor
//@Setter
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
