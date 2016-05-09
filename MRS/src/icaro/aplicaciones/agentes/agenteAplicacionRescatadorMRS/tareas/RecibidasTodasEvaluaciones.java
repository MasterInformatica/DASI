package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.tareas;

import icaro.aplicaciones.MRS.informacion.ListaIds;
import icaro.aplicaciones.MRS.informacion.Robot;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.ControlEvaluacionVictimas;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.EvaluacionObjetivo;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos.EsperaRobotAsignado;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos.InformarSoyElMejorRobot;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
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
public class RecibidasTodasEvaluaciones extends TareaSincrona {
	@Override
	public void ejecutar(Object... params) {
		// obj, fc, robots, ce, eo
		Objetivo                  obj = (Objetivo) params[0];
		Focus                     f   = (Focus) params[1];
		ListaIds                  lr  = (ListaIds) params[2];
		Robot                     yo  = (Robot) params[3];
		ControlEvaluacionVictimas ce  = (ControlEvaluacionVictimas) params[4];
		EvaluacionObjetivo        eo  = (EvaluacionObjetivo) params[5];

		//----------------------------------------------------------------------
		if(eo.getMejorRobot() == yo.getName())
			this.soyElMejorRobot(obj, f, lr, yo, ce, eo);
		else
			this.noSoyElMejorRobot(obj, f, lr, yo, ce, eo);
		
	}

	public void soyElMejorRobot(Objetivo obj, Focus f, ListaIds lr, Robot yo,
								ControlEvaluacionVictimas ce,
								EvaluacionObjetivo eo){

		Objetivo obj2 = new InformarSoyElMejorRobot(eo.getVictimaName());
		obj2.setSolving();
		this.getEnvioHechos().insertarHecho(obj2);
		
		
		//----------------------------------------------------------------------
		// Informar mediante trazas
		trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;

		trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente,
				"Recibida la evaluación de todos los robots. Esperando al mejor"
				+ " robot para la victima " + eo.getVictimaName(),
				InfoTraza.NivelTraza.info));
	}

	
	
	
	public void noSoyElMejorRobot(Objetivo obj, Focus f, ListaIds lr, Robot yo,
			ControlEvaluacionVictimas ce,
			EvaluacionObjetivo eo){
		
		Objetivo obj2 = new EsperaRobotAsignado(eo.getVictimaName());
		obj2.setSolving();
		this.getEnvioHechos().insertarHecho(obj2);
		
		
		
		//----------------------------------------------------------------------
		// Informar mediante trazas
		trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;

		trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente,
				"Recibida la evaluación de todos los robots. Soy el mejor robot"
				+ ". Generando objetivo de informar. Victima: " + eo.getVictimaName(),
				InfoTraza.NivelTraza.info));
	}
}
