package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.tareas;

import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.ControlEvaluacionVictimas;
import icaro.aplicaciones.MRS.informacion.ListaIds;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos.EvaluarSolicitudes;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class ProcesarNombreEquipo extends TareaSincrona {

	@Override
	public void ejecutar(Object... params) {
		/* params[0] -> nombre del agente
		 * params[1] -> el objeto de tipo ListaIds
		 * params[2] -> Objetivo actual
		 * params[3] -> foco
		 */
		
		String name = (String) params[0];
		ListaIds lr = (ListaIds) params[1];
		Objetivo o = (Objetivo) params[2];
		Focus f = (Focus) params[3];
		
		lr.deleteRobot(name);
		this.getEnvioHechos().actualizarHechoWithoutFireRules(lr);
		
		//Marcamos como resuelto
		o.setSolved();
		this.getEnvioHechos().actualizarHechoWithoutFireRules(o);		
		
		//Insertar nuevo objetivo
		Objetivo ob = new EvaluarSolicitudes();
		ob.setSolving();
		
		this.getEnvioHechos().insertarHechoWithoutFireRules(ob);
		f.setFoco(ob);
		this.getEnvioHechos().actualizarHechoWithoutFireRules(f);
		
		
		//Creamos la bbdd vacia para ir insertando solicitudes
		ControlEvaluacionVictimas ce = new ControlEvaluacionVictimas();
		this.getEnvioHechos().insertarHecho(ce);
	}	
}
