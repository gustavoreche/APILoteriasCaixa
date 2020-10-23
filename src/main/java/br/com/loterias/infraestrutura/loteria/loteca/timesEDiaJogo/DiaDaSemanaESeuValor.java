package br.com.loterias.infraestrutura.loteria.loteca.timesEDiaJogo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.com.loterias.aplicacao.loteca.VerificaTipoDoCampoEValor;

@Component("diaDaSemanaESeuValor")
public class DiaDaSemanaESeuValor implements VerificaTipoDoCampoEValor {

	private static final String CAMPO = "diaDaSemana";

	@Override
	public Map<String, String> resultado(String timeUm, String timeDois, String valor) {
		HashMap<String, String> resultado = new HashMap<String, String>();
		resultado.put(CAMPO, valor.contains("Sá") ? "Sábado" : "Domingo");
		return resultado;
	}

}
