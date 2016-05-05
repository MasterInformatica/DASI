package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;



public class ConocerEquipo extends Objetivo {
	
	public ConocerEquipo() {
		super.setgoalId("ConocerEquipo");
		
		this.setSolving();
	}
}
