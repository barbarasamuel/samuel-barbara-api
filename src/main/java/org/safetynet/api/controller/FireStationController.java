package org.safetynet.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.safetynet.api.model.FireStation;
import org.safetynet.api.model.MedicalRecord;
import org.safetynet.api.model.Person;
import org.safetynet.api.service.FireStationService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;
    @PostMapping("/fireStation")
    public ResponseEntity<FireStation> postFireStation(@RequestBody FireStation fireStation) throws Exception {
        FireStation addedFireStation;
        try {
            addedFireStation = fireStationService.postFireStation(fireStation);
            log.info("fireStationService.postFireStation with success");
            return new ResponseEntity<>(addedFireStation, HttpStatus.CREATED);
        }catch (Exception e){
            log.error("fireStationService.postFireStation failed",e);
            addedFireStation = fireStationService.postFireStation(fireStation);
            return new ResponseEntity<>(addedFireStation, HttpStatus.FAILED_DEPENDENCY);
        }
    }

    @PatchMapping("/fireStation/{id}")
    public ResponseEntity patchFireStation(@PathVariable("id") String id, @RequestBody FireStation fireStation) throws Exception {
        try{
            FireStation updatedFireStation = fireStationService.patchFireStation(id,fireStation);
            log.info("fireStationService.patchFireStation with success");
            return new ResponseEntity<>(updatedFireStation, HttpStatus.OK);
        }catch(Exception e){
            log.error("fireStationService.patchFireStation failed",e);
            FireStation updatedFireStation = fireStationService.patchFireStation(id,fireStation);
            return new ResponseEntity<>(updatedFireStation, HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @DeleteMapping("/fireStation/{id}")
    public ResponseEntity deleteFireStation(@PathVariable("id") String id) throws Exception {
        try{
            FireStation deletedFireStation = fireStationService.deleteMedicalRecord(id);
            log.info("fireStationService.deleteFireStation with success");
            return new ResponseEntity<>(deletedFireStation, HttpStatus.OK);
        }catch(Exception e){
            log.error("fireStationService.deleteFireStation failed",e);
            FireStation deletedFireStation = fireStationService.deleteMedicalRecord(id);
            return new ResponseEntity<>(deletedFireStation, HttpStatus.FAILED_DEPENDENCY);
        }
    }

}
