package org.safetynet.api;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.safetynet.api.builders.PersonBuilder;
import org.safetynet.api.builders.PersonEntityBuilder;
import org.safetynet.api.entity.MedicalRecordEntity;
import org.safetynet.api.entity.PersonEntity;
import org.safetynet.api.mappers.MedicalRecordMapper;
import org.safetynet.api.mappers.PersonMapper;
import org.safetynet.api.model.*;
import org.safetynet.api.repository.MedicalRecordRepository;
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
import java.text.Format;
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
public class PersonServiceTests {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private MedicalRecordMapper medicalRecordMapper;

    @Autowired
    private JSONReader jsonReader;

    @MockBean
    private List<PersonEntity> personEntityList;

    @BeforeEach
    void loadObjects() throws IOException {
        personEntityList = jsonReader.loadPersons();
        personRepository.loadData();
    }

    @AfterEach
    void garbageObjects() throws IOException {
        personEntityList = null;
    }

    @Test
    void firestationShouldReturnPersonEntityListTest() throws Exception {

        List<PersonEntity> list = this.personEntityList.stream().filter(e -> e.getIdFireStation().equals("1")).toList();
        List<Person> personsList = new ArrayList<>();

        for (PersonEntity cursePersonEntity : list
        ) {
            Person person = new PersonBuilder()
                    .withId(cursePersonEntity.getFirstName(),cursePersonEntity.getLastName())
                    .withFirstName(cursePersonEntity.getFirstName())
                    .withLastName(cursePersonEntity.getLastName())
                    .withPhone(cursePersonEntity.getPhone())
                    .withEMail(cursePersonEntity.getEMail())
                    .withAddress(cursePersonEntity.getAddress())
                    .withCity(cursePersonEntity.getCity())
                    .withZip(cursePersonEntity.getZip())
                    .withBirthDateDate(cursePersonEntity.getBirthDate())
                    .withFireStation(cursePersonEntity.getIdFireStation())
                    .withMedicalRecord(cursePersonEntity.getFirstName(),cursePersonEntity.getLastName())
                    .build();
            personsList.add(person);

        }
        PersonsFireStation personsFireStation = new PersonsFireStation(personsList,5,1);

        List<PersonEntity> dataPersonEntity = personRepository.getAllPersonsByStationNumber("1");
        List<Person> dataPerson = personMapper.convertToDtoList(dataPersonEntity);

        if(dataPerson!=null) {

            Date todayDate = new Date();
            Format formatter = new SimpleDateFormat("dd/MM/yyyy");
            String sTodayDate = formatter.format(todayDate);
            DateFormat formatterInDate = new SimpleDateFormat("dd/MM/yyyy");
            Date formattedDate = formatterInDate.parse(sTodayDate);

            int minorNumber = 0;
            int adultNumber = 0;

            for (Person cursePerson : dataPerson
            ) {

                long ageCalcul = formattedDate.getTime() - cursePerson.getBirthDate().getTime();
                double yearsNumber = ageCalcul / 3.15576e+10;
                int age = (int) Math.floor(yearsNumber);

                if (age > 18) {
                    adultNumber += 1;
                } else {
                    minorNumber += 1;
                }

            }
            PersonsFireStation personsFireStationGet = new PersonsFireStation(dataPerson, adultNumber, minorNumber);

            assertEquals(6,list.size());
            assertEquals(personsFireStation.getPersons(),personsFireStationGet.getPersons());
        }


    }

