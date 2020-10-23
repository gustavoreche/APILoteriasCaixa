package br.com.loterias.infraestrutura.loteria.pegarNumeros;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.com.loterias.aplicacao.loteria.EnumTipoDeLoteria;
import br.com.loterias.aplicacao.loteria.PegarNumerosSorteados;

@Component("numerosComTabela")
@RequestScope
public class PegarNumerosSorteadosComTabela implements PegarNumerosSorteados<List<Integer>> {
	
	private static final String SAO_OS_NUMEROS_SORTEADOS = "<table class=\"simple-table ";
	private static final String REGEX_PARA_PEGAR_NUMEROS = "\\d{2}";

	@Override
	public boolean foramSorteados(String linhaParaSerAnalisada, EnumTipoDeLoteria tipoDeLoteria) {
		return linhaParaSerAnalisada.contains(SAO_OS_NUMEROS_SORTEADOS + tipoDeLoteria.getNomeSorteados());
	}

	@Override
	public List<Integer> pega(String linhaParaSerAnalisada) {
		List<Integer> listaDeNumeros = new ArrayList<Integer>();
		Pattern modeloParaSepararCaractere = Pattern.compile(REGEX_PARA_PEGAR_NUMEROS);
		Matcher caractereSeparado = modeloParaSepararCaractere.matcher(linhaParaSerAnalisada);
		while (caractereSeparado.find()) {
			listaDeNumeros.add(Integer.parseInt(caractereSeparado.group(0)));
		}
		return listaDeNumeros;
	}

}
