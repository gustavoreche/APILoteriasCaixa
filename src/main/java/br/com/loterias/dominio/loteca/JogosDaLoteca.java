package br.com.loterias.dominio.loteca;

public class JogosDaLoteca {

	private int numeroDoJogo;
	private String timeUm;
	private String timeDois;
	private String diaDaSemana;
	private String resultado;

	public JogosDaLoteca(int numeroDoJogo, String timeUm, String timeDois, String diaDaSemana, String resultado) {
		this.numeroDoJogo = numeroDoJogo;
		this.timeUm = timeUm;
		this.timeDois = timeDois;
		this.diaDaSemana = diaDaSemana;
		this.resultado = resultado;
	}

	public int getNumeroDoJogo() {
		return numeroDoJogo;
	}

	public String getTimeUm() {
		return timeUm;
	}

	public String getTimeDois() {
		return timeDois;
	}

	public String getDiaDaSemana() {
		return diaDaSemana;
	}
	
	public String getResultado() {
		return resultado;
	}

}
