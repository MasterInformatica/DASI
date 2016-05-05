package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;



public class EvaluarSolicitudes extends Objetivo {
	
	public EvaluarSolicitudes() {
		super.setgoalId("ConocerEquipo");
		
		this.setSolving();
	}
}
