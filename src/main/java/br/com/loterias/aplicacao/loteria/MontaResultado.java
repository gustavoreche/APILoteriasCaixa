package br.com.loterias.aplicacao.loteria;

public interface MontaResultado<T> {
	
	boolean executa(String linhaParaSerAnalisada);
	
	T objetoMontado();

}
