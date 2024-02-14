package org.safetynet.api.service;

import lombok.Data;
import org.safetynet.api.entity.PersonEntity;
import org.safetynet.api.mappers.PersonMapper;
import org.safetynet.api.model.Person;
import org.safetynet.api.repository.FireStationRepository;
import org.safetynet.api.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
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
        Person createdPerson = personMapper.convertToPerson(dataPerson);
        return createdPerson;
    }

    public Iterable<PersonEntity>getListPersonWithStationNumber(String station) throws Exception {
        List<PersonEntity> dataPerson = personRepository.getFindAll();
        //List<FireStationEntity> dataFireStation = fireStationRepository.getFindAll();

        return dataPerson;
    }


    public Iterable<PersonEntity>getListChildren18orless(String address) throws Exception {
        List<PersonEntity> dataChildren = personRepository.getFindAll();
        //List<FireStationEntity> dataFireStation = fireStationRepository.getFindAll();
        return dataChildren;
    }

    public Iterable<PersonEntity> getListPhoneNumber(String station) throws Exception {
        List<PersonEntity> dataPhoneNumber = personRepository.getFindAll();

        return dataPhoneNumber;
    }

    public Iterable<PersonEntity> getListPersonsLivingTo(String address) throws Exception {
        List<PersonEntity> dataPersonsLivingTo = personRepository.getFindAll();

        return dataPersonsLivingTo;
    }

    public Iterable<PersonEntity> getListPersonsCorrespondentToStationNumbers(List<String> stationNumbers) throws Exception {
        List<PersonEntity> dataPersonsCorrespondentToStationNumbers = personRepository.getFindAll();

        return dataPersonsCorrespondentToStationNumbers;
    }

    public Iterable<PersonEntity> getListPersonsNaming(String firstName,String lastName) throws Exception {
        List<PersonEntity> dataPersonsNaming = personRepository.getFindAll();

        return dataPersonsNaming;
    }

    public Iterable<PersonEntity> getListCityAddressMails(String city) throws Exception {
        List<PersonEntity> dataCityAddressMails = personRepository.getFindAll();

        return dataCityAddressMails;
    }
}