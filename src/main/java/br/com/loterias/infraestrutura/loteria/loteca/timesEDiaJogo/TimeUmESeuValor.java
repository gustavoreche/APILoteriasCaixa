package br.com.loterias.infraestrutura.loteria.loteca.timesEDiaJogo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.com.loterias.aplicacao.loteca.VerificaTipoDoCampoEValor;

@Component("timeUmESeuValor")
public class TimeUmESeuValor implements VerificaTipoDoCampoEValor {

	private static final String CAMPO = "timeUm";

	@Autowired
	@Qualifier("diaDaSemanaESeuValor")
	private VerificaTipoDoCampoEValor proximaVerificacao;

	@Override
	public Map<String, String> resultado(String timeUm, String timeDois, String valor) {
		if (timeUm == null) {
			HashMap<String, String> resultado = new HashMap<String, String>();
			resultado.put(CAMPO, valor.trim());
			return resultado;
		}
		return proximaVerificacao.resultado(timeUm, timeDois, valor);
	}

}
