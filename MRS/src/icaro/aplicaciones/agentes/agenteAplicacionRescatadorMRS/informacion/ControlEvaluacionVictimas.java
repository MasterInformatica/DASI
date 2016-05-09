package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion;

import java.util.Iterator;
import java.util.TreeSet;

public class ControlEvaluacionVictimas {
	public String proximaVictima;
	
	public String getProximaVictima() {
		return proximaVictima;
	}

	public TreeSet<String> victimasArescatar;
	
	
	public ControlEvaluacionVictimas(){
		this.victimasArescatar = new TreeSet<String>();
		this.proximaVictima = null;
	}
	
	
	public void addVictima(String s){
		this.victimasArescatar.add(s);
		
		Iterator<String> it = this.victimasArescatar.iterator();
		this.proximaVictima = it.next();
	}
	
	public void eliminaVictima(String s){
		String v = this.victimasArescatar.pollFirst();
		assert(v == s);
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
