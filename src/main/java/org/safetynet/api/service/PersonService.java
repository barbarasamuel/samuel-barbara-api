package org.safetynet.api.service;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.extern.slf4j.Slf4j;
import org.safetynet.api.entity.MedicalRecordEntity;
import org.safetynet.api.entity.PersonEntity;
import org.safetynet.api.mappers.MedicalRecordMapper;
import org.safetynet.api.mappers.PersonMapper;
import org.safetynet.api.model.*;
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

            }else{
                return null;
            }
    }


    public MappingJacksonValue getListChildren18orless(String address, Date birthDate) throws Exception {
        List<PersonEntity> dataChildren = personRepository.getAllByAddressAndBirthDate(address,birthDate);
        List<Person> personsList = personMapper.convertToDtoList(dataChildren);

        Date todayDate = new Date();
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        String sTodayDate = formatter.format(todayDate);
        DateFormat formatterInDate = new SimpleDateFormat("dd/MM/yyyy");
        Date formattedDate = formatterInDate.parse(sTodayDate);

        List<PersonsChildren> childrenList = new ArrayList<PersonsChildren>();

        for (Person cursePerson : personsList
             ) {

            List<PersonEntity> childrenFamily = personRepository.getChildrenFamily(cursePerson.getFirstName(),cursePerson.getLastName(), address, cursePerson.getZip());
            List<Person> personsFamilyList = personMapper.convertToDtoList(childrenFamily);

            long ageCalcul = formattedDate.getTime() - cursePerson.getBirthDate().getTime();
            double yearsNumber = ageCalcul / 3.15576e+10;
            int age = (int) Math.floor(yearsNumber);
            String ageInString = String.valueOf(age)+ " ans";

            childrenList.add(new PersonsChildren(cursePerson.getFirstName(),cursePerson.getLastName(),ageInString,personsFamilyList));
        }

        SimpleBeanPropertyFilter filtrePersons = SimpleBeanPropertyFilter.filterOutAllExcept("firstName","lastName");
        FilterProvider listeDeNosFiltres = new SimpleFilterProvider().addFilter("filtreDynamiquePerson", filtrePersons);
        MappingJacksonValue childrenFiltres = new MappingJacksonValue(childrenList);
        childrenFiltres.setFilters(listeDeNosFiltres);

        return childrenFiltres;
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

    public MappingJacksonValue getListPersonsLivingTo(String address) throws ParseException {
        List<PersonEntity> dataPersonsLivingTo = personRepository.getAllLivingTo(address);
        List<Person> personsList = personMapper.convertToDtoList(dataPersonsLivingTo);

        Date todayDate = new Date();
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        String sTodayDate = formatter.format(todayDate);
        DateFormat formatterInDate = new SimpleDateFormat("dd/MM/yyyy");
        Date formattedDate = formatterInDate.parse(sTodayDate);

        List<PersonsFire> personsLivingToList = new ArrayList<PersonsFire>();

        for (Person cursePerson : personsList
        ) {

            Optional<MedicalRecordEntity> medicalRecordEntity = medicalRecordRepository.findById(cursePerson.getIdMedicalRecord());
            MedicalRecord medicalRecordPerson = medicalRecordMapper.convertToDtoMedicalRecord(medicalRecordEntity);

            long ageCalcul = formattedDate.getTime() - cursePerson.getBirthDate().getTime();
            double yearsNumber = ageCalcul / 3.15576e+10;
            int age = (int) Math.floor(yearsNumber);
            String ageInString = String.valueOf(age) + " ans";

            personsLivingToList.add(new PersonsFire(cursePerson.getFirstName(),cursePerson.getLastName(),ageInString,medicalRecordPerson.getMedications(),medicalRecordPerson.getAllergies()));

        }

        SimpleBeanPropertyFilter filtreMedicationsAllergies = SimpleBeanPropertyFilter.filterOutAllExcept("medications","allergies");
        FilterProvider listeDeNosFiltres = new SimpleFilterProvider().addFilter("filtreDynamiqueMedicalRecord", filtreMedicationsAllergies);
        MappingJacksonValue medicationsAllergiesFiltres = new MappingJacksonValue(personsLivingToList);
        medicationsAllergiesFiltres.setFilters(listeDeNosFiltres);

        return medicationsAllergiesFiltres;
    }

    public MappingJacksonValue getListPersonsCorrespondentToStationNumbers(List<String> stationNumbers) throws Exception {
        List<List<PersonEntity>> dataPersonsCorrespondentToStationNumbers = personRepository.getAllCorrespondentToStationNumbers(stationNumbers);
        //return personMapper.convertToDtoList(dataPersonsCorrespondentToStationNumbers);
        List<List<Person>> personsList = personMapper.convertToDtoListList(dataPersonsCorrespondentToStationNumbers);

        Date todayDate = new Date();
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        String sTodayDate = formatter.format(todayDate);
        DateFormat formatterInDate = new SimpleDateFormat("dd/MM/yyyy");
        Date formattedDate = formatterInDate.parse(sTodayDate);

        //HashMap<Person,MedicalRecord> medicationsAllergiesPersonsTable = new HashMap<Person,MedicalRecord>();
        List<PersonsFlood> correspondentPersonsList = new ArrayList<PersonsFlood>();

        for (List<Person> cursePersonList : personsList
        ) {
            for (Person cursePerson : cursePersonList
            ) {
                Optional<MedicalRecordEntity> medicalRecordEntity = medicalRecordRepository.findById(cursePerson.getIdMedicalRecord());
                MedicalRecord medicalRecordPerson = medicalRecordMapper.convertToDtoMedicalRecord(medicalRecordEntity);

                long ageCalcul = formattedDate.getTime() - cursePerson.getBirthDate().getTime();
                double yearsNumber = ageCalcul / 3.15576e+10;
                int age = (int) Math.floor(yearsNumber);
                String ageInString = String.valueOf(age) + " ans";

                //medicationsAllergiesPersonsTable.put(cursePerson,medicalRecordPerson);
                PersonsFloodSub personsFloodSub = new PersonsFloodSub(cursePerson.getFirstName(), cursePerson.getLastName(), ageInString, cursePerson.getPhone(), medicalRecordPerson.getMedications(), medicalRecordPerson.getAllergies());
                correspondentPersonsList.add(new PersonsFlood(cursePerson.getAddress(), cursePerson.getZip(), cursePerson.getCity(), personsFloodSub));
            }
        }

        SimpleBeanPropertyFilter filtreMedicationsAllergies = SimpleBeanPropertyFilter.filterOutAllExcept("medications","allergies");
        FilterProvider listeDeNosFiltres = new SimpleFilterProvider().addFilter("filtreDynamiqueMedicalRecord", filtreMedicationsAllergies);
        MappingJacksonValue personsCorrespondentToStationNumbersFilter = new MappingJacksonValue(correspondentPersonsList);
        personsCorrespondentToStationNumbersFilter.setFilters(listeDeNosFiltres);

        return personsCorrespondentToStationNumbersFilter;
    }

    public MappingJacksonValue getListPersonsNaming(String firstName,String lastName) throws Exception {
        List<PersonEntity> dataPersonsNaming = personRepository.getAllNaming(firstName,lastName);
        List<Person> personsList = personMapper.convertToDtoList(dataPersonsNaming);
        List<PersonsPersonInfo> personInfoList = new ArrayList<PersonsPersonInfo>();

        Date todayDate = new Date();
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        String sTodayDate = formatter.format(todayDate);
        DateFormat formatterInDate = new SimpleDateFormat("dd/MM/yyyy");
        Date formattedDate = formatterInDate.parse(sTodayDate);

        //HashMap<Person,MedicalRecord> medicationsAllergiesPersonsTable = new HashMap<Person,MedicalRecord>();

        for (Person cursePerson : personsList
        ) {

            Optional<MedicalRecordEntity> medicalRecordEntity = medicalRecordRepository.findById(cursePerson.getIdMedicalRecord());
            MedicalRecord medicalRecordPerson = medicalRecordMapper.convertToDtoMedicalRecord(medicalRecordEntity);

            long ageCalcul = formattedDate.getTime() - cursePerson.getBirthDate().getTime();
            double yearsNumber = ageCalcul / 3.15576e+10;
            int age = (int) Math.floor(yearsNumber);
            String ageInString = String.valueOf(age) + " ans";

            //PersonsFloodSub personsFloodSub = new PersonsFloodSub(ageInString,cursePerson.getPhone(),medicalRecordPerson);
            personInfoList.add(new PersonsPersonInfo(cursePerson.getFirstName(),cursePerson.getLastName(),cursePerson.getAddress(),cursePerson.getZip(),cursePerson.getCity(),ageInString,cursePerson.getEMail(),medicalRecordPerson.getMedications(),medicalRecordPerson.getAllergies()));

        }
        SimpleBeanPropertyFilter filtreMedicationsAllergies = SimpleBeanPropertyFilter.filterOutAllExcept("medications","allergies");
        FilterProvider listeDeNosFiltres = new SimpleFilterProvider().addFilter("filtreDynamiqueMedicalRecord", filtreMedicationsAllergies);
        MappingJacksonValue personsNamingFilter = new MappingJacksonValue(personInfoList);
        personsNamingFilter.setFilters(listeDeNosFiltres);

        return personsNamingFilter;
        //return personMapper.convertToDtoList(dataPersonsNaming);
    }

    public MappingJacksonValue getEmailsListToCity(String city) throws Exception {
        List<PersonEntity> dataCityAddressMails = personRepository.getAllEmailsListToCity(city);
        List<Person> emailsList = personMapper.convertToDtoList(dataCityAddressMails);

        SimpleBeanPropertyFilter filtreMedicationsAllergies = SimpleBeanPropertyFilter.filterOutAllExcept("email");
        FilterProvider listeDeNosFiltres = new SimpleFilterProvider().addFilter("filtreDynamiquePerson", filtreMedicationsAllergies);
        MappingJacksonValue emailslistFilter = new MappingJacksonValue(emailsList);
        emailslistFilter.setFilters(listeDeNosFiltres);

        return emailslistFilter;
    }
}