package org.safetynet.api.builders;

import org.safetynet.api.model.MedicalRecord;


import java.util.Date;

public class MedicalRecordBuilder {
    public String id;
    public String firstName;
    public String lastName;

    public Date birthDate;
    private String medications;

    private String allergies;

    public MedicalRecordBuilder withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }

    public MedicalRecordBuilder withLastName(String lastName){
        this.lastName = lastName;
        return this;
    }

    public MedicalRecordBuilder withId(String firstName, String lastName){
        this.id = firstName+lastName;
        return this;
    }

    public MedicalRecordBuilder withBirthDateDate (Date birthDate){
        this.birthDate = birthDate;
        return this;
    }

    public MedicalRecordBuilder withMedications(String medications){
        this.medications = medications;
        return this;
    }

    public MedicalRecordBuilder withAllergies(String allergies){
        this.allergies = allergies;
        return this;
    }

    public MedicalRecord build(){
        MedicalRecord medicalRecord = new MedicalRecord(this.id,this.firstName,this.lastName,this.birthDate,this.medications,this.allergies);
        return medicalRecord;
    }
}
