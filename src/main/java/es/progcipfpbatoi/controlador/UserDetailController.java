package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.entidades.User;
import es.progcipfpbatoi.modelo.repositorios.UserRepository;
import es.progcipfpbatoi.util.AlertMessages;
import es.progcipfpbatoi.validator.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class UserDetailController extends UserFormController {

    private User user;

    public UserDetailController(
            User user,
            UserRepository userRepository,
            Initializable controladorPadre,
            String vistaPadre) {

        super(userRepository, controladorPadre, vistaPadre);
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textFieldDNI.setText(user.getDni());
        textFieldName.setText(user.getName());
        textFieldSurname.setText(user.getSurname());
        textFieldEmail.setText(user.getEmail());
        textFieldPhone.setText(user.getMobilePhone());
        datePickerBirthDate.setValue(user.getBirthday());
        textFieldZipCode.setText(user.getZipCode());
        passwordField1.setText(user.getPassword());
        passwordField2.setText(user.getPassword());
    }

    @Override
    protected void saveUser(User user) throws DatabaseErrorException {
        userRepository.save(user);
        AlertMessages.mostrarAlertInformacion("Cambios guardados con éxito");
    }
}
