package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class ControlEvaluacionVictimas {
	//Control de las victimas evaluadas y asignadas
	public String proximaVictima;
	private int victimasEvaluadas = 0;
	public List<String> victimasArescatar;
	public boolean finalizadaTodasEvaluaciones;
	
	//Control de los robots ya asignados
	public Map<String, Boolean> robotsAsignados;
	
	
	
	public ControlEvaluacionVictimas(List<String> robots){
		//mineros
		this.victimasArescatar = new ArrayList<String>();
		this.proximaVictima = null;
		victimasEvaluadas=0;
		finalizadaTodasEvaluaciones=false;
		
		//robots
		this.robotsAsignados = new HashMap<String, Boolean>();
		for(String s: robots){
			this.robotsAsignados.put(s, new Boolean(false));
		}
	}
	
	
	public boolean isFinalizadaTodasEvaluaciones() {
		return finalizadaTodasEvaluaciones;
	}

	public boolean getFinalizadaTodasEvaluaciones() {
		return finalizadaTodasEvaluaciones;
	}


	public String getProximaVictima() {
		return proximaVictima;
	}	
	
	public void addVictima(String s){
		this.victimasArescatar.add(s);
			
		finalizadaTodasEvaluaciones = false;
		Collections.sort(this.victimasArescatar);
		
		this.proximaVictima = this.victimasArescatar.get(0);
	}
	
	public void eliminaVictima(String s){
		this.victimasArescatar.remove(s);
		if(this.victimasArescatar.size() != 0)
			this.proximaVictima = victimasArescatar.get(0);
	}
	

	public void informarEvaluacionFinalizada(String minero) {
		victimasEvaluadas ++;
		finalizadaTodasEvaluaciones = victimasEvaluadas == this.victimasArescatar.size();
	}
	
	
	public void setRobotAsignado(String robotId){
		this.robotsAsignados.put(robotId, true);
	}
	
	public boolean isRobotAsigned(String s){
		Boolean b = this.robotsAsignados.get(s);
		assert(b!=null);
		return (b);

	}
	
	
	//--------------------------------------------------------------------------

	public static void main(String args[]){
		ControlEvaluacionVictimas aa = new ControlEvaluacionVictimas(null);
		
		aa.addVictima("HOLA");
		System.out.println(aa.proximaVictima);
		aa.addVictima("BB");
		System.out.println(aa.proximaVictima);
		aa.addVictima("DD");
		System.out.println(aa.proximaVictima);
	}


}
