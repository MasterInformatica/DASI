package icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp;

import java.io.File;

import icaro.aplicaciones.MRS.informacion.Mapa;
import icaro.aplicaciones.Rosace.informacion.Coordinate;

public class ControladorVisorSimulador {

	
	private VisorEscenario visorEscenario;
	
	public ControladorVisorSimulador() throws Exception{
		
		visorEscenario = new VisorEscenario(this);
	}
	
	
	public void mostrarEscenarioMovimiento() {
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

	
}
