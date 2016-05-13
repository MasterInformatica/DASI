package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;


/** Objetivo que representa que se han recibido todas las evaluaciones del 
 * minero y se espera a que el mejor Robot se proclame como lider
 */

public class SacarVictima extends Objetivo {
	public String minero;
	
	public SacarVictima() {
		super.setgoalId("AlcanzarVictima");
		this.setSolving();
	}
	
	public SacarVictima(String m){
		super.setgoalId("AlcanzarVictima");
		this.minero = m;
		this.setSolving();
	}
	
	public String getMinero(){
		return this.minero;
	}
}
