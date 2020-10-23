package br.com.loterias.dominio.superSete;

import java.util.List;
import java.util.Map;

import br.com.loterias.aplicacao.loteria.EnumTipoDeLoteria;
import br.com.loterias.dominio.loteria.RegraDaEntidade;

public class SuperSete extends RegraDaEntidade {
	
	private String nomeDaLoteria;
	private List<Map<String, Integer>> numeros;
	private String numeroDeGanhadores;
	private String numeroDoConcurso;
	private String dataDoUltimoSorteio;

	public SuperSete(EnumTipoDeLoteria nomeDaLoteria, List<Map<String, Integer>> numeros, String numeroDeGanhadores, String numeroDoConcurso,
			String dataDoUltimoSorteio) {
		this.nomeDaLoteria = nomeDaLoteria.getNomeNoJsonParaExibir();
		this.numeros = numeros;
		this.numeroDeGanhadores = numeroDeGanhadores;
		this.numeroDoConcurso = numeroDoConcurso;
		this.dataDoUltimoSorteio = dataDoUltimoSorteio;
	}
	
	public String getNomeDaLoteria() {
		return nomeDaLoteria;
	}

	public List<Map<String, Integer>> getNumeros() {
		return numeros;
	}

	public String getNumeroDeGanhadores() {
		if(naoTeveRetornoDaCaixa(numeroDeGanhadores)) {
			return SEM_RETORNO_DA_CAIXA;
		}
		return numeroDeGanhadores;
	}

	public String getNumeroDoConcurso() {
		if(naoTeveRetornoDaCaixa(numeroDoConcurso)) {
			return SEM_RETORNO_DA_CAIXA;
		}
		return numeroDoConcurso;
	}

	public String getDataDoUltimoSorteio() {
		if(naoTeveRetornoDaCaixa(dataDoUltimoSorteio)) {
			return SEM_RETORNO_DA_CAIXA;
		}
		return dataDoUltimoSorteio;
	}

}
