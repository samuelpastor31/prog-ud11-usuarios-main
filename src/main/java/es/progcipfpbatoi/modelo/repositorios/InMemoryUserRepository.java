package es.progcipfpbatoi.modelo.repositorios;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dao.UserDAO;
import es.progcipfpbatoi.modelo.entidades.User;

import java.time.LocalDate;
import java.util.ArrayList;

public class InMemoryUserRepository implements UserDAO {

    private ArrayList<User> users;

    public InMemoryUserRepository() {
        users = new ArrayList<>();
        setDefaultUsers();
    }

    @Override
    public ArrayList<User> findAll() {
        return users;
    }

    @Override
    public ArrayList<User> findAll(String email) {
        ArrayList<User> filteredUsers = new ArrayList<>();
        for (User item : users) {
            if (item.getEmail().contains(email)) {
                filteredUsers.add(item);
            }
        }
        return filteredUsers;
    }

    @Override
    public User getById(String dni) throws NotFoundException, DatabaseErrorException {
        User usuario = findById(dni);
        if (usuario != null) {
            return usuario;
        }
        throw new NotFoundException("User", dni);
    }

    @Override
    public User findById(String dni) throws DatabaseErrorException{
        User searchedUser = new User(dni);
        if (users.contains(searchedUser)) {
            return users.get(users.indexOf(searchedUser));
        }
        return null;
    }

    @Override
    public User save(User user) throws DatabaseErrorException {
        int userIndex = users.indexOf(user);

        if (userIndex == -1) {
            if (this.users.add(user)) {
                return user;
            }

            throw new DatabaseErrorException("No se ha podido guardar el usuario");
        }

        this.users.set(userIndex, user);
        return user;
    }

    @Override
    public void remove(User user) throws NotFoundException {
        users.remove(user);
    }

    @Override
    public boolean getByDNI(String dni) throws NotFoundException {
        return false;
    }

    private void setDefaultUsers() {
        users.add(new User("Batoi", "surname 1 surname 2", "00000000A", "test@test.es", "03801", "+34600000111",
                LocalDate.of(1990,1,12), "!Aa23456789"));
        users.add(new User("Informática", "surname 2 surname 3", "00000000B", "test1@test.es", "03801", "+34600000222",
                LocalDate.of(1990,2,12), "01234567!Aa"));

    }
}
