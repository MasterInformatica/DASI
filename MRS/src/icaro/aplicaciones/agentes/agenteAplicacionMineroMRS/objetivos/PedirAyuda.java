package icaro.aplicaciones.agentes.agenteAplicacionMineroMRS.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

/**
 * Primer Objetivo de los mineros, su funcion es conseguir que los rescatadores 
 * se enteren de que existe. Cuando el objetivo esta focalizado en las reglas drools
 * se realiza la tarea <code>EnviarPeticionAyuda</code>.
 * @author Jesus Domenech
 */
public class PedirAyuda extends Objetivo {
	
	public PedirAyuda(){
		super.setgoalId("PedirAyuda");
		
		this.setSolving();
	}
}
