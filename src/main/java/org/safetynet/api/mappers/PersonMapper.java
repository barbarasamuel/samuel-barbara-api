package org.safetynet.api.mappers;

import org.safetynet.api.builders.PersonBuilder;
import org.safetynet.api.builders.PersonEntityBuilder;
import org.safetynet.api.entity.PersonEntity;
import org.safetynet.api.model.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonMapper {
    public PersonEntity convertToEntity(Person person){
        return new PersonEntityBuilder()
                .withId(person.getFirstName(),person.getLastName())
                .withFirstName(person.getFirstName())
                .withLastName(person.getLastName())
                .withPhone(person.getPhone())
                .withEMail(person.getEMail())
                .withAddress(person.getAddress())
                .withCity(person.getCity())
                .withZip(person.getZip())
                .withBirthDateDate(person.getBirthDate())
                .withFireStation("")
                .withMedicalRecord("","")
                //.withFireStation(person.getFireStation().getStation())//identifiant de FireStation
                //.withMedicalRecord(person.getMedicalRecord().getFirstName(),person.getMedicalRecord().getLastName())
                .build();
    }

    public Person convertToPerson(PersonEntity personEntity){

        return new PersonBuilder()
                .withId(personEntity.getFirstName(),personEntity.getLastName())
                .withFirstName(personEntity.getFirstName())
                .withLastName(personEntity.getLastName())
                .withPhone(personEntity.getPhone())
                .withEMail(personEntity.getEMail())
                .withAddress(personEntity.getAddress())
                .withCity(personEntity.getCity())
                .withZip(personEntity.getZip())
                .withBirthDateDate(personEntity.getBirthDate())
                .withFireStation(personEntity.getIdFireStation())
                .withMedicalRecord(personEntity.getFirstName(),personEntity.getLastName())
                .build();
    }

    public List<Person> convertToDtoList(List<PersonEntity> personEntities){
        return personEntities.stream().map(this::convertToPerson).toList();
    }

    public List<List<Person>> convertToDtoListList(List<List<PersonEntity>> personEntities){
        List<List<Person>> personsList = new ArrayList<List<Person>>();
        for (List<PersonEntity> personEntityListInCurse:personEntities
             ) {
            personsList.add(personEntityListInCurse.stream().map(this::convertToPerson).toList());
        }
        return personsList;
    }
    public List<PersonEntity> convertToEntityList(List<Person> persons){
        return persons.stream().map(this::convertToEntity).toList();
    }
}
