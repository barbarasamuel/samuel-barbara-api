package org.safetynet.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PersonsPersonInfo {
    private String firstName;
    private String lastName;
    private String address;
    private String zip;
    private String city;
    private String age;
    private String eMail;
    private List<String> medications;
    private List<String> allergies;
}
