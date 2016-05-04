package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.tareas;

import icaro.aplicaciones.MRS.informacion.RobotBaseConocimiento;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;


public class InicializarRobot extends TareaSincrona {

	/** Esta tarea se encarga de leer la informaci�n del robot del recurso de persistencia,
	 * inicializar la clase RobotBaseConocimiento, e insertarlo en el conocimiento de los agentes (reglas).
	 * 
	 * Se pasa como primer argumento el nombre del robot.
	 */
	
	@Override
	public void ejecutar(Object... params) {
		RobotBaseConocimiento r = new RobotBaseConocimiento();
		r.setIdRobot((String) params[0]);
	
		//Aqu� habr�a que acceder a la persistencia, y rellenar el conocimiento del robot
		//de acuerdo a los datos que est�n all� almacenados.
		
		this.getEnvioHechos().insertarHechoWithoutFireRules(r);
	}	
}
