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
        MappingJacksonValue addedFireStation = fireStationService.postFireStation(fireStation);
        log.info("fireStationService.postFireStation with success");

        return new ResponseEntity<>(addedFireStation, HttpStatus.CREATED);
    }

    /**
     *
     * To do a Firestation partial updating and see the result in JSON data
     *
     */
    @PatchMapping(value = "/firestations/{station}", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<MappingJacksonValue> patchFireStation(@RequestBody FireStation firestation, @PathVariable("station") String id) throws Exception {

        MappingJacksonValue updatedFireStation = fireStationService.patchFireStation(id,firestation);
        log.info("fireStationService.patchFireStation with success");
        return new ResponseEntity<>(updatedFireStation, HttpStatus.OK);

    }

    /**
     *
     * To delete a FireStation object and see the empty result
     *
     */
    @DeleteMapping("/firestations/{id}")
    public ResponseEntity<Void>  deleteFireStation(@PathVariable("id") String id) throws Exception {
        fireStationService.deleteFireStation(id);
        log.info("fireStationService.deleteFireStation with success");
        return ResponseEntity.ok().build();

    }

}
