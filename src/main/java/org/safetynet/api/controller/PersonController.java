package org.safetynet.api.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.extern.slf4j.Slf4j;
import org.safetynet.api.entity.PersonEntity;
import org.safetynet.api.model.Person;
import org.safetynet.api.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
@Slf4j
@RestController()
public class PersonController
{
    @Autowired
    private PersonService personService;

    @GetMapping(value="/persons?firestation={station_number}", produces = {"application/json"})
    public ResponseEntity findAll(@RequestParam("station_number") String stationNumber) {
        List<Person> allPerson;
        if(stationNumber!=null && !stationNumber.isEmpty()){
            allPerson = personService.getListPersonWithStationNumber(stationNumber);
        }
        else allPerson = personService.findAll();
        log.info("personService.postPerson with success");
        return new ResponseEntity<>(allPerson, HttpStatus.OK);
    }

    @PostMapping(value="/persons", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity postPerson(@RequestBody Person person) throws Exception {//public List<Person>getListPersonWithStationNumber(){
        Person addedPerson = personService.postPerson(person);
        log.info("personService.postPerson with success");
        return new ResponseEntity<>(addedPerson, HttpStatus.CREATED);
    }

    @PatchMapping(value="/persons/{id}", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity patchPerson(@PathVariable("id") String id, @RequestBody Person person) throws Exception {
        Person updatedPerson = personService.patchPerson(id,person);
        log.info("personService.patchPerson with success");
        return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
    }

    @DeleteMapping(value="/persons/{id}", produces = {"application/json"})
    public ResponseEntity deletePerson(@PathVariable("id") String id) throws Exception {
        Person deletedPerson = personService.deletePerson(id);
        log.info("personService.deletePerson with success");
        return new ResponseEntity<>(deletedPerson, HttpStatus.OK);
    }

    //TODO: j'ai changé l'url vous ne pouvez pas utiliser "firestation/id" alors que dans FIrestationConroller vous faite la meme chose
    // le probleme c'est que c'est la meme url que le get persons mais il s'agit d'un filtre
    // de plus vous utilisez à des moments @pathVarialble au lieu de @RequestParam
    /*@GetMapping(value="/persons?firestation={station_number}", produces = {"application/json"})
    public ResponseEntity<List<Person>> getListPersonWithStationNumber(@RequestParam("station_number") String station_number) throws Exception {
        List<Person> persons = personService.getListPersonWithStationNumber(station_number);
        log.info("Requete getListPersonWithStationNumber ok");
        return ResponseEntity.status(HttpStatus.OK).body(persons);
    }*/



    /*@GetMapping(value="/childAlert?address={address}&birthdate={birthdate}", produces = {"application/json"})
    public ResponseEntity<List<Person>> getListChildren18orless(@RequestParam("address") String address, @RequestParam("birthdate") String birthDate) throws Exception {
        DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        Date formatedDate = formatter.parse(birthDate);

        List<Person> persons = personService.getListChildren18orless(address, formatedDate);
        int childrenNb = persons.size();
        return ResponseEntity.status(HttpStatus.OK).body(persons);
    }*/

    /*@GetMapping(value="/childAlert", produces = {"application/json"})
    public ResponseEntity<List<Person>> getListChildren18orless(@RequestParam("address") String address, @RequestParam("birthdate") String birthDate) throws Exception {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date formattedDate = formatter.parse(birthDate);

        List<Person> persons = personService.getListChildren18orless(address, formattedDate);
        int childrenNb = persons.size();
        //List<List<Person>> personsListSet = new List
        return ResponseEntity.status(HttpStatus.OK).body(persons);
    }*/
    @GetMapping(value="/childAlert", produces = {"application/json"})
    public ResponseEntity<Hashtable<String, List<Person>>> getListChildren18orless(@RequestParam("address") String address, @RequestParam("birthdate") String birthDate) throws Exception {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date formattedDate = formatter.parse(birthDate);

        //List<Person> persons = personService.getListChildren18orless(address, formattedDate);
        //int childrenNb = persons.size();
        Hashtable<String, List<Person>> personsListSet = personService.getListChildren18orless(address, formattedDate);
        return ResponseEntity.status(HttpStatus.OK).body(personsListSet);
    }
    //@GetMapping(value="/phoneAlert?firestation={firestation_number}", produces = {"application/json"})
    @GetMapping(value="/phoneAlert", produces = {"application/json"})
    public ResponseEntity<Hashtable<String, String>> getListPhoneNumber(@RequestParam("firestation") String firestation_number) throws Exception {
        Hashtable<String, String> phonesList = personService.getListPhoneNumber(firestation_number);
        return ResponseEntity.status(HttpStatus.OK).body(phonesList);
    }

    @GetMapping(value="/fire?address={address}", produces = {"application/json"})
    public ResponseEntity<List<Person>> getListPersonsLivingTo(@RequestParam("address") String address) throws Exception {
        List<Person> persons = personService.getListPersonsLivingTo(address);
        return ResponseEntity.status(HttpStatus.OK).body(persons);
    }

    @GetMapping(value="/flood?stations={station_numbers}", produces = {"application/json"})
    public ResponseEntity<List<Person>> getListPersonsCorrespondentToStationNumbers(@RequestParam("station_numbers") List<String> stationNumbers) throws Exception {
        List<Person> personsCorrespondentToStationNumbers = personService.getListPersonsCorrespondentToStationNumbers(stationNumbers);
        return ResponseEntity.status(HttpStatus.OK).body(personsCorrespondentToStationNumbers);
    }

    @GetMapping(value="/personInfo?firstName={firstName}&lastName={lastName}", produces = {"application/json"})
    public ResponseEntity<List<Person>> getListPersonsNaming(@RequestParam("firstName") String firstName, @PathVariable("lastName") String lastName) throws Exception {
        List<Person> personsNaming = personService.getListPersonsNaming(firstName,lastName);
        return ResponseEntity.status(HttpStatus.OK).body(personsNaming);
    }

    @GetMapping(value="/communityEmail?city={city}", produces = {"application/json"})
    public ResponseEntity<List<Person>> getAddressMailsListToCity(@RequestParam("city") String city) throws Exception {
        List<Person> cityAddressMails = personService.getAddressMailsListToCity(city);
        return ResponseEntity.status(HttpStatus.OK).body(cityAddressMails);
    }
}
