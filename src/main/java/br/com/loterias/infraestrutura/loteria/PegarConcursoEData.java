package br.com.loterias.infraestrutura.loteria;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class PegarConcursoEData {
	
	private static final String SAO_OS_DADOS_DO_CONCURSO = "<p class=\"description\">Concurso";
	private static final String REGEX_PARA_PEGAR_NUMERO_DO_CONCURSO = "(?<=\">Concurso)([^-]+)";
	private static final String REGEX_PARA_PEGAR_DATA_DO_CONCURSO = "(?<=, )([^<\\/p>]+)";

	public boolean foramOsDadosDoConcurso(String linhaParaSerAnalisada) {
		return linhaParaSerAnalisada.contains(SAO_OS_DADOS_DO_CONCURSO);
	}

	public String pegaConcurso(String linhaParaSerAnalisada) {
		Pattern modeloParaSepararCaractere = Pattern.compile(REGEX_PARA_PEGAR_NUMERO_DO_CONCURSO);
		Matcher caractereSeparado = modeloParaSepararCaractere.matcher(linhaParaSerAnalisada);
		if (caractereSeparado.find()) {
			return caractereSeparado.group(0).trim();
		}
		return "";
	}
	
	public String pegaDataDoConcurso(String linhaParaSerAnalisada) {
		Pattern modeloParaSepararCaractere = Pattern.compile(REGEX_PARA_PEGAR_DATA_DO_CONCURSO);
		Matcher caractereSeparado = modeloParaSepararCaractere.matcher(linhaParaSerAnalisada);
		if (caractereSeparado.find()) {
			return caractereSeparado.group(0);
		}
		return "";
	}
	
}
