package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion;

import java.util.ArrayList;
import java.util.List;
import icaro.aplicaciones.MRS.informacion.Victima;


public class ListaMineros {
	public List<Victima> mineros=null;

	public ListaMineros(){
		this.mineros = new ArrayList<Victima>();
	}
	
	public ListaMineros(List<Victima> lv){
		this.mineros = lv;
	}
	
	public Victima getVictima(String s){
		for(Victima v : this.mineros)
			if(v.getName().equals(s)) return v;
		
		return null;
	}
}