    @Test
    void childAlertShouldReturnChildrenListTest() throws Exception {

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date formattedDate = formatter.parse("17/02/2012");

        List<PersonEntity> list = this.personEntityList.stream().filter(e -> e.getBirthDate().after(formattedDate) && e.getAddress().equals("1509 Culver St")).toList();
        List<Person> personsList = new ArrayList<>();

        for (PersonEntity cursePersonEntity : list
        ) {
            Person person = new PersonBuilder()
                    .withId(cursePersonEntity.getFirstName(),cursePersonEntity.getLastName())
                    .withFirstName(cursePersonEntity.getFirstName())
                    .withLastName(cursePersonEntity.getLastName())
                    .withPhone(cursePersonEntity.getPhone())
                    .withEMail(cursePersonEntity.getEMail())
                    .withAddress(cursePersonEntity.getAddress())
                    .withCity(cursePersonEntity.getCity())
                    .withZip(cursePersonEntity.getZip())
                    .withBirthDateDate(cursePersonEntity.getBirthDate())
                    .withFireStation(cursePersonEntity.getIdFireStation())
                    .withMedicalRecord(cursePersonEntity.getFirstName(),cursePersonEntity.getLastName())
                    .build();
            personsList.add(person);

        }

        List<PersonEntity> dataChildren = personRepository.getAllByAddressAndBirthDate("1509 Culver St",formattedDate);
        List<Person> personsListGet = personMapper.convertToDtoList(dataChildren);

        Date todayDate = new Date();
        Format formatterGet = new SimpleDateFormat("dd/MM/yyyy");
        String sTodayDate = formatterGet.format(todayDate);
        DateFormat formatterInDate = new SimpleDateFormat("dd/MM/yyyy");
        Date formattedDateGet = formatterInDate.parse(sTodayDate);

        List<PersonsChildren> childrenListGet = new ArrayList<PersonsChildren>();

        for (Person cursePerson : personsListGet
        ) {

            List<PersonEntity> childrenFamily = personRepository.getChildrenFamily(cursePerson.getFirstName(),cursePerson.getLastName(), "1509 Culver St", cursePerson.getZip());
            List<Person> personsFamilyList = personMapper.convertToDtoList(childrenFamily);

            long ageCalcul = formattedDateGet.getTime() - cursePerson.getBirthDate().getTime();
            double yearsNumber = ageCalcul / 3.15576e+10;
            int age = (int) Math.floor(yearsNumber);
            String ageInString = String.valueOf(age)+ " ans";

            childrenListGet.add(new PersonsChildren(cursePerson.getFirstName(),cursePerson.getLastName(),ageInString,personsFamilyList));
        }

        assertEquals(personsList.size(),childrenListGet.size());
    }

    @Test
    void phoneAlertShouldReturnPhonesListTest() throws Exception {

        List<PersonEntity> list = this.personEntityList.stream().filter(e -> e.getIdFireStation().equals("1")).toList();
        List<Person> personsList = new ArrayList<>();

        for (PersonEntity cursePersonEntity : list
        ) {
            Person person = new PersonBuilder()
                    .withId(cursePersonEntity.getFirstName(),cursePersonEntity.getLastName())
                    .withFirstName(cursePersonEntity.getFirstName())
                    .withLastName(cursePersonEntity.getLastName())
                    .withPhone(cursePersonEntity.getPhone())
                    .withEMail(cursePersonEntity.getEMail())
                    .withAddress(cursePersonEntity.getAddress())
                    .withCity(cursePersonEntity.getCity())
                    .withZip(cursePersonEntity.getZip())
                    .withBirthDateDate(cursePersonEntity.getBirthDate())
                    .withFireStation(cursePersonEntity.getIdFireStation())
                    .withMedicalRecord(cursePersonEntity.getFirstName(),cursePersonEntity.getLastName())
                    .build();
            personsList.add(person);

        }

        List<PersonEntity> dataPhoneNumber = personRepository.getAllPhoneNumberByStation("1");
        List<Person> personsListGet = personMapper.convertToDtoList(dataPhoneNumber);

        assertEquals(personsList,personsListGet);
    }

