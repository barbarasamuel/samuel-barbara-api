package org.safetynet.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
@AllArgsConstructor
public abstract class IdentityBasisEntity extends BasisEntity{

    public String firstName;
    public String lastName;
    public Date birthDate;

    public IdentityBasisEntity(){}


}
