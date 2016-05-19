package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;


/** 
 * Objetivo: El agente rescatador debe llegar a la victima asignada.
 */
public class AlcanzarVictima extends Objetivo {

	/**
	 * Identificador del agente victima.
	 */
	public String minero;
	
	/**
	 * Constructora de la clase.
	 */
	public AlcanzarVictima() {
		super.setgoalId("SacarVictima");
		this.setSolving();
	}
	
	/**
	 * Constructora de la clase.
	 * @param m Identificador del agente victima.
	 */
	public AlcanzarVictima(String m){
		this.minero = m;
		super.setgoalId("SacarVictima");
		this.setSolving();
	}
	
	/**
	 * Funcion que nos permite consultar el identificador del agente victima.
	 * @return Identificador del agente victima.
	 */
	public String getMinero(){
		return this.minero;
	}
}
