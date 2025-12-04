package uz.pdp.lmsad.exception;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.pdp.lmsad.dto.error.AppErrorDto;

import java.util.*;

//@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ObjectMapper objectMapper;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AppErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request)  {
        String friendlyMessage = "Not Valid Input";
        String errorPath = request.getRequestURI();
        Map<String, List<String>> developerMessage = new HashMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            developerMessage.compute(field, (s, values) -> {
                if (!Objects.isNull(values))
                    values.add(message);
                else
                    values = new ArrayList<>(Collections.singleton(message));
                return values;
            });
        }
        AppErrorDto appErrorDto = new AppErrorDto(friendlyMessage, developerMessage, errorPath, 400);
//        log.error("Validation Error : {}", appErrorDto);
        return ResponseEntity.status(400).body(appErrorDto);
    }


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<AppErrorDto> handleNoSuchElementException(NoSuchElementException e, HttpServletRequest request) {
        String friendlyMessage = e.getMessage();
        String errorPath = request.getRequestURI();
        AppErrorDto appErrorDto = new AppErrorDto(friendlyMessage, e.getMessage(), errorPath, 404);
//        log.error("Server Error : {}", appErrorDto);
        return ResponseEntity
                .status(404)
                .body(appErrorDto);
    }

//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<AppErrorDto> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
//        String friendlyMessage = "Internal Server Error";
//        String errorPath = request.getRequestURI();
//        AppErrorDto appErrorDto = new AppErrorDto(friendlyMessage, e.getMessage(), errorPath, 500);
////        log.error("Server Error : {}", appErrorDto);
//        return ResponseEntity
//                .status(500)
//                .body(appErrorDto);
//    }




}
