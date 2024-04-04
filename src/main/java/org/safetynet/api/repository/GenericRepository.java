package org.safetynet.api.repository;

import org.safetynet.api.entity.BasisEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * To manage the Person, MedicalRecord or FireStation element(s)
 * when a method can be used by the three types of object
 *
 */
public abstract class GenericRepository <E extends BasisEntity,I >{

    protected List<E> data = new ArrayList<E>();

    public abstract void loadData() throws IOException;

    public List<E> getFindAll() {

        return this.data;
    }

    public Optional<E> findById(I id ){

        Optional<E> elementById = data.stream().filter(e -> e.getId().equals(id)).findFirst();
        return elementById;
    }
    public E postElement(E element){

        if(element != null){
            this.data.add(element);
        }
        return element;
    }

    public E patchElement(I id, E element){
        int index = 0;

        List<E> elementAll = data.stream().filter(e -> e.getId().equals(id)).toList();
        if(!elementAll.isEmpty()) {
            Optional<E> elementById = data.stream().filter(e -> e.getId().equals(id)).findFirst();//renvoie un optional

            if (elementById.isPresent()) {

                index = this.data.indexOf(elementById.get());
                this.data.set(index, element);

            }
        }
        return this.data.get(index);
    }


    public void deleteElement(I id){
        int index =0;

        List<E> elementAll = data.stream().filter(e -> e.getId().equals(id)).toList();
        if(!elementAll.isEmpty()) {
            Optional<E> elementById = data.stream().filter(e -> e.getId().equals(id)).findFirst();

            if (elementById.isPresent()) {

                index = this.data.indexOf(elementById.get());
                this.data.remove(index);
            }
        }

    }

}