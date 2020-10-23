package br.com.loterias.aplicacao.loteria;

public interface PreenchedorDeCampo {
	
	boolean continuaPreenchendo(String linhaParaSerAnalisada, EnumTipoDeLoteria tipoDeLoteria, 
			MontaResultado<?> montador, int contadorParaPegarNumeroDoConcurso);

}
