package br.com.loterias.infraestrutura.loteria.superSete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.com.loterias.aplicacao.loteria.EnumTipoDeLoteria;
import br.com.loterias.aplicacao.loteria.MontaResultado;
import br.com.loterias.aplicacao.loteria.PegarNumerosSorteados;
import br.com.loterias.dominio.superSete.SuperSete;
import br.com.loterias.infraestrutura.loteria.PegarConcursoEData;
import br.com.loterias.infraestrutura.loteria.PegarGanhadores;

@Component("superSete")
@RequestScope
public class MontaResultadoSuperSete implements MontaResultado<SuperSete> {
	
	private static final String COLUNA = "Coluna ";
	
	private List<Map<String, Integer>> numeros = new ArrayList<Map<String, Integer>>();
	private String numeroDeGanhadores;
	private String numeroDoConcurso;
	private String dataDoUltimoSorteio;
	
	private int contadorParaPegarNumeroDoConcurso = 0;
	private int contadorParaPegarNumerosSuperSete = 0;
	
	@Autowired
	@Qualifier("numerosSuperSete")
	PegarNumerosSorteados<?> numerosSorteados;
	
	@Autowired
	PegarGanhadores ganhadores;
	
	@Autowired
	PegarConcursoEData dadosDoConcurso;
	
	@Override
	public boolean executa(String linhaParaSerAnalisada) {
		if (numerosSorteados.foramSorteados(linhaParaSerAnalisada, EnumTipoDeLoteria.SUPER_SETE) || ehParaPegarNumeros()) {
			contadorParaPegarNumerosSuperSete++;
			HashMap<String, Integer> colunaEDezena = new HashMap<String, Integer>();
			Integer pegaNumero = (Integer) numerosSorteados.pega(linhaParaSerAnalisada);
			if(pegaNumero != null) {
				colunaEDezena.put(COLUNA + String.valueOf(numeros.size() + 1), pegaNumero);
				numeros.add(colunaEDezena);				
				if(numeros.size() == 7) {
					contadorParaPegarNumeroDoConcurso++;
					contadorParaPegarNumerosSuperSete = 0;
				}
			}
		} else if (ganhadores.foramOsGanhadores(linhaParaSerAnalisada, EnumTipoDeLoteria.SUPER_SETE)) {
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
	
	private boolean ehParaPegarNumeros() {
		return contadorParaPegarNumerosSuperSete > 0;
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
	public SuperSete objetoMontado() {
		return new SuperSete(EnumTipoDeLoteria.SUPER_SETE, this.numeros, this.numeroDeGanhadores, this.numeroDoConcurso, this.dataDoUltimoSorteio);
	}

}
