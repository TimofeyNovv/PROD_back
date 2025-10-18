package com.example.prodBack8.exceptions;

import com.example.prodBack8.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex){
        ErrorResponse error = new ErrorResponse("USER_NOT_FOUND", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex){
        ErrorResponse error = new ErrorResponse("USER_ALREADY_EXISTS", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPassword(InvalidPasswordException ex){
        ErrorResponse error = new ErrorResponse("INVALID_PASSWORD", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(GroupAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleGroupExists(GroupAlreadyExistsException ex){
        ErrorResponse error = new ErrorResponse("GROUP_ALREADY_EXISTS", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(GroupNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleGroupNotFound(GroupNotFoundException ex){
        ErrorResponse error = new ErrorResponse("GROUP_NOT_FOUND", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(NoGPULeftException.class)
    public ResponseEntity<ErrorResponse> handleNoGPULeft(NoGPULeftException ex){
        ErrorResponse error = new ErrorResponse("NO_GPU_LEFT", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(NoActiveSessionException.class)
    public ResponseEntity<ErrorResponse> handleNoActiveSessionException(NoActiveSessionException ex){
        ErrorResponse errorResponse = new ErrorResponse("NO_ACTIVE_SESSIONS", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(YouAreInTheQueueException.class)
    public ResponseEntity<ErrorResponse> handleYouAreInTheQueue(YouAreInTheQueueException ex){
        ErrorResponse errorResponse = new ErrorResponse("YOU_IN_QUEUE", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(ActiveSessionException.class)
    public ResponseEntity<ErrorResponse> handleActiveSession(ActiveSessionException ex){
        ErrorResponse errorResponse = new ErrorResponse("ACTIVE_SESSION", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(TimeGroupException.class)
    public ResponseEntity<ErrorResponse> handleTimeGroup(TimeGroupException ex){
        ErrorResponse errorResponse = new ErrorResponse("TIME_GROUP", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(UserInQueueNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserInQueueNotFound(UserInQueueNotFoundException ex){
        ErrorResponse errorResponse = new ErrorResponse("USER_IN_QUEUE_NOT_FOUND", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ForbiddenGPUInCurrentTimeException.class)
    public ResponseEntity<ErrorResponse> HandleForbiddenGPUInCurrentTime(ForbiddenGPUInCurrentTimeException ex){
        ErrorResponse errorResponse = new ErrorResponse("FORBIDDEN_GPU_IN_CURRENT_TIME", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
