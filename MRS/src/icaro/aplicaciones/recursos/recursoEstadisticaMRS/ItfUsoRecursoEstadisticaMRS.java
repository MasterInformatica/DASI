package icaro.aplicaciones.recursos.recursoEstadisticaMRS;

import java.util.List;

import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * Interfaz del Recurso EstadisticaMRS
 * @author Hristo Ivanov
 */
public interface ItfUsoRecursoEstadisticaMRS extends ItfUsoRecursoSimple{

	/**
	 * Al empezar el recate.
	 * @param numeroDeRescatadores
	 * @param numeroDeVictimas
	 * @param listaDeRecadatore
	 * @throws Exception
	 */
	public void iniciarRecate(int numeroDeRescatadores, int numeroDeVictimas, List<String> listaDeRecadatore) throws Exception;

	/**
	 * Al finalizar el rescate.
	 * @throws Exception
	 */
	public void finalizarRescate() throws Exception;

	/**
	 * Al rescatar una victima.
	 * @throws Exception
	 */
	public void notificarRescate() throws Exception;

	/**
	 * Al encontrarse con un obstaculo.
	 * @throws Exception
	 */
	public void notificarObstaculo() throws Exception;

	/**
	 * Al moverse.
	 * @param rescatador
	 * @throws Exception
	 */
	public void notificarMovimineto(String rescatador) throws Exception;
	
	/**
	 * Al finalizar el rescate, a fin de mostrar las estadisticas.
	 * @throws Exception
	 */
	public void mostrarEstadisticas() throws Exception;
}
