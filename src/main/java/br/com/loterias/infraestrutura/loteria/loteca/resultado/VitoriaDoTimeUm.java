package br.com.loterias.infraestrutura.loteria.loteca.resultado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.com.loterias.aplicacao.loteca.VerificaResultadoDoJogo;

@Component("vitoriaTimeUm")
public class VitoriaDoTimeUm implements VerificaResultadoDoJogo {
	
	private static final String RESULTADO = "VITÓRIA TIME 1";
	
	@Autowired
	@Qualifier("empate")
	private VerificaResultadoDoJogo proximaVerificacao;

	@Override
	public String resultado(String timeUm, String timeDois) {
		if(timeUm == null) {
			return RESULTADO;
		}
		return proximaVerificacao.resultado(timeUm, timeDois);
	}

}
