package br.com.loterias.infraestrutura.loteria.loteca;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.com.loterias.aplicacao.loteria.EnumTipoDeLoteria;
import br.com.loterias.aplicacao.loteria.MontaResultado;
import br.com.loterias.aplicacao.loteria.PegarNumerosSorteados;
import br.com.loterias.dominio.loteca.JogosDaLoteca;
import br.com.loterias.dominio.loteca.Loteca;
import br.com.loterias.infraestrutura.loteria.PegarConcursoEData;
import br.com.loterias.infraestrutura.loteria.PegarGanhadores;

@Component("loteca")
@RequestScope
public class MontaResultadoLoteca implements MontaResultado<Loteca> {
	
	private static final String SAO_OS_JOGOS_SORTEADOS = "\"loteca nome-loteria\"";
	
	private List<JogosDaLoteca> jogos = new ArrayList<JogosDaLoteca>();
	private String numeroDeGanhadores;
	private String numeroDoConcurso;
	private String dataDoUltimoSorteio;
	
	private int contadorParaPegarNumeroDoConcurso = 0;
	private int contadorParaPegarNumerosLoteca = 0;
	
	@Autowired
	@Qualifier("numerosLoteca")
	PegarNumerosSorteados<?> numerosSorteados;
	
	@Autowired
	PegarGanhadores ganhadores;
	
	@Autowired
	PegarConcursoEData dadosDoConcurso;
	
	@Override
	public boolean executa(String linhaParaSerAnalisada) {
		if (ehParaPegarNumeros(linhaParaSerAnalisada)) {
			numerosSorteados.foramSorteados(linhaParaSerAnalisada, null);
			contadorParaPegarNumerosLoteca++;
			JogosDaLoteca pegaDadoDoJogo = (JogosDaLoteca) numerosSorteados.pega("");
			if(pegaDadoDoJogo != null) {
				jogos.add(pegaDadoDoJogo);				
				if(jogos.size() == 14) {
					contadorParaPegarNumeroDoConcurso++;
					contadorParaPegarNumerosLoteca = 0;
				}
			}
		} else if (ganhadores.foramOsGanhadores(linhaParaSerAnalisada, EnumTipoDeLoteria.LOTECA)) {
			numeroDeGanhadores = ganhadores.pega(linhaParaSerAnalisada);
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

	private boolean ehParaPegarNumeros(String linhaParaSerAnalisada) {
		return linhaParaSerAnalisada.contains(SAO_OS_JOGOS_SORTEADOS) || contadorParaPegarNumerosLoteca > 0;
	}

	private boolean ehAVezDosDadosDoConcurso() {
		return contadorParaPegarNumeroDoConcurso == 2;
	}
	
	private boolean pegaDataNaLinhaAbaixoDoCodigoFonte() {
		return dataDoUltimoSorteio != null && dataDoUltimoSorteio.isEmpty();
	}
	
	private boolean pegouTodosOsDados() {
		return contadorParaPegarNumeroDoConcurso > 3;
	}
	
	@Override
	public Loteca objetoMontado() {
		return new Loteca(EnumTipoDeLoteria.LOTECA, this.jogos, this.numeroDeGanhadores, this.numeroDoConcurso, this.dataDoUltimoSorteio);
	}

}
