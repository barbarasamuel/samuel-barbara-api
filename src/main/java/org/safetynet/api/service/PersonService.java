package org.safetynet.api.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.safetynet.api.entity.FireStationEntity;
import org.safetynet.api.entity.PersonEntity;
import org.safetynet.api.mappers.PersonMapper;
import org.safetynet.api.model.Person;
import org.safetynet.api.repository.FireStationRepository;
import org.safetynet.api.repository.GenericRepository;
import org.safetynet.api.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private PersonMapper personMapper;

    public List<Person> findAll(){
        return personMapper.convertToDtoList(personRepository.getFindAll());
    }

    public Person postPerson(Person addedPerson){

        PersonEntity personEntity = personMapper.convertToEntity(addedPerson);
        PersonEntity dataPerson = personRepository.postElement(personEntity);
        addedPerson  = personMapper.convertToPerson(dataPerson);

        return addedPerson;
    }

    public Person patchPerson(String id,Person updatedPerson){
        PersonEntity personEntity = personMapper.convertToEntity(updatedPerson);
        PersonEntity dataPerson = personRepository.patchElement(id,personEntity);
        updatedPerson  = personMapper.convertToPerson(dataPerson);
        return updatedPerson;
    }

    public Person deletePerson(String id){
        //PersonEntity personEntity = personMapper.convertToEntity(updatedPerson);
        PersonEntity dataPerson = personRepository.deleteElement(id);
        Person updatedPerson  = personMapper.convertToPerson(dataPerson);
        return updatedPerson;
    }

    public List<Person> getListPersonWithStationNumber(String station) {
            FireStationEntity fireStation = fireStationRepository.findById(station).orElse(null);
            if(fireStation!=null){
                List<PersonEntity> dataPerson = personRepository.getAllPersonsByStationNumber(fireStation);
                log.info("getListPersonWithStationNumber method ok");
                return personMapper.convertToDtoList(dataPerson);
            }
            return List.of();
    }


    /*public List<Person> getListChildren18orless(String address, Date birthDate) throws Exception {
        List<PersonEntity> dataChildren = personRepository.getAllByAddressAndBirthDate(address,birthDate);
        return personMapper.convertToDtoList(dataChildren);
    }*/
    public Hashtable<String, List<Person>> getListChildren18orless(String address, Date birthDate) throws Exception {
        List<PersonEntity> dataChildren = personRepository.getAllByAddressAndBirthDate(address,birthDate);
        List<Person> personsList = personMapper.convertToDtoList(dataChildren);
        Hashtable<String, List<Person>> personsTable= new Hashtable<String,List<Person>>();
        Date todayDate = new Date();
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        String sTodayDate = formatter.format(todayDate);
        DateFormat formatterInDate = new SimpleDateFormat("dd/MM/yyyy");
        Date formattedDate = formatterInDate.parse(sTodayDate);


        for (Person cursePerson : personsList
             ) {

            List<PersonEntity> childrenFamily = personRepository.getChildrenFamily(cursePerson.getFirstName(),cursePerson.getLastName(), address, cursePerson.getZip());
            List<Person> personsFamilyList = personMapper.convertToDtoList(childrenFamily);

            long ageCalcul = formattedDate.getTime() - cursePerson.getBirthDate().getTime();
            double yearsNumber = ageCalcul / 3.15576e+10;
            int age = (int) Math.floor(yearsNumber);

            personsTable.put(cursePerson.getFirstName()+" "+cursePerson.getLastName()+ " " + age + " ans", personsFamilyList);
        }


        return personsTable;
    }

    public Hashtable<String, String> getListPhoneNumber(String stationNumber) {
        List<PersonEntity> dataPhoneNumber = personRepository.getAllPhoneNumberByStation(stationNumber);
        List<Person> personsList = personMapper.convertToDtoList(dataPhoneNumber);
        Hashtable<String, String> phonesTable= new Hashtable<String,String>();
        for (Person cursePerson : personsList
        ) {
            phonesTable.put(cursePerson.getFirstName()+" "+cursePerson.getLastName(), cursePerson.getPhone());
        }

        return phonesTable;
    }

    public List<Person> getListPersonsLivingTo(String address) {
        List<PersonEntity> dataPersonsLivingTo = personRepository.getAllLivingTo(address);
        return personMapper.convertToDtoList(dataPersonsLivingTo);
    }

    public List<Person> getListPersonsCorrespondentToStationNumbers(List<String> stationNumbers) throws Exception {
        List<PersonEntity> dataPersonsCorrespondentToStationNumbers = personRepository.getAllCorrespondentToStationNumbers(stationNumbers);
        return personMapper.convertToDtoList(dataPersonsCorrespondentToStationNumbers);
    }

    public List<Person> getListPersonsNaming(String firstName,String lastName) throws Exception {
        List<PersonEntity> dataPersonsNaming = personRepository.getAllNaming(firstName,lastName);
        return personMapper.convertToDtoList(dataPersonsNaming);
    }

    public List<Person> getAddressMailsListToCity(String city) throws Exception {
        List<PersonEntity> dataCityAddressMails = personRepository.getAllAddressMailsListToCity(city);
        return personMapper.convertToDtoList(dataCityAddressMails);
    }
}