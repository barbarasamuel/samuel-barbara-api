package org.safetynet.api.repository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.safetynet.api.entity.MedicalRecordEntity;
import org.safetynet.api.model.MedicalRecord;
import org.safetynet.api.tools.JSONReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    public List<MedicalRecordEntity> getMedicationsAllergies(String firstName,String lastName){
        return this.data.stream().filter(e -> e.getFirstName().equals(firstName) && e.getLastName().equals(lastName)).toList();
        //return this.data;
    }
}
