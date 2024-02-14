package org.safetynet.api.repository;

import org.safetynet.api.entity.PersonEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository extends GenericRepository<PersonEntity,String> {

    List<PersonEntity> personsList = new ArrayList<PersonEntity>();
   /* @Autowired
    ReadService readService;*/
    //@Override
    public List<PersonEntity> getFindAll() throws Exception {


        return personsList;//this.getFindAll();
    }

    /*public void addItem(PersonEntity personList) {
        personList.setId(index++);
        personsList.add(personList);
    }*/

    public PersonEntity findById(String id) {
        for (PersonEntity personList : personsList) {
            if (personList.getId().equals(id)) {
                return personList;
            }
        }
        return null;
    }


   /* public void deleteElement(String id){

    }*/
}
