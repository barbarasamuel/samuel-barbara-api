package org.safetynet.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public abstract class BasisEntity {

    public String id;
    public String address;

    public BasisEntity(){}


}
