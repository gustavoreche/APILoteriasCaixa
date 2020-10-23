package br.com.loterias.infraestrutura.loteria;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.com.loterias.aplicacao.loteria.EnumNomeDosCampos;
import br.com.loterias.aplicacao.loteria.EnumTipoDeLoteria;
import br.com.loterias.aplicacao.loteria.MontaResultado;
import br.com.loterias.aplicacao.loteria.PegarNumerosSorteados;
import br.com.loterias.aplicacao.loteria.PreenchedorDeCampo;

@Component("preencheNumerosComTabela")
public class PreencheNumerosComTabela implements PreenchedorDeCampo {
	
	private int contadorParaPegarNumeros = 0;
	private List<Integer> listaDeNumeros = new ArrayList<Integer>();
	
	@Autowired
	@Qualifier("preencheGanhadores")
	private PreenchedorDeCampo proximaVerificacao;
	
	@Autowired
	@Qualifier("numerosComTabela")
	PegarNumerosSorteados<?> numerosSorteados;
	
	@Autowired
	PreenchedorDeCampoPorReflection preenchedorDeCampoPorReflection;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean continuaPreenchendo(String linhaParaSerAnalisada, EnumTipoDeLoteria tipoDeLoteria, 
			MontaResultado<?> montador, int contadorParaPegarNumeroDoConcurso) {
		if (numerosSorteados.foramSorteados(linhaParaSerAnalisada, tipoDeLoteria) || ehParaPegarNumeros()) {
			contadorParaPegarNumeros++;
			List<Integer> pegaNumeros = (List<Integer>) numerosSorteados.pega(linhaParaSerAnalisada);
			if(!pegaNumeros.isEmpty()) {
				listaDeNumeros.add(pegaNumeros.get(0));
				if(listaDeNumeros.size() == tipoDeLoteria.getQuantidadeDeNumeros() || contadorParaPegarNumeros > 80) {
					preenchedorDeCampoPorReflection.pegaCampoEDefineValor(EnumNomeDosCampos.NUMEROS.getNome(), listaDeNumeros, montador);
					preenchedorDeCampoPorReflection.pegaCampoEDefineValor(EnumNomeDosCampos.CONTADOR_PEGA_NUMERO_CONCURSO.getNome(), montador);
					contadorParaPegarNumeros = 0;
					listaDeNumeros = new ArrayList<Integer>();
				}
			}
			return true;
		}
		return proximaVerificacao.continuaPreenchendo(linhaParaSerAnalisada, tipoDeLoteria, montador, contadorParaPegarNumeroDoConcurso);
	}
	
	private boolean ehParaPegarNumeros() {
		return contadorParaPegarNumeros > 0;
	}

}
