package org.safetynet.api.mappers;

import org.safetynet.api.builders.FireStationBuilder;
import org.safetynet.api.builders.FireStationEntityBuilder;
import org.safetynet.api.entity.FireStationEntity;
import org.safetynet.api.model.FireStation;
import org.springframework.stereotype.Component;

/**
 *
 * To map from FireStationEntity type to FireStation type or inverse
 *
 */

@Component
public class FireStationMapper {
    public FireStationEntity convertToFireStationEntity(FireStation fireStation){
        return new FireStationEntityBuilder()
                .withId(fireStation.getStation())
                .withAddress(fireStation.getAddress())
                .build();
    }

    public FireStation convertToFireStation(FireStationEntity fireStationEntity){

        return new FireStationBuilder(fireStationEntity.getStation())
                .withAddress(fireStationEntity.getAddress())
                .build();

    }
}
