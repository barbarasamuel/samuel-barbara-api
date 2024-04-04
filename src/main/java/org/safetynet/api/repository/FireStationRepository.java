package org.safetynet.api.repository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.safetynet.api.entity.FireStationEntity;
import org.safetynet.api.tools.JSONReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
@Slf4j
@Repository
public class FireStationRepository extends GenericRepository <FireStationEntity,String>{

    @Autowired
    private JSONReader jsonReader;

    @PostConstruct
    public void loadData() throws IOException {
        try{
            this.data = jsonReader.loadFireStations();
            log.info("loadFireStations with success");
        }catch(IOException e) {
            log.error("loadFireStations failed");
        }
    }
}
