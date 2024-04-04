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
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = {"classpath:application-test.properties"})
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {SafetynetApplicationTest.class})
public class FireStationControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void postFireStationTest() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String,Object> body = new HashMap<>();
        body.put("station","18");
        body.put("address","8 rue des champs");

        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated());

    }

    @Test
    public void patchFireStationTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String,Object> body = new HashMap<>();
        body.put("station","1");
        body.put("address","8 rue des chants");

        mockMvc.perform(MockMvcRequestBuilders.patch("/firestations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.station").value("1"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("8 rue des chants"));

    }

    @Test
    public void deleteFireStationTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/firestations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }
}
