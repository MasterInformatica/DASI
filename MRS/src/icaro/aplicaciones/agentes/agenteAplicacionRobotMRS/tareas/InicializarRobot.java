package icaro.aplicaciones.agentes.agenteAplicacionRobotMRS.tareas;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class InicializarRobot extends TareaSincrona {

	@Override
	public void ejecutar(Object... params) {
		System.err.println("HOLA: "+params[0]+" . "+params[1]);
	}

}
