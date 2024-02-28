package org.safetynet.api.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.safetynet.api.entity.PersonEntity;
import org.safetynet.api.model.Person;
import org.safetynet.api.service.PersonService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Slf4j
@RestController
public class PersonController
{
    @Autowired
    private PersonService personService;


    @PostMapping("/person")
    public ResponseEntity postPerson(@RequestBody Person person) throws Exception {//public List<Person>getListPersonWithStationNumber(){
        try{
            Person addedPerson = personService.postPerson(person);
            log.info("personService.postPerson with success");
            return new ResponseEntity<>(addedPerson, HttpStatus.CREATED);
        }catch(Exception e){
            Person addedPerson = personService.postPerson(person);
            log.error("personService.postPerson failed",e);
            return new ResponseEntity<>(addedPerson, HttpStatus.FAILED_DEPENDENCY);
        }
    }

    @PatchMapping("/person/{id}")
    public ResponseEntity patchPerson(@PathVariable("id") String id, @RequestBody Person person) throws Exception {
        try{
            Person updatedPerson = personService.patchPerson(id,person);
            log.info("personService.patchPerson with success");
            return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
        }catch(Exception e){
            log.error("personService.patchPerson failed",e);
            Person updatedPerson = personService.patchPerson(id,person);
            return new ResponseEntity<>(updatedPerson, HttpStatus.FAILED_DEPENDENCY);
        }
    }

    @DeleteMapping("/person/{id}")
    public ResponseEntity patchPerson(@PathVariable("id") String id) throws Exception {
        try{
            Person deletedPerson = personService.deletePerson(id);
            log.info("personService.deletePerson with success");
            return new ResponseEntity<>(deletedPerson, HttpStatus.OK);
        }catch(Exception e){
            log.error("personService.deletePerson failed",e);
            Person deletedPerson = personService.deletePerson(id);
            return new ResponseEntity<>(deletedPerson, HttpStatus.FAILED_DEPENDENCY);
        }
    }

    //@GetMapping(value = "personsJson", produces = { MediaType.APPLICATION_JSON_VALUE })
    //@RequestMapping(method=RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE }, value="/firestation?stationNumber={station_number}")
    @GetMapping("/firestation?stationNumber={station_number}")
    //public Iterable<PersonEntity> getListPersonWithStationNumber(@PathVariable("station_number") String station_number) throws Exception {//public List<Person>getListPersonWithStationNumber(){
    public MappingJacksonValue getListPersonWithStationNumber(@PathVariable("station_number") String station_number) throws Exception {
        try {
            Iterable<PersonEntity> persons = personService.getListPersonWithStationNumber(station_number);
            SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("id", "eMail", "birthDate", "idFireStation", "idMedicalRecord");
            FilterProvider listeDeNosFiltres = new SimpleFilterProvider().addFilter("filtreDynamiquePersonEntity", monFiltre);
            MappingJacksonValue personsFiltres = new MappingJacksonValue(persons);
            personsFiltres.setFilters(listeDeNosFiltres);
            log.info("Requete getListPersonWithStationNumber ok");
            return ResponseEntity.status(HttpStatus.OK).body(personsFiltres).getBody();
        }catch(Exception e){
            log.error("Requete getListPersonWithStationNumber failed ", e);
            Iterable<PersonEntity> persons = personService.getListPersonWithStationNumber(station_number);
            MappingJacksonValue personsFiltres = new MappingJacksonValue(persons);
            return ResponseEntity.status(HttpStatus.OK).body(personsFiltres).getBody();
        }
    }

    @GetMapping("/childAlert?address={address}&birthdate>={birthdate}")
    public MappingJacksonValue getListChildren18orless(@PathVariable("address") String address, @PathVariable("birthdate") Date birthdate) throws Exception {
        Iterable<PersonEntity> persons = personService.getListChildren18orless(address,birthdate);
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("id","eMail","birthDate","phone","address","zip","city","idFireStation","idMedicalRecord");
        FilterProvider listeDeNosFiltres = new SimpleFilterProvider().addFilter("filtreDynamiquePersonEntity", monFiltre);
        MappingJacksonValue personsFiltres = new MappingJacksonValue(persons);
        personsFiltres.setFilters(listeDeNosFiltres);
        return personsFiltres;
    }

