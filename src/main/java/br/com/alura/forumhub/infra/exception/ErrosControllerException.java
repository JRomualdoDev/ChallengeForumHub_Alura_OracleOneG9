package br.com.alura.forumhub.infra.exception;

import java.util.Map;

public class ErrosControllerException  extends RuntimeException{

    private final Map<String,String> erros;

    public ErrosControllerException(Map<String,String> erros) {
        super("Erros de validações encontrados");
        this.erros = erros;
    }
    
    public Map<String,String> getErros() {
        return erros;
    }
}
