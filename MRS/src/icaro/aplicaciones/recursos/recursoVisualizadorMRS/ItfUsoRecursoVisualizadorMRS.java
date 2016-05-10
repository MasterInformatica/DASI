package icaro.aplicaciones.recursos.recursoVisualizadorMRS;

import java.io.File;

import icaro.aplicaciones.MRS.informacion.Coordenada;
import icaro.aplicaciones.MRS.informacion.Mapa;
import icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp.NotificadorEventos;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoRecursoVisualizadorMRS extends ItfUsoRecursoSimple{

	/* LuisMa:
	 *  Las siguientes llamadas las hago desde la clase encargada de inicializar el sistema entero.
	 *  Puede que algunos de estos métodos ya estén implementdos en la clase padre y solamente haya que hacer
	 *  un super.
	 *  
	 *  Como funciona la clase inicializadora y controladora de todo el cotarro es a través de una máquina de estados como sigue:
	 *  Estado inicial: Coge referencia de todas las interfaces, y se las pasa a los recursos necesarios (por ejemplo,
	 *    esta clase es llamada para pasarle una referencia a la interfaz de persistencia).
	 *  Estado esperandoDefinicionEscenario: En este estado la aplicación espera que se seleccione un xml válido, y en
	 *    caso de que no exista, sacar la pantalla de preguntar por el fichero del escenario (en un principio borraré este
	 *    estado). Este estado se encarga de mandar a este Recurso que pinte la ventana de control, y cuando el escenario
	 *    esté cargado, que pinte tmb la ventana de movimiento,
	 *  Estado esperandoIniciarSimulación: Este estado espera a que el usuario pinche el botón de salvar victimas, y se le
	 *    comunica de alguna manera a través de SendSequenceOfSimulatedVictimsToRobotTeam. Este metodo tiene varias
	 *    cabeceras, asi que no se muy bien quien lo llama, ni con qué parámetros.
	 *  Estado simulando: La ejecución del estado. Recoge muchos mensajes enviados entre los robots y manda realizar
	 *    las órdenes de estos
	 *  Estado final: Ha acabado la simulación y muestra estadisticas finales y eso.
	 *  
	 *  Para el prototipo primero, la máquina de estados la voy a cambiar, de tal manera que suprimimos el estado de esperar la
	 *  entrada de un xml correcto. A continuación pongo los métodos que CREO que son necesarios, y que deberían implementarse para
	 *  la primera interacción.
	 *  También indico métodos que se usaban por Rosace, pero que segunramente no haya que implementarlos y están comentados.
	 *  
	 *  Estos métodos solo son para iniciar la ventana del simulador y demás.
	 *  Más abajo y cuando lo prepare todo pongo los métodos para mover a los robots y agentes y eso.
	 */
	

	
	
	// Esta es la forma en la que te pasan el recurso de persistencia. En el primer prototipo no se si sería necesario
	//public void setItfUsoPersistenciaSimulador(ItfUsoRecursoPersistenciaEntornosSimulacion a);
	
	
	//Cuando leo un escenario y compruebo que este escenario es válido, llamo a este método
	//para PINTAR EL MAPA.
	//El escenario a pintar se pasa por parámetro, encapsulado en un tipo propio
	// Para el primer prototipo lo mejor será tenerlo cableado, aunque te puedo pasar
	// un array de []x[] de booleanos, que es como lo tiene Hristo si te es más fácil.
	//Otra opción es que te pase una referencia al recurso de Mapa de Hristo y lo cojas tu,
	//o incluso en un futuro al recurso de Persistencia que es el que lee de un xml el mapa.
	//public void mostrarEscenarioMovimiento(Mapa mapa) throws Exception;
	
	
	
	//Una vez que he validado el escenario y mandado pintar con el método anterior,
	//inicializo mi equipo de robots, y llamo al siuigente método.
	// No se que hace este método, así que no se si sería necesario implementarlo o no.
	//Por el momento lo dejo comentado en mi agente.
	//Recibe un array list. Pero está sin parametrizar, así que no se de que tipo. Supongo que string
	//public void mostrarIdentsEquipoRobots(identsAgtesEquipo);
	
	
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
	
	
	
	/* ESTOS SON LOS MÉTODOS NECESARIOS QUE APARECEN DE CARA A PINTAR EN LA INTERFAZ.
	 */
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
