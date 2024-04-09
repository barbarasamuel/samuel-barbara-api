package org.safetynet.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.safetynet.api.repository.MedicalRecordRepository;
import org.safetynet.api.repository.PersonRepository;
import org.safetynet.api.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = {"classpath:application-test.properties"})
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {SafetynetApplicationTest.class})

public class PersonControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @BeforeEach
    void loadObjects() throws IOException {
        medicalRecordRepository.loadData();
        personRepository.loadData();
    }

    @Test
    void firestationShouldReturnPersonListInJSONTest() throws Exception {

        this.mockMvc.perform(get("/firestation?station_number=1").accept("application/json")).andExpect(status().isOk())
                .andExpect(jsonPath("$.adultsNumber").isNumber())
                .andExpect(jsonPath("$.adultsNumber").value(5));
    }

    @Test
    void childAlertShouldReturnChildrenListInJSONTest() throws Exception {

        this.mockMvc.perform(get("/childAlert?address=1509 Culver St&birthdate=17/02/2012").accept("application/json")).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].firstName").value("Tenley"))
                .andExpect(jsonPath("$.[0].age").value("10 ans"));
    }

    @Test
    void phoneAlertShouldReturnPhonesListInJSONTest() throws Exception {

        this.mockMvc.perform(get("/phoneAlert?firestation=3").accept("application/json")).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].firstName").value("John"))
                .andExpect(jsonPath("$.[0].lastName").value("Boyd"))
                .andExpect(jsonPath("$.[0].phone").value("841-874-6512"));
    }

    @Test
    void fireShouldReturnPersonLivingToInJSONTest() throws Exception {

        this.mockMvc.perform(get("/fire?address=112 Steppes Pl").accept("application/json")).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].medications[0]").value("\"hydrapermazol:300mg\""));
    }

    @Test
    void floodShouldReturnPersonsByHomeInJSONTest() throws Exception {

        this.mockMvc.perform(get("/flood?station_numbers=2").accept("application/json")).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].address").value("29 15th St"))
                .andExpect(jsonPath("$.[0].personsFloodSub.lastName").value("Marrack"));
        this.mockMvc.perform(get("/flood?station_numbers=3").accept("application/json")).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].address").value("1509 Culver St"))
                .andExpect(jsonPath("$.[0].personsFloodSub.lastName").value("Boyd"));/**/

    }

    @Test
    void personInfoShouldReturnPersonDetailsInJSONTest() throws Exception {

        this.mockMvc.perform(get("/personInfo?firstName=Tony&lastName=Cooper").accept("application/json")).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].allergies[0]").value("\"shellfish\""))
                .andExpect(jsonPath("$.[0].email").value("tcoop@ymail.com"));
    }
    @Test
    void communityEmailShouldReturnEmailsListInJSONTest() throws Exception {

        this.mockMvc.perform(get("/communityEmail?city=Culver").accept("application/json")).andExpect(status().isOk())
                .andExpect(jsonPath("$.[6].email").value("tenz@email.com"));
    }

    @Test
    public void postPersonTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String,Object> body = new HashMap<>();
        body.put("firstName","Antoine");
        body.put("lastName","Lilier");
        body.put("address","15 rue des fleurs");
        body.put("city","Paris");
        body.put("zip","75001");
        body.put("phone","123-456-7890");
        body.put("email","alilierd@email.com");

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated());

    }

    @Test
    public void patchPersonTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String,Object> body = new HashMap<>();
        body.put("firstName","John");
        body.put("lastName","Boyd");
        body.put("address","15 rue des fleuves");
        body.put("city","Paris");
        body.put("zip","75001");
        body.put("phone","123-456-7890");
        body.put("email","alilierd@email.com");

        mockMvc.perform(MockMvcRequestBuilders.patch("/persons/JohnBoyd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("15 rue des fleuves"));

    }

    @Test
    public void deletePersonTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/persons/JohnBoyd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }
}
