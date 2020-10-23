package br.com.loterias.infraestrutura.loteca;

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
import br.com.loterias.dominio.loteca.Loteca;

@RestController
@RequestMapping("/loteca")
public class LotecaController {

	@Value("${url.loterias}")
	private String urlCompleta;
	
	@Autowired
	MontadorDeLoteria montador;
	
	@Autowired
	@Qualifier("loteca")
	MontaResultado<?> tipoDeLoteria;
	
	@GetMapping()
	public ResponseEntity<Loteca> pegaResultado() throws MalformedURLException, IOException {
		montador.defineTipoDeLoteria(tipoDeLoteria);
		montador.defineUrl(urlCompleta);
		Loteca resultadoLoteca = (Loteca) montador.monta();
		return new ResponseEntity<Loteca>(resultadoLoteca, HttpStatus.OK);
	}

}
