package org.safetynet.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.safetynet.api.controller.PersonController;

import org.safetynet.api.model.Person;
import org.safetynet.api.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;

import java.net.ServerSocket;
import java.util.Date;
import java.util.HashMap;
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = {"classpath:application-test.properties"})
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {SafetynetApplicationTest.class})
//@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonService personService;

    /*public static String asJsonString(Object obj) {
/*
        SimpleBeanPropertyFilter filtrePersons = SimpleBeanPropertyFilter.serializeAllExcept("id","birthDate","idFireStation","idMedicalRecord");
        FilterProvider listeDeNosFiltres = new SimpleFilterProvider().addFilter("filtreDynamiquePerson", filtrePersons);
        MappingJacksonValue personFiltres = new MappingJacksonValue(obj);
        personFiltres.setFilters(listeDeNosFiltres);
        return personFiltres;*/
   /*     try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    @Test
    void personPost() throws Exception {
        /*ResultActions resultActions = this.mockMvc.perform(post("/person").accept("application/json")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());*/
        /*this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/person").accept("application/json")
                        .content(asJsonString(new Person("PatriceGommet", "Patrice","Gommet","23 avenue des bleuets","97451","Culver","pgommet@email.com","123-456-789",new Date(2000, 02, 06),"","")))
                        .contentType(MediaType.APPLICATION_JSON)
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());*/
        //.accept(MediaType.APPLICATION_JSON))
        /*ResultActions resultActions = this.mockMvc.perform( MockMvcRequestBuilders
                .post("/person").accept("application/json").content(String.valueOf(asJsonString(new Person("PatriceGommet", "Patrice","Gommet","23 avenue des bleuets","97451","Culver","pgommet@email.com","123-456-789",new Date(2000, 02, 06),"","")))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());*/
        /*String idTest = "PatriceGommet";
        String firstNameTest = "Patrice";
        String lastNameTest = "Gommet";
        String addressTest = "23 avenue des bleuets";
        String zipTest = "97451";
        String cityTest = "Culver";
        String emailTest = "pgommet@email.com";
        String phoneTest = "123-456-789";

        when(personService.postPerson("Patrice","Gommet","23 avenue des bleuets","97451","Culver","pgommet@email.com","123-456-789")).thenReturn(true);

        this.mockMvc.perform((MockMvcRequestBuilders.post("/person").accept("application/json"))
                .param("firstName",firstNameTest)
                .param("lastName",lastNameTest)
                .param("address",addressTest)
                .param("zip",zipTest)
                .param("city",cityTest)
                .param("email",emailTest)
                .param("phone",phoneTest)
                .accept((MediaType) status().isOk()));*/

        /*this.mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).content(asJsonString(new Person("PatriceGommet", "Patrice","Gommet","23 avenue des bleuets","97451","Culver","pgommet@email.com","123-456-789",new Date(2000, 02, 06),"",""))))
                .andExpect(status().isCreated());*/
    }


    @Test
    void firestationShouldReturnPersonListInJSON() throws Exception {

        this.mockMvc.perform(get("/firestation?station_number=1").accept("application/json")).andExpect(status().isOk())
                .andExpect(jsonPath("$.firestation").exists());
    }

    @Test
    void childAlertShouldReturnChildrenListInJSON() throws Exception {

        this.mockMvc.perform(get("/childAlert?address=1509 Culver St&birthdate=17/02/2012").accept("application/json")).andExpect(status().isOk())
                .andExpect(jsonPath("$.childAlert").exists());
    }

    @Test
    void phoneAlertShouldReturnPhonesListInJSON() throws Exception {

        this.mockMvc.perform(get("/phoneAlert?firestation=1").accept("application/json")).andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneAlert").exists());
    }

    @Test
    void fireShouldReturnPersonLivingToInJSON() throws Exception {

        this.mockMvc.perform(get("/fire?address=1509 Culver St").accept("application/json")).andExpect(status().isOk())
                .andExpect(jsonPath("$.fire").exists());
    }

    @Test
    void floodShouldReturnPersonsByHomeInJSON() throws Exception {
        /*LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("station_numbers", "2");
        requestParams.add("station_numbers", "3");

        this.mockMvc.perform(get("/flood/stations").params(requestParams).accept("application/json")).andExpect(status().isOk())
                .andExpect(jsonPath("$.flood/stations").exists());*/
        this.mockMvc.perform(get("/flood?station_numbers=2").accept("application/json")).andExpect(status().isOk())
                .andExpect(jsonPath("$.flood").exists());
        this.mockMvc.perform(get("/flood?station_numbers=3").accept("application/json")).andExpect(status().isOk())
                .andExpect(jsonPath("$.flood/stations").exists());/**/

    }

    @Test
    void personInfoShouldReturnPersonDetailsInJSON() throws Exception {

        this.mockMvc.perform(get("/personInfo?firstName=Tony&lastName=Cooper").accept("application/json")).andExpect(status().isOk())
                .andExpect(jsonPath("$.personInfo").exists());
    }
    @Test
    void communityEmailShouldReturnEmailsListInJSON() throws Exception {

        this.mockMvc.perform(get("/communityEmail?city=Culver").accept("application/json")).andExpect(status().isOk())
                .andExpect(jsonPath("$.communityEmail").exists());
    }

    @Test
    public void testPostPerson() throws Exception {
        mockMvc.perform(get("/person/JadeHubert"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPatchPerson() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        HashMap<String, Object> updatedValue = new HashMap<>();
        updatedValue.put("address", "1 avenue Java");

        mockMvc.perform(patch("/person/JohnBoyd")
                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .content(objectMapper.writeValueAsString(updatedValue))
        ).andExpect(status().isOk());
               // .andExpect(content().string(equalTo("1 avenue Java")));
        assertEquals("1 avenue Java",content());
    }


}
