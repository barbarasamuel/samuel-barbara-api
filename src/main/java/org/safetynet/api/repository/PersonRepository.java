package org.safetynet.api.repository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
//import org.safetynet.api.entity.FireStationEntity;
import org.safetynet.api.entity.PersonEntity;
//import org.safetynet.api.model.Person;
import org.safetynet.api.tools.JSONReader;
//import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;

//import java.time.Instant;
//import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import static java.util.stream.Collectors.toList;
@Slf4j
@Repository
public class PersonRepository extends GenericRepository<PersonEntity,String> {
    @Autowired
    private JSONReader jsonReader;

    @PostConstruct
   public void loadData() throws IOException {
       try{
           this.data = jsonReader.loadPersons();
           log.info("loadPersons with success");
       }catch(IOException e){
           log.error("loadPersons failed");
       }
   }

    public List<PersonEntity> getAllPersonsByStationNumber(String station) {

        return this.data.stream().filter(e -> e.getIdFireStation().equals(station)).toList();

    }

    public List<PersonEntity> getAllByAddressAndBirthDate(String value, Date birthDate) {
        return this.data.stream().filter(e -> e.getBirthDate().after(birthDate) && e.getAddress().equals(value)).toList();

    }

    public List<PersonEntity> getChildrenFamily(String firstName, String lastName, String address, String zip){
        return this.data.stream().filter(e -> e.getLastName().equals(lastName) && e.getAddress().equals(address) && e.getZip().equals(zip) && !e.getFirstName().equals(firstName)).toList();
    }

    public List<PersonEntity> getAllPhoneNumberByStation(String stationNumber){
        return this.data.stream().filter(e -> e.getIdFireStation().equals(stationNumber)).toList();
    }

    public List<PersonEntity> getAllLivingTo(String address){
        return this.data.stream().filter(e -> e.getAddress().equals(address)).toList();
    }

    public List<List<PersonEntity>> getAllCorrespondentToStationNumbers(List<String> stationNumbers){

        List<List<PersonEntity>> personsCorrespondentToStationNumbers = new ArrayList<List<PersonEntity>>();
        for (String stationInCurse: stationNumbers
             ) {
            personsCorrespondentToStationNumbers.add(this.data.stream().filter(e -> e.getIdFireStation().equals(stationInCurse)).toList());

        }

        return personsCorrespondentToStationNumbers;

    }

    public List<PersonEntity> getAllNaming(String firstName,String lastName){//} throws Exception {
        return this.data.stream().filter(e -> e.getId().equals(firstName+lastName)).toList();
    }

    public List<PersonEntity> getAllEmailsListToCity(String city){
        return this.data.stream().filter(e -> e.getCity().equals(city)).toList();
    }


}