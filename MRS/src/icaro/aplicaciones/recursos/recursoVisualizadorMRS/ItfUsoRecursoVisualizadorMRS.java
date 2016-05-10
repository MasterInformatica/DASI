package icaro.aplicaciones.recursos.recursoVisualizadorMRS;

import java.io.File;

import icaro.aplicaciones.MRS.informacion.Coordenada;
import icaro.aplicaciones.MRS.informacion.Mapa;
import icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp.NotificadorEventos;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoRecursoVisualizadorMRS extends ItfUsoRecursoSimple{

	// Cuando una victima es rescatada, es decir, alguien inserta el evento/mensaje
	// en la cola de eventos, se notifica a la interfaz mediante este método
	public void mostrarVictimaRescatada(String VictimaId) throws Exception;


	/**
	 * Intenta mover un agente desde donde este hasta la posicion indicada,
	 * devuelve false si no puede moverse ahi ¿Eso me toca a mi o al controlador? 
	 * @param idAgente id del agente a mover
	 * @param coordActuales coordenadas donde se quiere mover
	 * @return Devuelve un boolean indicando si ha podido realizar el movimiento.
	 */
	public boolean mueveAgente(String idAgente, Coordenada coordActuales) throws Exception;
	
	// Cuando un robot se le asigna un objetivo, se llama a ete método para informar a la interfaz.
	// A lo mejor no queremos que se muestre la decisión por pantalla, o a lo mejor si.
	//Según lo que se quiera, dejar el cuerpo vacío o no.
	public void inicializarDestinoRobot(String id, Coordenada coordsAct, String idDest, Coordenada coordsDestino, double VelocidadRobot) throws Exception;
	 
	
	public void setMapa(Mapa mapa) throws Exception;


	public void muestraVentana()  throws Exception;


	public File getFicheroEscenario()  throws Exception;


	public void informaErrorEscenario(String string)  throws Exception;


	public void escenarioElegidoValido()  throws Exception;


	public void informarBloqueo(Coordenada c) throws Exception;
	
	public NotificadorEventos getNotificadorEventos() throws Exception;
	
	// El agante iniciador se registra a si mismo (lo llamo con this.nombreAgente)
	public void setAgenteIniciador(String nombreAgente) throws Exception;
}
