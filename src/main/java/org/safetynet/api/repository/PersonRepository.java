package org.safetynet.api.repository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.safetynet.api.entity.PersonEntity;
import org.safetynet.api.tools.JSONReader;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;
@Slf4j
@Repository
public class PersonRepository extends GenericRepository<PersonEntity,String> {
    @Autowired
    private JSONReader jsonReader;
    @PostConstruct
   public void LoadData() throws IOException {
       try{
           this.data = jsonReader.loadPersons();
           log.info("loadPersons with success");
       }catch(IOException e){
           log.error("loadPersons failed");
       }
   }

    public List<PersonEntity> getAllWithStationNumber(String station) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String sminorsDate = "19-02-2006";
        Date minorsDate = new Date(String.valueOf(dateFormat.parse(sminorsDate)));
        String smajorsDate = "19-02-2006";
        Date majorsDate = new Date(String.valueOf(dateFormat.parse(smajorsDate)));

        this.data.stream().filter(e -> e.getIdFireStation().equals(station)).toList();
        this.data.stream().filter(e -> e.getBirthDate().after(minorsDate) && e.getBirthDate().before(majorsDate)).count();
                //&&)).count()).toList();
        //nb de 18 ans et moins et nb d'adultes
        //long countMinors = this.data.stream().filter(e->e.getBirthDate().after("19-02-2006")).count();
        //long countMajors = this.data.stream().filter(e->e.getBirthDate().before("20-02-2006")).count();
        //long countMajors = DateStream.of(this.data).count()
        return this.data;
    }

    public List<PersonEntity> getAllByAddressAndBirthDate(String value, Date birthDate) {
        return this.data.stream().filter(e -> e.getBirthDate().after(birthDate) && e.getAddress().equals(value)).toList();
    }

    public List<PersonEntity> getAllPhoneNumberByStation(String station){
        return this.data.stream().filter(e -> e.getIdFireStation().equals(station)).toList();
    }

    public List<PersonEntity> getAllLivingTo(String address){
        return this.data.stream().filter(e -> e.getAddress().equals(address)).toList();
    }

    public List<PersonEntity> getAllCorrespondentToStationNumbers(List<String> stationNumbers){
        return this.data;//.stream().filter(e -> e.getIdFireStation().subSequence(stationNumbers)).toList();
    }

    public List<PersonEntity> getAllNaming(String firstName,String lastName){//} throws Exception {
        return this.data.stream().filter(e -> e.getId().equals(firstName+lastName)).toList();
    }

    public List<PersonEntity> getAllAddressMailsListToCity(String city){
        return this.data.stream().filter(e -> e.getCity().equals(city)).toList();
    }


}