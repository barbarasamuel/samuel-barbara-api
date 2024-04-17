package org.safetynet.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.safetynet.api.model.FireStation;
import org.safetynet.api.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;

    /**
     *
     * To create a FireStation data in JSON
     *
     */
    @PostMapping(value = "/firestation", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<MappingJacksonValue> postFireStation(@RequestBody FireStation fireStation) throws Exception {
        log.info("postFireStation PostMapping request");
        MappingJacksonValue addedFireStation = null;

        try{
            addedFireStation = fireStationService.postFireStation(fireStation);
            log.info("postFireStation PostMapping request with success "+addedFireStation.getValue());
            return new ResponseEntity<>(addedFireStation, HttpStatus.CREATED);
        }catch(Exception e){
            log.error("postFireStation PostMapping request : error",e);
            return new ResponseEntity<>(addedFireStation, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * To do a Firestation partial updating and see the result in JSON data
     *
     */
    @PatchMapping(value = "/firestations/{station}", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<MappingJacksonValue> patchFireStation(@RequestBody FireStation firestation, @PathVariable("station") String id) throws Exception {
        log.info("patchFireStation PatchMapping request");
        MappingJacksonValue updatedFireStation = null;

        try {
            updatedFireStation = fireStationService.patchFireStation(id,firestation);
            log.info("patchFireStation PatchMapping request with success "+updatedFireStation.getValue());
            return new ResponseEntity<>(updatedFireStation, HttpStatus.OK);
        }catch(Exception e){
            log.error("patchFireStation PatchMapping request : error",e);
            return new ResponseEntity<>(updatedFireStation, HttpStatus.BAD_REQUEST);
        }


    }

    /**
     *
     * To delete a FireStation object and see the empty result
     *
     */
    @DeleteMapping("/firestations/{id}")
    public ResponseEntity<Void>  deleteFireStation(@PathVariable("id") String id) throws Exception {
        log.info("deleteFireStation DeleteMapping request");
        try{
            fireStationService.deleteFireStation(id);
            log.info("deleteFireStation DeleteMapping request with success");
            return ResponseEntity.ok().build();
        }catch(Exception e){
            log.error("deleteFireStation DeleteMapping request : error",e);
            return ResponseEntity.badRequest().build();
        }

    }

}
