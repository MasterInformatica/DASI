package icaro.aplicaciones.agentes.agenteAplicacionMineroMRS.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

public class EsperarAuxilio extends Objetivo {
	
	public EsperarAuxilio(){
		super.setgoalId("Esperar auxilio");
		
		this.setSolving();
	}
}
