package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.tareas;



import icaro.aplicaciones.MRS.informacion.*;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.ControlEvaluacionVictimas;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.EvaluacionObjetivo;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.MsgEvaluacionRobot;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;


public class ProcesarEvaluacion extends TareaSincrona {

	/*
 	 * PRocesa la evaluación de otro robot recibida por mensaje 	
	 */
	
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
		 * params[6] -> mensaje
		 * params[7] -> controlEvaluacionVictimas
		 * params[8] -> Evaluacion Objetivo
		 */
		
		String name 		= (String) params[0];
		Robot yo 			= (Robot) params[1];
		Focus f 			= (Focus) params[2];
		MisObjetivos mo 	= (MisObjetivos) params[3];
		
		Objetivo o			= (Objetivo) params[4];
		
		ListaIds lr 					= (ListaIds) params[5];
		MsgEvaluacionRobot msg			= (MsgEvaluacionRobot) params[6];
		ControlEvaluacionVictimas ce 	= (ControlEvaluacionVictimas)params[7];
		EvaluacionObjetivo eo 			= (EvaluacionObjetivo) params[8];

		//----------------------------------------------------------------------
		assert(eo.victimaName.equals(msg.getMinero()));
				
		eo.addEvaluacion(msg.getRobot(), msg.getPuntuacion());
		if( eo.getFinalizadaEvaluacion() ){
			ce.informarEvaluacionFinalizada(msg.getMinero());
			this.getEnvioHechos().actualizarHecho(ce);
		}
		
		this.getEnvioHechos().actualizarHecho(eo);
		this.getEnvioHechos().eliminarHecho(msg);
		
		//----------------------------------------------------------------------
		// Informar mediante trazas
		trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;

		trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente,
				"Recibida la evaluación del robot " + msg.getRobot() + " sobre el minero " + 
						msg.getMinero() + "con coste " + msg.getPuntuacion(),
				InfoTraza.NivelTraza.info));
	}	
}
