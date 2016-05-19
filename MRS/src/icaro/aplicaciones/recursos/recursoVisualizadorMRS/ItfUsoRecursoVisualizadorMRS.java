package icaro.aplicaciones.recursos.recursoVisualizadorMRS;

import java.io.File;
import java.util.List;

import icaro.aplicaciones.MRS.informacion.Coordenada;
import icaro.aplicaciones.MRS.informacion.Mapa;
import icaro.aplicaciones.MRS.informacion.Robot;
import icaro.aplicaciones.MRS.informacion.Victima;
import icaro.aplicaciones.recursos.recursoPersistenciaMRS.ItfUsoRecursoPersistenciaMRS;
import icaro.aplicaciones.recursos.recursoPlanificadorMRS.ItfUsoRecursoPlanificadorMRS;
import icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp.NotificadorEventos;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * Interfaz del Recurso VisualizadorMRS
 * @author Jesus Domenech
 */
public interface ItfUsoRecursoVisualizadorMRS extends ItfUsoRecursoSimple{

	/**
	 * Este metodo se llama cuando se rescata a la victima <code>VictimaId</code>
	 * para poder mostrar por viasualizacion esa informacion.
	 * @param VictimaId es el id de la victima rescatada.
	 * @throws Exception
	 */
	public void mostrarVictimaRescatada(String VictimaId) throws Exception;

	/**
	 * Intenta mover un robot desde donde este hasta la posicion indicada,
	 * devuelve false si no puede moverse ahi ¿Eso me toca a mi o al controlador? 
	 * @param idAgente id del agente a mover
	 * @param coordActuales coordenadas donde se quiere mover
	 * @return Devuelve un boolean indicando si ha podido realizar el movimiento.
	 */
	public boolean mueveRobot(String idAgente, Coordenada coordActuales) throws Exception;
	
	/**
	 * Mueve visualmente una victima desde donde este hasta la posicion indicada,
	 * devuelve false si no se ha movido 
	 * @param idVictima id del agente a mover
	 * @param coordActuales coordenadas donde se quiere mover
	 * @return Devuelve un boolean indicando si ha realizado el movimiento.
	 */
	public boolean mueveVictima(String idVictima, Coordenada coordActuales) throws Exception;
	
	/**
	 * Cuando un robot se le asigna un objetivo, se llama a este metodo para informar a la interfaz.
	 * Para mostrar cambios en la pantalla si se quiere.
	 * @param id del Robot rescatador
	 * @param coordsAct posicion del Robot
	 * @param idDest de la Victima a ser rescatada
	 * @param coordsDestino posicion de la Victima
	 * @param VelocidadRobot velocidad de movimiento maxima del robot
	 * @throws Exception
	 */
	public void inicializarDestinoRobot(String id, Coordenada coordsAct, String idDest, Coordenada coordsDestino, double VelocidadRobot) throws Exception;
	 
	/**
	 * Pinta el mapa y los obstaculos del mismo
	 * @param mapa a pintar
	 * @throws Exception
	 */
	public void setMapa(Mapa mapa) throws Exception;

	/**
	 * Pinta los robots en la interfaz 
	 * @param listaRobots lista de todos los robots rescatadores del escenario actual
	 * @throws Exception
	 */
	public void setRobots(List<Robot> listaRobots) throws Exception;

	/**
	 * Pinta las victimas en la interfaz 
	 * @param listaVictimas lista de todos las victimas del escenario actual
	 * @throws Exception
	 */
	public void setVictimas(List<Victima> listaVictimas) throws Exception;

	/**
	 * muestra la ventana si no se ha mostrado ya
	 * @throws Exception
	 */
	public void muestraVentana()  throws Exception;

	/**
	 * Devuelve el fichero seleccionado por el usuario o null si no
	 * ha seleccionado ninguno.
	 * @return Devuelve el fichero seleccionado por el usuario o null  
	 * @throws Exception
	 */
	public File getFicheroEscenario()  throws Exception;

	/**
	 * Notifica a la interfaz que hay un error en el fichero analizado
	 * @param string informacion del error
	 * @throws Exception
	 */
	public void informaErrorEscenario(String string)  throws Exception;

	/**
	 * Notifica a la interfaz que el escenario ha sido validado con exito
	 * @throws Exception
	 */
	public void escenarioElegidoValido()  throws Exception;

	/**
	 * Notifica a la interfaz que el sistema conoce el bloqueo de las coordenada <code>c</code>
	 * @param c Coordenada donde se produce el derrumbe.
	 * @throws Exception
	 */
	public void informarBloqueo(Coordenada c) throws Exception;
	
	/**
	 * Devuelve el comunicador de eventos sucedidos en la interfaz.
	 * @return Devuelve el comunicador de eventos sucedidos en la interfaz.
	 * @throws Exception
	 */
	public NotificadorEventos getNotificadorEventos() throws Exception;
	
	/**
	 * Notifica a la interfaz el nombre del agente que gestiona todo el simulador
	 * @param nombreAgente nombre del agente gestor del simulador
	 * @throws Exception
	 */
	public void setAgenteIniciador(String nombreAgente) throws Exception;

	/**
	 * Notifica a la interfaz que hay un cambio de estado en el proceso de la simulacion
	 * @param st Estado nuevo, definidos en la clase <code>IncioEstado</code>
	 * @throws Exception
	 */
	public void cambioEstado(String st) throws Exception;

	/**
	 * Comunica las interfaces de persistencia y planificacion al visualizador para permitir
	 * editar el mapa.
	 * @param persi interfaz del recurso de PersitenciaMRS
	 * @param plan interfaz del recurso de PlanificacidorMRS
	 * @throws Exception
	 */
	void setItf(ItfUsoRecursoPersistenciaMRS persi, ItfUsoRecursoPlanificadorMRS plan) throws Exception;
}
