package org.safetynet.api.entity;

import lombok.Data;

/**
 *
 * To get the JSON data about firestations
 *
 */

@Data
public class FireStationEntity extends BasisEntity {

    private String station;


    public FireStationEntity(String station, String address){
        this.station = station;
        this.id = station;
        this.address = address;
    }

}
