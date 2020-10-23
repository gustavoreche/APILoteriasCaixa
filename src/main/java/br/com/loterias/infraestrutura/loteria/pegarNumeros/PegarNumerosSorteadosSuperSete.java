package br.com.loterias.infraestrutura.loteria.pegarNumeros;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.com.loterias.aplicacao.loteria.EnumTipoDeLoteria;
import br.com.loterias.aplicacao.loteria.PegarNumerosSorteados;

@Component("numerosSuperSete")
@RequestScope
public class PegarNumerosSorteadosSuperSete implements PegarNumerosSorteados<Integer> {
	
	private static final String SAO_OS_NUMEROS_SORTEADOS = "ul class=\"resultado-loteria supersete\"";
	private static final String REGEX_PARA_PEGAR_NUMEROS = "(?<=\"dezena\">)([^<\\/a>]+)";

	@Override
	public boolean foramSorteados(String linhaParaSerAnalisada, EnumTipoDeLoteria tipoDeLoteria) {
		return linhaParaSerAnalisada.contains(SAO_OS_NUMEROS_SORTEADOS);
	}

	@Override
	public Integer pega(String linhaParaSerAnalisada) {
		Pattern modeloParaSepararCaractere = Pattern.compile(REGEX_PARA_PEGAR_NUMEROS);
		Matcher caractereSeparado = modeloParaSepararCaractere.matcher(linhaParaSerAnalisada);
		if (caractereSeparado.find()) {
			return Integer.parseInt(caractereSeparado.group(0));
		}
		return null;
	}

}
