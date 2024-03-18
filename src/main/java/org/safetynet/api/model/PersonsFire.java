package org.safetynet.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PersonsFire {
    private String firsName;
    private String lastName;
    private String age;
    private List<String> medications;
    private List<String> allergies;
}
