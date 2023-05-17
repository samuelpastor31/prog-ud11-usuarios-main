package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.exceptions.AlreadyExistsException;
import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.entidades.User;
import es.progcipfpbatoi.modelo.repositorios.UserRepository;
import es.progcipfpbatoi.util.AlertMessages;
import es.progcipfpbatoi.validator.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/*
 * Clase común al formulario de inserción de nuevo usuario y al de detalle de usuario
 */

public abstract class UserFormController implements Initializable {

    @FXML
    protected TextField textFieldDNI;
    @FXML
    protected TextField textFieldName;

    @FXML
    protected TextField textFieldSurname;

    @FXML
    protected TextField textFieldEmail;

    @FXML
    protected TextField textFieldPhone;

    @FXML
    protected DatePicker datePickerBirthDate;

    @FXML
    protected TextField textFieldZipCode;

    @FXML
    protected PasswordField passwordField1;

    @FXML
    protected PasswordField passwordField2;

    protected UserRepository userRepository;
    private Initializable controladorPadre;
    private String vistaPadre;

    public UserFormController(UserRepository userRepository, Initializable controladorPadre, String vistaPadre) {
        this.userRepository = userRepository;
        this.controladorPadre = controladorPadre;
        this.vistaPadre = vistaPadre;
    }

    /**
     * Inicialización de la vista (difiere entre la vista de detalle y la vista de nuevo usuario).
     * @param url
     * @param resourceBundle
     */
    @Override
    public abstract void initialize(URL url, ResourceBundle resourceBundle);

    /**
     * Función que se ejecuta al hacer click sobre el botón Confirmar
     * Valida todos los datos del formulario, y si no cumple con alguna restricción no añade/actualiza al usuario
     * @param event
     */
    @FXML
    private void handleButtonSave(ActionEvent event) {

        ArrayList<String> errors = new ArrayList<>();

        String name = textFieldName.getText();
        if (!Validator.hasLength(name, 3)) {
            errors.add("El nombre debe tener al menos 3 caracteres");
        }

        String surname = textFieldSurname.getText();
        if (!Validator.hasLength(surname, 3)) {
            errors.add("El apellido debe tener al menos 3 caracteres");
        }

        String email = textFieldEmail.getText();
        if (!Validator.isValidEmail(email)) {
            errors.add("El email no es válido");
        }

        String dni = textFieldDNI.getText();
        if (!Validator.isValidDNI(dni)) {
            errors.add("El DNI no es válido");
        }


        String mobilePhone = textFieldPhone.getText();
        if (!Validator.isValidMobilePhone(mobilePhone)) {
            errors.add("El teléfono no es un móvil válido");
        }

        // Por la naturaleza del componente datePicker, no se valida la fecha
        LocalDate birthdayDate = datePickerBirthDate.getValue();

        String zipCode = textFieldZipCode.getText();
        if (!Validator.isValidZipCode(zipCode)) {
            errors.add("EL código postal no es un código postal español válido");
        }


        String password1 = passwordField1.getText();
        if (!Validator.isValidPassword(password1)) {
            errors.add("La contraseña debe tener una longitud entre 5 y 20 caracteres y contener al menos letras mayúsculas, minúsculas y un carácter especial");
        }

        String password2 = passwordField2.getText();
        if (!password1.equals(password2)) {
            errors.add("Las contraseñas introducidas no coinciden");
        }

        if (errors.size() > 0) {
            AlertMessages.mostrarAlertError(errors.toString());
        } else {
            try {
                User user = new User(name, surname, dni, email, zipCode, mobilePhone, birthdayDate, password1);
                saveUser(user);
                ChangeScene.change(event, controladorPadre, vistaPadre);
            } catch (AlreadyExistsException ex) {
                AlertMessages.mostrarAlertError(ex.getMessage());
            } catch (DatabaseErrorException ex) {
                ex.printStackTrace();
                AlertMessages.mostrarAlertError("No se han podido guardar los cambios");
            } catch (IOException ex) {
                ex.printStackTrace();
                AlertMessages.mostrarAlertError("No se puede mostrar el listado de usuarios");
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void handleButtonBack(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ChangeScene.change(stage, controladorPadre, vistaPadre);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método abstracto que será implementado de manera diferente por la inserción de
     * un nuevo usuario (donde se debe comprobar que el dni no exista ya antes de almacenar)
     * y por la actualización de un nuevo usuario (donde se actualiza directamente sus datos ya que el usuario
     * ya existe)
     * @param user
     * @throws AlreadyExistsException
     * @throws DatabaseErrorException
     */
    protected abstract void saveUser(User user) throws AlreadyExistsException, DatabaseErrorException, NotFoundException;
}
