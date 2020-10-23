package br.com.loterias.aplicacao.loteca;

public enum EnumRegexJogoDaLoteca {

	REGEX_PARA_PEGAR_TIME_E_DIA_DA_SEMANA("(?<=capitalize\\;\\\"\\>)([^<\\/]+)", "timeUm"),
	REGEX_PARA_PEGAR_RESULTADO("(?<=class=\"selected\">)([^<]+)", "resultado"),
	;

	private String regex;
	private String nomeDoCampo;

	private EnumRegexJogoDaLoteca(String regex, String nomeDoCampo) {
		this.regex = regex;
		this.nomeDoCampo = nomeDoCampo;
	}

	public String getRegex() {
		return regex;
	}
	
	public String getNomeDoCampo() {
		return nomeDoCampo;
	}

}
