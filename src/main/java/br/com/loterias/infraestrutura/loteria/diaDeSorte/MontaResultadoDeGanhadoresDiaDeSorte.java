package br.com.loterias.infraestrutura.loteria.diaDeSorte;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.com.loterias.aplicacao.loteria.MontaResultado;

@Component("ganhadoresDiaDeSorte")
@RequestScope
public class MontaResultadoDeGanhadoresDiaDeSorte implements MontaResultado<String> {
	
	private static final String SAO_OS_GANHADORES = "<h3 class=\"epsilon\" ng-show=\"resultado.acumulado\"";
	private static final String REGEX_PARA_PEGAR_GANHADOR = "(?<=\">)([^<\\/a>]+)";
	
	private String numeroDeGanhadores;
	
	@Override
	public boolean executa(String linhaParaSerAnalisada) {
		if(linhaParaSerAnalisada.contains(SAO_OS_GANHADORES)) {
			Pattern modeloParaSepararCaractere = Pattern.compile(REGEX_PARA_PEGAR_GANHADOR);
			Matcher caractereSeparado = modeloParaSepararCaractere.matcher(linhaParaSerAnalisada);
			if (caractereSeparado.find()) {
				this.numeroDeGanhadores = caractereSeparado.group(0);
				return false;
			}
		}
		return true;
	}

	@Override
	public String objetoMontado() {
		return this.numeroDeGanhadores;
	}
	
}
