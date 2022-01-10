package br.com.loterias.infraestrutura.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Component;

import br.com.loterias.aplicacao.loteria.MontaResultado;
import br.com.loterias.aplicacao.loteria.MontadorDeLoteria;

@Component
public class MontadorDeLoteriaPorSite implements MontadorDeLoteria {

	private MontaResultado<?> montaResultado;
	private String urlCompleta;

	@Override
	public Object monta() throws IOException, MalformedURLException {
		URL url = new URL(urlCompleta);
		CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
		return montaOTipoDeLoteria(url);
	}

	private Object montaOTipoDeLoteria(URL url) throws IOException {
		String linhaDoCodigoFonteDaCaixa;
		
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
		
		BufferedReader linhas = new BufferedReader(new InputStreamReader(con.getInputStream()));
		while ((linhaDoCodigoFonteDaCaixa = linhas.readLine()) != null) {
			if (!this.montaResultado.executa(linhaDoCodigoFonteDaCaixa)) {
				linhas.close();
				return this.montaResultado.objetoMontado();
			}
		}
		linhas.close();
		return this.montaResultado.objetoMontado();
	}

	@Override
	public void defineTipoDeLoteria(MontaResultado<?> tipoDeLoteria) {
		this.montaResultado = tipoDeLoteria;
	}

	@Override
	public void defineUrl(String urlCompleta) {
		this.urlCompleta = urlCompleta;
	}
	

}
