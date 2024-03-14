package org.safetynet.api.builders;

import org.safetynet.api.entity.MedicalRecordEntity;


import java.util.Date;
import java.util.List;

public class MedicalRecordEntityBuilder {
    public String id;
    public String firstName;
    public String lastName;

    public Date birthDate;
    private List<String> medications;

    private List<String> allergies;

    public MedicalRecordEntityBuilder withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }

    public MedicalRecordEntityBuilder withLastName(String lastName){
        this.lastName = lastName;
        return this;
    }

    public MedicalRecordEntityBuilder withId(String firstName, String lastName){
        this.id = firstName+lastName;
        return this;
    }

    public MedicalRecordEntityBuilder withBirthDateDate (Date birthDate){
        this.birthDate = birthDate;
        return this;
    }

    public MedicalRecordEntityBuilder withMedications(List<String> medications){
        this.medications = medications;
        return this;
    }

    public MedicalRecordEntityBuilder withAllergies(List<String> allergies){
        this.allergies = allergies;
        return this;
    }

    public MedicalRecordEntity build(){
        MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity(this.id,this.firstName,this.lastName,this.birthDate,this.medications,this.allergies);
        return medicalRecordEntity;
    }
}
