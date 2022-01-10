package br.com.loterias.infraestrutura.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import br.com.loterias.aplicacao.loteria.MontaResultado;
import br.com.loterias.aplicacao.loteria.MontadorDeLoteria;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
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
		
		
		Document doc = Jsoup.connect("https://loterias.caixa.gov.br/wps/portal/loterias")
				.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
				.get();
        Elements media = doc.select("ul");

        for (Element src : media) {
        	if(src.getElementsByClass("resultado-loteria mega-sena") != null &&
        			!src.getElementsByClass("resultado-loteria mega-sena").isEmpty()) {
        		log.info("TESTE: " + src.getElementsByClass("resultado-loteria mega-sena"));
        	}
        }
		
		
		BufferedReader linhas = new BufferedReader(new InputStreamReader(url.openStream()));
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
