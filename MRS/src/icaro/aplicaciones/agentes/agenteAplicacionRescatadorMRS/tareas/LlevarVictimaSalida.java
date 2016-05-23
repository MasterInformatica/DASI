package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.tareas;

import icaro.aplicaciones.MRS.informacion.MsgVictimaAlcanzada;
import icaro.aplicaciones.MRS.informacion.Rescatador;
import icaro.aplicaciones.MRS.informacion.Robot;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos.SacarVictima;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 * Al alcanzar al minero que tenemos que rescatar se dispara esta tarea.
 * Primero se informa al minero de que acabamos de alcanzarle.
 * Seguidamente se inicializa el componente de movimiento hacia la salida de la mina.
 * Finalmente se fija el nuevo objetivo 'SacarVictima'. 
 */
public class LlevarVictimaSalida extends TareaSincrona {
	@SuppressWarnings("unused")
	@Override
	public void ejecutar(Object... params) {
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