    @Test
    void fireShouldReturnPersonLivingToTest() throws Exception {

        List<PersonEntity> list = this.personEntityList.stream().filter(e -> e.getAddress().equals("112 Steppes Pl")).toList();
        List<Person> personsList = new ArrayList<>();

        for (PersonEntity cursePersonEntity : list
        ) {
            Person person = new PersonBuilder()
                    .withId(cursePersonEntity.getFirstName(),cursePersonEntity.getLastName())
                    .withFirstName(cursePersonEntity.getFirstName())
                    .withLastName(cursePersonEntity.getLastName())
                    .withPhone(cursePersonEntity.getPhone())
                    .withEMail(cursePersonEntity.getEMail())
                    .withAddress(cursePersonEntity.getAddress())
                    .withCity(cursePersonEntity.getCity())
                    .withZip(cursePersonEntity.getZip())
                    .withBirthDateDate(cursePersonEntity.getBirthDate())
                    .withFireStation(cursePersonEntity.getIdFireStation())
                    .withMedicalRecord(cursePersonEntity.getFirstName(),cursePersonEntity.getLastName())
                    .build();
            personsList.add(person);

        }

        List<PersonEntity> dataPersonsLivingTo = personRepository.getAllLivingTo("112 Steppes Pl");
        List<Person> personsListGet = personMapper.convertToDtoList(dataPersonsLivingTo);

        Date todayDate = new Date();
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        String sTodayDate = formatter.format(todayDate);
        DateFormat formatterInDate = new SimpleDateFormat("dd/MM/yyyy");
        Date formattedDate = formatterInDate.parse(sTodayDate);

        List<PersonsFire> personsLivingToList = new ArrayList<PersonsFire>();

        for (Person cursePerson : personsList
        ) {

            Optional<MedicalRecordEntity> medicalRecordEntity = medicalRecordRepository.findById(cursePerson.getIdMedicalRecord());
            MedicalRecord medicalRecordPerson = medicalRecordMapper.convertToMedicalRecord(medicalRecordEntity);

            long ageCalcul = formattedDate.getTime() - cursePerson.getBirthDate().getTime();
            double yearsNumber = ageCalcul / 3.15576e+10;
            int age = (int) Math.floor(yearsNumber);
            String ageInString = String.valueOf(age) + " ans";

            personsLivingToList.add(new PersonsFire(cursePerson.getFirstName(),cursePerson.getLastName(),ageInString,medicalRecordPerson.getMedications(),medicalRecordPerson.getAllergies()));

        }

        List<PersonsFire> personsLivingToListGet = new ArrayList<PersonsFire>();

        for (Person cursePerson : personsListGet
        ) {

            Optional<MedicalRecordEntity> medicalRecordEntity = medicalRecordRepository.findById(cursePerson.getIdMedicalRecord());
            MedicalRecord medicalRecordPerson = medicalRecordMapper.convertToMedicalRecord(medicalRecordEntity);

            long ageCalcul = formattedDate.getTime() - cursePerson.getBirthDate().getTime();
            double yearsNumber = ageCalcul / 3.15576e+10;
            int age = (int) Math.floor(yearsNumber);
            String ageInString = String.valueOf(age) + " ans";

            personsLivingToListGet.add(new PersonsFire(cursePerson.getFirstName(),cursePerson.getLastName(),ageInString,medicalRecordPerson.getMedications(),medicalRecordPerson.getAllergies()));

        }

        assertEquals(personsLivingToList.getClass(),personsLivingToListGet.getClass());
    }

    @Test
    void floodShouldReturnPersonsByHomeTest() throws Exception {
        List<String> stationNumbersList = new ArrayList<>();
        int personsNumber = 0;
        stationNumbersList.add("2");
        stationNumbersList.add("3");

        List<List<PersonEntity>> personsCorrespondentToStationNumbers = new ArrayList<List<PersonEntity>>();
        for (String stationInCurse: stationNumbersList
        ) {
            personsCorrespondentToStationNumbers.add(this.personEntityList.stream().filter(e -> e.getIdFireStation().equals(stationInCurse)).toList());

        }

        for (List<PersonEntity> cursePersonCorrespondent : personsCorrespondentToStationNumbers
        ) {
            for (PersonEntity cursePerson : cursePersonCorrespondent
            ) {
                personsNumber++;
            }
        }

        List<List<PersonEntity>> dataPersonsCorrespondentToStationNumbers = personRepository.getAllCorrespondentToStationNumbers(stationNumbersList);
        List<List<Person>> personsList = personMapper.convertToDtoListList(dataPersonsCorrespondentToStationNumbers);

        Date todayDate = new Date();
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        String sTodayDate = formatter.format(todayDate);
        DateFormat formatterInDate = new SimpleDateFormat("dd/MM/yyyy");
        Date formattedDate = formatterInDate.parse(sTodayDate);

        List<PersonsFlood> correspondentPersonsList = new ArrayList<PersonsFlood>();

        for (List<Person> cursePersonList : personsList
        ) {
            for (Person cursePerson : cursePersonList
            ) {
                Optional<MedicalRecordEntity> medicalRecordEntity = medicalRecordRepository.findById(cursePerson.getIdMedicalRecord());
                MedicalRecord medicalRecordPerson = medicalRecordMapper.convertToMedicalRecord(medicalRecordEntity);

                long ageCalcul = formattedDate.getTime() - cursePerson.getBirthDate().getTime();
                double yearsNumber = ageCalcul / 3.15576e+10;
                int age = (int) Math.floor(yearsNumber);
                String ageInString = String.valueOf(age) + " ans";

                PersonsFloodSub personsFloodSub = new PersonsFloodSub(cursePerson.getFirstName(), cursePerson.getLastName(), ageInString, cursePerson.getPhone(), medicalRecordPerson.getMedications(), medicalRecordPerson.getAllergies());
                correspondentPersonsList.add(new PersonsFlood(cursePerson.getAddress(), cursePerson.getZip(), cursePerson.getCity(), personsFloodSub));
            }
        }

        assertEquals(personsNumber,correspondentPersonsList.size());

    }

