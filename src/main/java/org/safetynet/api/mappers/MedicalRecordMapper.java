package org.safetynet.api.mappers;

import org.safetynet.api.builders.MedicalRecordBuilder;
import org.safetynet.api.builders.MedicalRecordEntityBuilder;
import org.safetynet.api.entity.MedicalRecordEntity;
import org.safetynet.api.model.MedicalRecord;
import org.springframework.stereotype.Component;
@Component
public class MedicalRecordMapper {

    public MedicalRecordEntity convertToMedicalRecordEntity(MedicalRecord medicalRecord){
        return new MedicalRecordEntityBuilder()
                .withId(medicalRecord.getFirstName(),medicalRecord.getLastName())
                .withFirstName(medicalRecord.getFirstName())
                .withLastName(medicalRecord.getLastName())
                .withBirthDateDate(medicalRecord.getBirthDate())
                .withMedications(medicalRecord.getMedications())
                .withAllergies(medicalRecord.getAllergies())
                .build();
    }

    public MedicalRecord convertToMedicalRecord(MedicalRecordEntity medicalRecordEntity){

        return new MedicalRecordBuilder()
                .withId(medicalRecordEntity.getFirstName(),medicalRecordEntity.getLastName())
                .withFirstName(medicalRecordEntity.getFirstName())
                .withLastName(medicalRecordEntity.getLastName())
                .withBirthDateDate(medicalRecordEntity.getBirthDate())
                .withMedications(medicalRecordEntity.getMedications())
                .withAllergies(medicalRecordEntity.getAllergies())
                .build();

    }
}
