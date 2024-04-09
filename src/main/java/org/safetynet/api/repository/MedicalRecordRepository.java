package org.safetynet.api.repository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.safetynet.api.entity.MedicalRecordEntity;
import org.safetynet.api.tools.JSONReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;

/**
 *
 * To load the information in the MedicalRecordEntity Objects
 *
 */

@Slf4j
@Repository
public class MedicalRecordRepository extends GenericRepository<MedicalRecordEntity,String>{

    @Autowired
    private JSONReader jsonReader;
    @PostConstruct
    public void loadData() throws IOException {
        try{
            this.data = jsonReader.loadMedicalRecords();
            log.info("loadMedicalRecords with success");
        }catch(IOException e){
            log.error("loadMedicalRecords failed");
        }
    }
}
