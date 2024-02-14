package org.safetynet.api.controller;

import org.safetynet.api.entity.PersonEntity;
import org.safetynet.api.model.Person;
import org.safetynet.api.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class PersonController
{
    @Autowired
    private PersonService personService;


    @PostMapping("/person")
    public ResponseEntity postPerson(@RequestBody Person person) throws Exception {//public List<Person>getListPersonWithStationNumber(){
        Person addedPerson = personService.postPerson(person);
        return new ResponseEntity<>(addedPerson, HttpStatus.CREATED);
    }

    //@GetMapping(value = "personsJson", produces = { MediaType.APPLICATION_JSON_VALUE })
    //@RequestMapping(method=RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE }, value="/firestation?stationNumber={station_number}")
    @GetMapping("/firestation?stationNumber={station_number}")
    public Iterable<PersonEntity> getListPersonWithStationNumber(@PathVariable("station_number") String station_number) throws Exception {//public List<Person>getListPersonWithStationNumber(){
        Iterable<PersonEntity> persons = personService.getListPersonWithStationNumber(station_number);
        return persons;
    }

    @GetMapping("/childAlert?address={address}&birthdate>={birthdate}")
    public Iterable<PersonEntity> getListChildren18orless(@PathVariable("address") String address, @PathVariable("birthdate") Date birthdate) throws Exception {
        Iterable<PersonEntity> persons = personService.getListChildren18orless(address);
        return persons;
    }
  /*
    @RequestMapping(method= RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE }, value="/firestation?stationNumber={station_number}")
    public List<Person> getListPersonWithStationNumber(@PathVariable("station_number"),int station_number){
        List<Person> list = personService.getListPersonWithStationNumber();
        return list;
    }
    */
  @GetMapping("/phoneAlert?firestation={firestation_number}")
  public Iterable<PersonEntity> getListPhoneNumber(@PathVariable("firestation_number") String firestation_number) throws Exception {
      Iterable<PersonEntity> phoneNumbers = personService.getListPhoneNumber(firestation_number);
      return phoneNumbers;
  }

    @GetMapping("/fire?address={address}")
    public Iterable<PersonEntity> getListPersonsLivingTo(@PathVariable("address") String address) throws Exception {
        Iterable<PersonEntity> personsLivingTo = personService.getListPersonsLivingTo(address);
        return personsLivingTo;
    }

    @GetMapping("/flood/stations?stations={station_numbers}")
    public Iterable<PersonEntity> getListPersonsCorrespondentToStationNumbers(@PathVariable("station_numbers") List<String> station_numbers) throws Exception {
        Iterable<PersonEntity> personsCorrespondentToStationNumbers = personService.getListPersonsCorrespondentToStationNumbers(station_numbers);
        return personsCorrespondentToStationNumbers;
    }

    @GetMapping("/personInfo?firstName={firstName}&lastName={lastName}")
    public Iterable<PersonEntity> getListPersonsNaming(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) throws Exception {
        Iterable<PersonEntity> personsNaming = personService.getListPersonsNaming(firstName,lastName);
        return personsNaming;
    }

    @GetMapping("/communityEmail?city={city}")
    public Iterable<PersonEntity> getListCityAddressMails(@PathVariable("city") String city) throws Exception {
        Iterable<PersonEntity> cityAddressMails = personService.getListCityAddressMails(city);
        return cityAddressMails;
    }
}
