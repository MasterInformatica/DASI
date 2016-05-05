package icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp;

import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;

public class NotificadorEventos extends ComunicacionAgentes{

	private String recurso;
	private String agente;
	public NotificadorEventos(String recursoId, String agenteId) {
		super(recursoId);
		recurso = recursoId;
		agente = agenteId;
	}

	public void notificar(String e){
		this.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo(e), agente);
	}
	
	public void notificar(String e,String s){
		this.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo(e,s), agente);
	}
	
	
	public void setAgente(String agenteId) {
		agente = agenteId;
		
	};

}
