package icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp;

public class ControladorVisorSimulador {

	private VisorControl visorControl;
	private VisorEscenario visorEscenario;
	
	public ControladorVisorSimulador(){
		visorControl = new VisorControl(this);
		/*visorEscenario = new VisorEscenario(this);*/
	}

	
	
	
	
	public void mostrarEscenarioMovimiento() {
		if(visorEscenario!= null)
			visorEscenario.mostrar();
	}





	public boolean mueveAgente(String idAgente, Coords coord) {
		if(visorEscenario!= null)
			return false;
		else{
			return visorEscenario.mueveAgente(idAgente,coord);	
		}
	}
	
	
	
}
