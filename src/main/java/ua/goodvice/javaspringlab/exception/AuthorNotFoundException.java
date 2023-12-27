package ua.goodvice.javaspringlab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AuthorNotFoundException extends RuntimeException{
    public AuthorNotFoundException(String name){
        super("Author " + name +" does not exist");
    }
}
