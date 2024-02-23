package org.safetynet.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.safetynet.api.model.FireStation;
import org.safetynet.api.model.MedicalRecord;
import org.safetynet.api.model.Person;
import org.safetynet.api.service.MedicalRecordService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;
    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> postMedicalRecord(@RequestBody MedicalRecord medicalRecord) throws Exception {//public List<Person>getListPersonWithStationNumber(){
        try{
            MedicalRecord addedMedicalRecord = medicalRecordService.postMedicalRecord(medicalRecord);
            log.info("medicalRecordService.postMedicalRecord with success");
            return new ResponseEntity<>(addedMedicalRecord, HttpStatus.CREATED);
        }catch(Exception e){
            log.error("medicalRecordService.postMedicalRecord failed",e);
            MedicalRecord addedMedicalRecord = medicalRecordService.postMedicalRecord(medicalRecord);
            return new ResponseEntity<>(addedMedicalRecord, HttpStatus.FAILED_DEPENDENCY);
        }
    }

    @PatchMapping("/medicalRecord/{id}")
    public ResponseEntity patchFireStation(@PathVariable("id") String id, @RequestBody MedicalRecord medicalRecord) throws Exception {
        try{
            MedicalRecord updatedMedicalRecord = medicalRecordService.patchMedicalRecord(id,medicalRecord);
            log.info("medicalRecordService.patchMedicalRecord with success");
            return new ResponseEntity<>(updatedMedicalRecord, HttpStatus.OK);
        }catch(Exception e){
            log.error("medicalRecordService.patchMedicalRecord failed",e);
            MedicalRecord updatedMedicalRecord = medicalRecordService.patchMedicalRecord(id,medicalRecord);
            return new ResponseEntity<>(updatedMedicalRecord, HttpStatus.FAILED_DEPENDENCY);
        }
    }

    @DeleteMapping("/medicalRecord/{id}")
    public ResponseEntity patchPerson(@PathVariable("id") String id) throws Exception {
        try{
            MedicalRecord deletedMedicalRecord = medicalRecordService.deleteMedicalRecord(id);
            log.info("medicalRecordService.deleteMedicalRecord with success");
            return new ResponseEntity<>(deletedMedicalRecord, HttpStatus.OK);
        }catch(Exception e){
            log.error("medicalRecordService.deleteMedicalRecord failed",e);
            MedicalRecord deletedMedicalRecord = medicalRecordService.deleteMedicalRecord(id);
            return new ResponseEntity<>(deletedMedicalRecord, HttpStatus.FAILED_DEPENDENCY);
        }
    }
}
