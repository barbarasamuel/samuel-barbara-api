package org.safetynet.api.builders;

import org.safetynet.api.entity.FireStationEntity;


public class FireStationEntityBuilder {
    public String id;
    private String address;


    public FireStationEntityBuilder withId(String station){
        this.id = station;
        return this;
    }
/*
    public FireStationEntityBuilder (String station){
        this.id = station;
    }*/
    public FireStationEntityBuilder withAddress(String address){
        this.address = address;
        return this;
    }

    public FireStationEntity build(){
        return new FireStationEntity(this.id, this.address);
    }
}
