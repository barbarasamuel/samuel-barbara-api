package org.safetynet.api.entity;

import lombok.Data;

@Data
public class FireStationEntity extends BasisEntity {

    private String station;


    public FireStationEntity(String station, String address){
        this.station = station;
        this.id = station;
        this.address = address;
    }

}
