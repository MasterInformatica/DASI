package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.tareas;

import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.ControlEvaluacionVictimas;
import icaro.aplicaciones.MRS.informacion.ListaIds;
import icaro.aplicaciones.MRS.informacion.Robot;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos.EvaluarSolicitudes;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class ProcesarNombreEquipo extends TareaSincrona {

	@Override
	public void ejecutar(Object... params) {
		/* 
		 * params[0] -> nombre del agente
		 * params[1] -> yo
		 * params[2] -> fc
		 * params[3] -> MisObjetivos
		 * --------------------------------
		 * params[4] -> Objetivo actual
		 * params[5] -> Lista Robots
		 */
		
		String name = (String) params[0];
		Robot yo = (Robot) params[1];
		Focus f = (Focus) params[2];
		MisObjetivos mo = (MisObjetivos) params[3];
		Objetivo o = (Objetivo) params[4];
		ListaIds lr = (ListaIds) params[5];
		
		ControlEvaluacionVictimas ce = new ControlEvaluacionVictimas(lr.getNames());
		
		lr.deleteRobot(name);
		this.getEnvioHechos().actualizarHechoWithoutFireRules(lr);
		
		//Marcamos como resuelto
		o.setSolved();
		this.getEnvioHechos().actualizarHechoWithoutFireRules(o);		
		
		//Insertar nuevo objetivo
		Objetivo ob = new EvaluarSolicitudes();
		ob.setSolving();
		
		
		mo.addObjetivo(ob);
		this.getEnvioHechos().actualizarHechoWithoutFireRules(mo);
		this.getEnvioHechos().insertarHechoWithoutFireRules(ob);
		f.setFoco(ob);
		this.getEnvioHechos().actualizarHechoWithoutFireRules(f);
		
		
		//Creamos la bbdd vacia para ir insertando solicitudes
		this.getEnvioHechos().insertarHecho(ce);
	}	
}
