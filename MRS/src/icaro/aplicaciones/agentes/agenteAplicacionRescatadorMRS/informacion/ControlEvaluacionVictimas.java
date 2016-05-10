package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class ControlEvaluacionVictimas {
	//Control de las victimas evaluadas y asignadas
	public String proximaVictima;
	private int victimasEvaluadas = 0;
	public TreeSet<String> victimasArescatar;
	public boolean finalizadaTodasEvaluaciones;
	
	//Control de los robots ya asignados
	public Map<String, Boolean> robotsAsignados;
	
	
	
	public ControlEvaluacionVictimas(List<String> robots){
		//mineros
		this.victimasArescatar = new TreeSet<String>();
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
		
		Iterator<String> it = this.victimasArescatar.iterator();
		this.proximaVictima = it.next();
		finalizadaTodasEvaluaciones = false;
	}
	
	public void eliminaVictima(String s){
		String v = this.victimasArescatar.pollFirst();
		assert(v == s);
		victimasEvaluadas--;
		finalizadaTodasEvaluaciones = victimasEvaluadas == this.victimasArescatar.size();
	}
	

	public void informarEvaluacionFinalizada(String minero) {
		victimasEvaluadas ++;
		
		finalizadaTodasEvaluaciones = victimasEvaluadas == this.victimasArescatar.size();
	}
	
	
	
	public boolean isRobotAsigned(String s){
		Boolean b = this.robotsAsignados.get(s);
		
		return (b==null || b.booleanValue());

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
