package br.com.loterias.infraestrutura.duplaSena;

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
import br.com.loterias.dominio.duplaSena.DuplaSena;

@RestController
@RequestMapping("/dupla-sena")
public class DuplaSenaController {
	
	@Value("${url.loterias}")
	private String urlCompleta;
	
	@Autowired
	MontadorDeLoteria montador;
	
	@Autowired
	@Qualifier("duplaSena")
	MontaResultado<?> tipoDeLoteria;
	
	@GetMapping()
	public ResponseEntity<DuplaSena> pegaResultado() throws MalformedURLException, IOException {
		montador.defineTipoDeLoteria(tipoDeLoteria);
		montador.defineUrl(urlCompleta);
		DuplaSena resultadoDuplaSena = (DuplaSena) montador.monta();
		return new ResponseEntity<DuplaSena>(resultadoDuplaSena, HttpStatus.OK);
	}

}
