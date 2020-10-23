package br.com.loterias.aplicacao.loteria;

public interface PegarNumerosSorteados<T> {
	
	boolean foramSorteados(String linhaParaSerAnalisada, EnumTipoDeLoteria tipoDeLoteria);
	
	T pega(String linhaParaSerAnalisada);	

}
