package icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp;

public class NotificadorEventos {

	private String recurso;
	private String agente;
	public NotificadorEventos(String recursoId, String agenteId) {
		recurso = recursoId;
		agente = agenteId;
	}

	public void notificar(Eventos e){
		
	}
	
	public void notificar(Eventos e,String s){
		
	}
	
	public static enum Eventos { ARCHIVO_CAMBIADO, BOTON_START, BOTON_REINICIAR, MENU_SALVAR, MENU_SALIR};

}
