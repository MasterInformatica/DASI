package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion;

import java.util.ArrayList;
import java.util.List;

import icaro.aplicaciones.MRS.informacion.Robot;

public class ListaRobots {
	public List<String> nombres = null;
	
	public ListaRobots(){
		this.nombres = new ArrayList<String>();
	}
	
	public ListaRobots(List<Robot> l){
		this.nombres = new ArrayList<String>();
		
		for(Robot r : l){
			this.nombres.add(r.getName());
		}
	}
	
	public void addRobot(Robot r){
		if(this.nombres==null)
			nombres = new ArrayList<String>();
		
		this.nombres.add(r.getName());
	}
	
	public void addRobot(String s){
		if(this.nombres == null)
			nombres = new ArrayList<String>();
		
		this.nombres.add(s);
	}
	
	public void deleteRobot(String s){
		this.nombres.remove(s);
	}
	
	public List<String> getNames(){
		return this.nombres;
	}
}
