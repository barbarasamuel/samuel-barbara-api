package org.safetynet.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 *
 * DTO design pattern to collect the informations about PersonFire
 *
 */

@Getter
@AllArgsConstructor
public class PersonsFire {
    private String firsName;
    private String lastName;
    private String age;
    private List<String> medications;
    private List<String> allergies;
}
