package br.com.loterias.infraestrutura.loteria;

import java.lang.reflect.Field;

import org.springframework.stereotype.Component;

import br.com.loterias.aplicacao.loteria.MontaResultado;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PreenchedorDeCampoPorReflection {
	
	public Object pegaValorDoCampo(String campo, MontaResultado<?> montador) {
		Field campoDaClasse = pegaOCampo(campo, montador);
		if(campoDaClasse != null) {
			return pegaValor(campoDaClasse, montador);
		}
		return null;
	}
	
	private Object pegaValor(Field campoDaClasse, MontaResultado<?> montador) {
		try {
			return campoDaClasse.get(montador);
		} catch (IllegalArgumentException e) {
			log.error("defineValor - Erro no argumento do campo. Exception: {}", e);
		} catch (IllegalAccessException e) {
			log.error("defineValor - Acesso ilegal ao atributo. Exception: {}", e);
		}
		return null;
	}

	public void pegaCampoEDefineValor(String campo, MontaResultado<?> montador) {
		pegaCampoEDefineValor(campo, null, montador);
	}
	
	public void pegaCampoEDefineValor(String campo, Object valorDoCampo, MontaResultado<?> montador) {
		Field campoDaClasse = pegaOCampo(campo, montador);
		if(campoDaClasse != null) {
			defineValor(campoDaClasse, valorDoCampo, montador);
		}
	}
	
	private Field pegaOCampo(String campo, MontaResultado<?> montador) {
		Class<?> classeDoObjeto = montador.getClass();
		Field campoDaClasse;
		try {
			campoDaClasse = classeDoObjeto.getDeclaredField(campo);
			campoDaClasse.setAccessible(true);
			return campoDaClasse;
		} catch (NoSuchFieldException e) {
			log.error("pegaCampoEValor - Não encontrou o campo. Exception: {}", e);
		} catch (SecurityException e) {
			log.error("pegaCampoEValor - Erro de segurança. Exception: {}", e);
		}
		return null;
	}

	private void defineValor(Field campoDaClasse, Object valorDoCampo, MontaResultado<?> montador) {
		try {
			if (valorDoCampo == null) {
				Integer contador = (Integer) campoDaClasse.get(montador);
				contador++;
				valorDoCampo = contador;				
			}
			campoDaClasse.set(montador, valorDoCampo);
		} catch (IllegalArgumentException e) {
			log.error("defineValor - Erro no argumento do campo. Exception: {}", e);
		} catch (IllegalAccessException e) {
			log.error("defineValor - Acesso ilegal ao atributo. Exception: {}", e);
		}
	}
	
}
