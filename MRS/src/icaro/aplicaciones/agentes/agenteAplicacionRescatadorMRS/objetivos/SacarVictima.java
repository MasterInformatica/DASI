package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

/** 
 * Objetivo: Una vez alcanzado el agente victima, el objetivo es guiar a este hacia la salida de la mina.
 */
public class SacarVictima extends Objetivo {

	/**
	 * Identificador del agente victima.
	 */
	public String minero;
	
	/**
	 * Constructora de la clase.
	 */
	public SacarVictima() {
		super.setgoalId("SacarVictima");
		this.setSolving();
	}
	
	/**
	 * Constructora de la clase.
	 * @param m Identificador del agente victima.
	 */
	public SacarVictima(String m){
		super.setgoalId("SacarVictima");
		this.minero = m;
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
