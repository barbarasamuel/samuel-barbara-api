package org.safetynet.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.safetynet.api.builders.FireStationBuilder;
import org.safetynet.api.builders.FireStationEntityBuilder;
import org.safetynet.api.builders.MedicalRecordBuilder;
import org.safetynet.api.entity.FireStationEntity;
import org.safetynet.api.entity.MedicalRecordEntity;
import org.safetynet.api.mappers.FireStationMapper;
import org.safetynet.api.mappers.MedicalRecordMapper;
import org.safetynet.api.model.FireStation;
import org.safetynet.api.model.MedicalRecord;
import org.safetynet.api.repository.FireStationRepository;
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
public class FireStationServiceTests {
    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private FireStationMapper fireStationMapper;

    @Test
    void shouldPostFireStationTest() throws ParseException {

        FireStation addedFireStation = new FireStationBuilder("8")
                .withAddress("5 rue des fleuves")
                .build();

        FireStationEntity fireStationEntity = fireStationMapper.convertToFireStationEntity(addedFireStation);
        FireStationEntity dataFireStation = fireStationRepository.postElement(fireStationEntity);
        FireStation createdFireStation = fireStationMapper.convertToFireStation(dataFireStation);

        assertEquals(addedFireStation,createdFireStation);
    }

    @Test
    void shouldPatchFireStationTest() throws ParseException, IOException {
        fireStationRepository.loadData();

        FireStation modifiedFireStation = new FireStationBuilder("4")
                .withAddress("5 rue des fleuves")
                .build();

        FireStationEntity fireStationEntity = fireStationMapper.convertToFireStationEntity(modifiedFireStation);
        FireStationEntity dataFireStation = fireStationRepository.patchElement("4",fireStationEntity);
        FireStation updatedFireStation = fireStationMapper.convertToFireStation(dataFireStation);

        assertEquals(modifiedFireStation, updatedFireStation);
    }

}
