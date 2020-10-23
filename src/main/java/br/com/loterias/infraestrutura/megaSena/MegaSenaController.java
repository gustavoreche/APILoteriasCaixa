package br.com.loterias.infraestrutura.megaSena;

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
@RequestMapping("/mega-sena")
public class MegaSenaController {
	
	@Value("${url.loterias}")
	private String urlCompleta;
	
	@Autowired
	MontadorDeLoteria montador;
	
	@Autowired
	@Qualifier("megaSena")
	MontaResultado<?> tipoDeLoteria;
	
	@GetMapping()
	public ResponseEntity<Loteria> pegaResultado() throws MalformedURLException, IOException {
		montador.defineTipoDeLoteria(tipoDeLoteria);
		montador.defineUrl(urlCompleta);
		Loteria resultadoMegaSena = (Loteria) montador.monta();
		return new ResponseEntity<Loteria>(resultadoMegaSena, HttpStatus.OK);
	}

}
