package icaro.aplicaciones.MRS.tareasComunes;

import java.util.List;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class EnviarMensajeBroadcast extends TareaSincrona {

	/**
	 * Envia el mensaje pasado por parámetro a todos los agentes que se indican.
	 *   1: List<String> con el nombre de todos los agentes a los que enviar el mensaje
	 *   2: Object con el mensaje a enviar
	 */
	@Override
	public void ejecutar(Object... params) {
		List<String> agtes = (List<String>)params[1];
	
		this.comunicator = this.getComunicator();
		for(String s : agtes){
			comunicator.enviarInfoAotroAgente(params[2], s);
		}
	}

}
