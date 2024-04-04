package org.safetynet.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = {"classpath:application-test.properties"})
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {SafetynetApplicationTest.class})
public class MedicalRecordControllerTests {
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void postMedicalRecordTest() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String,Object> body = new HashMap<>();
        body.put("firstName","Yvan");
        body.put("lastName","Boyd");
        body.put("birthdate","2014-05-05");
        body.put("medications", List.of("aznol:350mg", "hydrapermazol:100mg"));
        body.put("allergies",List.of("nillacilan"));

        mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated());

    }

    @Test
    public void patchMedicalRecordTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String,Object> body = new HashMap<>();
        body.put("firstName","John");
        body.put("lastName","Boyd");
        body.put("birthdate","2014-05-05");
        body.put("medications", List.of("\"doliprane:500mg\"", "\"hydrapermazol:100mg\""));
        body.put("allergies",List.of("\"nillacilan\""));

        mockMvc.perform(MockMvcRequestBuilders.patch("/medicalRecord/JohnBoyd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.medications[0]").value("\"doliprane:500mg\""));

    }

    @Test
    public void deleteMedicalRecordTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/medicalrecords/JohnBoyd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }
}
