package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion;

/**
 * Clase que respresenta un mensaje en el que un robot anuncia que vuelve a estar libre.
 * Este mensaje es enviado por un rescatador al termina el rescate que esta llevando a cabo.
 * @author Luis Maria Costero Valero
 */
public class MsgRobotLibre {

	/**
	 * Identificador del agente rescatador.
	 */
	public String robot;
	
	/**
	 * Constructora de la clase.
	 * @param a Identificador del agente rescatador.
	 */
	public MsgRobotLibre(String a){
		this.robot = a;
	}

	/**
	 * Funcion que nos permite consultar el identificador del agente rescatdor.
	 * @return Identificador del agente rescatdor.
	 */
	public String getRobot() {
		return robot;
	}
	
	@Override
	public String toString(){
		return "Robot " + robot + " está libre";
	}
	
}
