package org.safetynet.api.mappers;

import org.safetynet.api.builders.FireStationBuilder;
import org.safetynet.api.builders.FireStationEntityBuilder;
import org.safetynet.api.entity.FireStationEntity;
import org.safetynet.api.model.FireStation;
import org.springframework.stereotype.Component;

@Component
public class FireStationMapper {
    public FireStationEntity convertToFireStationEntity(FireStation fireStation){
        return new FireStationEntityBuilder()
                .withId(fireStation.getStation())
                .withAddress(fireStation.getAddress())
                .build();
    }

    public FireStation convertToFireStation(FireStationEntity fireStationEntity){

        return new FireStationBuilder()
                .withId(fireStationEntity.getStation())
                .withAddress(fireStationEntity.getAddress())
                .build();

    }
}
