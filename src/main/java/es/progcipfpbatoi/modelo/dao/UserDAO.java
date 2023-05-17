package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.entidades.User;

import java.util.ArrayList;

public interface UserDAO {
    /**
     *  Obtiene todas las tareas
     */
    ArrayList<User> findAll() throws DatabaseErrorException;

    /**
     * Obtiene todas las tareas que comiencen por @text
     * @param text
     * @return
     */
    ArrayList<User> findAll(String text) throws DatabaseErrorException;

    User getById(String dni) throws NotFoundException, DatabaseErrorException;

    User findById(String dni) throws DatabaseErrorException;

    User save(User user) throws DatabaseErrorException;

    void remove(User user) throws DatabaseErrorException, NotFoundException;

    boolean getByDNI(String dni) throws NotFoundException;
}
