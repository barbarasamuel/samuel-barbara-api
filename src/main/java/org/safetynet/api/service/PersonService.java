package org.safetynet.api.service;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.extern.slf4j.Slf4j;
import org.safetynet.api.entity.MedicalRecordEntity;
import org.safetynet.api.entity.PersonEntity;
import org.safetynet.api.mappers.MedicalRecordMapper;
import org.safetynet.api.mappers.PersonMapper;
import org.safetynet.api.model.MedicalRecord;
import org.safetynet.api.model.Person;
import org.safetynet.api.model.PersonsFireStation;
import org.safetynet.api.repository.FireStationRepository;
import org.safetynet.api.repository.MedicalRecordRepository;
import org.safetynet.api.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;

@Slf4j
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    MedicalRecordMapper medicalRecordMapper;

    @Autowired
    private FireStationRepository fireStationRepository;



    public List<Person> findAll(){
        return personMapper.convertToDtoList(personRepository.getFindAll());
    }

    public Person postPerson(Person addedPerson){

        PersonEntity personEntity = personMapper.convertToEntity(addedPerson);
        PersonEntity dataPerson = personRepository.postElement(personEntity);
        addedPerson  = personMapper.convertToPerson(dataPerson);

        return addedPerson;
    }
/*
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
    }*/

    public MappingJacksonValue getListPersonWithStationNumber(String station) throws ParseException {

            List<PersonEntity> dataPersonEntity = personRepository.getAllPersonsByStationNumber(station);
            List<Person> dataPerson = personMapper.convertToDtoList(dataPersonEntity);

            if(dataPerson!=null){

                Date todayDate = new Date();
                Format formatter = new SimpleDateFormat("dd/MM/yyyy");
                String sTodayDate = formatter.format(todayDate);
                DateFormat formatterInDate = new SimpleDateFormat("dd/MM/yyyy");
                Date formattedDate = formatterInDate.parse(sTodayDate);

                int minorNumber = 0;
                int majorNumber = 0;

                for (Person cursePerson : dataPerson
                ) {

                    long ageCalcul = formattedDate.getTime() - cursePerson.getBirthDate().getTime();
                    double yearsNumber = ageCalcul / 3.15576e+10;
                    int age = (int) Math.floor(yearsNumber);

                    if(age>18){
                        majorNumber += 1;
                    }else{
                        minorNumber += 1;
                    }
                    //sDataPerson.add(cursePerson.getFirstName() + " " + cursePerson.getLastName() + " " + cursePerson.getAddress() + " " + cursePerson.getPhone() );

                }

                SimpleBeanPropertyFilter filtrePersons = SimpleBeanPropertyFilter.filterOutAllExcept("firstName","lastName","address","phone");
                FilterProvider listeDeNosFiltres = new SimpleFilterProvider().addFilter("filtreDynamiquePerson", filtrePersons);
                MappingJacksonValue personsFiltres = new MappingJacksonValue(new PersonsFireStation(dataPerson,majorNumber,minorNumber));
                personsFiltres.setFilters(listeDeNosFiltres);

                log.info("getListPersonWithStationNumber method ok");
                return personsFiltres;
                //return new PersonResponse(dataPerson,majorNumber,minorNumber);//personMapper.convertToDtoList(dataPerson);
            }else{
                return null;
            }
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

    public MappingJacksonValue getListPhoneNumber(String stationNumber) {
        List<PersonEntity> dataPhoneNumber = personRepository.getAllPhoneNumberByStation(stationNumber);
        List<Person> personsList = personMapper.convertToDtoList(dataPhoneNumber);

        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("id","email","birthDate","address","zip","city","idFireStation","idMedicalRecord");
        FilterProvider listeDeNosFiltres = new SimpleFilterProvider().addFilter("filtreDynamiquePerson", monFiltre);
        MappingJacksonValue phoneNumbersFiltres = new MappingJacksonValue(personsList);
        phoneNumbersFiltres.setFilters(listeDeNosFiltres);

        return phoneNumbersFiltres;
    }

    public Hashtable<String, MedicalRecord> getListPersonsLivingTo(String address) throws ParseException {
        List<PersonEntity> dataPersonsLivingTo = personRepository.getAllLivingTo(address);
        //return personMapper.convertToDtoList(dataPersonsLivingTo);
        List<Person> personsList = personMapper.convertToDtoList(dataPersonsLivingTo);
        Hashtable<String, MedicalRecord> personsTable= new Hashtable<String, MedicalRecord>();
        Date todayDate = new Date();
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        String sTodayDate = formatter.format(todayDate);
        DateFormat formatterInDate = new SimpleDateFormat("dd/MM/yyyy");
        Date formattedDate = formatterInDate.parse(sTodayDate);


        for (Person cursePerson : personsList
        ) {
            //MedicalRecordEntity medicalRecordEntity = medicalRecordRepository.findById(cursePerson.getIdMedicalRecord());
            Optional<MedicalRecordEntity> medicalRecordEntity = medicalRecordRepository.findById(cursePerson.getIdMedicalRecord());//getMedicationsAllergies(cursePerson.getFirstName(),cursePerson.getLastName());//(cursePerson.getFirstName(),cursePerson.getLastName());
            MedicalRecord medicalRecordPerson = medicalRecordMapper.convertToDtoMedicalRecord(medicalRecordEntity);

            long ageCalcul = formattedDate.getTime() - cursePerson.getBirthDate().getTime();
            double yearsNumber = ageCalcul / 3.15576e+10;
            int age = (int) Math.floor(yearsNumber);

            /*SimpleBeanPropertyFilter filtreMedicationsAllergies = SimpleBeanPropertyFilter.filterOutAllExcept("firstName","lastName","medications","allergies");
            FilterProvider listeDeNosFiltres = new SimpleFilterProvider().addFilter("filtreDynamiqueMedicalRecord", filtreMedicationsAllergies);
            MappingJacksonValue medicationsAllergiesFiltres = new MappingJacksonValue(medicalRecordPerson);
            medicationsAllergiesFiltres.setFilters(listeDeNosFiltres);*/

            personsTable.put(cursePerson.getFirstName()+" "+cursePerson.getLastName()+ " " + age + " ans", medicalRecordPerson);
        }


        return personsTable;
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