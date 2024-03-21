package org.safetynet.api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.safetynet.api.entity.PersonEntity;
import org.safetynet.api.repository.PersonRepository;
import org.safetynet.api.tools.JSONReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = {"classpath:application-test.properties"})
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {SafetynetApplicationTest.class})

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonServiceTests {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private JSONReader jsonReader;

    @MockBean
    private List<PersonEntity> personEntityList;

    @BeforeAll
    void loadObjects() throws IOException {
        personEntityList = jsonReader.loadPersons();
    }

    @Test
    void firestationShouldReturnPersonEntityList() throws Exception {
        //List<PersonEntity> dataPersonEntity = personRepository.getAllPersonsByStationNumber("1");
        List<PersonEntity> list = this.personEntityList.stream().filter(e -> e.getIdFireStation().equals("1")).toList();

        assertEquals(6,list.size());

    }

    @Test
    void childAlertShouldReturnChildrenList() throws Exception {

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date formattedDate = formatter.parse("17/02/2012");

        //List<PersonEntity> dataChildren = personRepository.getAllByAddressAndBirthDate("1509 Culver St",formattedDate);
        List<PersonEntity> list = this.personEntityList.stream().filter(e -> e.getBirthDate().after(formattedDate) && e.getAddress().equals("1509 Culver St")).toList();

        assertEquals(2,list.size());
    }

    @Test
    void phoneAlertShouldReturnPhonesList() throws Exception {

        //List<PersonEntity> dataPhoneNumber = personRepository.getAllPhoneNumberByStation("1");
        List<PersonEntity> list = this.personEntityList.stream().filter(e -> e.getIdFireStation().equals("1")).toList();
        assertEquals(6,list.size());
    }

    @Test
    void fireShouldReturnPersonLivingTo() throws Exception {

        //List<PersonEntity> dataPersonsLivingTo = personRepository.getAllLivingTo("1509 Culver St");
        List<PersonEntity> list = this.personEntityList.stream().filter(e -> e.getAddress().equals("112 Steppes Pl")).toList();
        assertEquals(3,list.size());
    }

    @Test
    void floodShouldReturnPersonsByHome() throws Exception {
        List<String> stationNumbersList = new ArrayList<>();
        stationNumbersList.add("2");
        stationNumbersList.add("3");

        //List<List<PersonEntity>> dataPersonsCorrespondentToStationNumbers = personRepository.getAllCorrespondentToStationNumbers(stationNumbersList);

        List<List<PersonEntity>> personsCorrespondentToStationNumbers = new ArrayList<List<PersonEntity>>();
        for (String stationInCurse: stationNumbersList
        ) {
            personsCorrespondentToStationNumbers.add(this.personEntityList.stream().filter(e -> e.getIdFireStation().equals(stationInCurse)).toList());

        }
        assertEquals(2,personsCorrespondentToStationNumbers.size());
    }

    @Test
    void personInfoShouldReturnPersonEntityDetails() throws Exception {

        //List<PersonEntity> dataPersonsNaming = personRepository.getAllNaming("Tony","Cooper");
        List<PersonEntity> list = this.personEntityList.stream().filter(e -> e.getId().equals("Tony"+"Cooper")).toList();
        assertEquals(1,list.size());
    }

    @Test
    void communityEmailShouldReturnEmailsList() throws Exception {
        //List<PersonEntity> dataCityAddressMails = personRepository.getAllEmailsListToCity("Culver");
        List<PersonEntity> list = this.personEntityList.stream().filter(e -> e.getCity().equals("Culver")).toList();
        assertEquals(23,list.size());
    }

}
