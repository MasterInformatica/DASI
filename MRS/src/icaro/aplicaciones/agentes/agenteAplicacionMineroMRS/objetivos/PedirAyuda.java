package icaro.aplicaciones.agentes.agenteAplicacionMineroMRS.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

public class PedirAyuda extends Objetivo {
	
	public PedirAyuda(){
		super.setgoalId("PedirAyuda");
		
		this.setSolving();
	}
}
