package org.safetynet.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.safetynet.api.controller.PersonController;

import org.safetynet.api.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.util.LinkedMultiValueMap;

import java.util.HashMap;
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = {"classpath:application-test.properties"})
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {SafetynetApplicationTest.class})
//@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTests {

    @Autowired
    private MockMvc mockMvc;

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
