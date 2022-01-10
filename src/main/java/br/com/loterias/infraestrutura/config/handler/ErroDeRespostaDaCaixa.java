package br.com.loterias.infraestrutura.config.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.loterias.dominio.handler.ErroRetornadoPelaCaixa;

//@RestControllerAdvice
public class ErroDeRespostaDaCaixa {
	
	private static final String ERRO_CAIXA = "O site da CAIXA não retornou os dados! Tente novamente";
	
	@ResponseStatus(code = HttpStatus.GATEWAY_TIMEOUT)
	@ExceptionHandler(IOException.class)
	public ErroRetornadoPelaCaixa handle(IOException exception) {
		return new ErroRetornadoPelaCaixa(ERRO_CAIXA);
	}

}
