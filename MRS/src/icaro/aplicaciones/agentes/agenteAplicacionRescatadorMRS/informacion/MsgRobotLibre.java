package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion;

public class MsgRobotLibre {
	public String robot;
	
	public MsgRobotLibre(String a){
		this.robot = a;
	}

	public String getRobot() {
		return robot;
	}
	
	@Override
	public String toString(){
		return "Robot " + robot + " está libre";
	}
	
}
