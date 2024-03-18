package org.safetynet.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;

@Getter
@AllArgsConstructor
public class PersonsFlood {

    private String address;
    private String zip;
    private String city;
    private PersonsFloodSub personsFloodSub;

}
