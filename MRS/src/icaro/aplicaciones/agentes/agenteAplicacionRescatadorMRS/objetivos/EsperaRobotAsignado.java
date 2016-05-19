package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

/** 
 * Objetivo: Esperar la confirmacion del robot que hemos determinado que es el más oportuno para rescatar la victima.
 */
public class EsperaRobotAsignado extends Objetivo {

	/**
	 * Identificador del agente victima.
	 */
	public String minero;
	
	/**
	 * Constructora de la clase.
	 */
	public EsperaRobotAsignado() {
		super.setgoalId("AsignarRescatador");
		this.setSolving();
	}
	
	/**
	 * Constructora de la clase.
	 * @param m Identificador del agente victima.
	 */
	public EsperaRobotAsignado(String m){
		this.minero = m;
		super.setgoalId("AsignarRescatador");
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
