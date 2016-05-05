package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.tareas;


import java.util.List;

import icaro.aplicaciones.MRS.informacion.Coordenada;
import icaro.aplicaciones.MRS.informacion.Robot;
import icaro.aplicaciones.MRS.informacion.Victima;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.ControlEvaluacionVictimas;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.EvaluacionObjetivo;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.ListaRobots;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.MsgEvaluacionRobot;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos.ConocerEquipo;
import icaro.aplicaciones.recursos.recursoPlanificadorRuta.ItfUsoRecursoPlanificadorRuta;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;


public class ProcesarSolicitudAyuda extends TareaSincrona {

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
		Robot yo = (Robot)params[3];
		
		//TODO: SolicitudAyuda sa = (SolicitudAyuda)params[0];
		// String mineroName = sa.getVictima().getName();
		String mineroName="";
		Victima minero = null;
		ControlEvaluacionVictimas ce = (ControlEvaluacionVictimas)params[1];
		
		ce.addVictima(mineroName);
		this.getEnvioHechos().actualizarHechoWithoutFireRules(ce);
		
		
		ListaRobots lr = (ListaRobots) params[2];
		EvaluacionObjetivo eo = new EvaluacionObjetivo(minero, lr.size()+1);

		
		//Actualizamos la información propia del propio robot
		try {
			ItfUsoRecursoPlanificadorRuta pr = (ItfUsoRecursoPlanificadorRuta)
					this.repoInterfaces.obtenerInterfazUso(NombresPredefinidos.ITF_USO + "RecursoPlanificadorRuta1");
			
			List<Coordenada> l = pr.getRuta(yo.getCoordenadasIniciales(), minero.getPosicion());
			
			eo.addEvaluacion(yo.getName(), l.size());
			
			this.getEnvioHechos().insertarHechoWithoutFireRules(eo);
			
			//Informamos al resto de robots de nuestra evaluacion
			MsgEvaluacionRobot msg = new MsgEvaluacionRobot(yo.getName(), minero.getName(),
															l.size());
			
			List<String> ls = lr.getNames();
			this.comunicator = this.getComunicator();
			for(String s : ls){
				comunicator.enviarInfoAotroAgente(msg, s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		// Informar mediante trazas
		trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;

		trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente,
				"Recibida la peticion de ayuda de " + minero,
				InfoTraza.NivelTraza.info));
		
	}	
}
