package com.christos.controllers;

import com.christos.model.SiteUser;
import com.christos.model.VerificationToken;
import com.christos.service.EmailService;
import com.christos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.util.Date;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Value("${message.registration.confirmed}")
    private String registrationConfirmedMessage;

    @Value("${message.expired.token}")
    private String expiredTokenMessage;

    @Value("${message.invalid.user}")
    private String invalidUserMessage;

    @RequestMapping("/login")
    String admin(){
        return "app.login";
    }

    @RequestMapping("/verifyEmail")
    String verifyEmail(){
        return "app.verifyEmail";
    }

    // The controller for the messages
    @RequestMapping("/confirmRegistration")
    ModelAndView registrationConfirmed(ModelAndView modelAndView, @RequestParam("t") String tokenString){

        // We verify that the string stored in our database is the same as the user clicked
        VerificationToken token = userService.getVerificationToken(tokenString);

        if(token == null){
            modelAndView.setViewName("redirect:/invalidUser");
            userService.deleteToken(token);
            return modelAndView;
        }

        Date expiryDate = token.getExpiry();
        // Here we check if the token has expired
        if(expiryDate.before(new Date())){
            modelAndView.setViewName("redirect:/expiredToken");
            userService.deleteToken(token);
            return modelAndView;
        }

        SiteUser user = token.getUser();
        // Here we check if the user still exists
        if (user == null){
            modelAndView.setViewName("redirect:/invalidUser");
            userService.deleteToken(token);
            return modelAndView;
        }

        user.setEnabled(true);
        userService.save(user);

        modelAndView.getModel().put("message", registrationConfirmedMessage);
        modelAndView.setViewName("app.message");
        return modelAndView;
    }

    // The controller for the messages
    @RequestMapping("/expiredToken")
    ModelAndView expiredToken(ModelAndView modelAndView){
        modelAndView.getModel().put("message", expiredTokenMessage);
        modelAndView.setViewName("app.message");
        return modelAndView;
    }

    // The controller for the messages
    @RequestMapping("/invalidUser")
    ModelAndView invalidUser(ModelAndView modelAndView){
        modelAndView.getModel().put("message", invalidUserMessage);
        modelAndView.setViewName("app.message");
        return modelAndView;
    }

    @RequestMapping (value = "/register", method = RequestMethod.GET)
    ModelAndView register (ModelAndView modelAndView) throws FileNotFoundException {

        SiteUser user = new SiteUser();
        modelAndView.getModel().put("user", user);
        modelAndView.setViewName("app.register");

        return modelAndView;
    }


    @RequestMapping (value = "/register", method = RequestMethod.POST)
    ModelAndView register (ModelAndView modelAndView, @ModelAttribute(value = "user")
    @Valid SiteUser user, BindingResult result) {

        modelAndView.setViewName("app.register");

        if(!result.hasErrors()){
            userService.register(user);

            String token = userService.createEmailVerificationToken(user);

            // We send the verification email to the user email
            emailService.sendVerificationEmail(user.getEmail(), token);

            modelAndView.setViewName("redirect:/verifyEmail");

            // For testing, we set the user to enabled exactly after registration
            //user.setEnabled(true);
            //userService.save(user);
        }

        return modelAndView;
    }

}
