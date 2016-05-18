package icaro.aplicaciones.MRS.informacion;

/**
 * Mensaje enviado del Robot a la Victima
 * 
 * @author Luis Maria
 */
public class MsgVictimaAlcanzada {

	/**
	 * Id del Robot
	 */
	private String robot;
	/**
	 * Id de la Victima
	 */
	private String victima;

	/**
	 * Constructora del mensaje
	 * 
	 * @param robot
	 *            id del Rescatador
	 * @param victima
	 *            id del Minero
	 */
	public MsgVictimaAlcanzada(String robot, String victima) {
		this.robot = robot;
		this.victima = victima;
	}

	/**
	 * Devuelve el id de la Victima
	 * 
	 * @return id de la Victima
	 */
	public String getVictima() {
		return victima;
	}

	/**
	 * Devuelve el id del Robot
	 * 
	 * @return id del Robot
	 */
	public String getRobot() {
		return robot;
	}

	@Override
	public String toString() {
		return "Robot " + robot + " alcanza a victima " + victima;
	}
}
