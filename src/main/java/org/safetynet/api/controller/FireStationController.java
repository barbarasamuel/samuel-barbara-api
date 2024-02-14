package org.safetynet.api.controller;

import org.safetynet.api.model.FireStation;
import org.safetynet.api.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class FireStationController {
    @Autowired
    private FireStationService fireStationService;
    @PostMapping("/fireStation")
    public ResponseEntity postFireStation(@RequestBody FireStation fireStation) throws Exception {
        FireStation addedFireStation = fireStationService.postFireStation(fireStation);
        return new ResponseEntity<>(addedFireStation, HttpStatus.CREATED);
    }
}
