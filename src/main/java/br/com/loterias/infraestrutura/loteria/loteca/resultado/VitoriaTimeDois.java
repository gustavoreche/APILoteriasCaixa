package br.com.loterias.infraestrutura.loteria.loteca.resultado;

import org.springframework.stereotype.Component;

import br.com.loterias.aplicacao.loteca.VerificaResultadoDoJogo;

@Component("vitoriaTimeDois")
public class VitoriaTimeDois implements VerificaResultadoDoJogo {
	
	private static final String RESULTADO = "VITÓRIA TIME 2";
	
	@Override
	public String resultado(String timeUm, String timeDois) {
		if(timeUm != null && timeDois != null) {
			return RESULTADO;
		}
		return "";
	}

}
