package br.com.loterias.infraestrutura.loteria;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class PegarMesDaSorte {
	
	private static final String EH_O_MES_DA_SORTE = "<span class=\"description\">";
	private static final String REGEX_PARA_PEGAR_MES_DA_SORTE = "(?<=\">)([^<\\/a>]+)";
	
	public boolean foiOMesDaSorte(String linhaParaSerAnalisada) {
		return linhaParaSerAnalisada.contains(EH_O_MES_DA_SORTE);
	}

	public String pega(String linhaParaSerAnalisada) {
		Pattern modeloParaSepararCaractere = Pattern.compile(REGEX_PARA_PEGAR_MES_DA_SORTE);
		Matcher caractereSeparado = modeloParaSepararCaractere.matcher(linhaParaSerAnalisada);
		if (caractereSeparado.find()) {
			return caractereSeparado.group(0).trim();
		}
		return "";
	}

}
