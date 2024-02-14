package org.safetynet.api.controller;

import org.safetynet.api.model.MedicalRecord;
import org.safetynet.api.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;
    @PostMapping("/medicalRecord")
    public ResponseEntity postMedicalRecord(@RequestBody MedicalRecord medicalRecord) throws Exception {//public List<Person>getListPersonWithStationNumber(){
        MedicalRecord addedMedicalRecord = medicalRecordService.postMedicalRecord(medicalRecord);
        return new ResponseEntity<>(addedMedicalRecord, HttpStatus.CREATED);
    }
}
