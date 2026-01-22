package br.com.alura.forumhub.infra.exception;

import java.util.LinkedHashMap;
import java.util.Map;

public class ErroControllerException extends RuntimeException {

    Map<String, String> erro = new LinkedHashMap<>();

    public ErroControllerException(String titulo, String mensagem) {
        super("Erro de validação");
        erro.put("titulo", titulo);
        erro.put("mensagem", mensagem);
    }

    public Map<String, String> getErro() {
        return erro;
    }
}