    @Test
    void personInfoShouldReturnPersonDetailsTest() throws Exception {

        List<PersonEntity> list = this.personEntityList.stream().filter(e -> e.getId().equals("Tony"+"Cooper")).toList();
        List<Person> personsList = new ArrayList<>();

        for (PersonEntity cursePersonEntity : list
        ) {
            Person person = new PersonBuilder()
                    .withId(cursePersonEntity.getFirstName(),cursePersonEntity.getLastName())
                    .withFirstName(cursePersonEntity.getFirstName())
                    .withLastName(cursePersonEntity.getLastName())
                    .withPhone(cursePersonEntity.getPhone())
                    .withEMail(cursePersonEntity.getEMail())
                    .withAddress(cursePersonEntity.getAddress())
                    .withCity(cursePersonEntity.getCity())
                    .withZip(cursePersonEntity.getZip())
                    .withBirthDateDate(cursePersonEntity.getBirthDate())
                    .withFireStation(cursePersonEntity.getIdFireStation())
                    .withMedicalRecord(cursePersonEntity.getFirstName(),cursePersonEntity.getLastName())
                    .build();
            personsList.add(person);

        }

        List<PersonsPersonInfo> personInfoList = new ArrayList<PersonsPersonInfo>();

        List<PersonEntity> dataPersonsNaming = personRepository.getAllNaming("Tony","Cooper");
        List<Person> personsListGet = personMapper.convertToDtoList(dataPersonsNaming);
        List<PersonsPersonInfo> personInfoListGet = new ArrayList<PersonsPersonInfo>();

        Date todayDate = new Date();
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        String sTodayDate = formatter.format(todayDate);
        DateFormat formatterInDate = new SimpleDateFormat("dd/MM/yyyy");
        Date formattedDate = formatterInDate.parse(sTodayDate);

        for (Person cursePerson : personsList
        ) {

            Optional<MedicalRecordEntity> medicalRecordEntity = medicalRecordRepository.findById(cursePerson.getIdMedicalRecord());
            MedicalRecord medicalRecordPerson = medicalRecordMapper.convertToMedicalRecord(medicalRecordEntity);

            long ageCalcul = formattedDate.getTime() - cursePerson.getBirthDate().getTime();
            double yearsNumber = ageCalcul / 3.15576e+10;
            int age = (int) Math.floor(yearsNumber);
            String ageInString = String.valueOf(age) + " ans";

            personInfoList.add(new PersonsPersonInfo(cursePerson.getFirstName(),cursePerson.getLastName(),cursePerson.getAddress(),cursePerson.getZip(),cursePerson.getCity(),ageInString,cursePerson.getEMail(),medicalRecordPerson.getMedications(),medicalRecordPerson.getAllergies()));

        }

        for (Person cursePerson : personsListGet
        ) {

            Optional<MedicalRecordEntity> medicalRecordEntity = medicalRecordRepository.findById(cursePerson.getIdMedicalRecord());
            MedicalRecord medicalRecordPerson = medicalRecordMapper.convertToMedicalRecord(medicalRecordEntity);

            long ageCalcul = formattedDate.getTime() - cursePerson.getBirthDate().getTime();
            double yearsNumber = ageCalcul / 3.15576e+10;
            int age = (int) Math.floor(yearsNumber);
            String ageInString = String.valueOf(age) + " ans";

            personInfoListGet.add(new PersonsPersonInfo(cursePerson.getFirstName(),cursePerson.getLastName(),cursePerson.getAddress(),cursePerson.getZip(),cursePerson.getCity(),ageInString,cursePerson.getEMail(),medicalRecordPerson.getMedications(),medicalRecordPerson.getAllergies()));

        }

        assertEquals(personInfoList.getClass(),personInfoListGet.getClass());
    }

