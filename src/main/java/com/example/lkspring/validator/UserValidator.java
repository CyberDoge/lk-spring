package com.example.lkspring.validator;


import com.example.lkspring.model.User;
import com.example.lkspring.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Component
public class UserValidator implements Validator {
    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(User.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User)target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername().length() < 2 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "username have to be longer then 6 and shorter then 32");
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "user with this username is already exist");
        }
        try {
            var emailAddress = new InternetAddress(user.getEmail());
            emailAddress.validate();
        } catch (AddressException e) {
            errors.rejectValue("email", "this email is not correct, or not exist");
        }
        if(userService.findByEmail(user.getEmail())!=null){
            errors.rejectValue("email", "this email has been already registered");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 6 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "password have to be longer then 6 and shorter then 32");
        }

        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "passwords are not confirm");
        }
    }
}
