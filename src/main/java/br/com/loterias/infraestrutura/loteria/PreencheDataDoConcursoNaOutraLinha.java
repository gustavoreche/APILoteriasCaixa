package br.com.loterias.infraestrutura.loteria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.com.loterias.aplicacao.loteria.EnumNomeDosCampos;
import br.com.loterias.aplicacao.loteria.EnumTipoDeLoteria;
import br.com.loterias.aplicacao.loteria.MontaResultado;
import br.com.loterias.aplicacao.loteria.PreenchedorDeCampo;

@Component("preencheDataDoConcursoNaOutraLinha")
public class PreencheDataDoConcursoNaOutraLinha implements PreenchedorDeCampo {
	
	@Autowired
	@Qualifier("pegouTodosOsDados")
	private PreenchedorDeCampo proximaVerificacao;
	
	@Autowired
	PegarConcursoEData dadosDoConcurso;
	
	@Autowired
	PreenchedorDeCampoPorReflection preenchedorDeCampoPorReflection;
	
	@Override
	public boolean continuaPreenchendo(String linhaParaSerAnalisada, EnumTipoDeLoteria tipoDeLoteria, 
			MontaResultado<?> montador, int contadorParaPegarNumeroDoConcurso) {
		if (ehAVezDosDadosDoConcurso(contadorParaPegarNumeroDoConcurso)) {
			preenchedorDeCampoPorReflection.pegaCampoEDefineValor(EnumNomeDosCampos.CONTADOR_PEGA_NUMERO_CONCURSO.getNome(), montador);
			String dataDoSorteio = (String) preenchedorDeCampoPorReflection.pegaValorDoCampo(EnumNomeDosCampos.DATA_DO_CONCURSO.getNome(), montador);
			if(dataDoSorteio != null && dataDoSorteio.isEmpty()) {
				String dataDoUltimoSorteio = dadosDoConcurso.pegaDataDoConcurso(linhaParaSerAnalisada);
				preenchedorDeCampoPorReflection.pegaCampoEDefineValor(EnumNomeDosCampos.DATA_DO_CONCURSO.getNome(), dataDoUltimoSorteio, montador);				
				return true;
			}
		}
		return proximaVerificacao.continuaPreenchendo(linhaParaSerAnalisada, tipoDeLoteria, montador, contadorParaPegarNumeroDoConcurso);
	}
	
	private boolean ehAVezDosDadosDoConcurso(int contadorParaPegarNumeroDoConcurso) {
		return contadorParaPegarNumeroDoConcurso == 3;
	}
	
}
