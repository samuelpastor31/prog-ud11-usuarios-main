package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.entidades.User;
import es.progcipfpbatoi.modelo.repositorios.UserRepository;
import es.progcipfpbatoi.util.AlertMessages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.format.DateTimeFormatter;

public class UserListViewCellController extends ListCell<User> {

    private static final DateTimeFormatter ENGLISH_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private AnchorPane root;

    @FXML
    private Label nameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label birthLabel;

    @FXML
    private Label zipcodeLabel;

    private UserController userController;
    private UserRepository userRepository;

    private ListView<User> userListView;
    private User user;


    public UserListViewCellController(ListView<User> userListView, UserController userController, UserRepository userRepository) {

        this.userListView = userListView;
        this.userController = userController;
        this.userRepository = userRepository;

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/vistas/user_list_item.fxml"));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(User user, boolean empty) {

        super.updateItem(user, empty);

        if (empty) {
            setGraphic(null);
        } else {
            this.user = user;
            nameLabel.setText(user.getName());
            emailLabel.setText(user.getEmail());
            birthLabel.setText(user.getBirthday().format(ENGLISH_DATE_FORMATTER));
            zipcodeLabel.setText(user.getZipCode());
            setGraphic(root);
        }
    }

    @FXML
    private void goToEditUserForm(ActionEvent event) {
        try{
            UserDetailController userDetailController = new UserDetailController(user, userRepository, userController, "/vistas/user_list.fxml");
            ChangeScene.change(event, userDetailController, "/vistas/user_form.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
            AlertMessages.mostrarAlertError("No se puede acceder al detalle de este usuario");
        }
    }

    @FXML
    private void deleteUser() {
        try {
            userRepository.remove(user);
            userListView.getItems().remove(user);
        } catch (NotFoundException ex) {
            ex.printStackTrace();
            AlertMessages.mostrarAlertError("No se ha podido eliminar al usuario");
        } catch (DatabaseErrorException e) {
            throw new RuntimeException(e);
        }
    }
}
