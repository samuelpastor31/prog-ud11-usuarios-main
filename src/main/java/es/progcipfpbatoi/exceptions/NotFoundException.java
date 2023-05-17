package es.progcipfpbatoi.exceptions;

public class NotFoundException extends Exception {

    public NotFoundException(String tipo, String id) {
        super("La entidad " +  tipo + " identificador " + id + " no ha sido encontrada");
    }

}

