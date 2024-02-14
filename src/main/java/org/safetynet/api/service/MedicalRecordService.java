package org.safetynet.api.service;

import org.safetynet.api.entity.MedicalRecordEntity;
import org.safetynet.api.mappers.MedicalRecordMapper;
import org.safetynet.api.model.MedicalRecord;
import org.safetynet.api.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MedicalRecordService {
    @Autowired
    MedicalRecordMapper medicalRecordMapper;
    @Autowired
    MedicalRecordRepository medicalRecordRepository;
    public MedicalRecord postMedicalRecord(MedicalRecord addedMedicalRecord){
        MedicalRecordEntity medicalRecordEntity = medicalRecordMapper.convertToMedicalRecordEntity(addedMedicalRecord);
        MedicalRecordEntity dataMedicalRecord = medicalRecordRepository.postElement(medicalRecordEntity);
        MedicalRecord createdMedicalRecord = medicalRecordMapper.convertToMedicalRecord(dataMedicalRecord);
        return createdMedicalRecord;
    }
}
