package org.safetynet.api.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public List<FireStationEntity> loadFireStations() throws IOException{

        List<FireStationEntity> fireStations = new ArrayList<>();
        //Map<String,String> fireStations = null;
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

                    //FireStationEntity fireStation = new FireStationEntity(station, address);;
                    FireStationEntity fireStation = new FireStationEntityBuilder()
                            .withId(station)
                            .withAddress(address)
                            .build();
                    fireStations.add(fireStation);
                    System.out.println(fireStation.getAddress()+ " " +fireStation.getId());
                    //fireStations.put(fireStation.getStation(),fireStation.getAddress());
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
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
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
                    String medications = medicalRecordNode.get("medications").asText();
                    String allergies = medicalRecordNode.get("allergies").asText();

                    //birthDateInString = birthDateInString.replace("/","-");
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date birthDate = formatter.parse(birthDateInString);

                    //MedicalRecordEntity medicalRecord = new MedicalRecordEntity(firstName+lastName,firstName,lastName,birthDate,medications,allergies);;
                    MedicalRecordEntity medicalRecord = new MedicalRecordEntityBuilder()
                            .withId(firstName,lastName)
                            .withFirstName(firstName)
                            .withLastName(lastName)
                            .withBirthDateDate(birthDate)
                            .withMedications(medications)
                            .withAllergies(allergies)
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
        /*String station = "";
        Date birthDate = new Date();
        String idMedicalRecord = "";*/


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
                        //PersonEntity person = new PersonEntity(firstName+lastName,firstName,lastName,address,zip,city,email,phone,birthDate,station,idMedicalRecord);
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

    /*@Autowired
    private ReadService readService;
    public PersonEntity convertJSONToJava() throws JsonProcessingException, Exception {
    //Pour convertir données JSON en Objet Java
        List<PersonEntity> personsList = new ArrayList<PersonEntity>();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPersonsArray = readService.loadFile();
        personsList = objectMapper.readValue(jsonPersonsArray, new TypeReference<List<PersonEntity>>() {
        });
        return (PersonEntity) personsList;
    }
    public String convertJavaToJSON(PersonEntity personsList) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPersonsList = objectMapper.writeValueAsString(personsList);
        return jsonPersonsList;
    }*/


}
