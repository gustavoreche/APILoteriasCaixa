package br.com.loterias.dominio.loteria;

import java.util.List;

import br.com.loterias.aplicacao.loteria.EnumTipoDeLoteria;

public class Loteria extends RegraDaEntidade {
	
	private String nomeDaLoteria;
	private List<Integer> numeros;
	private String numeroDeGanhadores;
	private String numeroDoConcurso;
	private String dataDoUltimoSorteio;

	public Loteria(EnumTipoDeLoteria nomeDaLoteria, List<Integer> numeros, String numeroDeGanhadores, String numeroDoConcurso,
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

	public List<Integer> getNumeros() {
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
