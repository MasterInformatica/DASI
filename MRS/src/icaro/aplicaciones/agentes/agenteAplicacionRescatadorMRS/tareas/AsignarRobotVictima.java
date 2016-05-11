package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.tareas;

import icaro.aplicaciones.MRS.informacion.ListaIds;
import icaro.aplicaciones.MRS.informacion.Rescatador;
import icaro.aplicaciones.MRS.informacion.Robot;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.ControlEvaluacionVictimas;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.EvaluacionObjetivo;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos.EsperaRobotAsignado;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos.InformarSoyElMejorRobot;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 * Cuando se reciben todas las evaluaciones de un minero, se procesan generando
 * nuevos objetivos según el caso:
 *   a) Si yo soy el mejor robot para el minero, se genera el objetivo de informar
 * al resto de robots.
 *   b) En caso contrario, se genera el objetivo de esperar al mejro robot que nos
 * informe
 */
public class AsignarRobotVictima extends TareaSincrona {
	@Override
	public void ejecutar(Object... params) {
		/* 
		 * params[0] -> nombre del agente
		 * params[1] -> yo
		 * params[2] -> fc
		 * params[3] -> MisObjetivos
		 * --------------------------------
		 * params[4] -> Objetivo Actual
		 * params[5] -> robots
		 * params[6] -> controlEvaluacionVictimas
		 * params[7] -> evaluacionObjetivo
		 */
		String agentId 	= (String) params[0];
		Robot yo 		= (Robot) params[1];
		Focus f 		= (Focus) params[2];
		MisObjetivos mo = (MisObjetivos) params[3];
		
		Objetivo obj	 = (Objetivo) params[4];
		ListaIds lr 	 = (ListaIds) params[5];
		
		ControlEvaluacionVictimas ce = (ControlEvaluacionVictimas) params[6];
		EvaluacionObjetivo eo 		 = (EvaluacionObjetivo) params[7];
		//----------------------------------------------------------------------
		
		String mejorRobot = eo.getMejorRobot();
		System.err.println(agentId + "Hola Mundo....");
		while(ce.isRobotAsigned(mejorRobot)){
			mejorRobot = eo.getNextMejorRobot();
		}
	
		System.err.println(agentId + " " + mejorRobot + " para minero: "+ eo.victimaName);
		System.err.println(eo);
		if(mejorRobot == yo.getName())
			this.soyElMejorRobot(obj, f, lr, yo, ce, mo, eo);
		else
			this.noSoyElMejorRobot(obj, f, lr, yo, ce, mo, eo);
	}

	
	public void soyElMejorRobot(Objetivo obj, Focus f, ListaIds lr, Robot yo,
								ControlEvaluacionVictimas ce, MisObjetivos mo,
								EvaluacionObjetivo eo){

		Objetivo obj2 = new InformarSoyElMejorRobot(eo.getVictimaName());
		obj2.setSolving();
		mo.addObjetivo(obj2);
		f.setFoco(obj2);
		this.getEnvioHechos().actualizarHecho(f);
		this.getEnvioHechos().actualizarHecho(mo);
		this.getEnvioHechos().insertarHecho(obj2);
		
		//----------------------------------------------------------------------
		// Informar mediante trazas
		trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;

		trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente,
				"Recibida la evaluación de todos los robots. Informando de que soy"
				+ " el mejor para la victima " + eo.getVictimaName(),
				InfoTraza.NivelTraza.info));
	}

	
	
	
	public void noSoyElMejorRobot(Objetivo obj, Focus f, ListaIds lr, Robot yo,
			ControlEvaluacionVictimas ce, MisObjetivos mo,
			EvaluacionObjetivo eo){
		
		Objetivo obj2 = new EsperaRobotAsignado(eo.getVictimaName());
		obj2.setSolving();
		mo.addObjetivo(obj2);
		f.setFoco(obj2);
		this.getEnvioHechos().actualizarHecho(mo);
		this.getEnvioHechos().insertarHecho(obj2);
		this.getEnvioHechos().actualizarHecho(f);
		
		
		
		//----------------------------------------------------------------------
		// Informar mediante trazas
		trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;

		trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente,
				"Recibida la evaluación de todos los robots. NO soy el mejor robot"
				+ " Victima: " + eo.getVictimaName(),
				InfoTraza.NivelTraza.info));
	}
}
