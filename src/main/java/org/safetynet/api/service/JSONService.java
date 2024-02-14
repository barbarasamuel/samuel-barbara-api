package org.safetynet.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.safetynet.api.entity.PersonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class JSONService {
    @Autowired
    private ReadService readService;
    public PersonEntity convertJSONToJava() throws JsonProcessingException, Exception {
    /*Pour convertir données JSON en Objet Java*/
        List<PersonEntity> personsList = new ArrayList<PersonEntity>();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPersonsArray = readService.loadFile();
        personsList = objectMapper.readValue(jsonPersonsArray, new TypeReference<List<PersonEntity>>() {
        });
        return (PersonEntity) personsList;
    }
    public String convertJavaToJSON(PersonEntity personsList) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPersonsList = objectMapper.writeValueAsString(personsList);/**/
        return jsonPersonsList;
    }
}
