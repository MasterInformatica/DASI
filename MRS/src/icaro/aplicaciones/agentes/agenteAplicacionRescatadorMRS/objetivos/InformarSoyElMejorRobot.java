package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

/** 
 * Objetivo: Avisar a los demas agentes rescatadores de que he determinado que soy el mejor robot para rescatar una victima.
 */
public class InformarSoyElMejorRobot extends Objetivo {

	/**
	 * Identificador del agente victima.
	 */
	public String minero;
	
	/**
	 * Constructora de la clase.
	 */
	public InformarSoyElMejorRobot() {
		super.setgoalId("InformarSoyElMejorRobot");
		this.setSolving();
	}
	
	/**
	 * Constructora de la clase.
	 * @param m Identificador del agente victima.
	 */
	public InformarSoyElMejorRobot(String m){
		this.minero = m;
		super.setgoalId("InformarSoyElMejorRobot");
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
