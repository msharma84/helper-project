package org.example.streams.validator;

import java.util.function.Predicate;

public class RegistrationService {

    private final Predicate<String> emailValidator;
    private final Predicate<String> passwordValidator;

    public RegistrationService(Predicate<String> emailValidator, Predicate<String> passwordValidator) {
        this.emailValidator = emailValidator;
        this.passwordValidator = passwordValidator;
    }

    public String register(User user){

        if(!emailValidator.test(user.getEmail())){
            throw new InCorrectEmailException("Email is incorrect");
        }
        if(passwordValidator.test(user.getPassword())){
            throw new InValidPasswordException("Password is invalid");
        }
        return "User is registered : "+user;
    }
}
