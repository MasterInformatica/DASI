package icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp;

import java.io.File;
import java.util.List;

import icaro.aplicaciones.MRS.informacion.Coordenada;
import icaro.aplicaciones.MRS.informacion.Escenario;
import icaro.aplicaciones.MRS.informacion.Mapa;
import icaro.aplicaciones.MRS.informacion.Robot;
import icaro.aplicaciones.MRS.informacion.TipoCelda;
import icaro.aplicaciones.MRS.informacion.Victima;
import icaro.aplicaciones.recursos.recursoPersistenciaMRS.ItfUsoRecursoPersistenciaMRS;
import icaro.aplicaciones.recursos.recursoPlanificadorMRS.ItfUsoRecursoPlanificadorMRS;
/**
 * Clase controladora de la interfaz, 
 * @author Jesus Domenech
 */
public class ControladorVisorSimulador {

	/**
	 * Ventana grafica
	 */
	private VisorEscenario visorEscenario;
	
	/**
	 * Punto de salida del recurso
	 */
	private ClaseGeneradoraRecursoVisualizadorMRS outPoint;

	/**
	 * Interfaz del Recurso PlanificadorMRS
	 */
	private ItfUsoRecursoPlanificadorMRS itfPlanificadorMRS;

	/**
	 * Interfaz del Recurso PersistenciaMRS
	 */
	private ItfUsoRecursoPersistenciaMRS itfPersistenciaMRS;
	
	/**
	 * Constructora del Controlador
	 * @param cgrvm punto de salida del recurso, clase generadora del recurso
	 * @throws Exception
	 */
	public ControladorVisorSimulador(ClaseGeneradoraRecursoVisualizadorMRS cgrvm ) throws Exception{
		outPoint = cgrvm;
		visorEscenario = new VisorEscenario(this);
	}
	
	/**
	 * Manda mostrar el escenario
	 */
	public void mostrarEscenario() {
		if(visorEscenario != null)
			visorEscenario.mostrar();
	}
	
	/**
	 * Manda mover un Robot a una posicion
	 * @param idAgente nombre del agente
	 * @param coord posicion del agente
	 * @return devuelve si ha movido el agente.
	 */
	public boolean mueveRobot(String idAgente,Coordenada coord){
		return mueveAgente(idAgente,coord,"Robot");
	}
	
	/**
	 * Manda mover un Minero a una posicion
	 * @param idAgente nombre del agente
	 * @param coord posicion del agente
	 * @return devuelve si ha movido el agente.
	 */
	public boolean mueveVictima(String idAgente,Coordenada coord){
		return mueveAgente(idAgente,coord,"Miner");
	}
	
	/**
	 * Manda mover un Agente a una posicion
	 * @param idAgente nombre del agente
	 * @param coord posicion del agente
	 * @param tipo Tipo de agente a mover
	 * @return devuelve si ha movido el agente.
	 */
	public boolean mueveAgente(String idAgente, Coordenada coord, String tipo) {
		if(visorEscenario != null)
			return visorEscenario.mueveAgente(idAgente,coord,tipo);	
		return false;
	}

	/**
	 * Manda finalizar la ventana.
	 */
	public void termina() {
		if ( visorEscenario != null )
			visorEscenario.termina();
	}

	/**
	 * Manda Cambiar el mapa del escenario
	 * @param mapa nuevo mapa
	 */
	public void setMapa(Mapa mapa) {
		if ( visorEscenario != null )
			visorEscenario.setMapa(mapa);
	}

	/**
	 * Manda cambiar la lista de robots
	 * @param listaRobots nueva lista de robots
	 */
	public void setRobots(List<Robot> listaRobots) {
		if ( visorEscenario != null )
			visorEscenario.setRobots(listaRobots);
	}
	
	/**
	 * Manda cambiar la lista de victimas
	 * @param listaRobots nueva lista de victimas
	 */
	public void setVictimas(List<Victima> listaVictimas) {
		if ( visorEscenario != null )
			visorEscenario.setVictimas(listaVictimas);
	}
	
	/**
	 * Manda devolver el fichero del escenario
	 * @return Fichero del escenario
	 */
	public File getFicheroEscenario() {
		return visorEscenario.getFicheroEscenario();
	}

	/**
	 * Manda visualizar un error
	 * @param string Titulo del error
	 * @param string2 Contenido del error
	 */
	public void muestaError(String string, String string2) {
		visorEscenario.muestraError(string,string2);
	}

	/**
	 * Notifica un error en el archivo
	 */
	public void errorFileEscenario() {
		visorEscenario.errorFileEscenario();	
	}
	
	/**
	 * Notifica a la ventana que se ha detectado un bloqueo
	 * en la coordenada <code>c</code> por los agentes.
	 * @param c coordenadas del bloqueo
	 */
	public void informarBloqueo(Coordenada c) {
		visorEscenario.informarBloqueo(c);
	}

	/**
	 * Abstraccion de la notificacion al agente inciciador
	 * @param event tipo de evento
	 */
	public void notificar(String event) {
		outPoint.notificar(event);
	}
	
	/**
	 * Abstraccion de la notificacion al agente inciciador con informacion adicional
	 * @param event tipo de evento
	 * @param s informacion adicional al evento
	 */
	public void notificar(String event,String s) {
		outPoint.notificar(event,s);
	}

	/**
	 * Notifica a la interfaz que hay un cambio de estado en el proceso de la simulacion
	 * @param st Estado nuevo, definidos en la clase <code>IncioEstado</code>
	 */
	public void cambioEstado(String st) {
		visorEscenario.cambioEstado(st);
	}

	/**
	 * Notifica al planificador cambios en el mapa
	 * @param x posision vertical del cambio
	 * @param y posicion horizontal del cambio
	 * @param t nuevo tipo de celda
	 * @throws Exception
	 */
	public void changeMap(int x, int y, TipoCelda t) throws Exception {
		this.itfPlanificadorMRS.changeMap(x,y,t);
	}

	/**
	 * Comunica las interfaces de persistencia y planificacion al visualizador para permitir
	 * editar el mapa.
	 * @param persi interfaz del recurso de PersitenciaMRS
	 * @param plan interfaz del recurso de PlanificacidorMRS
	 */
	public void setItf(ItfUsoRecursoPersistenciaMRS persi,
			ItfUsoRecursoPlanificadorMRS plan) {
		this.itfPersistenciaMRS = persi;
		this.itfPlanificadorMRS = plan;
		
	}

	/**
	 * Notifica al recurso persistencia que guarde el escenario 
	 * en un fichero
	 * @param esc Escenario a guardar 
	 * @param f Fichero destino
	 * @throws Exception
	 */
	 void saveMap(Escenario esc,File f) throws Exception {
		this.itfPersistenciaMRS.escenarioToXML(esc, f);
	}
}
