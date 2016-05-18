package icaro.aplicaciones.MRS.informacion;

/**
 * Modelo, Victima
 * 
 * @author Jesus Domenech
 * @author Veronica Chamorro
 */
public interface Victima {

	/**
	 * Devuelve el tipo de Victima
	 * 
	 * @return "M"
	 */
	public String getTipo();

	/**
	 * Devuelve las coordenadas originales de la victima
	 * 
	 * @return posicion inicial
	 */
	public Coordenada getCoordenadasIniciales();

	/**
	 * Cambia las coordenadas originales
	 * 
	 * @param coordenadasIniciales
	 */
	public void setCoordenadasIniciales(Coordenada coordenadasIniciales);

	/**
	 * Cambia el tipo de Victima
	 * 
	 * @param tipo
	 *            "M"
	 */
	public void setTipo(String tipo);

	/**
	 * Devuelve el nombre de la victima
	 * 
	 * @return id de la victima
	 */
	public String getName();
}
