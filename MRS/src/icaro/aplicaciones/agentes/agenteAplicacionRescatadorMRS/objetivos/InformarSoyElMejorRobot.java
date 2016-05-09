package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;


public class InformarSoyElMejorRobot extends Objetivo {
	public String minero;
	
	public InformarSoyElMejorRobot() {
		super.setgoalId("InformarSoyElMejorRobot");
		this.setSolving();
	}
	
	public InformarSoyElMejorRobot(String m){
		this.minero = m;
		super.setgoalId("InformarSoyElMejorRobot");
		this.setSolving();
	}
	
	public String getMinero(){
		return this.minero;
	}
}
