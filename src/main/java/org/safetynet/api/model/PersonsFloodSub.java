package org.safetynet.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 *
 * DTO design pattern to collect the informations about PersonsFlood (complement)
 *
 */

@Getter
@AllArgsConstructor
public class PersonsFloodSub {
    private String firstName;
    private String lastName;
    private String age;
    private String phone;
    private List<String> medications;
    private List<String> allergies;
}
