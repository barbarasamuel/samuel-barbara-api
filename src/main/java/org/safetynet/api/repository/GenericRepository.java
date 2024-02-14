package org.safetynet.api.repository;

import lombok.Getter;
import org.safetynet.api.entity.GenericEntity;
import org.safetynet.api.entity.IdentityBasisEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.stream;

public class GenericRepository <E extends IdentityBasisEntity,I>{
    //public class GenericRepository <E extends GenericEntity,I>{
    List<E> data = new ArrayList<E>();

    /*@Getter
    IdentityBasisEntity identityBasisEntity;*/
    public List<E> getFindAll() throws Exception {
        return this.data;
    }

    public Optional<E> getElement(I id ){
        //TODO
        Optional<E> elementById = data.stream().filter(e -> e.getId().equals(id)).findFirst();//renvoie un optional
    return elementById;
    }
    public E postElement(E element){

        if(element != null){
            this.data.add(element);
        }
        return element;
    }

    public List<E> putElement(I id, E element){
        Optional<E> elementById = data.stream().filter(e -> e.getId().equals(id)).findFirst();//renvoie un optional

        if(elementById.isPresent()){

            int index = this.data.indexOf(elementById.get());
            this.data.set(index,element);

        }
        return this.data;
    }

    public List<E> deleteElement(I id){
        Optional<E> elementById = data.stream().filter(e -> e.getId().equals(id)).findFirst();

        if(elementById.isPresent()){

            int index = this.data.indexOf(elementById.get());
            this.data.remove(index);
        }
        return this.data;


    }

}