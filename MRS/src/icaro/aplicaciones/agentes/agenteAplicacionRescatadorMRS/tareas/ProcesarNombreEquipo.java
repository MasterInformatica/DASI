package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.tareas;

import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.ListaRobots;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class ProcesarNombreEquipo extends TareaSincrona {

	@Override
	public void ejecutar(Object... params) {
		/* params[0] -> nombre del agente
		 * params[1] -> el objeto de tipo ListaRobots
		 * params[2] -> Objetivo actual
		 * params[3] -> foco
		 */
		
		String name = (String) params[0];
		ListaRobots lr = (ListaRobots) params[1];
		Objetivo o = (Objetivo) params[2];
		Focus f = (Focus) params[3];
		
		lr.deleteRobot(name);
		this.getEnvioHechos().actualizarHechoWithoutFireRules(lr);
		
		
		f.setFoco(null);
		this.getEnvioHechos().actualizarHechoWithoutFireRules(f);
		this.getEnvioHechos().eliminarHecho(o);		
	}	
}
