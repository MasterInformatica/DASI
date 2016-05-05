package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion;

public class MsgEvaluacionRobot {
	String minero;
	String robot;
	int puntuacion;
	
	public MsgEvaluacionRobot(String a, String b, int c){
		this.robot = a;
		this.minero = b;
		this.puntuacion = c;
	}
}
