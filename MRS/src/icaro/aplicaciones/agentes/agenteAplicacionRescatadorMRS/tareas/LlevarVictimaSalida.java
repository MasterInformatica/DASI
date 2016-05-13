package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.tareas;

import icaro.aplicaciones.MRS.informacion.ListaIds;
import icaro.aplicaciones.MRS.informacion.MsgVictimaAlcanzada;
import icaro.aplicaciones.MRS.informacion.Rescatador;
import icaro.aplicaciones.MRS.informacion.Robot;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.ControlEvaluacionVictimas;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.EvaluacionObjetivo;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos.EsperaRobotAsignado;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos.InformarSoyElMejorRobot;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos.SacarVictima;
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
public class LlevarVictimaSalida extends TareaSincrona {
	@Override
	public void ejecutar(Object... params) {
		//t1.ejecutar(agentId, $yo, $fc, $mo, $obj, $v);
		/* 
		 * params[0] -> nombre del agente
		 * params[1] -> yo
		 * params[2] -> fc
		 * params[3] -> MisObjetivos
		 * --------------------------------
		 * params[4] -> Objetivo actual
		 * params[5] -> Nombre del minero
		 */

		String agentId 	= (String) params[0];
		Robot yo 		= (Robot) params[1];
		Focus f 		= (Focus) params[2];
		MisObjetivos mo = (MisObjetivos) params[3];
		
		Objetivo obj	= (Objetivo) params[4];
		String mineroName = (String) params[5];
		//----------------------------------------------------------------------
	
		//Informamos al minero de que le hemos alcanzado (aunque no haga nada con el mensaje)
		MsgVictimaAlcanzada msg = new MsgVictimaAlcanzada(yo.getName(), mineroName);
		this.comunicator = this.getComunicator();
		this.comunicator.enviarInfoAotroAgente(msg, mineroName);
		
		
		//Eliminamos el objetivo actual, creamos el de llevar a la salida
		obj.setSolved();
		this.getEnvioHechos().actualizarHechoWithoutFireRules(obj);
		Objetivo obj2 = new SacarVictima(mineroName);
		this.getEnvioHechos().insertarHecho(obj2);
		
		//Movemos al robot hacia la salida
		((Rescatador)yo).compInternoMovimineto.setMinero(mineroName);
		((Rescatador)yo).compInternoMovimineto.setDestino(yo.getCoordenadasIniciales());
		
		//----------------------------------------------------------------------
		// Informar mediante trazas
		trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;

		trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente,
				"Se ha alcanzado al minero " + mineroName + ". Llevandolo a la salida",
				InfoTraza.NivelTraza.info));
	}
}