package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.tareas;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class SaludaRobot extends TareaSincrona {

	@Override
	public void ejecutar(Object... params) {
		System.err.println("HOLA: "+params[0]+" . "+params[1]);
	}
	
}
