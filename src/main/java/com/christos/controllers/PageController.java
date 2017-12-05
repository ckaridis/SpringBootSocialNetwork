package com.christos.controllers;

import com.christos.model.StatusUpdate;
import com.christos.service.StatusUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class PageController {

    // We Autowire the Status Update Service. Spring will find it automatically.
    @Autowired
    private StatusUpdateService statusUpdateService;

    @Value("${message.error.forbidden}")
    private String accessDeniedMessage;

    @RequestMapping("/")
    ModelAndView home(ModelAndView modelAndView) {

        StatusUpdate statusUpdate = statusUpdateService.getlatest();

        modelAndView.getModel().put("statusUpdate", statusUpdate);

        modelAndView.setViewName("app.homepage");
        return modelAndView;
    }

    @RequestMapping("/403")
    ModelAndView accessDenied(ModelAndView modelAndView){
        modelAndView.getModel().put("message", accessDeniedMessage);
        modelAndView.setViewName("app.message");
        return modelAndView;
    }


    @RequestMapping("/about")
    String about() {
        return "app.about";
    }

}
