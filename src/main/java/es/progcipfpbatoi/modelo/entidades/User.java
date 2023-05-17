package es.progcipfpbatoi.modelo.entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class User {

    private  String name;

    private String surname;

    private String dni;

    private String email;

    private String zipCode;

    private String mobilePhone;

    private LocalDate birthday;

    private String password;

    public User(String name, String surname,String dni, String email, String zipCode,
                String mobilePhone, LocalDate birthday, String password) {
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.email = email;
        this.zipCode = zipCode;
        this.mobilePhone = mobilePhone;
        this.birthday = birthday;
        this.password = password;
    }

    public User(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public String getDni() {
        return dni;
    }

    public String getEmail() {
        return email;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return getDni().equals(user.getDni());
    }

    public boolean empiezaPor(String text) {
        return this.email.startsWith(text);
    }

    public void setDni(String dni) {
        this.dni = dni;
    }



    @Override
    public int hashCode() {
        return Objects.hash(getDni());
    }
}

