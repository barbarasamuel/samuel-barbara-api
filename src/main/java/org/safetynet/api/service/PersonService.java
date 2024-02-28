package org.safetynet.api.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.safetynet.api.entity.PersonEntity;
import org.safetynet.api.mappers.PersonMapper;
import org.safetynet.api.model.Person;
import org.safetynet.api.repository.FireStationRepository;
import org.safetynet.api.repository.GenericRepository;
import org.safetynet.api.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private PersonMapper personMapper;


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

    public Iterable<PersonEntity>getListPersonWithStationNumber(String station) throws Exception {
        try {
            List<PersonEntity> dataPerson = personRepository.getAllWithStationNumber(station);
            log.info("getListPersonWithStationNumber method ok");
            return dataPerson;
        }catch(Exception e){
            List<PersonEntity> dataPerson = personRepository.getAllWithStationNumber(station);
            log.error("getListPersonWithStationNumber method failed ", e);
            return dataPerson;
        }
    }


    public Iterable<PersonEntity>getListChildren18orless(String address, Date birthDate) throws Exception {

        List<PersonEntity> dataChildren = personRepository.getAllByAddressAndBirthDate(address,birthDate);

        return dataChildren;
    }

    public Iterable<PersonEntity> getListPhoneNumber(String station) {
        List<PersonEntity> dataPhoneNumber = personRepository.getAllPhoneNumberByStation(station);

        return dataPhoneNumber;
    }

    public Iterable<PersonEntity> getListPersonsLivingTo(String address) {
        List<PersonEntity> dataPersonsLivingTo = personRepository.getAllLivingTo(address);

        return dataPersonsLivingTo;
    }

    public Iterable<PersonEntity> getListPersonsCorrespondentToStationNumbers(List<String> stationNumbers) throws Exception {
        List<PersonEntity> dataPersonsCorrespondentToStationNumbers = personRepository.getAllCorrespondentToStationNumbers(stationNumbers);

        return dataPersonsCorrespondentToStationNumbers;
    }

    public Iterable<PersonEntity> getListPersonsNaming(String firstName,String lastName) throws Exception {
        List<PersonEntity> dataPersonsNaming = personRepository.getAllNaming(firstName,lastName);

        return dataPersonsNaming;
    }

    public Iterable<PersonEntity> getAddressMailsListToCity(String city) throws Exception {
        List<PersonEntity> dataCityAddressMails = personRepository.getAllAddressMailsListToCity(city);

        return dataCityAddressMails;
    }
}