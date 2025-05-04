package org.ivanov.front.handler;


import org.ivanov.front.handler.exception.AccountException;
import org.ivanov.front.handler.exception.LoginException;
import org.ivanov.front.handler.response.ApiError;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(LoginException.class)
    private ResponseEntity<ApiError> handleException(LoginException e) {
        ApiError apiError = new ApiError();
        apiError.setMessage(e.getMessage());
        apiError.setStatus(e.getStatus());
        return ResponseEntity.status(Integer.parseInt(apiError.getStatus())).body(apiError);
    }
}
