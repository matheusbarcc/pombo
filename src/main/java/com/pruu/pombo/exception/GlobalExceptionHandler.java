package com.pruu.pombo.exception;

import java.util.stream.Collectors;
import com.pruu.pombo.exception.PomboException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Tratamento para PomboException
    @ExceptionHandler(PomboException.class)
    public ResponseEntity<String> handlePomboException(PomboException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatusCode());
    }

    // Tratamento para validações (ex: @Email, @CPF)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Concatena todas as mensagens de erro com um hífen no início, primeira letra maiúscula e ponto final
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .map(this::formatMessage)  // Aplica formatação
                .collect(Collectors.joining("\n")); // Cada erro em uma nova linha

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    // Função auxiliar para capitalizar a primeira letra, adicionar hífen e ponto final
    private String formatMessage(String message) {
        if (message == null || message.isEmpty()) {
            return message;
        }

        // Capitaliza a primeira letra e adiciona o hífen
        String formattedMessage = "- " + message.substring(0, 1).toUpperCase() + message.substring(1);

        // Adiciona ponto final se não houver
        if (!formattedMessage.endsWith(".")) {
            formattedMessage += ".";
        }

        return formattedMessage;
    }
}
