package org.safetynet.api.service;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.safetynet.api.entity.MedicalRecordEntity;
import org.safetynet.api.mappers.MedicalRecordMapper;
import org.safetynet.api.model.MedicalRecord;
import org.safetynet.api.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Optional;

@Service
public class MedicalRecordService {
    @Autowired
    MedicalRecordMapper medicalRecordMapper;
    @Autowired
    MedicalRecordRepository medicalRecordRepository;
   public MappingJacksonValue postMedicalRecord(MedicalRecord addedMedicalRecord) throws ParseException {
        MedicalRecordEntity medicalRecordEntity = medicalRecordMapper.convertToMedicalRecordEntity(addedMedicalRecord);
        MedicalRecordEntity dataMedicalRecord = medicalRecordRepository.postElement(medicalRecordEntity);
        MedicalRecord createdMedicalRecord = medicalRecordMapper.convertToMedicalRecord(Optional.ofNullable(dataMedicalRecord));

       SimpleBeanPropertyFilter filtrePersons = SimpleBeanPropertyFilter.filterOutAllExcept("firstName","lastName","birthDate","medications", "allergies");
       FilterProvider listeDeNosFiltres = new SimpleFilterProvider().addFilter("filtreDynamiqueMedicalRecord", filtrePersons);
       MappingJacksonValue medicalRecordsFiltres = new MappingJacksonValue(createdMedicalRecord);
       medicalRecordsFiltres.setFilters(listeDeNosFiltres);

        return medicalRecordsFiltres;
    }



    public MappingJacksonValue  patchMedicalRecord(String id,MedicalRecord updatedMedicalRecord) throws ParseException {
        MedicalRecordEntity medicalRecordEntity = medicalRecordMapper.convertToMedicalRecordEntity(updatedMedicalRecord);
        MedicalRecordEntity dataMedicalRecord = medicalRecordRepository.patchElement(id,medicalRecordEntity);
        //updatedMedicalRecord = medicalRecordMapper.convertToMedicalRecord(Optional.ofNullable(dataMedicalRecord));
        updatedMedicalRecord = medicalRecordMapper.convertToDtoMedicalRecord(Optional.ofNullable(dataMedicalRecord));

        SimpleBeanPropertyFilter filtrePersons = SimpleBeanPropertyFilter.filterOutAllExcept("firstName","lastName","birthDate","medications", "allergies");
        FilterProvider listeDeNosFiltres = new SimpleFilterProvider().addFilter("filtreDynamiqueMedicalRecord", filtrePersons);
        MappingJacksonValue medicalRecordsFiltres = new MappingJacksonValue(updatedMedicalRecord);
        medicalRecordsFiltres.setFilters(listeDeNosFiltres);

        return medicalRecordsFiltres;
    }
/**/
    public MappingJacksonValue  deleteMedicalRecord(String id) throws ParseException {
        MedicalRecordEntity dataMedicalRecord = medicalRecordRepository.deleteElement(id);
        //MedicalRecord deletedMedicalRecord = medicalRecordMapper.convertToMedicalRecord(dataMedicalRecord);
        MedicalRecord deletedMedicalRecord = medicalRecordMapper.convertToDtoMedicalRecord(Optional.ofNullable(dataMedicalRecord));

        SimpleBeanPropertyFilter filtrePersons = SimpleBeanPropertyFilter.filterOutAllExcept("firstName","lastName","birthDate","medications", "allergies");
        FilterProvider listeDeNosFiltres = new SimpleFilterProvider().addFilter("filtreDynamiqueMedicalRecord", filtrePersons);
        MappingJacksonValue medicalRecordsFiltres = new MappingJacksonValue(deletedMedicalRecord);
        medicalRecordsFiltres.setFilters(listeDeNosFiltres);

        return medicalRecordsFiltres;
    }
}
