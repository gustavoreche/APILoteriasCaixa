package br.com.loterias.infraestrutura.loteria.loteca.resultado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.com.loterias.aplicacao.loteca.VerificaResultadoDoJogo;

@Component("empate")
public class Empate implements VerificaResultadoDoJogo {
	
	private static final String RESULTADO = "EMPATE";
	
	@Autowired
	@Qualifier("vitoriaTimeDois")
	private VerificaResultadoDoJogo proximaVerificacao;

	@Override
	public String resultado(String timeUm, String timeDois) {
		if(timeUm != null && timeDois == null) {
			return RESULTADO;
		}
		return proximaVerificacao.resultado(timeUm, timeDois);
	}

}
