package org.safetynet.api.service;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.safetynet.api.entity.FireStationEntity;
import org.safetynet.api.mappers.FireStationMapper;
import org.safetynet.api.model.FireStation;
import org.safetynet.api.repository.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

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
    public MappingJacksonValue patchFireStation(String id,FireStation updatedFireStation){
        FireStationEntity fireStationEntity = fireStationMapper.convertToFireStationEntity(updatedFireStation);
        FireStationEntity dataFireStation = fireStationRepository.patchElement(id,fireStationEntity);
        updatedFireStation = fireStationMapper.convertToFireStation(dataFireStation);

        SimpleBeanPropertyFilter filtrePersons = SimpleBeanPropertyFilter.filterOutAllExcept("address","station");
        FilterProvider listeDeNosFiltres = new SimpleFilterProvider().addFilter("filtreDynamiqueFireStation", filtrePersons);
        MappingJacksonValue firestationFiltres = new MappingJacksonValue(updatedFireStation);
        firestationFiltres.setFilters(listeDeNosFiltres);

        return firestationFiltres;
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
