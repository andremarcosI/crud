package br.com.dio.dao;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.List;

import br.com.dio.UserModel;

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
        var message = String.format("Não existe usuário com o id %s cadasrrado", id);
       return models.stream()
        .filter(u -> u.getId() == id)
        .findFirst()
        .orElseThrow(()-> new UserNotFoundException(message));
    }


    public List<UserModel> findAll(){
        return models;
    } 




}
