package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion;

/**
 * Clase que respresenta un mensaje de evaluacion de una victima por parte de un rescatador.  
 * @author Luis Maria Costero Valero
 */
public class MsgEvaluacionRobot {

	/**
	 * Identificador del agente victima.
	 */
	public String minero;

	/**
	 * Identificador del agente rescatador.
	 */
	public String robot;

	/**
	 * Valor numerico de la evaluacion realizada.
	 */
	public int puntuacion;
	
	/**
	 * Constructora de la clase.
	 * @param a Identificador del agente rescatador.
	 * @param b Identificador del agente victima.
	 * @param c Valor numerico de la evaluacion realizada.
	 */
	public MsgEvaluacionRobot(String a, String b, int c){
		this.robot = a;
		this.minero = b;
		this.puntuacion = c;
	}

	/**
	 * Funcion que nos permite consultar el identificador del agente victima.
	 * @return Identificador del agente victima.
	 */
	public String getMinero() {
		return minero;
	}

	/**
	 * Funcion que nos permite consultar el identificador del agente rescatdor.
	 * @return Identificador del agente rescatdor.
	 */
	public String getRobot() {
		return robot;
	}


	/**
	 * Funcion que nos permite consultar el valor numerico de la evaluacion que se anuncia en este mensaje.
	 * @return Valor numerico de la evaluacion.
	 */
	public int getPuntuacion() {
		return puntuacion;
	}
	
	@Override
	public String toString(){
		return "Evaluación del robot " + robot + " sobre la victima " + minero 
				+ "con una valoración de " + this.puntuacion;
	}
	
}
