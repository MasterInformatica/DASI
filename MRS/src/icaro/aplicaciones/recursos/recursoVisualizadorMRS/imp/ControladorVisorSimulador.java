package icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp;

import icaro.aplicaciones.Rosace.informacion.Coordinate;

public class ControladorVisorSimulador {

	private VisorControl visorControl;
	private VisorEscenario visorEscenario;
	
	public ControladorVisorSimulador() throws Exception{
		/*visorControl = new VisorControl(this);*/
		visorEscenario = new VisorEscenario(this);
	}
	
	
	public void mostrarEscenarioMovimiento() {
		if(visorEscenario != null)
			visorEscenario.mostrar();
	}

	public boolean mueveAgente(String idAgente, Coordinate coord) {
		if(visorEscenario == null)
			return false;
		else{
			return visorEscenario.mueveAgente(idAgente,coord);	
		}
	}

	public void termina() {
		if ( visorEscenario != null )
			visorEscenario.termina();
		if ( visorControl != null )
			visorControl.termina();
		
	}
	
}
