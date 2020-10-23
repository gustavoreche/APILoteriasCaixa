package br.com.loterias.infraestrutura.loteria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.com.loterias.aplicacao.loteria.EnumNomeDosCampos;
import br.com.loterias.aplicacao.loteria.EnumTipoDeLoteria;
import br.com.loterias.aplicacao.loteria.MontaResultado;
import br.com.loterias.aplicacao.loteria.PreenchedorDeCampo;

@Component("preencheGanhadores")
public class PreencheGanhadores implements PreenchedorDeCampo {
	
	@Autowired
	@Qualifier("preencheDadosDoConcurso")
	private PreenchedorDeCampo proximaVerificacao;
	
	@Autowired
	PegarGanhadores ganhadores;
	
	@Autowired
	PreenchedorDeCampoPorReflection preenchedorDeCampoPorReflection;
	
	@Override
	public boolean continuaPreenchendo(String linhaParaSerAnalisada, EnumTipoDeLoteria tipoDeLoteria, 
			MontaResultado<?> montador,int contadorParaPegarNumeroDoConcurso) {
		if (ganhadores.foramOsGanhadores(linhaParaSerAnalisada, tipoDeLoteria)) {
			String resultadoDoGanhador = ganhadores.pega(linhaParaSerAnalisada);
			preenchedorDeCampoPorReflection.pegaCampoEDefineValor(EnumNomeDosCampos.NUMERO_DE_GANHADORES.getNome(), resultadoDoGanhador, montador);
			preenchedorDeCampoPorReflection.pegaCampoEDefineValor(EnumNomeDosCampos.CONTADOR_PEGA_NUMERO_CONCURSO.getNome(), montador);
			return true;
		}
		return proximaVerificacao.continuaPreenchendo(linhaParaSerAnalisada, tipoDeLoteria, montador, contadorParaPegarNumeroDoConcurso);
	}
	
}
