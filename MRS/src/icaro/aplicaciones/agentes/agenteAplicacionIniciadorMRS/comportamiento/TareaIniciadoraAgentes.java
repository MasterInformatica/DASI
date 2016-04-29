package icaro.aplicaciones.agentes.agenteAplicacionIniciadorMRS.comportamiento;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaAsincrona;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;


public class TareaIniciadoraAgentes extends TareaAsincrona {

	@Override
	public void ejecutar(Object... params) {
		System.err.println("KLJLKJLKJ: " + (int) params[0]);
		//System.err.println("get : "+this.getEnvioHechos());
		

		
		//this.getEnvioHechos().insertarHechoWithoutFireRules(new Integer((int)params[0]));
	}

}
