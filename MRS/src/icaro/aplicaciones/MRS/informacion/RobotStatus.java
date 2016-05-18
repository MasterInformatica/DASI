package icaro.aplicaciones.MRS.informacion;

/**
 * Al principio esto era un enum, pero cosas de drools no compila muy bien
 * 
 * @author Luis Costero
 */
public class RobotStatus {
	/**
	 * El Robot no tiene victima Asignada y no se mueve
	 */
	public static final int PARADO = 0;
	/**
	 * El Robot tiene victima Asignada
	 */
	public static final int HACIA_MINERO = 1;
	/**
	 * El Robot tiene el minero con el
	 */
	public static final int CON_MINERO = 2;
	/**
	 * El Robot va hacia la salida
	 */
	public static final int HACIA_SALIDA = 3;
}
