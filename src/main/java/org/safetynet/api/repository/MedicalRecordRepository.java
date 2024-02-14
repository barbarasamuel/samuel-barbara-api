package org.safetynet.api.repository;

import org.safetynet.api.entity.MedicalRecordEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicalRecordRepository extends GenericRepository<MedicalRecordEntity,String>{
    List<MedicalRecordEntity> MedicalRecordsList = new ArrayList<MedicalRecordEntity>();
}
