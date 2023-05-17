package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.entidades.User;
import es.progcipfpbatoi.services.MySqlConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class SQLUserDAO implements UserDAO {

    private Connection connection;
    private static final String TABLE_NAME = "User";

    @Override
    public ArrayList<User> findAll() throws DatabaseErrorException {
        String sql = String.format("SELECT * FROM %s", TABLE_NAME);

        ArrayList<User>users = new ArrayList<>();
        connection =  new MySqlConnection("192.168.56.101", "UsuariosProg", "batoi", "1234").getConnection();

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {

            while(resultSet.next()) {
                User user = getTaskFromResultset(resultSet);
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en la conexión o acceso a la base de datos (select)");
        }

        return users;
    }

    @Override
    public ArrayList<User> findAll(String email) throws DatabaseErrorException {
        ArrayList<User> tareasFiltradas = new ArrayList<>();
        for (User user: findAll()) {
            if (user.empiezaPor(email)) {
                tareasFiltradas.add(user);
            }
        }

        return tareasFiltradas;
    }

    @Override
    public User getById(String dni) throws NotFoundException, DatabaseErrorException {
        String sql = String.format("SELECT * FROM %s WHERE dni = ?",TABLE_NAME);
        connection =  new MySqlConnection("192.168.56.101", "UsuariosProg", "batoi", "1234").getConnection();

        try (
                PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, dni);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                User user = getTaskFromResultset(resultSet);
                if (user.getDni().equals( dni)) {
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (select)");
        }
        return null;
    }

    @Override
    public boolean getByDNI(String dni) {
        try {
            ArrayList<User> users = findAll();
            for (User user : users) {
                if (user.getDni().equals(dni)) {
                    return true;
                }
            }
        } catch (DatabaseErrorException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public User findById(String dni) throws DatabaseErrorException {
        try {
            return getById(dni);
        } catch (NotFoundException ex) {
            return null;
        }
    }

    @Override
    public User save(User user) throws DatabaseErrorException {
        if (findById(user.getDni()) == null) {
            return insert(user);
        } else {
            return update(user);
        }
    }

    private User insert(User user) throws DatabaseErrorException {
        String sql = "INSERT INTO "+ TABLE_NAME + " VALUES (?,?,?,?,?,?,?,?);";
        connection =  new MySqlConnection("192.168.56.101", "UsuariosProg", "batoi", "1234").getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getDni());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getZipCode());
            preparedStatement.setString(6, user.getMobilePhone());
            preparedStatement.setTimestamp(7, Timestamp.valueOf(user.getBirthday().atTime(LocalTime.now())));
            preparedStatement.setString(8, user.getPassword());
            preparedStatement.executeUpdate();

            return user;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (insert)");
        }
    }

    private User update(User user) throws DatabaseErrorException{
        String sql = String.format("UPDATE %s SET name = ?, surname = ?, email = ?, zipCode = ?, mobilePhone = ?, birthday = ?, password = ? WHERE dni = ?",
                TABLE_NAME);
         connection =  new MySqlConnection("192.168.56.101", "UsuariosProg", "batoi", "1234").getConnection();
        try (

                PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getZipCode());
            statement.setString(5, user.getMobilePhone());
            statement.setTimestamp(6, Timestamp.valueOf(user.getBirthday().atStartOfDay()));
            statement.setString(7, user.getPassword());
            statement.setString(8, user.getDni());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (update)");
        }

        return user;
    }

    @Override
    public void remove(User user) throws DatabaseErrorException, NotFoundException {
        String sql = String.format("DELETE FROM %s WHERE dni = ?", TABLE_NAME);
        connection =  new MySqlConnection("192.168.56.101", "UsuariosProg", "batoi", "1234").getConnection();
        try (
                PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, user.getDni());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (delete)");
        }
    }

    private User getTaskFromResultset(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String dni = rs.getString("dni");
        String email = rs.getString("email");
        String zipCode = rs.getString("zipCode");
        String mobilePhone = rs.getString("mobilePhone");
        LocalDate birthday = rs.getTimestamp("birthday").toLocalDateTime().toLocalDate();
        String password = rs.getString("password");

        return new User(name,surname,dni,email,zipCode,mobilePhone,birthday,password);
    }

}
