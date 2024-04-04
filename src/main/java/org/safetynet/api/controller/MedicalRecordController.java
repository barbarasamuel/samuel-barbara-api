package org.safetynet.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.safetynet.api.model.MedicalRecord;
import org.safetynet.api.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    /**
     *
     * To create a MedicalRecord data in JSON
     *
     */
   @PostMapping(value="/medicalRecord", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<MappingJacksonValue> postMedicalRecord(@RequestBody MedicalRecord medicalRecord) throws Exception {//public List<Person>getListPersonWithStationNumber(){

       MappingJacksonValue addedMedicalRecord = medicalRecordService.postMedicalRecord(medicalRecord);
       log.info("medicalRecordService.postMedicalRecord with success");
       return new ResponseEntity<>(addedMedicalRecord, HttpStatus.CREATED);

    }

    /**
     *
     * To do a MedicalRecord partial updating and see the result in JSON data
     *
     */
    @PatchMapping(value="/medicalRecord/{id}", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<MappingJacksonValue> patchFireStation(@PathVariable("id") String id, @RequestBody MedicalRecord medicalRecord) throws Exception {

       MappingJacksonValue updatedMedicalRecord = medicalRecordService.patchMedicalRecord(id,medicalRecord);
       log.info("medicalRecordService.patchMedicalRecord with success");
       return new ResponseEntity<>(updatedMedicalRecord, HttpStatus.OK);

    }

    /**
     *
     * To delete a MedicalRecord object and see the empty result
     *
     */
    @DeleteMapping(value="/medicalrecords/{id}", produces = {"application/json"})
    public ResponseEntity<Void> patchPerson(@PathVariable("id") String id) throws Exception {

       medicalRecordService.deleteMedicalRecord(id);
       log.info("medicalRecordService.deleteMedicalRecord with success");
       return ResponseEntity.ok().build();

    }
}
