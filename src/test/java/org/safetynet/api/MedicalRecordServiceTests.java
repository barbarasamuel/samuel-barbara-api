package org.safetynet.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.safetynet.api.builders.MedicalRecordBuilder;
import org.safetynet.api.entity.MedicalRecordEntity;
import org.safetynet.api.mappers.MedicalRecordMapper;
import org.safetynet.api.model.MedicalRecord;
import org.safetynet.api.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = {"classpath:application-test.properties"})
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {SafetynetApplicationTest.class})

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MedicalRecordServiceTests {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private MedicalRecordMapper medicalRecordMapper;

    @Test
    void shouldPostMedicalRecordTest() throws ParseException {
        String birthDateInString = "21/09/1994";
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date birthDate = formatter.parse(birthDateInString);

        List<String> medicationsList = new ArrayList<>();
        medicationsList.add("aznol:350mg");
        medicationsList.add("hydrapermazol:100mg");

        List<String> allergiesList = new ArrayList<>();
        allergiesList.add("nillacilan");
        allergiesList.add("peanut");

        MedicalRecord addedMedicalRecord = new MedicalRecordBuilder()
                .withId("Antoine","Lilier")
                .withFirstName("Antoine")
                .withLastName("Lilier")
                .withBirthDateDate(birthDate)
                .withMedications(medicationsList)
                .withAllergies(allergiesList)
                .build();

        MedicalRecordEntity medicalRecordEntity = medicalRecordMapper.convertToMedicalRecordEntity(addedMedicalRecord);
        MedicalRecordEntity dataMedicalRecord = medicalRecordRepository.postElement(medicalRecordEntity);
        MedicalRecord createdMedicalRecord  = medicalRecordMapper.convertToMedicalRecord(Optional.ofNullable(dataMedicalRecord));

        assertEquals(addedMedicalRecord,createdMedicalRecord);
    }

    @Test
    void shouldPatchMedicalRecordTest() throws ParseException, IOException {
        medicalRecordRepository.loadData();

        String birthDateInString = "03/06/1984";
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date birthDate = formatter.parse(birthDateInString);

        List<String> medicationsList = new ArrayList<>();
        medicationsList.add("aznol:350mg");
        medicationsList.add("hydrapermazol:100mg");

        List<String> allergiesList = new ArrayList<>();
        allergiesList.add("nillacilan");
        allergiesList.add("peanut");

        MedicalRecord modifiedMedicalRecord = new MedicalRecordBuilder()
                .withId("John", "Boyd")
                .withFirstName("John")
                .withLastName("Boyd")
                .withBirthDateDate(birthDate)
                .withMedications(medicationsList)
                .withAllergies(allergiesList)
                .build();

        MedicalRecordEntity medicalRecordEntity = medicalRecordMapper.convertToMedicalRecordEntity(modifiedMedicalRecord);
        MedicalRecordEntity dataMedicalRecord = medicalRecordRepository.patchElement("JohnBoyd", medicalRecordEntity);
        MedicalRecord updatedMedicalRecord = medicalRecordMapper.convertToMedicalRecord(Optional.ofNullable(dataMedicalRecord));


        assertEquals(modifiedMedicalRecord, updatedMedicalRecord);
    }
}
