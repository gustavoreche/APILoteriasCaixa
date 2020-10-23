package br.com.loterias.dominio.duplaSena;

import java.util.List;

import br.com.loterias.aplicacao.loteria.EnumTipoDeLoteria;
import br.com.loterias.dominio.loteria.RegraDaEntidade;

public class DuplaSena extends RegraDaEntidade {
	
	private String nomeDaLoteria;
	private List<Integer> numerosPrimeiroSorteio;
	private List<Integer> numerosSegundoSorteio;
	private String numeroDeGanhadores;
	private String numeroDoConcurso;
	private String dataDoUltimoSorteio;

	public DuplaSena(EnumTipoDeLoteria nomeDaLoteria, List<Integer> numerosPrimeiroSorteio, List<Integer> numerosSegundoSorteio, 
			String numeroDeGanhadores, String numeroDoConcurso, String dataDoUltimoSorteio) {
		this.nomeDaLoteria = nomeDaLoteria.getNomeNoJsonParaExibir();
		this.numerosPrimeiroSorteio = numerosPrimeiroSorteio;
		this.numerosSegundoSorteio = numerosSegundoSorteio;
		this.numeroDeGanhadores = numeroDeGanhadores;
		this.numeroDoConcurso = numeroDoConcurso;
		this.dataDoUltimoSorteio = dataDoUltimoSorteio;
	}

	public String getNomeDaLoteria() {
		return nomeDaLoteria;
	}

	public List<Integer> getNumerosPrimeiroSorteio() {
		return numerosPrimeiroSorteio;
	}

	public List<Integer> getNumerosSegundoSorteio() {
		return numerosSegundoSorteio;
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
