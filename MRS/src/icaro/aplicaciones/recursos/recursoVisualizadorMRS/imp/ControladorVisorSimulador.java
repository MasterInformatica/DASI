package icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp;

import icaro.aplicaciones.MRS.informacion.Mapa;
import icaro.aplicaciones.Rosace.informacion.Coordinate;

public class ControladorVisorSimulador {

	private VisorControl visorControl;
	private VisorEscenario visorEscenario;
	
	public ControladorVisorSimulador() throws Exception{
		/*visorControl = new VisorControl(this);*/
		visorEscenario = new VisorEscenario(this);
	}
	
	
	public void mostrarEscenarioMovimiento(Mapa mapa) {
		if(visorEscenario != null)
			visorEscenario.mostrar(mapa);
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
		if ( visorControl != null )
			visorControl.termina();
		
	}


	public void setMapa(Mapa mapa) {
		if ( visorEscenario != null )
			visorEscenario.setMapa(mapa);
		
	}
	
}
