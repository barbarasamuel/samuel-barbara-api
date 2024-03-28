package org.safetynet.api.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.extern.slf4j.Slf4j;
import org.safetynet.api.builders.*;
import org.safetynet.api.entity.FireStationEntity;
import org.safetynet.api.entity.MedicalRecordEntity;
import org.safetynet.api.entity.PersonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.safetynet.api.constants.PathsConstants.READFILEPATH;
@Slf4j
@Component
public class JSONReader {

    @Autowired
    private ResourceLoader resourceLoader;
    //Pour convertir les données JSON en Objets Java
    public List<FireStationEntity> loadFireStations() throws IOException{

        List<FireStationEntity> fireStations = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Resource resource = resourceLoader.getResource(READFILEPATH);
            InputStream inputStream = resource.getInputStream();
            JsonNode rootNode = objectMapper.readTree(inputStream);

            JsonNode fireStationsNode = rootNode.get("firestations");

            if (fireStationsNode != null && fireStationsNode.isArray()) {
                for (JsonNode fireStationNode : fireStationsNode) {
                    String station = fireStationNode.get("station").asText();
                    String address = fireStationNode.get("address").asText();

                    FireStationEntity fireStation = new FireStationEntityBuilder()
                            .withId(station)
                            .withAddress(address)
                            .build();
                    fireStations.add(fireStation);
                    //System.out.println(fireStation.getAddress()+ " " +fireStation.getId());

                }
            }
            log.info("The fireStations list is loaded." + fireStations.size() + " firestations found.");
        }catch(IOException e){
            log.error("Error loading the fireStations list.", e);


        }
        return fireStations;
    }
    public List<MedicalRecordEntity> loadMedicalRecords() throws IOException {
        List<MedicalRecordEntity> medicalRecords = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Resource resource = resourceLoader.getResource(READFILEPATH);
            assert resource != null;
            InputStream inputStream = resource.getInputStream();
            JsonNode rootNode = objectMapper.readTree(inputStream);

            JsonNode medicalRecordsNode = rootNode.get("medicalrecords");

            if (medicalRecordsNode != null && medicalRecordsNode.isArray()) {
                for (JsonNode medicalRecordNode : medicalRecordsNode) {
                    String firstName = medicalRecordNode.get("firstName").asText();
                    String lastName = medicalRecordNode.get("lastName").asText();
                    String birthDateInString = medicalRecordNode.get("birthdate").asText();
                    List<String> medicationsList = new ArrayList<>();
                    ArrayNode medicationsNode = (ArrayNode) medicalRecordNode.get("medications");
                    List<String> allergiesList = new ArrayList<>();
                    ArrayNode allergiesNode = (ArrayNode) medicalRecordNode.get("allergies");

                    for (JsonNode curseMedication : medicationsNode
                         ) {
                        medicationsList.add(String.valueOf(curseMedication));
                    }

                    for (JsonNode curseAllergie : allergiesNode
                    ) {
                        allergiesList.add(String.valueOf(curseAllergie));
                    }

                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date birthDate = formatter.parse(birthDateInString);
                    /*for (String curseMed: medicationsList
                         ) {
                        System.out.println(curseMed);
                    }
                    for (String curseAll: allergiesList
                         ) {
                        System.out.println(curseAll);
                    }*/

                    MedicalRecordEntity medicalRecord = new MedicalRecordEntityBuilder()
                            .withId(firstName,lastName)
                            .withFirstName(firstName)
                            .withLastName(lastName)
                            .withBirthDateDate(birthDate)
                            .withMedications(medicationsList)
                            .withAllergies(allergiesList)
                            .build();
                    medicalRecords.add(medicalRecord);
                    System.out.println(medicalRecord.getId()+ " " +medicalRecord.getMedications());
                }

            }

            log.info("The medicalRecords list is loaded.");
        }catch(IOException e){
            log.error("Error loading the medicalRecords list.", e);


        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return medicalRecords;
    }
    public List<PersonEntity> loadPersons() throws IOException {
        List<PersonEntity> persons = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        InputStream inputStream;

        try {
            List<FireStationEntity> fireStationList = loadFireStations();
            List<MedicalRecordEntity> medicalRecordList = loadMedicalRecords();

            Resource resource = resourceLoader.getResource(READFILEPATH);
            if(resource != null){
                inputStream = resource.getInputStream();
                JsonNode rootNode = objectMapper.readTree(inputStream);

                JsonNode personsNode = rootNode.get("persons");

                if (personsNode != null && personsNode.isArray()) {
                    for (JsonNode personNode : personsNode) {
                        String firstName = personNode.get("firstName").asText();
                        String lastName = personNode.get("lastName").asText();
                        String address = personNode.get("address").asText();
                        String city = personNode.get("city").asText();
                        String zip = personNode.get("zip").asText();
                        String phone = personNode.get("phone").asText();
                        String email = personNode.get("email").asText();
                        String station = null;
                        Date birthDate = null;
                        String medicalRecordFirstName = null;
                        String medicalRecordLastName = null;

                        for (FireStationEntity fireStation : fireStationList) {
                            if(Objects.equals(fireStation.getAddress(), address)) {
                                station = fireStation.getId();
                                break;
                            }
                        }

                        for (MedicalRecordEntity medicalRecord : medicalRecordList) {
                            if(Objects.equals(medicalRecord.getId(), (firstName+lastName))) {
                                medicalRecordFirstName = medicalRecord.getFirstName();
                                medicalRecordLastName = medicalRecord.getLastName();
                                birthDate = medicalRecord.getBirthDate();
                                break;
                            }
                        }

                        PersonEntity person = new PersonEntityBuilder()
                                .withId(firstName,lastName)
                                .withFirstName(firstName)
                                .withLastName(lastName)
                                .withPhone(phone)
                                .withEMail(email)
                                .withAddress(address)
                                .withCity(city)
                                .withZip(zip)
                                .withBirthDateDate(birthDate)
                                .withFireStation(station)
                                .withMedicalRecord(medicalRecordFirstName,medicalRecordLastName)
                                .build();
                        persons.add(person);
                    }
                }
            }
            log.info("The persons list is loaded.");
        }catch(IOException e){
            log.error("Error loading the persons list.", e);

        }
        return persons;
    }
}
