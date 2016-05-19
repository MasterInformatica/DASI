package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion;

/**
 * Clase que respresenta un mensaje de asignacion de una victima a un rescatador.  
 * @author Luis Maria Costero Valero
 */
public class MsgAsignacionObjetivo {
	
    /**
	* Identificador del agente victima.
    */
	public String minero;

    /**
	* Identificador del agente rescatador.
    */
	public String robot;
	
	/**
	 * Constructora de la clase.
	 * @param a Identificador del agente rescatador.
	 * @param b Identificador del agente victima.
	 */
	public MsgAsignacionObjetivo(String a, String b){
		this.robot = a;
		this.minero = b;
	}

	/**
	 * Funcion que nos consultar el identificador del agente victima.
	 * @return Identificador del agente victima.
	 */
	public String getMinero() {
		return minero;
	}

	/**
	 * Funcion que nos consultar el identificador del agente rescatdor.
	 * @return Identificador del agente rescatdor.
	 */
	public String getRobot() {
		return robot;
	}
	
	
	@Override
	public String toString(){
		return "Robot " + robot + " se asigna al minero " + minero;
	}
}
