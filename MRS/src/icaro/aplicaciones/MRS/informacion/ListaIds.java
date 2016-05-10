package icaro.aplicaciones.MRS.informacion;

import java.util.ArrayList;
import java.util.List;

import icaro.aplicaciones.MRS.informacion.Robot;

public class ListaIds {
	public List<String> nombres = null;
	public int numMineros = 0;
	
	public ListaIds(){
		this.nombres = new ArrayList<String>();
		this.numMineros=0;
	}
	
	public ListaIds(ListaIds l){
		this.nombres = new ArrayList<String>(l.nombres);
		this.numMineros = l.numMineros;
	}
	
	public ListaIds(List l){
		this.nombres = new ArrayList<String>();
		this.numMineros = 0;
		
		
		for(Object r : l){
			if(r instanceof Robot){
				Robot b = (Robot)r;
				this.nombres.add(b.getName());
			}else{
				Victima b = (Victima)r;
				this.nombres.add(b.getName());
			}
		}
		
		this.numMineros = this.nombres.size();
	}
	
	public void addVictima(Victima r){
		if(this.nombres==null){
			nombres = new ArrayList<String>();
			this.numMineros = 0;
		}
		
		this.nombres.add(r.getName());
		this.numMineros++;
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
	
	@Override
	public String toString() {
		return nombres.toString();
	}
}
