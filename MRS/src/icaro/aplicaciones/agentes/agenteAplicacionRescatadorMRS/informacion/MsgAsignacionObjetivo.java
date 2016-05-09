package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion;

public class MsgAsignacionObjetivo {
	public String minero;
	public String robot;
	
	public MsgAsignacionObjetivo(String a, String b){
		this.robot = a;
		this.minero = b;
	}

	public String getMinero() {
		return minero;
	}

	public String getRobot() {
		return robot;
	}
	
}
