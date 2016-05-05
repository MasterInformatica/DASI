package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.tareas;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.aplicaciones.MRS.informacion.*;

public class TareaKK extends TareaSincrona {

	/** Esta tarea se encarga de leer la informaci�n del robot del recurso de persistencia,
	 * inicializar la clase RobotBaseConocimiento, e insertarlo en el conocimiento de los agentes (reglas).
	 * 
	 * Se pasa como primer argumento el nombre del robot.
	 */
	
	@Override
	public void ejecutar(Object... params) {
		
		System.err.println("\n\n" + (String)params[0] + ":  " + ((CACA)(params[1])).kkId + "\n\n");
				
		this.getEnvioHechos().actualizarHecho(params[1]);
	}	
}
