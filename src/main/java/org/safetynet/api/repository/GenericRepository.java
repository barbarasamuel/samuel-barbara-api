package org.safetynet.api.repository;

import org.safetynet.api.entity.BasisEntity;
import org.safetynet.api.entity.IdentityBasisEntity;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;


public abstract class GenericRepository <E extends BasisEntity,I >{
    //public class GenericRepository <E extends GenericEntity,I>{
    protected List<E> data = new ArrayList<E>();
    protected E currentObject;

    public abstract void loadData() throws IOException;

    public List<E> getFindAll() {

        return this.data;
    }

    public Optional<E> findById(I id ){

        Optional<E> elementById = data.stream().filter(e -> e.getId().equals(id)).findFirst();//renvoie un optional
        return elementById;
    }
    public E postElement(E element){

        if(element != null){
            this.data.add(element);
        }
        return element;
    }

    //public List<E> putElement(I id, E element){
    public E patchElement(I id, E element){
        int index = 0;
        Optional<E> elementById = data.stream().filter(e -> e.getId().equals(id)).findFirst();//renvoie un optional

        if(elementById.isPresent()){

            index = this.data.indexOf(elementById.get());
            this.data.set(index,element);

        }
        return this.data.get(index);
    }

    public E deleteElement(I id){
        int index =0;
        Optional<E> elementById = data.stream().filter(e -> e.getId().equals(id)).findFirst();

        if(elementById.isPresent()){

            index = this.data.indexOf(elementById.get());
            this.data.remove(index);
        }
        return this.data.get(index);


    }

}