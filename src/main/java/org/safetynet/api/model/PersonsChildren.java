package org.safetynet.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
public class PersonsChildren {
    private String firstName;
    private String lastName;
    private String age;
    private List<Person> familyList;
}
