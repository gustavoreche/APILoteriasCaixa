package br.com.loterias.infraestrutura.loteria.loteca.timesEDiaJogo;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.com.loterias.aplicacao.loteca.VerificaTipoDoCampoEValor;
import br.com.loterias.infraestrutura.loteria.pegarNumeros.PegarNumerosSorteadosLoteca;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DefineTimesEDiaDoJogo {
	
	@Autowired
	@Qualifier("timeDoisESeuValor")
	private VerificaTipoDoCampoEValor verificaTipoDoCampoEValor;
	
	private PegarNumerosSorteadosLoteca classe;
	
	public void executa(PegarNumerosSorteadosLoteca classe, String valor) {
		this.classe = classe;
		Map<String, String> tipoDoCampoEResultado = verificaTipoDoCampoEValor.resultado(classe.getTimeUm(), classe.getTimeDois(), valor);
		for (Entry<String, String> valores : tipoDoCampoEResultado.entrySet()) {
			pegaCampoEValor(valores.getKey(), valores.getValue());
		}
	}
	
	private void pegaCampoEValor(String campo, Object valorDoCampo) {
		Class<?> classeDoObjeto = this.classe.getClass();
		Field campoDaClasse;
		try {
			campoDaClasse = classeDoObjeto.getDeclaredField(campo);
			campoDaClasse.setAccessible(true);
			defineValor(campoDaClasse, valorDoCampo);
		} catch (NoSuchFieldException e) {
			log.error("pegaCampoEValor - Não encontrou o campo. Exception: {}", e);
		} catch (SecurityException e) {
			log.error("pegaCampoEValor - Erro de segurança. Exception: {}", e);
		}
	}

	private void defineValor(Field campoDaClasse, Object valorDoCampo) {
		try {
			campoDaClasse.set(this.classe, valorDoCampo);
		} catch (IllegalArgumentException e) {
			log.error("defineValor - Erro no argumento do campo. Exception: {}", e);
		} catch (IllegalAccessException e) {
			log.error("defineValor - Acesso ilegal ao atributo. Exception: {}", e);
		}
	}

}
