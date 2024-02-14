package org.safetynet.api.model;

import lombok.Data;

@Data
public class FireStation extends IdentityBasis{
    private String station;
    //private String address;

    public FireStation(String station, String address){

        this.id = station;
        this.address = address;
    }
}
