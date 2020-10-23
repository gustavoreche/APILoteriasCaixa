package br.com.loterias.infraestrutura.loteria.pegarNumeros;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.com.loterias.aplicacao.loteca.EnumRegexJogoDaLoteca;
import br.com.loterias.aplicacao.loteca.VerificaResultadoDoJogo;
import br.com.loterias.aplicacao.loteria.EnumTipoDeLoteria;
import br.com.loterias.aplicacao.loteria.PegarNumerosSorteados;
import br.com.loterias.dominio.loteca.JogosDaLoteca;
import br.com.loterias.infraestrutura.loteria.loteca.timesEDiaJogo.DefineTimesEDiaDoJogo;

@Component("numerosLoteca")
@RequestScope
public class PegarNumerosSorteadosLoteca implements PegarNumerosSorteados<JogosDaLoteca> {
	
	private int contadorNumeroDoJogo;
	private String timeUm;
	private String timeDois;
	private String diaDaSemana;
	private String resultado;
	
	@Autowired
	@Qualifier("vitoriaTimeUm")
	private VerificaResultadoDoJogo verificaResultadoDoJogo;
	
	@Autowired
	private DefineTimesEDiaDoJogo defineTimesEDiaDoJogo;
	
	@Override
	public boolean foramSorteados(String linhaParaSerAnalisada, EnumTipoDeLoteria tipoDeLoteria) {
		temAlgumDadoDoJogoDaLoteca(linhaParaSerAnalisada);
		return false;
	}

	private void temAlgumDadoDoJogoDaLoteca(String linhaParaSerAnalisada) {
		for (EnumRegexJogoDaLoteca regex : EnumRegexJogoDaLoteca.values()) {
			Matcher valorEncontrado = analisaRegex(linhaParaSerAnalisada, regex);
			if (valorEncontrado.find()) {
				defineTipoDoCampo(valorEncontrado, regex);
			}			
		}
	}
	
	private Matcher analisaRegex(String linhaParaSerAnalisada, EnumRegexJogoDaLoteca regex) {
		Pattern modeloParaEncontrarValor = Pattern.compile(regex.getRegex());
		Matcher valorEncontrado = modeloParaEncontrarValor.matcher(linhaParaSerAnalisada);
		return valorEncontrado;
	}

	private void defineTipoDoCampo(Matcher valorEncontrado, EnumRegexJogoDaLoteca regex) {
		if(regex.getNomeDoCampo().equals(EnumRegexJogoDaLoteca.REGEX_PARA_PEGAR_TIME_E_DIA_DA_SEMANA.getNomeDoCampo())) {
			verificaPreenchimentoDeTime(valorEncontrado.group(0));								
		} else {
			verificaResultado();								
		}
	}

	private void verificaPreenchimentoDeTime(String valor) {
		defineTimesEDiaDoJogo.executa(this, valor);
	}

	private void verificaResultado() {
		this.resultado = verificaResultadoDoJogo.resultado(this.timeUm, this.timeDois);
	}

	@Override
	public JogosDaLoteca pega(String linhaParaSerAnalisada) {
		if(this.diaDaSemana != null) {
			contadorNumeroDoJogo++;
			JogosDaLoteca jogosDaLoteca = new JogosDaLoteca(this.contadorNumeroDoJogo, this.timeUm, this.timeDois, this.diaDaSemana, this.resultado);
			voltaValoresParaNulo();
			return jogosDaLoteca;
		}
		return null;
	}

	private void voltaValoresParaNulo() {
		this.timeUm = null; 
		this.timeDois = null;
		this.diaDaSemana = null;
		this.resultado = null;
	}
	
	public String getTimeUm() {
		return timeUm;
	}
	
	public String getTimeDois() {
		return timeDois;
	}

}
