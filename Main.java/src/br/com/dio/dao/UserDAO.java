package br.com.dio.dao;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.List;

import br.com.dio.UserModel;
import br.com.dio.exception.EmpyStorageException;

public class UserDAO {

    private long nextId = 1L;


    private List<UserModel> models = new ArrayList<>();

    public UserDAO(){}
    public UserDAO(String message) {
        
    }

    public UserModel save(final UserModel model){
     model.setId(nextId++);
     models.add(model);
    return model;
    }

    public UserModel update(final UserModel model){
        var toUpdate = findById(model.getId());
        models.remove(toUpdate);
        models.add(model);
        return model;
    }

    public void delete(final long id){
        var toDelete = findById(id);
         models.remove(toDelete);

    }



    public UserModel findById(final long id){
        verifyStorage();
        var message = String.format("Não existe usuário com o id %s cadasrrado", id);
       return models.stream()
        .filter(u -> u.getId() == id)
        .findFirst()
        .orElseThrow(()-> new UserNotFoundException(message));
    }


    public List<UserModel> findAll(){
        List<UserModel> result;
        try{
            verifyStorage();
            result = models;
        } catch(EmpyStorageException ex){
            ex.printStackTrace();
            result = new ArrayList<>();
        } 
        return result;
    } 


    private void verifyStorage(){
        if (models.isEmpty()) throw new EmpyStorageException("Está vázio!!!");
    }

}
