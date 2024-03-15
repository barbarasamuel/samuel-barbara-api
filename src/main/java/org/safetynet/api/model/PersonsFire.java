package org.safetynet.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PersonsFire {
    private String firsName;
    private String lastName;
    private String age;
    MedicalRecord medicationsAllergies;
}
