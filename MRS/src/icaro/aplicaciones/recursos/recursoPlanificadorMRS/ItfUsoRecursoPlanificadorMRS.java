package icaro.aplicaciones.recursos.recursoPlanificadorMRS;

import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;
import icaro.aplicaciones.MRS.informacion.Mapa;
import icaro.aplicaciones.MRS.informacion.TipoCelda;

import java.util.ArrayList;

import icaro.aplicaciones.MRS.informacion.Coordenada;

/**
 * Interfaz del Recurso PlanificadorMRS
 * Facilita la abstraccion del calculo de rutas.
 * @author Hristo Ivanov
 */
public interface ItfUsoRecursoPlanificadorMRS extends ItfUsoRecursoSimple{
	/**
	 * Cambia el Mapa sobre el cual se realiza el calculo de rutas
	 * @param mapa nuevo mapa
	 * @throws Exception
	 */
	public void setMapa(Mapa mapa) throws Exception;
	
	/**
	 * Dada una coordenada de inicio y una de fin devuelve una Ruta
	 * @param start punto de inicio
	 * @param finish punto de fin
	 * @return ruta en formato lista de coordenadas por donde debe pasar, si no hay ruta
	 * devuelve lista vacia
	 * @throws Exception
	 */
	public ArrayList<Coordenada> getRuta(Coordenada start, Coordenada finish) throws Exception;
	
	/**
	 * Se modifica el conocimiento del mapa insertando un bloqueo en la coordenada recibida
	 * @param c coordenada con bloqueo descubierto
	 * @return devuelve <code>true</code> si no se conocia el obstaculo
	 * @throws Exception
	 */
	public boolean informarBloqueo(Coordenada c) throws Exception;
	
	/**
	 * Se utiliza para notificar al recurso de que ha habido un cambio de estado en la simulacion
	 * @param st nuevo estado
	 * @throws Exception
	 */
	public void cambioEstado(String st) throws Exception;
	
	/**
	 * Cambia una coordenada concreta del mapa a un tipo
	 * @param x horizontal
	 * @param y vertical
	 * @param t nuevo tipo
	 * @throws Exception
	 */
	public void changeMap(int x, int y, TipoCelda t) throws Exception;
}
