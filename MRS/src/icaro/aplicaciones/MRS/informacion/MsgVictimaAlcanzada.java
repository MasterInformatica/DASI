package icaro.aplicaciones.MRS.informacion;

public class MsgVictimaAlcanzada {

	private String robot, victima;

	public MsgVictimaAlcanzada(String robot, String victima){
		this.robot = robot;
		this.victima = victima;
	}
	
	public String getVictima(){
		return victima;
	}
	public String getRobot(){
		return robot;
	}
	
	@Override
	public String toString() {
		return "Robot " + robot + " alcanza a victima " + victima;
	}
}
