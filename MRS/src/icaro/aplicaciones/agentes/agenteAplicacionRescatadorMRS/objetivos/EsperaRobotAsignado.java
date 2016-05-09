package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;


/** Objetivo que representa que se han recibido todas las evaluaciones del 
 * minero y se espera a que el mejor Robot se proclame como lider
 */

public class EsperaRobotAsignado extends Objetivo {
	public String minero;
	
	public EsperaRobotAsignado() {
		super.setgoalId("AsignarRescatador");
		this.setSolving();
	}
	
	public EsperaRobotAsignado(String m){
		this.minero = m;
		super.setgoalId("AsignarRescatador");
		this.setSolving();
	}
	
	public String getMinero(){
		return this.minero;
	}
}
