package org.safetynet.api.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
@AllArgsConstructor
public abstract class IdentityBasisEntity {

    public String id;
    public String firstName;
    public String lastName;
    public Date birthDate;
    public String address;
    public IdentityBasisEntity(){}


}
