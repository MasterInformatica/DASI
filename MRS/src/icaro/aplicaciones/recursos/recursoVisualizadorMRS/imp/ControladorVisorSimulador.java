package icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp;

import java.io.File;

import icaro.aplicaciones.MRS.informacion.Coordenada;
import icaro.aplicaciones.MRS.informacion.Mapa;
import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp.NotificadorEventos.Eventos;

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
	
	public boolean mueveRobot(String idAgente,Coordinate coord){
		return mueveAgente(idAgente,coord,"Robot");
	}
	
	public boolean mueveVictima(String idAgente,Coordinate coord){
		return mueveAgente(idAgente,coord,"Miner");
	}
	
	public boolean mueveAgente(String idAgente, Coordinate coord, String tipo) {
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

	public void notificar(Eventos event) {
		outPoint.notificar(event);
	}
	public void notificar(Eventos event,String s) {
		outPoint.notificar(event,s);
	}

}
