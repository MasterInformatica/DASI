package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

/** 
 * Objetivo: Los agentes rescatadores deben conocer a los demás.
 */
public class ConocerEquipo extends Objetivo {
	
	/**
	 * Constructora de la clase.
	 */
	public ConocerEquipo() {
		super.setgoalId("ConocerEquipo");
		this.setSolving();
	}
}
