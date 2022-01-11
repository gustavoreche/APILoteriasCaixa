package br.com.loterias.infraestrutura.util;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import br.com.loterias.aplicacao.loteria.MontaResultado;
import br.com.loterias.aplicacao.loteria.MontadorDeLoteria;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
		System.out.println("entrou");
		log.info("entrou no log");
		

//        HttpClient client = HttpClientBuilder.create().build();
//        HttpGet requestGet = new HttpGet("https://loterias.caixa.gov.br/wps/portal/loterias");
//        HttpResponse response = client.execute(requestGet);
//        HttpEntity entity = response.getEntity();
//        String responseString = EntityUtils.toString(entity, "UTF-8");
        
        
        String fixieUrl = System.getenv("FIXIE_URL");
        String[] fixieValues = fixieUrl.split("[/(:\\/@)/]+");
        String fixieUser = fixieValues[1];
        String fixiePassword = fixieValues[2];
        String fixieHost = fixieValues[3];
        int fixiePort = Integer.parseInt(fixieValues[4]);
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(fixieHost, fixiePort)));

        OkHttpClient client = clientBuilder.build();
        Request request = new Request.Builder().url("http:/loterias.caixa.gov.br/wps/portal/loterias").build();
        Response response = client.newCall(request).execute();
        
        
		Document doc = Jsoup.parse(response.body().toString());
		
		
//		Document doc = Jsoup.connect("https://loterias.caixa.gov.br/wps/portal/loterias")
//				.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
//				.get();
        Elements media = doc.select("ul");

        for (Element src : media) {
        	if(src.getElementsByClass("resultado-loteria mega-sena") != null &&
        			!src.getElementsByClass("resultado-loteria mega-sena").isEmpty()) {
        		System.out.println("TESTE: " + src.getElementsByClass("resultado-loteria mega-sena"));
        	}
        }
		return null;
		
//		BufferedReader linhas = new BufferedReader(new InputStreamReader(url.openStream()));
//		while ((linhaDoCodigoFonteDaCaixa = linhas.readLine()) != null) {
//			if (!this.montaResultado.executa(linhaDoCodigoFonteDaCaixa)) {
//				linhas.close();
//				return this.montaResultado.objetoMontado();
//			}
//		}
//		linhas.close();
//		return this.montaResultado.objetoMontado();
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