    @Test
    void communityEmailShouldReturnEmailsListTest() throws Exception {

        List<PersonEntity> list = this.personEntityList.stream().filter(e -> e.getCity().equals("Culver")).toList();
        List<Person> emailsList = new ArrayList<>();

        for (PersonEntity cursePersonEntity : list
        ) {
            Person person = new PersonBuilder()
                    .withId(cursePersonEntity.getFirstName(),cursePersonEntity.getLastName())
                    .withFirstName(cursePersonEntity.getFirstName())
                    .withLastName(cursePersonEntity.getLastName())
                    .withPhone(cursePersonEntity.getPhone())
                    .withEMail(cursePersonEntity.getEMail())
                    .withAddress(cursePersonEntity.getAddress())
                    .withCity(cursePersonEntity.getCity())
                    .withZip(cursePersonEntity.getZip())
                    .withBirthDateDate(cursePersonEntity.getBirthDate())
                    .withFireStation(cursePersonEntity.getIdFireStation())
                    .withMedicalRecord(cursePersonEntity.getFirstName(),cursePersonEntity.getLastName())
                    .build();
            emailsList.add(person);

        }

        List<PersonEntity> dataCityAddressMails = personRepository.getAllEmailsListToCity("Culver");
        List<Person> emailsListGet = personMapper.convertToDtoList(dataCityAddressMails);

        assertEquals(emailsList,emailsListGet);
    }

    @Test
    void shouldPostPersonTest() throws ParseException {
        String birthDateInString = "21/09/1994";
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date birthDate = formatter.parse(birthDateInString);

        Person addedPerson = new PersonBuilder()
                .withId("Antoine","Lilier")
                .withFirstName("Antoine")
                .withLastName("Lilier")
                .withAddress("15 rue des fleurs")
                .withZip("75001")
                .withCity("Paris")
                .withEMail("alilierd@email.com")
                .withPhone("123-456-7890")
                .withBirthDateDate(birthDate)
                .withFireStation("")
                .withMedicalRecord("Antoine","Lilier")
                .build();

        PersonEntity personEntity = personMapper.convertToEntity(addedPerson);
        PersonEntity dataPerson = personRepository.postElement(personEntity);
        Person createdPerson  = personMapper.convertToPerson(dataPerson);

        assertEquals(addedPerson,createdPerson);
    }

    @Test
    void shouldPatchPersonTest() throws ParseException {
        String birthDateInString = "21/09/1994";
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date birthDate = formatter.parse(birthDateInString);

        Person modifiedPerson = new PersonBuilder()
                .withId("John", "Boyd")
                .withFirstName("John")
                .withLastName("Boyd")
                .withAddress("19 Culver St")
                .withZip("97451")
                .withCity("Culver")
                .withEMail("jaboyd@email.com")
                .withPhone("841-874")
                .withBirthDateDate(birthDate)
                .withFireStation("")
                .withMedicalRecord("John", "Boyd")
                .build();

        PersonEntity personEntity = personMapper.convertToEntity(modifiedPerson);
        PersonEntity dataPerson = personRepository.patchElement("JohnBoyd", personEntity);
        Person updatedPerson = personMapper.convertToPerson(dataPerson);


        assertEquals(modifiedPerson, updatedPerson);
    }
}
