package org.safetynet.api;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.safetynet.api.controller.FireStationController;
import org.safetynet.api.model.FireStation;
import org.safetynet.api.service.FireStationService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class FireStationControllerTests {
/*

    @Mock
    FireStationService mockFireStationService;
    @InjectMocks
    FireStationController fireStationController = new FireStationController();

    MockMvc mockMvc;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standAloneSetup(fireStationController).build();
    }

    @Test
    public void testInsertFireStation(){

        FireStation student = new FireStation(18,"8 rue des champs");

        fireStationController.postFireStation(fireStation);

        mockMvc.perform(post("fireStation/fireStation")
                .accept(MediaType.APPLICATION_JSON)
                .andExpect(status().isOk())
                .andExpect(content().string("{}"));

        verify(fireStationService, times(1)).postFireStation(fireStation);
    }*/
}
