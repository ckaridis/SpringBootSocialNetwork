package com.christos.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Value("${message.error.exception}")
    private String exceptionMessage;

    @Value("${message.error.dublicate.user}")
    private String dublicateUserMessage;


    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ModelAndView dublicateUserHandler(HttpServletRequest req, Exception e) {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.getModel().put("message", dublicateUserMessage);
        modelAndView.getModel().put("url", req.getRequestURI());
        modelAndView.getModel().put("exception", e);

        modelAndView.setViewName("app.exception");

        return modelAndView;

    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.getModel().put("message", exceptionMessage);
        modelAndView.getModel().put("url", req.getRequestURI());
        modelAndView.getModel().put("exception", e);

        modelAndView.setViewName("app.exception");

        return modelAndView;

    }

}
