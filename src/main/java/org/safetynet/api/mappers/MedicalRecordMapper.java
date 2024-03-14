package org.safetynet.api.mappers;

import org.safetynet.api.builders.MedicalRecordBuilder;
import org.safetynet.api.builders.MedicalRecordEntityBuilder;
import org.safetynet.api.entity.MedicalRecordEntity;
import org.safetynet.api.entity.PersonEntity;
import org.safetynet.api.model.MedicalRecord;
import org.safetynet.api.model.Person;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Component
public class MedicalRecordMapper {

    //private final MedicalRecordMapper this.,  = this;

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

    public MedicalRecord convertToMedicalRecord(Optional<MedicalRecordEntity> medicalRecordEntity) throws ParseException {

        String firstName = String.valueOf(medicalRecordEntity.map(MedicalRecordEntity::getFirstName));
        String lastName = String.valueOf(medicalRecordEntity.map(MedicalRecordEntity::getLastName));
        //String birthDateInString = String.valueOf(medicalRecordEntity.map(MedicalRecordEntity::getBirthDate));
        Date birthDate = medicalRecordEntity.get().getBirthDate();
        /*DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date birthDate = formatter.parse(String.valueOf(birthDateInString));*/

        List<String> medications = medicalRecordEntity.get().getMedications();//Collections.singletonList(String.valueOf(medicalRecordEntity.map(MedicalRecordEntity::getMedications)));
        List<String> allergies = medicalRecordEntity.get().getAllergies();//Collections.singletonList(String.valueOf(medicalRecordEntity.map(MedicalRecordEntity::getAllergies)));
                /*.withFirstName(medicalRecordEntity.map(MedicalRecordEntity::getFirstName))
                .withLastName(medicalRecordEntity.map(MedicalRecordEntity::getLastName))
                .withBirthDateDate(medicalRecordEntity.map(MedicalRecordEntity::getBirthDate))
                .withMedications(medicalRecordEntity.map(MedicalRecordEntity::getMedications))
                .withAllergies(medicalRecordEntity.map(MedicalRecordEntity::getAllergies))*/

        return new MedicalRecordBuilder()
                /*.withId(medicalRecordEntity.map(MedicalRecordEntity::getId))
                .withFirstName(medicalRecordEntity.map(MedicalRecordEntity::getFirstName))
                .withLastName(medicalRecordEntity.map(MedicalRecordEntity::getLastName))
                .withBirthDateDate(medicalRecordEntity.map(MedicalRecordEntity::getBirthDate))
                .withMedications(medicalRecordEntity.map(MedicalRecordEntity::getMedications))
                .withAllergies(medicalRecordEntity.map(MedicalRecordEntity::getAllergies))*//*
                .withId(medicalRecordEntity.getFirstName(),medicalRecordEntity.getLastName())
                .withFirstName(medicalRecordEntity.getFirstName())
                .withLastName(medicalRecordEntity.getLastName())
                .withBirthDateDate(medicalRecordEntity.getBirthDate())
                .withMedications(medicalRecordEntity.getMedications())
                .withAllergies(medicalRecordEntity.getAllergies())*/
                .withId(firstName,lastName)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withBirthDateDate(birthDate)
                .withMedications(medications)
                .withAllergies(allergies)
                .build();

    }

    /**/public MedicalRecord convertToDtoMedicalRecord(Optional<MedicalRecordEntity> curseMedicalRecordEntity) throws ParseException {
        //MedicalRecord medicalRecord;
        return this.convertToMedicalRecord(curseMedicalRecordEntity);
        /*return curseMedicalRecordEntity.map(e -> {
            try {
                return this.convertToMedicalRecord(curseMedicalRecordEntity);
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        });
        //eturn medicalRecord
         //   .map(e ->this.convertToDtoMedicalRecord(medicalRecordEntity) );
        //.stream().map(this::convertToMedicalRecord).toList();
    */}
}
