package org.ivanov.front.handler;


import org.ivanov.front.handler.exception.AccountException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(AccountException.class)
    private ModelAndView handleException(AccountException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");
        modelAndView.addObject("errorMessage", e.getMessage());
        modelAndView.addObject("createAccountDto", e.getDto());
        return modelAndView;
    }
}
