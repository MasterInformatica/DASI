package icaro.aplicaciones.MRS.informacion;

import icaro.aplicaciones.MRS.informacion.Mapa;
import icaro.aplicaciones.MRS.informacion.Robot;
import icaro.aplicaciones.MRS.informacion.Victima;
import java.util.ArrayList;
import java.util.List;


public class Escenario {
	private Mapa mapa;
	private List<Victima> listaVictimas;
	private List<Robot> listaRobots;

	public Escenario()	{
		listaVictimas=new ArrayList<Victima>();
		listaRobots=new ArrayList<Robot>();
	}


	public List<Victima> getListaVictimas() {
		return listaVictimas;
	}
	public void addVictima(Victima v)	{
		listaVictimas.add(v);
	}

	
	public List<Robot> getListaRobots() {
		return listaRobots;
	}
	
	
	public List<Robot> getListaRobotTipo(String tipo){
		ArrayList<Robot> lr = new ArrayList<Robot>();
		
		
		for (Robot r : this.listaRobots){
			if(r.getTipo().equals(tipo))
				lr.add(r);
		}
		
		
		return lr;
	}
	public void addRobot(Robot r)	{
		listaRobots.add(r);
	}

	
	public Mapa getMapa() {
		return mapa;
	}

	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}

	@Override
	public String toString(){
		return "Escenario\n MAPA:\n"+mapa+"\n\nRobots:\n"+listaRobots+"\n\nVictimas:\n"+listaVictimas+"\nSonr�e!!!";

	}


	public Robot getRobot(String s) {
		for (Robot r : this.listaRobots){
			if(r.getName().equals(s))
				return r;
		}
		
		return null;
	}

}