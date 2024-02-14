package org.safetynet.api.repository;

import org.safetynet.api.entity.FireStationEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FireStationRepository extends GenericRepository <FireStationEntity,String>{
    List<FireStationEntity> FireStationsList = new ArrayList<FireStationEntity>();
}
