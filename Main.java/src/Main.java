import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import br.com.dio.UserModel;
import br.com.dio.dao.UserDAO;
import br.com.dio.dao.UserNotFoundException;
import br.com.dio.exception.EmptyStorageException;
import br.com.dio.model.MenuOption;

public class Main {

    private static UserDAO dao= new UserDAO();
    private final static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Bem vindo ao cadastro de usuários,");
        System.out.println("Selecione a operação desejada");
        System.out.println("1 - Cadastrar");
        System.out.println("2 - Atualizar");
        System.out.println("3 - Excluir");
        System.out.println("4 - Buscar por identificador");
        System.out.println("5 - listar");
        System.out.println("6 - Sair");
        var userInput = scanner.nextInt();
        while (true) {
            var selectedOption = MenuOption.values()[userInput -1];
            switch (selectedOption) {
                case SAVE->{
                   var user = dao.save(requestToSave());
                    System.out.printf("Usuário cadastrado %s", user);
                }
                case UPDATE ->{
                 try {
                    var user = dao.update(requestToUpdate());
                    System.out.printf("Usuário atualizado %s", user);  
                }catch(UserNotFoundException | EmptyStorageException ex){
                      System.out.println(ex.getMessage());
                } finally{
                      System.out.println("===================");
                }
                }

                case DELETE -> {
                  try{  
                    dao.delete(requestId());
                    System.out.printf("Usuário excluído");
                   }catch(UserNotFoundException | EmptyStorageException ex){
                      System.out.println(ex.getMessage());
                    } finally{
                      System.out.println("===================");
                    }
                  }
                case FIND_BY_ID ->{
                  try{
                var id = requestId();
                var user = dao.findById(id);
                System.out.printf("Usuário com id %s:",id);
                System.out.println(user);
                  }catch(UserNotFoundException | EmptyStorageException ex){
                    System.out.println(ex.getMessage());
                  }
            }
                case FIND_ALL -> {
                    var users = dao.findAll();
                    System.out.println("Usuários cadastrados");
                    users.forEach(System.out::println);
                }
                
                case EXIT   -> System.exit(0);
                    
            }
            
        }
      }


      private static long requestId(){
        System.out.println("Informe o identificador do usuário");
        return scanner.nextLong();
      }

      private static UserModel requestToSave(){
        System.out.println("Informe o nome do usuário");
        var name = scanner.next();
        System.out.println("Informe o email do usuário");
        var email = scanner.next();
        System.out.println("Informe a data de nascimento do usuário (dd/MM/yyyy)");
        var birthdayString = scanner.next();
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var birthday = LocalDate.parse(birthdayString, formatter);
        return new UserModel(0, name, email, birthday);
      }
   
      private static UserModel requestToUpdate(){
        System.out.println("Informe o identificador do usuário");
        var id = scanner.nextLong();
        System.out.println("Informe o nome do usuário");
        var name = scanner.next();
        System.out.println("Informe o email do usuário");
        var email = scanner.next();
        System.out.println("Informe a data de nascimento do usuário (dd/MM/yyyy)");
        var birthdayString = scanner.next();
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var birthday = LocalDate.parse(birthdayString, formatter);
        return new UserModel(id, name, email, birthday);
      }
      
}
