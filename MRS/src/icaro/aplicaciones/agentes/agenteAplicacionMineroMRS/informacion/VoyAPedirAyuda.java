package icaro.aplicaciones.agentes.agenteAplicacionMineroMRS.informacion;

import java.util.Random;

/**
 * Clase que decide si un minero pide ayuda o no, originalmente la pide 
 * en un 80% de los casos. Por simplicidad, se ha puesto el 100%
 * @author Jesus Domenech
 *
 */
public class VoyAPedirAyuda {
	private boolean pido;
	
	public VoyAPedirAyuda(){
		Random rnd = new Random();
		int n = rnd.nextInt(10);
		pido = true;// n < 8; //random
	}
}
