package br.com.loterias.dominio.handler;

public class ErroRetornadoPelaCaixa {
	
	private String descricao;
	
	public ErroRetornadoPelaCaixa(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
