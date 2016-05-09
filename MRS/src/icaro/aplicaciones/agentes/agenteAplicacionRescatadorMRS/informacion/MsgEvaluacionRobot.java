package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion;

public class MsgEvaluacionRobot {
	public String minero;
	public String robot;
	public int puntuacion;
	
	public MsgEvaluacionRobot(String a, String b, int c){
		this.robot = a;
		this.minero = b;
		this.puntuacion = c;
	}

	public String getMinero() {
		return minero;
	}

	public String getRobot() {
		return robot;
	}

	public int getPuntuacion() {
		return puntuacion;
	}
	
	@Override
	public String toString(){
		return "Evaluación del robot " + robot + " sobre la victima " + minero 
				+ "con una valoración de " + this.puntuacion;
	}
	
}
