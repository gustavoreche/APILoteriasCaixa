package br.com.loterias.dominio.loteria;

public class RegraDaEntidade {
	
	protected static final String SEM_RETORNO_DA_CAIXA = "A CAIXA AINDA NÃO INFORMOU!";
	
	protected boolean naoTeveRetornoDaCaixa(String campo) {
		return campo == null || campo.isEmpty();
	}

}
