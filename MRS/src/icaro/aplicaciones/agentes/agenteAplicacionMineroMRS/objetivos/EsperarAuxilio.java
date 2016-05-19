package icaro.aplicaciones.agentes.agenteAplicacionMineroMRS.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

/**
 * Objetivo de espera al rescatador, una vez se ha pedido ayuda el minero
 * queda esperando la llegada de un rescatador.
 * @author Luis Costero
 *
 */
public class EsperarAuxilio extends Objetivo {
	
	public EsperarAuxilio(){
		super.setgoalId("Esperar auxilio");
		
		this.setSolving();
	}
}
