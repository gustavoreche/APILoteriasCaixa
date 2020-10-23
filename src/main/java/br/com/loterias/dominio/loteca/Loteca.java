package br.com.loterias.dominio.loteca;

import java.util.List;

import br.com.loterias.aplicacao.loteria.EnumTipoDeLoteria;
import br.com.loterias.dominio.loteria.RegraDaEntidade;

public class Loteca extends RegraDaEntidade {
	
	private String nomeDaLoteria;
	private List<JogosDaLoteca> jogos;
	private String numeroDeGanhadores;
	private String numeroDoConcurso;
	private String dataDoUltimoSorteio;
	
	public Loteca(EnumTipoDeLoteria nomeDaLoteria, List<JogosDaLoteca> jogos, String numeroDeGanhadores, String numeroDoConcurso,
			String dataDoUltimoSorteio) {
		this.nomeDaLoteria = nomeDaLoteria.getNomeNoJsonParaExibir();
		this.jogos = jogos;
		this.numeroDeGanhadores = numeroDeGanhadores;
		this.numeroDoConcurso = numeroDoConcurso;
		this.dataDoUltimoSorteio = dataDoUltimoSorteio;
	}

	public String getNomeDaLoteria() {
		return nomeDaLoteria;
	}

	public List<JogosDaLoteca> getJogos() {
		return jogos;
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
