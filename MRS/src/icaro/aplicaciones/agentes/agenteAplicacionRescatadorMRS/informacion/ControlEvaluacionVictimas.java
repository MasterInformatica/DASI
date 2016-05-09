package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion;

import java.util.Iterator;
import java.util.TreeSet;

public class ControlEvaluacionVictimas {
	public String proximaVictima;
	private int victimasEvaluadas = 0;
	public TreeSet<String> victimasArescatar;
	public boolean finalizadaTodasEvaluaciones;
	
	
	public String getProximaVictima() {
		return proximaVictima;
	}

	
	
	
	public ControlEvaluacionVictimas(){
		this.victimasArescatar = new TreeSet<String>();
		this.proximaVictima = null;
		victimasEvaluadas=0;
		finalizadaTodasEvaluaciones=false;
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
	
	
	//--------------------------------------------------------------------------

	public static void main(String args[]){
		ControlEvaluacionVictimas aa = new ControlEvaluacionVictimas();
		
		aa.addVictima("HOLA");
		System.out.println(aa.proximaVictima);
		aa.addVictima("BB");
		System.out.println(aa.proximaVictima);
		aa.addVictima("DD");
		System.out.println(aa.proximaVictima);
	}


}
