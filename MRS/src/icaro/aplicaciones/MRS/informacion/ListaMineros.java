package icaro.aplicaciones.MRS.informacion;

import java.util.ArrayList;
import java.util.List;
import icaro.aplicaciones.MRS.informacion.Victima;


public class ListaMineros {
	public List<String> nombres = null;
	public int numMineros = 0;
	
	public ListaMineros(){
		this.nombres = new ArrayList<String>();
		this.numMineros=0;
	}
	
	public ListaMineros(ListaMineros l){
		this.nombres = new ArrayList<String>(l.nombres);
		this.numMineros = l.numMineros;
	}
	
	public ListaMineros(List<Victima> l){
		this.nombres = new ArrayList<String>();
		this.numMineros = 0;
		
		
		for(Victima r : l){
			this.nombres.add(r.getName());
		}
		
		this.numMineros = this.nombres.size();
	}
	
	public void addRobot(Robot r){
		if(this.nombres==null){
			nombres = new ArrayList<String>();
			this.numMineros = 0;
		}
		
		this.nombres.add(r.getName());
		this.numMineros++;
	}
	
	public void addRobot(String s){
		if(this.nombres == null){
			nombres = new ArrayList<String>();
			this.numMineros = 0;
		}
		
		this.nombres.add(s);
	}
	
	public void deleteRobot(String s){
		this.nombres.remove(s);
		this.numMineros--;

		//--------------------------
		assert(numMineros >= 0);
		//--------------------------
	}
	
	public List<String> getNames(){
		return this.nombres;
	}

	public int size() {
		return this.numMineros;
	}
}
