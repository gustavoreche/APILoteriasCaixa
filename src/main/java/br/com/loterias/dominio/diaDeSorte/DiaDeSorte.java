package br.com.loterias.dominio.diaDeSorte;

import java.util.List;

import br.com.loterias.aplicacao.loteria.EnumTipoDeLoteria;
import br.com.loterias.dominio.loteria.RegraDaEntidade;

public class DiaDeSorte extends RegraDaEntidade {
	
	private String nomeDaLoteria;
	private List<Integer> numeros;
	private String mesDaSorte;
	private String numeroDeGanhadores;
	private String numeroDoConcurso;
	private String dataDoUltimoSorteio;

	public DiaDeSorte(EnumTipoDeLoteria nomeDaLoteria, List<Integer> numeros, String mesDaSorte, 
			String numeroDeGanhadores, String numeroDoConcurso, String dataDoUltimoSorteio) {
		this.nomeDaLoteria = nomeDaLoteria.getNomeNoJsonParaExibir();
		this.numeros = numeros;
		this.mesDaSorte = mesDaSorte;
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
	
	public String getMesDaSorte() {
		if(naoTeveRetornoDaCaixa(mesDaSorte)) {
			return SEM_RETORNO_DA_CAIXA;
		}
		return mesDaSorte;
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
