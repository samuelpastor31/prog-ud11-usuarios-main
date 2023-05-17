package es.progcipfpbatoi;

import es.progcipfpbatoi.controlador.ChangeScene;
import es.progcipfpbatoi.controlador.UserController;
import es.progcipfpbatoi.modelo.dao.SQLUserDAO;
import es.progcipfpbatoi.modelo.dao.UserDAO;
import es.progcipfpbatoi.modelo.repositorios.InMemoryUserRepository;
import es.progcipfpbatoi.modelo.repositorios.UserRepository;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private UserDAO userDAO;

    @Override
    public void start(Stage stage) throws IOException {
        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
        SQLUserDAO sqlUserDAO = new SQLUserDAO();
        UserRepository userRepository = new UserRepository(sqlUserDAO);
        UserController userController = new UserController(userRepository);
        ChangeScene.change(stage, userController, "/vistas/user_list.fxml");
    }

    public static void main(String[] args) {
        launch();
    }

}