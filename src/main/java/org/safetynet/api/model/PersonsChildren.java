package org.safetynet.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 *
 * DTO design pattern to collect the informations about PersonChildren
 *
 */

@Getter
@AllArgsConstructor
public class PersonsChildren {
    private String firstName;
    private String lastName;
    private String age;
    private List<Person> familyList;
}
