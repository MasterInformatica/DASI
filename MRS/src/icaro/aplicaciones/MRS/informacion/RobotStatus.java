package icaro.aplicaciones.MRS.informacion;

/* Al principio esto era un enum, pero cosas de drools no compila muy bien */
public class RobotStatus {
	public static final int PARADO = 0;
	public static final int HACIA_MINERO = 1;
	public static final int CON_MINERO = 2;
	public static final int HACIA_SALIDA = 3;
}
