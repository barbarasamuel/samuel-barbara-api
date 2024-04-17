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
       log.info("postMedicalRecord PostMapping request");
       MappingJacksonValue addedMedicalRecord = null;

       try{
           addedMedicalRecord = medicalRecordService.postMedicalRecord(medicalRecord);
           log.info("postMedicalRecord PostMapping request with success "+addedMedicalRecord.getValue());
           return new ResponseEntity<>(addedMedicalRecord, HttpStatus.CREATED);
       }catch(Exception e){
           log.error("postMedicalRecord PostMapping request : error",e);
           return new ResponseEntity<>(addedMedicalRecord, HttpStatus.BAD_REQUEST);
       }

    }

    /**
     *
     * To do a MedicalRecord partial updating and see the result in JSON data
     *
     */
    @PatchMapping(value="/medicalRecord/{id}", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<MappingJacksonValue> patchMedicalRecord(@PathVariable("id") String id, @RequestBody MedicalRecord medicalRecord) throws Exception {
        log.info("patchMedicalRecord PatchMapping request");
        MappingJacksonValue updatedMedicalRecord = null;
        try{
            updatedMedicalRecord = medicalRecordService.patchMedicalRecord(id,medicalRecord);
            log.info("patchMedicalRecord PatchMapping request with success "+updatedMedicalRecord.getValue());
            return new ResponseEntity<>(updatedMedicalRecord, HttpStatus.OK);
        }catch(Exception e){
            log.error("patchMedicalRecord PatchMapping request : error",e);
            return new ResponseEntity<>(updatedMedicalRecord, HttpStatus.BAD_REQUEST);
        }

    }

    /**
     *
     * To delete a MedicalRecord object and see the empty result
     *
     */
    @DeleteMapping(value="/medicalrecords/{id}", produces = {"application/json"})
    public ResponseEntity<Void> patchPerson(@PathVariable("id") String id) throws Exception {
        log.info("deleteMedicalRecord DeleteMapping request");
        try{
            medicalRecordService.deleteMedicalRecord(id);
            log.info("deleteMedicalRecord DeleteMapping request with success");
            return ResponseEntity.ok().build();
        }catch(Exception e){
            log.info("deleteMedicalRecord DeleteMapping request : error",e);
            return ResponseEntity.badRequest().build();
        }

    }
}
