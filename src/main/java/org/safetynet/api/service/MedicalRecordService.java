package org.safetynet.api.service;

import org.safetynet.api.entity.MedicalRecordEntity;
import org.safetynet.api.mappers.MedicalRecordMapper;
import org.safetynet.api.model.MedicalRecord;
import org.safetynet.api.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordService {
    @Autowired
    MedicalRecordMapper medicalRecordMapper;
    @Autowired
    MedicalRecordRepository medicalRecordRepository;
   /* public MedicalRecord postMedicalRecord(MedicalRecord addedMedicalRecord){
        MedicalRecordEntity medicalRecordEntity = medicalRecordMapper.convertToMedicalRecordEntity(addedMedicalRecord);
        MedicalRecordEntity dataMedicalRecord = medicalRecordRepository.postElement(medicalRecordEntity);
        MedicalRecord createdMedicalRecord = medicalRecordMapper.convertToMedicalRecord(dataMedicalRecord);
        return createdMedicalRecord;
    }

    public MedicalRecord  patchMedicalRecord(String id,MedicalRecord updatedMedicalRecord){
        MedicalRecordEntity medicalRecordEntity = medicalRecordMapper.convertToMedicalRecordEntity(updatedMedicalRecord);
        MedicalRecordEntity dataMedicalRecord = medicalRecordRepository.patchElement(id,medicalRecordEntity);
        updatedMedicalRecord = medicalRecordMapper.convertToMedicalRecord(dataMedicalRecord);
        return updatedMedicalRecord;
    }

    public MedicalRecord  deleteMedicalRecord(String id){
        MedicalRecordEntity dataMedicalRecord = medicalRecordRepository.deleteElement(id);
        MedicalRecord deletedMedicalRecord = medicalRecordMapper.convertToMedicalRecord(dataMedicalRecord);
        return deletedMedicalRecord;
    }*/
}
