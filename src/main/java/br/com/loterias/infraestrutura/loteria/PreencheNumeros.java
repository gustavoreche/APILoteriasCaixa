package br.com.loterias.infraestrutura.loteria;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.com.loterias.aplicacao.loteria.EnumNomeDosCampos;
import br.com.loterias.aplicacao.loteria.EnumTipoDeLoteria;
import br.com.loterias.aplicacao.loteria.MontaResultado;
import br.com.loterias.aplicacao.loteria.PegarNumerosSorteados;
import br.com.loterias.aplicacao.loteria.PreenchedorDeCampo;

@Component("preencheNumeros")
public class PreencheNumeros implements PreenchedorDeCampo {
	
	@Autowired
	@Qualifier("preencheGanhadores")
	private PreenchedorDeCampo proximaVerificacao;
	
	@Autowired
	@Qualifier("generico")
	PegarNumerosSorteados<?> numerosSorteados;
	
	@Autowired
	PreenchedorDeCampoPorReflection preenchedorDeCampoPorReflection;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean continuaPreenchendo(String linhaParaSerAnalisada, EnumTipoDeLoteria tipoDeLoteria, 
			MontaResultado<?> montador, int contadorParaPegarNumeroDoConcurso) {
		if (numerosSorteados.foramSorteados(linhaParaSerAnalisada, tipoDeLoteria)) {
			List<Integer> listaDeNumeros = (List<Integer>) numerosSorteados.pega(linhaParaSerAnalisada);
			preenchedorDeCampoPorReflection.pegaCampoEDefineValor(EnumNomeDosCampos.NUMEROS.getNome(), listaDeNumeros, montador);
			preenchedorDeCampoPorReflection.pegaCampoEDefineValor(EnumNomeDosCampos.CONTADOR_PEGA_NUMERO_CONCURSO.getNome(), montador);
			return true;
		}
		return proximaVerificacao.continuaPreenchendo(linhaParaSerAnalisada, tipoDeLoteria, montador, contadorParaPegarNumeroDoConcurso);
	}

}
