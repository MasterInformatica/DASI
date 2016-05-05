package icaro.aplicaciones.agentes.agenteAplicacionMineroMRS.informacion;

import java.util.Random;

public class VoyAPedirAyuda {
	private boolean pido;
	
	public VoyAPedirAyuda(){
		Random rnd = new Random();
		int n = rnd.nextInt(10);
		pido = true;// n < 8; //random
	}
}
