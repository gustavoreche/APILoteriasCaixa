package br.com.loterias.infraestrutura.loteria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.com.loterias.aplicacao.loteria.EnumNomeDosCampos;
import br.com.loterias.aplicacao.loteria.EnumTipoDeLoteria;
import br.com.loterias.aplicacao.loteria.MontaResultado;
import br.com.loterias.aplicacao.loteria.PreenchedorDeCampo;

@Component("preencheDadosDoConcurso")
public class PreencheDadosDoConcurso implements PreenchedorDeCampo {
	
	@Autowired
	@Qualifier("preencheDataDoConcursoNaOutraLinha")
	private PreenchedorDeCampo proximaVerificacao;
	
	@Autowired
	PegarConcursoEData dadosDoConcurso;
	
	@Autowired
	PreenchedorDeCampoPorReflection preenchedorDeCampoPorReflection;
	
	@Override
	public boolean continuaPreenchendo(String linhaParaSerAnalisada, EnumTipoDeLoteria tipoDeLoteria, 
			MontaResultado<?> montador, int contadorParaPegarNumeroDoConcurso) {
		if (dadosDoConcurso.foramOsDadosDoConcurso(linhaParaSerAnalisada) && ehAVezDosDadosDoConcurso(contadorParaPegarNumeroDoConcurso)) {
			String numeroDoConcurso = dadosDoConcurso.pegaConcurso(linhaParaSerAnalisada);
			String dataDoUltimoSorteio = dadosDoConcurso.pegaDataDoConcurso(linhaParaSerAnalisada);
			preenchedorDeCampoPorReflection.pegaCampoEDefineValor(EnumNomeDosCampos.NUMERO_DO_CONCURSO.getNome(), numeroDoConcurso, montador);
			preenchedorDeCampoPorReflection.pegaCampoEDefineValor(EnumNomeDosCampos.DATA_DO_CONCURSO.getNome(), dataDoUltimoSorteio, montador);
			preenchedorDeCampoPorReflection.pegaCampoEDefineValor(EnumNomeDosCampos.CONTADOR_PEGA_NUMERO_CONCURSO.getNome(), montador);
			return true;
		}
		return proximaVerificacao.continuaPreenchendo(linhaParaSerAnalisada, tipoDeLoteria, montador, contadorParaPegarNumeroDoConcurso);
	}
	
	private boolean ehAVezDosDadosDoConcurso(int contadorParaPegarNumeroDoConcurso) {
		return contadorParaPegarNumeroDoConcurso == 2;
	}
	
}
