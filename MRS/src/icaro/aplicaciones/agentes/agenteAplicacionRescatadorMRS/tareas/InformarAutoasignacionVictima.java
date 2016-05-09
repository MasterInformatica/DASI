package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.tareas;

import java.util.List;

import icaro.aplicaciones.MRS.informacion.ListaIds;
import icaro.aplicaciones.MRS.informacion.Robot;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.ControlEvaluacionVictimas;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.EvaluacionObjetivo;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.MsgAsignacionObjetivo;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

public class InformarAutoasignacionVictima extends TareaSincrona {

	@Override
	public void ejecutar(Object... params) {
		//agentIs, yo, fc, mo, lr, obj, ce, eo);
		String agentId = (String) params[0];
		Robot yo = (Robot) params[1];
		Focus fc = (Focus) params[2];
		MisObjetivos mo = (MisObjetivos) params[3];
		ListaIds lr = (ListaIds) params[4];
		Objetivo obj = (Objetivo) params[5];
		ControlEvaluacionVictimas ce = (ControlEvaluacionVictimas) params[6];
		EvaluacionObjetivo eo = (EvaluacionObjetivo) params[7];
		//----------------------------------------------------------------------
		
		
		//Enviar mensaje de que soy el mejor al resto de robots
		List<String> agtes = lr.getNames();
		this.comunicator = this.getComunicator();
		
		for(String s : agtes){
			comunicator.enviarInfoAotroAgente(new MsgAsignacionObjetivo(agentId, 
					eo.getVictimaName()), s);
		}
		
		//Actualizar info: (eliminar objetivo, eliminar lista de evaluaciones)
		obj.setSolved();
		this.getEnvioHechos().eliminarHechoWithoutFireRules(obj);
		this.getEnvioHechos().eliminarHechoWithoutFireRules(eo);
		ce.eliminaVictima(eo.getVictimaName());
		this.getEnvioHechos().actualizarHecho(ce);
		
		
		
		//----------------------------------------------------------------------
		// Informar mediante trazas
		trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
		trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente,
				"Recibido el mensaje de que soy el mejor robot para la victima : "
				+ eo.getVictimaName(),
				InfoTraza.NivelTraza.info));
	}
}
