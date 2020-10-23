package br.com.loterias.infraestrutura.loteria.diaDeSorte;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.com.loterias.aplicacao.loteria.EnumTipoDeLoteria;
import br.com.loterias.aplicacao.loteria.MontaResultado;
import br.com.loterias.aplicacao.loteria.MontadorDeLoteria;
import br.com.loterias.aplicacao.loteria.PegarNumerosSorteados;
import br.com.loterias.dominio.diaDeSorte.DiaDeSorte;
import br.com.loterias.infraestrutura.loteria.PegarConcursoEData;
import br.com.loterias.infraestrutura.loteria.PegarGanhadores;
import br.com.loterias.infraestrutura.loteria.PegarMesDaSorte;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("diaDeSorte")
@RequestScope
public class MontaResultadoDiaDeSorte implements MontaResultado<DiaDeSorte> {
	
	private List<Integer> numeros = new ArrayList<Integer>();
	private String mesDaSorte;
	private String numeroDeGanhadores;
	private String numeroDoConcurso;
	private String dataDoUltimoSorteio;
	
	private int contadorParaPegarNumeroDoConcurso = 0;
	
	@Value("${url.dia.de.sorte}")
	private String urlCompleta;
	
	@Autowired
	@Qualifier("generico")
	PegarNumerosSorteados<?> numerosSorteados;
	
	@Autowired
	PegarGanhadores ganhadores;
	
	@Autowired
	PegarMesDaSorte pegaMesDaSorte;
	
	@Autowired
	PegarConcursoEData dadosDoConcurso;
	
	@Autowired
	MontadorDeLoteria montador;
	
	@Autowired
	@Qualifier("ganhadoresDiaDeSorte")
	MontaResultado<?> tipoDeLoteria;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean executa(String linhaParaSerAnalisada) {
		if (numerosSorteados.foramSorteados(linhaParaSerAnalisada, EnumTipoDeLoteria.DIA_DE_SORTE)) {
			numeros = (List<Integer>) numerosSorteados.pega(linhaParaSerAnalisada);	
			contadorParaPegarNumeroDoConcurso++;
		} else if (ganhadores.foramOsGanhadores(linhaParaSerAnalisada, EnumTipoDeLoteria.DIA_DE_SORTE)) {
			numeroDeGanhadores = ganhadores.pega(linhaParaSerAnalisada);
			contadorParaPegarNumeroDoConcurso++;
		} else if (pegaMesDaSorte.foiOMesDaSorte(linhaParaSerAnalisada)) {
			mesDaSorte = pegaMesDaSorte.pega(linhaParaSerAnalisada);
			contadorParaPegarNumeroDoConcurso++;
		} else if (dadosDoConcurso.foramOsDadosDoConcurso(linhaParaSerAnalisada) && ehAVezDosDadosDoConcurso()) {
			numeroDoConcurso = dadosDoConcurso.pegaConcurso(linhaParaSerAnalisada);
			dataDoUltimoSorteio = dadosDoConcurso.pegaDataDoConcurso(linhaParaSerAnalisada);
			contadorParaPegarNumeroDoConcurso++;
		} else if (pegaDataNaLinhaAbaixoDoCodigoFonte()) {
			dataDoUltimoSorteio = dadosDoConcurso.pegaDataDoConcurso(linhaParaSerAnalisada);
			contadorParaPegarNumeroDoConcurso++;
		} else if (pegouTodosOsDados()) {
			return false;
		}
		return true;
	}

	private boolean ehAVezDosDadosDoConcurso() {
		return contadorParaPegarNumeroDoConcurso == 2;
	}
	
	private boolean pegaDataNaLinhaAbaixoDoCodigoFonte() {
		return dataDoUltimoSorteio != null && dataDoUltimoSorteio.isEmpty() && contadorParaPegarNumeroDoConcurso < 5;
	}
	
	private boolean pegouTodosOsDados() {
		return contadorParaPegarNumeroDoConcurso > 4;
	}
	
	@Override
	public DiaDeSorte objetoMontado() {
		this.numeroDeGanhadores = verificaSeGanhadoresEstaoPreenchidos(this.numeroDeGanhadores);
		return new DiaDeSorte(EnumTipoDeLoteria.DIA_DE_SORTE, this.numeros, this.mesDaSorte,
				this.numeroDeGanhadores, this.numeroDoConcurso, this.dataDoUltimoSorteio);
	}
	
	private String verificaSeGanhadoresEstaoPreenchidos(String numeroDeGanhadores) {
		if(numeroDeGanhadores == null) {
			montador.defineTipoDeLoteria(tipoDeLoteria);
			montador.defineUrl(urlCompleta);
			try {
				return (String) montador.monta();
			} catch (MalformedURLException e) {
				log.error("verificaSeGanhadoresEstaoPreenchidos - Erro na URL: ", e);
			} catch (IOException e) {
				log.error("verificaSeGanhadoresEstaoPreenchidos - Erro na resposta do site: ", e);
			}
		}
		return numeroDeGanhadores;
	}

}
