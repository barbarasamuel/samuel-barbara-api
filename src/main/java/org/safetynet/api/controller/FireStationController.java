package org.safetynet.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.safetynet.api.model.FireStation;
import org.safetynet.api.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;
    @PostMapping(value = "/fireStation", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<FireStation> postFireStation(@RequestBody FireStation fireStation) throws Exception {
        FireStation addedFireStation;
        try {
            addedFireStation = fireStationService.postFireStation(fireStation);
            log.info("fireStationService.postFireStation with success");
            return new ResponseEntity<>(addedFireStation, HttpStatus.CREATED);
        }catch (Exception e){
            log.error("fireStationService.postFireStation failed",e);
            return new ResponseEntity<>(fireStation, HttpStatus.BAD_REQUEST);
        }
    }

   /* @PatchMapping(value= "/fireStation/{id}", produces = {"application/json"})
    public ResponseEntity patchFireStation(@PathVariable("id") String id, @RequestBody FireStation fireStation) throws Exception {
        try{
            FireStation updatedFireStation = fireStationService.patchFireStation(id,fireStation);
            log.info("fireStationService.patchFireStation with success");
            return new ResponseEntity<>(updatedFireStation, HttpStatus.OK);
        }catch(Exception e){
            log.error("fireStationService.patchFireStation failed",e);
            return new ResponseEntity<>(fireStation, HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<>(id, HttpStatus.BAD_REQUEST);
        }
    }*/

}
