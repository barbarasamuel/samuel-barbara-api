package org.safetynet.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.safetynet.api.model.MedicalRecord;
import org.safetynet.api.model.Person;
import org.safetynet.api.model.PersonsChildren;
import org.safetynet.api.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController()
public class PersonController
{
    @Autowired
    private PersonService personService;


    @PostMapping(value="/person", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<MappingJacksonValue> postPerson(@RequestBody Person person) throws Exception {//public List<Person>getListPersonWithStationNumber(){
        MappingJacksonValue addedPerson = personService.postPerson(person);
        log.info("personService.postPerson with success");
        return new ResponseEntity<>(addedPerson, HttpStatus.CREATED);
    }

    /**/@PatchMapping(value="/persons/{id}", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<MappingJacksonValue> patchPerson(@PathVariable("id") String id, @RequestBody Person person) throws Exception {
        MappingJacksonValue updatedPerson = personService.patchPerson(id,person);
        log.info("personService.patchPerson with success");
        return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
    }

    @DeleteMapping(value="/persons/{id}", produces = {"application/json"})
    public ResponseEntity<Void> deletePerson(@PathVariable("id") String id) throws Exception {
        personService.deletePerson(id);
        log.info("personService.deletePerson with success");
        return ResponseEntity.ok().build();
    }

    //TODO: j'ai changé l'url vous ne pouvez pas utiliser "firestation/id" alors que dans FIrestationConroller vous faite la meme chose
    // le probleme c'est que c'est la meme url que le get persons mais il s'agit d'un filtre
    // de plus vous utilisez à des moments @pathVarialble au lieu de @RequestParam

    @GetMapping(value="/firestation", produces = {"application/json"})
    public ResponseEntity<MappingJacksonValue> findAll(@RequestParam("station_number") String stationNumber) throws ParseException {

        MappingJacksonValue personResponse = null;
        if((stationNumber!=null) && (!stationNumber.isEmpty())){
            personResponse = personService.getListPersonWithStationNumber(stationNumber);
        }

        log.info("personService.postPerson with success");
        return ResponseEntity.status(HttpStatus.OK).body(personResponse);
    }

    @GetMapping(value="/childAlert", produces = {"application/json"})
    public ResponseEntity<MappingJacksonValue> getListChildren18orless(@RequestParam("address") String address, @RequestParam("birthdate") String birthDate) throws Exception {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date formattedDate = formatter.parse(birthDate);

        MappingJacksonValue personsListSet = personService.getListChildren18orless(address, formattedDate);
        return ResponseEntity.status(HttpStatus.OK).body(personsListSet);
    }

    @GetMapping(value="/phoneAlert", produces = {"application/json"})
    public ResponseEntity<MappingJacksonValue> getListPhoneNumber(@RequestParam("firestation") String firestation_number) throws Exception {
        MappingJacksonValue phonesList = personService.getListPhoneNumber(firestation_number);

        return ResponseEntity.status(HttpStatus.OK).body(phonesList);
    }

    @GetMapping(value="/fire", produces = {"application/json"})
    public ResponseEntity<MappingJacksonValue> getListPersonsLivingTo(@RequestParam("address") String address ) throws Exception {
        MappingJacksonValue persons = personService.getListPersonsLivingTo(address);
        return ResponseEntity.status(HttpStatus.OK).body(persons);
    }

    @GetMapping(value="/flood", produces = {"application/json"})
    public ResponseEntity<MappingJacksonValue> getListPersonsCorrespondentToStationNumbers(@RequestParam("station_numbers") List<String> stationNumbers) throws Exception {
        MappingJacksonValue personsCorrespondentToStationNumbers = personService.getListPersonsCorrespondentToStationNumbers(stationNumbers);
        return ResponseEntity.status(HttpStatus.OK).body(personsCorrespondentToStationNumbers);
    }

    @GetMapping(value="/personInfo", produces = {"application/json"})
    public ResponseEntity<MappingJacksonValue> getListPersonsNaming(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) throws Exception {
        MappingJacksonValue personsNaming = personService.getListPersonsNaming(firstName,lastName);
        return ResponseEntity.status(HttpStatus.OK).body(personsNaming);
    }

    @GetMapping(value="/communityEmail", produces = {"application/json"})
    public ResponseEntity<MappingJacksonValue> getAddressMailsListToCity(@RequestParam("city") String city) throws Exception {
        MappingJacksonValue cityAddressMails = personService.getEmailsListToCity(city);
        return ResponseEntity.status(HttpStatus.OK).body(cityAddressMails);
    }
}