  @GetMapping("/phoneAlert?firestation={firestation_number}")
  public MappingJacksonValue getListPhoneNumber(@PathVariable("firestation_number") String firestation_number) throws Exception {
      Iterable<PersonEntity> phoneNumbers = personService.getListPhoneNumber(firestation_number);
      SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("id","firstName","lastName","eMail","birthDate","phone","address","zip","city","idFireStation","idMedicalRecord");
      FilterProvider listeDeNosFiltres = new SimpleFilterProvider().addFilter("filtreDynamiquePersonEntity", monFiltre);
      MappingJacksonValue phoneNumbersFiltres = new MappingJacksonValue(phoneNumbers);
      phoneNumbersFiltres.setFilters(listeDeNosFiltres);
      return phoneNumbersFiltres;
  }

    @GetMapping("/fire?address={address}")
    public MappingJacksonValue getListPersonsLivingTo(@PathVariable("address") String address) throws Exception {
        Iterable<PersonEntity> personsLivingTo = personService.getListPersonsLivingTo(address);
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("id","firstName","eMail","birthDate","address","zip","city","idFireStation","idMedicalRecord");
        FilterProvider listeDeNosFiltres = new SimpleFilterProvider().addFilter("filtreDynamiquePersonEntity", monFiltre);
        MappingJacksonValue personsLivingToFiltres = new MappingJacksonValue(personsLivingTo);
        personsLivingToFiltres.setFilters(listeDeNosFiltres);
        /////////////
        //List<PersonEntity> personEntityList = new ArrayList<PersonEntity>();
        //personEntityList.add(dataPerson);
        ////////////

        return personsLivingToFiltres;
    }

    @GetMapping("/flood/stations?stations={station_numbers}")
    public MappingJacksonValue getListPersonsCorrespondentToStationNumbers(@PathVariable("station_numbers") List<String> station_numbers) throws Exception {
        Iterable<PersonEntity> personsCorrespondentToStationNumbers = personService.getListPersonsCorrespondentToStationNumbers(station_numbers);
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("id","firstName","eMail","birthDate","address","zip","city","idFireStation","idMedicalRecord");
        FilterProvider listeDeNosFiltres = new SimpleFilterProvider().addFilter("filtreDynamiquePersonEntity", monFiltre);
        MappingJacksonValue personsCorrespondentToStationNumbersFiltres = new MappingJacksonValue(personsCorrespondentToStationNumbers);
        personsCorrespondentToStationNumbersFiltres.setFilters(listeDeNosFiltres);
        return personsCorrespondentToStationNumbersFiltres;
    }

    @GetMapping("/personInfo?firstName={firstName}&lastName={lastName}")
    public MappingJacksonValue getListPersonsNaming(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) throws Exception {
        Iterable<PersonEntity> personsNaming = personService.getListPersonsNaming(firstName,lastName);
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("id","birthDate","idFireStation","idMedicalRecord");
        FilterProvider listeDeNosFiltres = new SimpleFilterProvider().addFilter("filtreDynamiquePersonEntity", monFiltre);
        MappingJacksonValue personsNamingFiltres = new MappingJacksonValue(personsNaming);
        personsNamingFiltres.setFilters(listeDeNosFiltres);
        return personsNamingFiltres;
    }

    @GetMapping("/communityEmail?city={city}")
    public MappingJacksonValue getAddressMailsListToCity(@PathVariable("city") String city) throws Exception {
        Iterable<PersonEntity> cityAddressMails = personService.getAddressMailsListToCity(city);
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("id","firstName","lastName","birthDate","phone","address","zip","idFireStation","idMedicalRecord");
        FilterProvider listeDeNosFiltres = new SimpleFilterProvider().addFilter("filtreDynamiquePersonEntity", monFiltre);
        MappingJacksonValue cityAddressMailsFiltres = new MappingJacksonValue(cityAddressMails);
        cityAddressMailsFiltres.setFilters(listeDeNosFiltres);
        return cityAddressMailsFiltres;
    }
}
