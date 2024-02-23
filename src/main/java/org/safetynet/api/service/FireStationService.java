package org.safetynet.api.service;

import org.safetynet.api.entity.FireStationEntity;
import org.safetynet.api.mappers.FireStationMapper;
import org.safetynet.api.model.FireStation;
import org.safetynet.api.repository.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FireStationService {
    @Autowired
    FireStationMapper fireStationMapper;
    @Autowired
    FireStationRepository fireStationRepository;
    public FireStation postFireStation(FireStation addedFireStation){
        FireStationEntity fireStationEntity = fireStationMapper.convertToFireStationEntity(addedFireStation);
        FireStationEntity dataFireStation = fireStationRepository.postElement(fireStationEntity);
        FireStation createdFireStation = fireStationMapper.convertToFireStation(dataFireStation);
        return createdFireStation;
    }

    public FireStation patchFireStation(String id,FireStation updatedFireStation){
        FireStationEntity fireStationEntity = fireStationMapper.convertToFireStationEntity(updatedFireStation);
        FireStationEntity dataFireStation = fireStationRepository.patchElement(id,fireStationEntity);
        updatedFireStation = fireStationMapper.convertToFireStation(dataFireStation);
        return updatedFireStation;
    }

    public FireStation deleteMedicalRecord(String id){

        FireStationEntity dataFireStation = fireStationRepository.deleteElement(id);
        FireStation deletedFireStation = fireStationMapper.convertToFireStation(dataFireStation);
        return deletedFireStation;
    }
}
