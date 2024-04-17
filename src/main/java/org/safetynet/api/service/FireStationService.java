package org.safetynet.api.service;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.extern.slf4j.Slf4j;
import org.safetynet.api.entity.FireStationEntity;
import org.safetynet.api.mappers.FireStationMapper;
import org.safetynet.api.model.FireStation;
import org.safetynet.api.repository.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class FireStationService {
    @Autowired
    FireStationMapper fireStationMapper;
    @Autowired
    FireStationRepository fireStationRepository;

    /**
     *
     * To create a FireStation data and can display in JSON format
     *
     */
    public MappingJacksonValue postFireStation(FireStation addedFireStation){
        FireStationEntity fireStationEntity = fireStationMapper.convertToFireStationEntity(addedFireStation);
        FireStationEntity dataFireStation = fireStationRepository.postElement(fireStationEntity);
        FireStation createdFireStation = fireStationMapper.convertToFireStation(dataFireStation);

        SimpleBeanPropertyFilter filtrePersons = SimpleBeanPropertyFilter.filterOutAllExcept("address","station");
        FilterProvider listeDeNosFiltres = new SimpleFilterProvider().addFilter("filtreDynamiqueFireStation", filtrePersons);
        MappingJacksonValue firestationFiltres = new MappingJacksonValue(createdFireStation);
        firestationFiltres.setFilters(listeDeNosFiltres);

        return firestationFiltres;
    }

    /**
     *
     * To do a partial updating for a FireStation data and can display in JSON format
     *
     */
    public MappingJacksonValue patchFireStation(String id,FireStation modifiedFireStation){
        FireStationEntity fireStationEntity = fireStationMapper.convertToFireStationEntity(modifiedFireStation);
        FireStationEntity dataFireStation = fireStationRepository.patchElement(id,fireStationEntity);
        FireStation updatedFireStation = fireStationMapper.convertToFireStation(dataFireStation);

        if(updatedFireStation!=null) {
            SimpleBeanPropertyFilter filtrePersons = SimpleBeanPropertyFilter.filterOutAllExcept("address", "station");
            FilterProvider listeDeNosFiltres = new SimpleFilterProvider().addFilter("filtreDynamiqueFireStation", filtrePersons);
            MappingJacksonValue firestationFiltres = new MappingJacksonValue(updatedFireStation);
            firestationFiltres.setFilters(listeDeNosFiltres);

            return firestationFiltres;
        }
        log.debug("patchFireStation method returns a null updatedFireStation for the "+id+" id.");
        return null;
    }

    /**
     *
     * To delete a FireStation data
     *
     */
    public void deleteFireStation(String id){

        fireStationRepository.deleteElement(id);

    }
}
