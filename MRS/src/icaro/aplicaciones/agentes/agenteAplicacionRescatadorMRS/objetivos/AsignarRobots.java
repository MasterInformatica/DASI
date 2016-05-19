package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

/** 
 * Objetivo: El agente rescatador debe decidir si asignarse la proxima victima o esperar. 
 */
public class AsignarRobots extends Objetivo {

	/**
	 * Constructora de la clase.
	 */
	public AsignarRobots() {
		super.setgoalId("AsignarRobots");
		this.setSolving();
	}
}
