package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.tareas;

import icaro.aplicaciones.MRS.informacion.ListaIds;
import icaro.aplicaciones.MRS.informacion.Rescatador;
import icaro.aplicaciones.MRS.informacion.Robot;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.ControlEvaluacionVictimas;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.EvaluacionObjetivo;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.MsgRobotLibre;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos.EsperaRobotAsignado;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos.InformarSoyElMejorRobot;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 * Procesar un mensaje de robot libre, 'MsgRobotLibre'.
 */
public class ProcesarRobotInactivo extends TareaSincrona {
	@Override
	public void ejecutar(Object... params) {
		//t1.ejecutar(agentId, $yo, $fc, $mo, $lr, $msg, $ce);
		/* 
		 * params[0] -> nombre del agente
		 * params[1] -> yo
		 * params[2] -> fc
		 * params[3] -> MisObjetivos
		 * --------------------------------
		 * params[4] -> Lista de robots
		 * params[5] -> MsgRobotLibre
		 * params[6] -> controlEvaluacionVictimas
		 */
		String agentId 	= (String) params[0];
		Robot yo 		= (Robot) params[1];
		Focus f 		= (Focus) params[2];
		MisObjetivos mo = (MisObjetivos) params[3];
		
		ListaIds lr 	 = (ListaIds) params[4];
		
		MsgRobotLibre msg = (MsgRobotLibre) params[5];
		
		ControlEvaluacionVictimas ce = (ControlEvaluacionVictimas) params[6];
		//----------------------------------------------------------------------
		
		ce.unsetRobotAsignado(msg.getRobot());
		this.getEnvioHechos().actualizarHechoWithoutFireRules(ce);
		
		this.getEnvioHechos().eliminarHecho(msg);
		
		
		
		
		trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente,
				"Recibido que el robot " + msg.getRobot() + " está libre",
				InfoTraza.NivelTraza.info));
	}
}
