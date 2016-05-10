package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.tareas;

import icaro.aplicaciones.MRS.informacion.*;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.*;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos.*;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.*;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;


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

		

		this.getEnvioHechos().insertarHecho(o2);
		this.getEnvioHechos().actualizarHecho(mo);
		this.getEnvioHechos().actualizarHecho(f);
		
		
		//----------------------------------------------------------------------
		// Informar mediante trazas
		trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;

		trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente,
				"Recibida la evaluación de todas las victimas. Cambiando de objetivo",
				InfoTraza.NivelTraza.info));
	}

}
