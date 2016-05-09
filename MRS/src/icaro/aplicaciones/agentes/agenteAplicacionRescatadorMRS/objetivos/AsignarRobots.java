package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

public class AsignarRobots extends Objetivo {
	public AsignarRobots() {
		super.setgoalId("AsignarRobots");
		
		this.setSolving();
	}
}
