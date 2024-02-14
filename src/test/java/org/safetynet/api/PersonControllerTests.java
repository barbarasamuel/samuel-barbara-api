package org.safetynet.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.safetynet.api.controller.PersonController;
import org.safetynet.api.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    public void testGetPersons() throws Exception {
        mockMvc.perform(get("/person"))
                .andExpect(status().isOk());
    }


}
