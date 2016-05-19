package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.tareas;


import java.util.List;

import icaro.aplicaciones.MRS.informacion.*;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.ControlEvaluacionVictimas;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.EvaluacionObjetivo;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.MsgAsignacionObjetivo;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.MsgRobotLibre;
import icaro.aplicaciones.recursos.recursoEstadisticaMRS.ItfUsoRecursoEstadisticaMRS;
import icaro.aplicaciones.recursos.recursoPlanificadorMRS.ItfUsoRecursoPlanificadorMRS;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;


public class VictimaSalvada extends TareaSincrona {
	
	@Override
	public void ejecutar(Object... params) {
		//	t1.ejecutar(agentId, $yo, $fc, $mo, $obj, $lr, $o2, $ce, $eo);
		/* 
		 * params[0] -> nombre del agente
		 * params[1] -> yo
		 * params[2] -> fc
		 * params[3] -> MisObjetivos
		 * --------------------------------
		 * params[4] -> Objetivo Actual
		 * params[5] -> Lista de robots
		 * params[6] -> Objetivo a focalizar al salir
		 * params[7] -> controlEvaluacionVictimas
		 * params[8] -> evaluacionObjetivo
		 */
		
		String agentId 	= (String) params[0];
		Robot yo 		= (Robot) params[1];
		Focus fc 		= (Focus) params[2];
		MisObjetivos mo = (MisObjetivos) params[3];
		
		Objetivo obj				= (Objetivo) params[4];
		ListaIds lr 				= (ListaIds) params[5];
		Objetivo obj2	 			= (Objetivo) params[6];
		
		ControlEvaluacionVictimas ce = (ControlEvaluacionVictimas) params[7];
		//----------------------------------------------------------------------
		
		obj.setSolved();
		this.getEnvioHechos().actualizarHechoWithoutFireRules(obj);

		fc.setFoco(obj2);
		obj2.setSolving();
		this.getEnvioHechos().actualizarHechoWithoutFireRules(fc);
		this.getEnvioHechos().actualizarHechoWithoutFireRules(obj2);

		List<String> l = lr.getNames();
		this.comunicator = this.getComunicator();
		for(String s : l){
			MsgRobotLibre msg = new MsgRobotLibre(agentId);
			this.comunicator.enviarInfoAotroAgente(msg, s);
		}
		
		ce.unsetRobotAsignado(agentId);
		this.getEnvioHechos().actualizarHecho(ce);

		// Si no quedan mas victimas que salvar, y todos los robots esten libres, es que se ha acabado la simulacion.
		// Informo al agente Reactivo para que pase de estado y avise a quien tenga que avisar.
		if(ce.getNumVictimasARescatar()==0 && ce.getNumRobotsOcupados()==0)
			this.comunicator.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo("finSimulacion"), "AgenteAplicacionIniciadorMRS1");
		

		// Informar mediante trazas
		trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
		trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente,
				"Yo, robot: " + agentId + " me he quedado libre",
				InfoTraza.NivelTraza.info));
		
		// Informar al recurso de estadistica.
		ItfUsoRecursoEstadisticaMRS est = null;
		try {
			est = (ItfUsoRecursoEstadisticaMRS)
					this.repoInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + "RecursoEstadisticaMRS1");
			est.notificarRescate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
