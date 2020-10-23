package br.com.loterias.infraestrutura.loteria;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import br.com.loterias.aplicacao.loteria.EnumTipoDeLoteria;

@Component
public class PegarGanhadores {
	
	private static final String SAO_OS_GANHADORES = "a href=\"/wps/portal/loterias/landing/";
	private static final String REGEX_PARA_PEGAR_GANHADORES = "(?<=\">)([^<\\/a>]+)";
	private static final String LINHA_PARA_CONFERENCIA = "Confira";
	
	public boolean foramOsGanhadores(String linhaParaSerAnalisada, EnumTipoDeLoteria tipoDeLoteria) {
		return linhaParaSerAnalisada.contains(SAO_OS_GANHADORES + tipoDeLoteria.getNomeGanhadores()) && 
				!linhaParaSerAnalisada.contains(LINHA_PARA_CONFERENCIA);
	}

	public String pega(String linhaParaSerAnalisada) {
		Pattern modeloParaSepararCaractere = Pattern.compile(REGEX_PARA_PEGAR_GANHADORES);
		Matcher caractereSeparado = modeloParaSepararCaractere.matcher(linhaParaSerAnalisada);
		if (caractereSeparado.find()) {
			return caractereSeparado.group(0);
		}
		return "";
	}
	
}
