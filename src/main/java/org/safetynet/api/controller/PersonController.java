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

    /**
     *
     * To create a Person data in JSON
     *
     */
    @PostMapping(value="/person", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<MappingJacksonValue> postPerson(@RequestBody Person person) throws Exception {//public List<Person>getListPersonWithStationNumber(){
        log.info("postPerson PostMapping request");
        MappingJacksonValue addedPerson = null;

        try {
            addedPerson = personService.postPerson(person);
            log.info("postPerson PostMapping request with success "+ addedPerson.getValue());
            return new ResponseEntity<>(addedPerson, HttpStatus.CREATED);
        } catch(Exception e) {
            log.error("postPerson PostMapping request : error", e);
            return new ResponseEntity<>(addedPerson, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * To do a Person partial updating and see the result in JSON data
     *
     */
    /**/@PatchMapping(value="/persons/{id}", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<MappingJacksonValue> patchPerson(@PathVariable("id") String id, @RequestBody Person person) throws Exception {
        log.info("patchPerson PatchMapping request");
        MappingJacksonValue updatedPerson = null;

        try {
            updatedPerson = personService.patchPerson(id,person);
            log.info("patchPerson PatchMapping request with success "+ updatedPerson.getValue());
            return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
        } catch(Exception e) {
            log.error("patchPerson PatchMapping request : error", e);
            return new ResponseEntity<>(updatedPerson, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * To delete a Person object and see the empty result
     *
     */
    @DeleteMapping(value="/persons/{id}", produces = {"application/json"})
    public ResponseEntity<Void> deletePerson(@PathVariable("id") String id) throws Exception {
        log.info("deletePerson DeleteMapping request");

        try{
            personService.deletePerson(id);
            log.info("deletePerson DeleteMapping request with success "+ id);
            return ResponseEntity.ok().build();
        }catch(Exception e) {
            log.error("patchPerson PatchMapping request : error", e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     *
     * @param stationNumber To get all the persons corresponding to this station number
     * @param personResponse To display the result in JSON format
     *
     */
    @GetMapping(value="/firestation", produces = {"application/json"})
    public ResponseEntity<MappingJacksonValue> findAll(@RequestParam("station_number") String stationNumber) throws ParseException {
        log.info("findAll GetMapping request");
        MappingJacksonValue personResponse = null;

        try {
            if ((stationNumber != null) && (!stationNumber.isEmpty())) {
                personResponse = personService.getListPersonWithStationNumber(stationNumber);
                log.info("findAll GetMapping request with success "+ personResponse.getValue());
            }

            return ResponseEntity.status(HttpStatus.OK).body(personResponse);

        }catch(Exception e){
            log.info("findAll GetMapping request : error", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(personResponse);
        }
    }

    /**
     *
     * @param address To get all the persons corresponding to this address
     * @param formattedDate And also evaluate their age
     * @param personsListSet To display the result in JSON format
     *
     */
    @GetMapping(value="/childAlert", produces = {"application/json"})
    public ResponseEntity<MappingJacksonValue> getListChildren18orless(@RequestParam("address") String address, @RequestParam("birthdate") String birthDate) throws Exception {
        log.info("getListChildren18orless GetMapping request");
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date formattedDate = formatter.parse(birthDate);

        MappingJacksonValue personsListSet = null;

        try {
            personsListSet = personService.getListChildren18orless(address, formattedDate);
            log.info("getListChildren18orless GetMapping request with success "+ personsListSet.getValue());
            return ResponseEntity.status(HttpStatus.OK).body(personsListSet);
        }catch(Exception e){
            log.error("getListChildren18orless GetMapping request : error ", e);
            return ResponseEntity.status(HttpStatus.OK).body(personsListSet);
        }
    }

    /**
     *
     * @param firestation_number To get the persons phone numbers corresponding to this firestation number
     * @param phonesList To display the result in JSON format
     *
     */
    @GetMapping(value="/phoneAlert", produces = {"application/json"})
    public ResponseEntity<MappingJacksonValue> getListPhoneNumber(@RequestParam("firestation") String firestation_number) throws Exception {
        log.info("getListPhoneNumber GetMapping request");
        MappingJacksonValue phonesList = null;

        try{
            phonesList = personService.getListPhoneNumber(firestation_number);
            log.info("getListPhoneNumber GetMapping request with succes "+ phonesList.getValue());
            return ResponseEntity.status(HttpStatus.OK).body(phonesList);
        }catch(Exception e){
            log.error("getListPhoneNumber GetMapping request : error", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(phonesList);
        }
    }

    /**
     *
     * @param address To get the persons list living to this address
     * @param persons To display the result in JSON format
     *
     */
    @GetMapping(value="/fire", produces = {"application/json"})
    public ResponseEntity<MappingJacksonValue> getListPersonsLivingTo(@RequestParam("address") String address ) throws Exception {
        log.info("getListPersonsLivingTo GetMapping request");
        MappingJacksonValue persons = null;

        try{
            persons = personService.getListPersonsLivingTo(address);
            log.info("getListPersonsLivingTo GetMapping request with succes "+ persons.getValue());
            return ResponseEntity.status(HttpStatus.OK).body(persons);
        }catch(Exception e){
            log.error("getListPersonsLivingTo GetMapping request : error", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(persons);
        }

    }

    /**
     *
     * @param stationNumbers To get the persons lists corresponding to these station numbers
     * @param personsCorrespondentToStationNumbers To display the result in JSON format
     *
     */
    @GetMapping(value="/flood", produces = {"application/json"})
    public ResponseEntity<MappingJacksonValue> getListPersonsCorrespondentToStationNumbers(@RequestParam("station_numbers") List<String> stationNumbers) throws Exception {
        log.info("getListPersonsCorrespondentToStationNumbers GetMapping request");
        MappingJacksonValue personsCorrespondentToStationNumbers = null;

        try{
            personsCorrespondentToStationNumbers = personService.getListPersonsCorrespondentToStationNumbers(stationNumbers);
            log.info("getListPersonsCorrespondentToStationNumbers GetMapping request with success "+ personsCorrespondentToStationNumbers.getValue());
            return ResponseEntity.status(HttpStatus.OK).body(personsCorrespondentToStationNumbers);
        }catch(Exception e){
            log.error("getListPersonsCorrespondentToStationNumbers GetMapping request : error", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(personsCorrespondentToStationNumbers);
        }

    }

    /**
     *
     * @param firstName To get the persons list having this firstname
     * @param lastName And having this lastname
     * @param personsNaming To display the result in JSON format
     *
     */
    @GetMapping(value="/personInfo", produces = {"application/json"})
    public ResponseEntity<MappingJacksonValue> getListPersonsNaming(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) throws Exception {
        log.info("getListPersonsNaming GetMapping request");
        MappingJacksonValue personsNaming = null;

        try {
            personsNaming = personService.getListPersonsNaming(firstName, lastName);
            log.info("getListPersonsNaming GetMapping request with success "+ personsNaming.getValue());
            return ResponseEntity.status(HttpStatus.OK).body(personsNaming);
        }catch(Exception e){
            log.error("getListPersonsNaming GetMapping request : error ",e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(personsNaming);
        }
    }

    /**
     *
     * @param city To get the persons list linving in this city
     * @param cityAddressMails To display the result in JSON format
     *
     */
    @GetMapping(value="/communityEmail", produces = {"application/json"})
    public ResponseEntity<MappingJacksonValue> getAddressMailsListToCity(@RequestParam("city") String city) throws Exception {
        log.info("getAddressMailsListToCity GetMapping request");
        MappingJacksonValue cityAddressMails = null;

        try{
            cityAddressMails = personService.getEmailsListToCity(city);
            log.info("getAddressMailsListToCity GetMapping request with success "+cityAddressMails.getValue());
            return ResponseEntity.status(HttpStatus.OK).body(cityAddressMails);
        }catch(Exception e){
            log.error("getAddressMailsListToCity GetMapping request : error",e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cityAddressMails);
        }
    }
}
