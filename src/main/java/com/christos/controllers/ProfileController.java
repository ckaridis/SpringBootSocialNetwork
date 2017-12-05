package com.christos.controllers;

import com.christos.model.Profile;
import com.christos.model.SiteUser;
import com.christos.service.ProfileService;
import com.christos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

// This is the controller for the profile pages.
@Controller
public class ProfileController {

    // We get the user service automatically
    @Autowired
    UserService userService;

    @Autowired
    ProfileService profileService;

    private SiteUser getUser() {

        // Here we get the authentication object from the authenticated user.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        return userService.get(email);

    }

    @RequestMapping(value = "/profile")
    public ModelAndView showProfile(ModelAndView modelAndView) {

        SiteUser user = getUser();
        Profile profile = profileService.getUserProfile(user);

        // If a user profile does not exist, we create a new one here. We create it,
        // we set the user associated with it, and finally we save it.
        if (profile == null) {
            profile = new Profile();
            profile.setUser(user);
            profileService.save(profile);
        }

        // We now add the profile to the View to be displayed. For security reasons we don't pass the
        // whole profile, but just the duplicated version we created earlier.
        Profile webProfile = new Profile();
        webProfile.safeCopyFrom(profile);

        // We pass the new profile to the JSP
        modelAndView.getModel().put("profile", webProfile);

        modelAndView.setViewName("app.profile");

        return modelAndView;
    }

    @RequestMapping(value = "/editProfileAbout", method = RequestMethod.GET)
    public ModelAndView editProfileAbout(ModelAndView modelAndView) {

        SiteUser user = getUser();
        Profile profile = profileService.getUserProfile(user);

        Profile webProfile = new Profile();
        webProfile.safeCopyFrom(profile);

        modelAndView.getModel().put("profile", webProfile);
        modelAndView.setViewName("app.editProfileAbout");

        return modelAndView;
    }
}
