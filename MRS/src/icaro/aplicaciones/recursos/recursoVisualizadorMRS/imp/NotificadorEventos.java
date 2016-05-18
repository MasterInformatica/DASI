package icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp;

import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;

/**
 * Clase que gestiona las notificaciones entre el recurso y el agente iniciadr
 * @author Jesus Domenech
 */
public class NotificadorEventos extends ComunicacionAgentes{
	/**
	 * Id del recurso
	 */
	private String recurso;
	/**
	 * Id del agente a notificar
	 */
	private String agente;
	/**
	 * Constructora del notificador
	 * @param recursoId id del recurso
	 * @param agenteId id del agente
	 */
	public NotificadorEventos(String recursoId, String agenteId) {
		super(recursoId);
		recurso = recursoId;
		agente = agenteId;
	}

	/**
	 * notificacion sencilla
	 * @param e Mensaje
	 */
	public void notificar(String e){
		this.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo(e), agente);
	}
	
	/**
	 * notificacion con contenido
	 * @param e Mensaje
	 * @param s Contenido
	 */
	public void notificar(String e,String s){
		this.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo(e,s), agente);
	}
	
	/**
	 * Cambia el id del agente
	 * @param agenteId nuevo id del agente
	 */
	public void setAgente(String agenteId) {
		agente = agenteId;
	};
}
