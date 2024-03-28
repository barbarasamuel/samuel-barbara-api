package org.safetynet.api.model;

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
