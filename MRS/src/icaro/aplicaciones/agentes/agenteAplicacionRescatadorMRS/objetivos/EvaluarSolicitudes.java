package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

/** 
 * Objetivo: Recibir los mensajes de auxilio de los agentes victimas,
 * y también los mensajes de evaluacion por parte de los agentes rescatadores.
 */
public class EvaluarSolicitudes extends Objetivo {
	
	/**
	 * Constructora de la clase.
	 */
	public EvaluarSolicitudes() {
		super.setgoalId("ConocerEquipo");
		this.setSolving();
	}
}
