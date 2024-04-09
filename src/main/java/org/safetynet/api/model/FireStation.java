package org.safetynet.api.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Data;

@JsonFilter("filtreDynamiqueFireStation")
@Data
public class FireStation extends IdentityBasis{

    private String station;
    

    public FireStation(String station, String address){
        this.station = station;
        this.id = station;
        this.address = address;
    }
}
