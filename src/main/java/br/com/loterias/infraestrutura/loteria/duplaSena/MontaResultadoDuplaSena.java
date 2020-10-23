package br.com.loterias.infraestrutura.loteria.duplaSena;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.com.loterias.aplicacao.loteria.EnumTipoDeLoteria;
import br.com.loterias.aplicacao.loteria.MontaResultado;
import br.com.loterias.aplicacao.loteria.PreenchedorDeCampo;
import br.com.loterias.dominio.duplaSena.DuplaSena;

@Component("duplaSena")
@RequestScope
public class MontaResultadoDuplaSena implements MontaResultado<DuplaSena> {
	
	private List<Integer> numerosPrimeiroSorteio = new ArrayList<Integer>();
	private List<Integer> numerosSegundoSorteio = new ArrayList<Integer>();
	private String numeroDeGanhadores;
	private String numeroDoConcurso;
	private String dataDoUltimoSorteio;
	
	private int contadorParaPegarNumeroDoConcurso = 0;

	@Autowired
	@Qualifier("preencheNumerosDuplaSena")
	PreenchedorDeCampo preenchedorDeCampo;
	
	@Override
	public boolean executa(String linhaParaSerAnalisada) {
		return preenchedorDeCampo.continuaPreenchendo(linhaParaSerAnalisada, EnumTipoDeLoteria.DUPLA_SENA, this, contadorParaPegarNumeroDoConcurso);
	}
	
	@Override
	public DuplaSena objetoMontado() {
		return new DuplaSena(EnumTipoDeLoteria.DUPLA_SENA, this.numerosPrimeiroSorteio, this.numerosSegundoSorteio,
				this.numeroDeGanhadores, this.numeroDoConcurso, this.dataDoUltimoSorteio);
	}

}
