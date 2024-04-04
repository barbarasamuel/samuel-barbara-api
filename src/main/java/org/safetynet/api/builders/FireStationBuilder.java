package org.safetynet.api.builders;

import org.safetynet.api.model.FireStation;

/**
 *
 * To make easy the instantiation of a FireStation Object
 *
 */
public class FireStationBuilder {
    public String id;
    private String address;


    /*public FireStationBuilder withId(String station){
        this.id = station;
        return this;
    }*/
    public FireStationBuilder (String station){
        this.id = station;
    }

    public FireStationBuilder withAddress(String address){
        this.address = address;
        return this;
    }

    public FireStation build(){
        FireStation fireStation = new FireStation(this.id,this.address);
        return fireStation;
    }
}
