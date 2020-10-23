package br.com.loterias.infraestrutura.loteria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.loterias.aplicacao.loteria.EnumTipoDeLoteria;
import br.com.loterias.aplicacao.loteria.MontaResultado;
import br.com.loterias.aplicacao.loteria.PreenchedorDeCampo;

@Component("pegouTodosOsDados")
public class PegouTodosOsDados implements PreenchedorDeCampo {
	
	@Autowired
	PreenchedorDeCampoPorReflection preenchedorDeCampoPorReflection;
	
	@Override
	public boolean continuaPreenchendo(String linhaParaSerAnalisada, EnumTipoDeLoteria tipoDeLoteria, 
			MontaResultado<?> montador, int contadorParaPegarNumeroDoConcurso) {
		if (preencheuTodosOsAtributos(contadorParaPegarNumeroDoConcurso)) {
			return false;
		}
		return true;
	}
	
	private boolean preencheuTodosOsAtributos(int contadorParaPegarNumeroDoConcurso) {
		return contadorParaPegarNumeroDoConcurso == 4;
	}
	
}
