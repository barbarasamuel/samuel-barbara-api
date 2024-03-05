package org.safetynet.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.Date;
@Data
@AllArgsConstructor
public abstract class IdentityBasis {
    protected String id;
    protected String firstName;
    protected String lastName;
    protected Date birthDate;
    protected String address;

    public IdentityBasis(){}

}
