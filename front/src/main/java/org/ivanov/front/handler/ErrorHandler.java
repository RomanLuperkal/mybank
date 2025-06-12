package org.ivanov.front.handler;


import org.ivanov.front.handler.exception.GatewayException;
import org.ivanov.front.handler.exception.RegistrationException;
import org.ivanov.front.handler.exception.AccountException;
import org.ivanov.front.handler.exception.TransferException;
import org.ivanov.front.handler.response.ApiError;
import org.ivanov.transferdto.ResponseTransferDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(RegistrationException.class)
    private ModelAndView handleException(RegistrationException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");
        modelAndView.addObject("errorMessage", e.getMessage());
        modelAndView.addObject("createAccountDto", e.getDto());
        return modelAndView;
    }

    @ExceptionHandler(AccountException.class)
    private ResponseEntity<ApiError> handleException(AccountException e) {
        ApiError apiError = new ApiError();
        apiError.setMessage(e.getMessage());
        apiError.setStatus(e.getStatus());
        return ResponseEntity.status(Integer.parseInt(apiError.getStatus())).body(apiError);
    }

    @ExceptionHandler(GatewayException.class)
    private ResponseEntity<ApiError> handleException(GatewayException e) {
        ApiError apiError = new ApiError();
        apiError.setMessage(e.getMessage());
        apiError.setStatus(e.getStatus().toString());
        return ResponseEntity.status(Integer.parseInt(apiError.getStatus())).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ApiError> handleException(MethodArgumentNotValidException e) {
        ApiError apiError = new ApiError();
        apiError.setMessage(e.getBindingResult().getAllErrors().getFirst().getDefaultMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(ResponseStatusException.class)
    private ResponseEntity<?> handleException(ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
    }

    @ExceptionHandler(TransferException.class)
    private ResponseEntity<ResponseTransferDto> handleException(TransferException e) {
        return ResponseEntity.status(e.getStatus()).body(new ResponseTransferDto(e.getMessage()));
    }
}
