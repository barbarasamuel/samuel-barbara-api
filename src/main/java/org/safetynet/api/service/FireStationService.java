package org.safetynet.api.service;

import org.safetynet.api.entity.FireStationEntity;
import org.safetynet.api.mappers.FireStationMapper;
import org.safetynet.api.model.FireStation;
import org.safetynet.api.repository.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;

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
}
