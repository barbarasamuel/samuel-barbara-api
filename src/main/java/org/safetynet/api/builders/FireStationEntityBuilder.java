package org.safetynet.api.builders;

import org.safetynet.api.entity.FireStationEntity;


public class FireStationEntityBuilder {
    public String id;
    private String address;


    public FireStationEntityBuilder withId(String station){
        this.id = station;
        return this;
    }


    public FireStationEntityBuilder withAddress(String address){
        this.address = address;
        return this;
    }

    public FireStationEntity build(){
        FireStationEntity fireStationEntity = new FireStationEntity(this.id,this.address);
        return fireStationEntity;
    }
}
