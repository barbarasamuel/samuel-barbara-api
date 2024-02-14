package org.safetynet.api.entity;

import lombok.Data;

@Data
public class FireStationEntity extends IdentityBasisEntity {
    private String station;
    //private String address;

    public FireStationEntity(String station, String address){

        this.id = station;
        this.address = address;
    }
    /*public String getId() {
        return this.station;
    }*/
}
