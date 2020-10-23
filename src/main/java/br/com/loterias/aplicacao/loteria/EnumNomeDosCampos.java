package br.com.loterias.aplicacao.loteria;

public enum EnumNomeDosCampos {
	
	NUMEROS("numeros"),
	NUMEROS_PRIMEIRO_SORTEIO("numerosPrimeiroSorteio"),
	NUMEROS_SEGUNDO_SORTEIO("numerosSegundoSorteio"),
	CONTADOR_PEGA_NUMERO_CONCURSO("contadorParaPegarNumeroDoConcurso"),
	NUMERO_DO_CONCURSO("numeroDoConcurso"),
	DATA_DO_CONCURSO("dataDoUltimoSorteio"),
	NUMERO_DE_GANHADORES("numeroDeGanhadores"),
	;
	
	private String nome;

	private EnumNomeDosCampos(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
	
}
