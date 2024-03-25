package org.safetynet.api.mappers;

import org.safetynet.api.builders.MedicalRecordBuilder;
import org.safetynet.api.builders.MedicalRecordEntityBuilder;
import org.safetynet.api.entity.MedicalRecordEntity;
import org.safetynet.api.model.MedicalRecord;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Component
public class MedicalRecordMapper {


    public static String getValue(Optional<String> name) {
        return name.get();
    }
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

        String firstName = getValue(medicalRecordEntity.map(MedicalRecordEntity::getFirstName));
        String lastName = getValue(medicalRecordEntity.map(MedicalRecordEntity::getLastName));

        Date birthDate = medicalRecordEntity.get().getBirthDate();

        List<String> medications = medicalRecordEntity.get().getMedications();//Collections.singletonList(String.valueOf(medicalRecordEntity.map(MedicalRecordEntity::getMedications)));
        List<String> allergies = medicalRecordEntity.get().getAllergies();


        return new MedicalRecordBuilder()
                .withId(firstName,lastName)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withBirthDateDate(birthDate)
                .withMedications(medications)
                .withAllergies(allergies)
                .build();

    }

    public MedicalRecord convertToDtoMedicalRecord(Optional<MedicalRecordEntity> curseMedicalRecordEntity) throws ParseException {

        return this.convertToMedicalRecord(curseMedicalRecordEntity);

    }
}
