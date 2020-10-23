package br.com.loterias.infraestrutura.quina;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.loterias.aplicacao.loteria.MontaResultado;
import br.com.loterias.aplicacao.loteria.MontadorDeLoteria;
import br.com.loterias.dominio.loteria.Loteria;

@RestController
@RequestMapping("/quina")
public class QuinaController {
	
	@Value("${url.loterias}")
	private String urlCompleta;
	
	@Autowired
	MontadorDeLoteria montador;
	
	@Autowired
	@Qualifier("quina")
	MontaResultado<?> tipoDeLoteria;
	
	@GetMapping()
	public ResponseEntity<Loteria> pegaResultado() throws MalformedURLException, IOException {
		montador.defineTipoDeLoteria(tipoDeLoteria);
		montador.defineUrl(urlCompleta);
		Loteria resultadoQuina = (Loteria) montador.monta();
		return new ResponseEntity<Loteria>(resultadoQuina, HttpStatus.OK);
	}

}
