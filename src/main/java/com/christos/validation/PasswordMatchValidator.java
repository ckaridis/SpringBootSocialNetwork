package com.christos.validation;

import com.christos.model.SiteUser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, SiteUser>{


    @Override
    public void initialize(PasswordMatch constraintAnnotation) {

    }

    @Override
    public boolean isValid(SiteUser user, ConstraintValidatorContext c) {

        String plainPassword = user.getPlainPassword();
        String repeatPassword = user.getRepeatPassword();

        // This is going to run every time a user object is created or updated

        // This has to be added in case the repead password is null (used in update user)
        if (plainPassword == null){
            return true;
        }

        if(!plainPassword.equals(repeatPassword)){
            return false;
        }

        return true;
    }
}
