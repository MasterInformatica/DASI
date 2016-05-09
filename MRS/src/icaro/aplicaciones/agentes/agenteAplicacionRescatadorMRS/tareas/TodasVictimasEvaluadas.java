package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.tareas;

import icaro.aplicaciones.MRS.informacion.Robot;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.ControlEvaluacionVictimas;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos.AsignarRobots;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class TodasVictimasEvaluadas extends TareaSincrona {

	@Override
	public void ejecutar(Object... params) {
		/* 
		 * params[0] -> nombre del agente
		 * params[1] -> yo
		 * params[2] -> fc
		 * params[3] -> MisObjetivos
		 * --------------------------------
		 * params[4] -> Objetivo Actual
		 * params[5] -> ControlEvaluacionVictimas
		 */
		
		String name 		= (String) params[0];
		Robot yo 			= (Robot) params[1];
		Focus f 			= (Focus) params[2];
		MisObjetivos mo 	= (MisObjetivos) params[3];
		
		Objetivo o			= (Objetivo) params[4];
		
		ControlEvaluacionVictimas ce 	= (ControlEvaluacionVictimas)params[5];
		
		
		o.setSolved();
		this.getEnvioHechos().actualizarHechoWithoutFireRules(o);
		
		Objetivo o2 = new AsignarRobots();
		o2.setSolving();
		mo.addObjetivo(o2);
		f.setFoco(o2);
		this.getEnvioHechos().insertarHechoWithoutFireRules(o2);
		this.getEnvioHechos().actualizarHechoWithoutFireRules(f);
		this.getEnvioHechos().actualizarHecho(mo);
	}

}
