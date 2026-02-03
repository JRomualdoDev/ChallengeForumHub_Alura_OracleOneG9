package br.com.alura.forumhub.infra.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Arrays;
import java.util.Map;

@RestControllerAdvice
public class tratamentoDosErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity tratarResourceNotFoundException(NoResourceFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recurso não encontrado");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErroValidacao(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors()
                        .stream()
                        .map(DadosErroValidacao::new)
                        .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity tratarErro400(HttpMessageNotReadableException ex) {

        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException erro = (InvalidFormatException) ex.getCause();

            String valorInvalido = erro.getValue().toString();

            String mensagem = "O valor '" + valorInvalido + "' não é aceito. Valores aceitos: " +
                    Arrays.toString(erro.getTargetType().getEnumConstants());

            return ResponseEntity.badRequest().body(
                        new DadosErroValidacao("Valor incorreto", mensagem)
                    );
        }

        String erro = "Formato invalido.";

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DadosErroValidacao("Erro de Sintaxe JSON", erro));
    }

    @ExceptionHandler(ErrosControllerException.class)
    public ResponseEntity tratarErrosCustomController(ErrosControllerException ex) {
        Map<String, String> erros = ex.getErros();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }

    @ExceptionHandler(ErroControllerException.class)
    public ResponseEntity tratarErroCustomController(ErroControllerException ex) {
        Map<String, String> erro = ex.getErro();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    public record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        };

    }
}
