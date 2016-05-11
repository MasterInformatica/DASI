package icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp;

import java.io.File;
import java.util.List;

import icaro.aplicaciones.MRS.informacion.Coordenada;
import icaro.aplicaciones.MRS.informacion.Mapa;
import icaro.aplicaciones.MRS.informacion.Robot;
import icaro.aplicaciones.MRS.informacion.Victima;

public class ControladorVisorSimulador {

	private VisorEscenario visorEscenario;
	private ClaseGeneradoraRecursoVisualizadorMRS outPoint;
	public ControladorVisorSimulador(ClaseGeneradoraRecursoVisualizadorMRS cgrvm ) throws Exception{
		outPoint = cgrvm;
		visorEscenario = new VisorEscenario(this);
	}
	
	public void mostrarEscenario() {
		if(visorEscenario != null)
			visorEscenario.mostrar();
	}
	
	public boolean mueveRobot(String idAgente,Coordenada coord){
		return mueveAgente(idAgente,coord,"Robot");
	}
	
	public boolean mueveVictima(String idAgente,Coordenada coord){
		return mueveAgente(idAgente,coord,"Miner");
	}
	
	public boolean mueveAgente(String idAgente, Coordenada coord, String tipo) {
		if(visorEscenario != null)
			return visorEscenario.mueveAgente(idAgente,coord,tipo);	
		return false;
	}

	public void termina() {
		if ( visorEscenario != null )
			visorEscenario.termina();
	}

	public void setMapa(Mapa mapa) {
		if ( visorEscenario != null )
			visorEscenario.setMapa(mapa);
	}

	public void setRobots(List<Robot> listaRobots) {
		if ( visorEscenario != null )
			visorEscenario.setRobots(listaRobots);
	}
	
	public void setVictimas(List<Victima> listaVictimas) {
		if ( visorEscenario != null )
			visorEscenario.setVictimas(listaVictimas);
	}
	
	public File getFicheroEscenario() {
		return visorEscenario.getFicheroEscenario();
	}


	public void muestaError(String string, String string2) {
		visorEscenario.muestraError(string,string2);
	}

	public void errorFileEscenario() {
		visorEscenario.errorFileEscenario();	
	}
	

	
	/*   Metodos para los objetos controlados */
	
	public void informarBloqueo(Coordenada c) {
		visorEscenario.informarBloqueo(c);
	}

	public void notificar(String event) {
		outPoint.notificar(event);
	}
	public void notificar(String event,String s) {
		outPoint.notificar(event,s);
	}





}
