package org.safetynet.api.model;

/**
 *
 * DTO design pattern to collect the informations about PersonsFireStation
 *
 */


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
public class PersonsFireStation {
    private List<Person> persons;
    private Integer adultsNumber;
    private Integer minorsNumber;

}
