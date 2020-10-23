package br.com.loterias.aplicacao.loteria;

public enum EnumTipoDeLoteria {
	
	MEGA_SENA("mega-sena", "megasena", "MEGA-SENA"),
	QUINA("quina", "quina", "QUINA"),
	TIMEMANIA("timemania", "timemania", "TIMEMANIA"),
	LOTOFACIL("lotofacil", "lotofacil", "LOTOFACIL", 15),
	LOTOMANIA("lotomania", "lotomania", "LOTOMANIA", 20),
	DUPLA_SENA("duplasena", "duplasena", "DUPLA-SENA"),
	DIA_DE_SORTE("diadesorte", "diadesorte", "DIA DE SORTE"),
	SUPER_SETE("supersete", "supersete", "SUPER SETE"),
	LOTECA("loteca", "loteca", "LOTECA"),
	;
	
	private String nomeSorteados;
	private String nomeGanhadores;
	private String nomeNoJsonParaExibir;
	private int quantidadeDeNumeros;

	private EnumTipoDeLoteria(String nomeSorteados, String nomeGanhadores, String nomeNoJsonParaExibir) {
		this.nomeSorteados = nomeSorteados;
		this.nomeGanhadores = nomeGanhadores;
		this.nomeNoJsonParaExibir = nomeNoJsonParaExibir;
	}
	
	private EnumTipoDeLoteria(String nomeSorteados, String nomeGanhadores, String nomeNoJsonParaExibir, int quantidadeDeNumeros) {
		this.nomeSorteados = nomeSorteados;
		this.nomeGanhadores = nomeGanhadores;
		this.nomeNoJsonParaExibir = nomeNoJsonParaExibir;
		this.quantidadeDeNumeros = quantidadeDeNumeros;
	}
	
	public String getNomeSorteados() {
		return nomeSorteados;
	}
	
	public String getNomeGanhadores() {
		return nomeGanhadores;
	}
	
	public String getNomeNoJsonParaExibir() {
		return nomeNoJsonParaExibir;
	}
	
	public int getQuantidadeDeNumeros() {
		return quantidadeDeNumeros;
	}

}
