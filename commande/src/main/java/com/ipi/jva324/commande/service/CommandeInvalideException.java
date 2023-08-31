package com.ipi.jva324.commande.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST) // https://www.baeldung.com/exception-handling-for-rest-with-spring
public class CommandeInvalideException extends Exception {

    public CommandeInvalideException(String message) {
        super(message);
    }

}
