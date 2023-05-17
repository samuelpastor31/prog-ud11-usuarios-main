package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.exceptions.AlreadyExistsException;
import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.entidades.User;
import es.progcipfpbatoi.modelo.repositorios.UserRepository;
import es.progcipfpbatoi.util.AlertMessages;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class NewUserController extends UserFormController {

    @FXML
    private Label titleLabel;

    public NewUserController(UserRepository userRepository, Initializable controladorPadre, String vistaPadre) {
        super(userRepository, controladorPadre, vistaPadre);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleLabel.setText("Crear nuevo usuario");
    }

    @Override
    protected void saveUser(User user) throws AlreadyExistsException, DatabaseErrorException, NotFoundException {
        if (userRepository.getByDNI(user.getDni())) {
            throw new AlreadyExistsException("El dni del usuario ya existe");
        } else {
            userRepository.save(user);
            AlertMessages.mostrarAlertInformacion("Nuevo usuario guardado con éxito");
        }
    }
}
