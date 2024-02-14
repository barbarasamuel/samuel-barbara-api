package org.safetynet.api.builders;

import org.safetynet.api.entity.MedicalRecordEntity;


import java.util.Date;

public class MedicalRecordEntityBuilder {
    public String id;
    public String firstName;
    public String lastName;

    public Date birthDate;
    private String medications;

    private String allergies;

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

    public MedicalRecordEntityBuilder withMedications(String medications){
        this.medications = medications;
        return this;
    }

    public MedicalRecordEntityBuilder withAllergies(String allergies){
        this.allergies = allergies;
        return this;
    }

    public MedicalRecordEntity build(){
        MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity(this.id,this.firstName,this.lastName,this.birthDate,this.medications,this.allergies);
        return medicalRecordEntity;
    }
}
