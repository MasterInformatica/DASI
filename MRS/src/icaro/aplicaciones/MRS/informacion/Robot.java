package icaro.aplicaciones.MRS.informacion;

/**
 * Modelo, Robot
 * 
 * @author Jesus Domenech
 * @author Veronica Chamorro
 */
public interface Robot {

	/**
	 * Devuelve el tipo de Robot
	 * 
	 * @return "R"
	 */
	public String getTipo();

	/**
	 * Devuelve las coordenadas originales del Robot
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
	 * Cambia el tipo de Robot
	 * 
	 * @param tipo
	 *            "R"
	 */
	public void setTipo(String tipo);

	/**
	 * Devuelve el nombre del Robot
	 * 
	 * @return id del Robot
	 */
	public String getName();

	/**
	 * Cambia el estado del robot Es un entero para poder trabajar desde drools
	 * 
	 * @param st clase <code>RobotStatus</code> 
	 */
	public void SetStatus(int st);

	/**
	 * Devuelve el estado del robot
	 * 
	 * @return
	 */
	public int getStatus();
}