package icaro.aplicaciones.MRS.informacion;

/**
 * Mensaje de Victima a todos los Robots
 * 
 * @author Jesus Domenech
 */
public class SolicitudAyuda {

	/**
	 * Victima que pide ayuda
	 */
	private Victima v;

	/**
	 * Constructora del Mensaje
	 * 
	 * @param vic
	 */
	public SolicitudAyuda(Victima vic) {
		v = vic;
	}

	/**
	 * Devuelve la victima que lo solicita
	 * 
	 * @return victima que lo solicita
	 */
	public Victima getVictima() {
		return v;
	}

	@Override
	public String toString() {
		return "Pide ayuda: " + v.toString();
	}
}
