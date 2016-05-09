package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.tareas;



import icaro.aplicaciones.MRS.informacion.*;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.ControlEvaluacionVictimas;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.EvaluacionObjetivo;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.MsgEvaluacionRobot;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;


public class ProcesarEvaluacion extends TareaSincrona {

	/*
 	 * PRocesa la evaluación de otro robot recibida por mensaje 	
	 */
	
	@Override
	public void ejecutar(Object... params) {
		//t1.ejecutar(obj, fc, robots, msg, ce, eo);
		
		Objetivo o      = (Objetivo) params[0];
		Focus    f      = (Focus) params[1];
		ListaIds lr     = (ListaIds) params[2];
		
		MsgEvaluacionRobot msg = (MsgEvaluacionRobot) params[3];
		
		ControlEvaluacionVictimas ce = (ControlEvaluacionVictimas) params[4];
		EvaluacionObjetivo        eo = (EvaluacionObjetivo) params[5];

		//----------------------------------------------------------------------
		assert(eo.victimaName.equals(msg.getMinero()));
				
		eo.addEvaluacion(msg.getRobot(), msg.getPuntuacion());
		
		this.getEnvioHechos().actualizarHechoWithoutFireRules(eo);
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
