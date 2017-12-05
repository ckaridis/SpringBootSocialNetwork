// This is the custom validation to check if the Password
// on the second field matches the password on the first field during registration
// This is how it needs to be done.

package com.christos.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Target(TYPE)                     // This is telling Spring that it's going to check a type (SiteUser type)
@Retention(RUNTIME)               // This indicates that it's going to run at runtime (not just check)
@Constraint(validatedBy = PasswordMatchValidator.class)   // This is where the validation is going to happen
@Documented                       // This just makes the documentation
public @interface PasswordMatch {

    String message() default "{error.password.mismatch}"; // This is the default error message

    Class<?>[] groups() default {};                       // Allows to create constrain validation groups (needed)

Class<? extends Payload>[] payload() default {};
}