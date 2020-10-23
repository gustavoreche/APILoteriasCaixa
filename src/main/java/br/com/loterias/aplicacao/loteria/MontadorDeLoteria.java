package br.com.loterias.aplicacao.loteria;

import java.io.IOException;
import java.net.MalformedURLException;

public interface MontadorDeLoteria {
	
	void defineTipoDeLoteria(MontaResultado<?> tipoDeLoteria);
	
	void defineUrl(String url);
	
	Object monta() throws IOException, MalformedURLException;

}
