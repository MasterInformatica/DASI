package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.tareas;


import java.util.List;

import icaro.aplicaciones.MRS.informacion.*;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.ControlEvaluacionVictimas;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.EvaluacionObjetivo;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.MsgAsignacionObjetivo;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.MsgEvaluacionRobot;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos.ConocerEquipo;
import icaro.aplicaciones.recursos.recursoPlanificadorMRS.ItfUsoRecursoPlanificadorMRS;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;


public class ProcesarMsgAsignacionRobot extends TareaSincrona {

	/*
	 * Inicializa las estructuras necesarias para procesar la petición de ayuda
	 * 
	 * 0: Mensaje de ayuda
	 * 1: control evaluacion
	 * 2: lista de robots
	 * 3: Robot yo
	 */
	
	@Override
	public void ejecutar(Object... params) {
		//AgentId, yo, fc, mo, obj, msg, ce, eo
		
		String       agentId = (String) params[0];
		Robot        yo      = (Robot) params[1];
		Focus        f       = (Focus) params[2];
		MisObjetivos mo      = (MisObjetivos) params[3];
		
		Objetivo              obj = (Objetivo) params[4];
		MsgAsignacionObjetivo msg = (MsgAsignacionObjetivo) params[5];
		
		ControlEvaluacionVictimas ce = (ControlEvaluacionVictimas) params[6];
		EvaluacionObjetivo        eo = (EvaluacionObjetivo) params[7];
		//----------------------------------------------------------------------		
		
		assert(msg.getMinero() == eo.getVictimaName());
		assert(msg.getRobot() == eo.getMejorRobot());
		
		//Eliminamos la lista de evaluaciones de la victima. Ya está resuelto
		this.getEnvioHechos().eliminarHechoWithoutFireRules(eo);
		
		//Eliminamos la victima de la lista de victimas a rescatar
		ce.eliminaVictima(msg.getMinero());
		this.getEnvioHechos().actualizarHechoWithoutFireRules(ce);

		//Eliminamos el mensaje para que no se repita.
		this.getEnvioHechos().eliminarHechoWithoutFireRules(msg);
		
		//Marcamos el objetivo como resuelto y lo eliminamos.
		obj.setSolved();
		this.getEnvioHechos().eliminarHecho(obj);
		
		
		//----------------------------------------------------------------------
		// Informar mediante trazas
		trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
		trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente,
				"Recibido el mensaje del robot: " + msg.getRobot() + ", como el "
				+ "mejor candidato para salvar a la victima " + msg.getMinero(),
				InfoTraza.NivelTraza.info));
		
	}	
}
