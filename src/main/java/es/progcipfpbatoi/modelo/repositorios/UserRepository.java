package es.progcipfpbatoi.modelo.repositorios;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dao.UserDAO;
import es.progcipfpbatoi.modelo.entidades.User;

import java.util.ArrayList;

public class UserRepository {

    private UserDAO userDAO;

    public UserRepository(UserDAO userDAO){
        this.userDAO = userDAO;
    }
    public ArrayList<User> findAll() throws DatabaseErrorException{
        return userDAO.findAll();
    }

    public ArrayList<User> findAll(String email) throws DatabaseErrorException{
        return userDAO.findAll(email);
    }

    public User getById(String dni) throws DatabaseErrorException, NotFoundException {
        return userDAO.getById(dni);
    }


    public void save(User user) throws DatabaseErrorException {
        userDAO.save(user);
    }

    public void remove(User user) throws NotFoundException, DatabaseErrorException {
        userDAO.remove(user);
    }

    public boolean getByDNI(String dni) throws NotFoundException {
        return this.userDAO.getByDNI(dni);
    }
}
