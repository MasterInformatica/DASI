package icaro.aplicaciones.MRS.informacion;

/**
 * Clase que abstae la fase de la simulacion
 * 
 * @author Jesus Domenech
 */
public class InicioEstado {
	/**
	 * Fase: Escenario Nuevo detectado
	 */
	public final static String ST_NuevoEscenario = "ST_NuevoEscenario";

	/**
	 * Fase: Simulacion iniciada
	 */
	public final static String ST_Inicio = "ST_Incio";

	/**
	 * Fase: Simulacion acabada
	 */
	public final static String ST_Fin = "ST_Fin";

	/**
	 * Estado/Fase
	 */
	public String estadoIniciado;

	/**
	 * Constructora del Estado
	 * 
	 * @param st
	 *            estado concreto
	 */
	public InicioEstado(String st) {
		estadoIniciado = st;
	}

	/**
	 * Devuelve el id del estado o fase
	 * 
	 * @return
	 */
	public String getEstadoIniciado() {
		return estadoIniciado;
	}

	@Override
	public String toString() {
		return "Iniciado: " + estadoIniciado;
	}
}
