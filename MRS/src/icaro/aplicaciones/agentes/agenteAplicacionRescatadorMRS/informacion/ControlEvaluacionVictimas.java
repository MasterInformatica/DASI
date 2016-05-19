package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que controla la evaluacion y asignacion de las victimas por parte de los rescatadores.
 * @author Luis Maria Costero Valero
 */
public class ControlEvaluacionVictimas {

	/**
	 * La proxima victima a ser asignada a un robot.
	 */
	public String proximaVictima;
	
	/**
	 * El numero de victimas evaluadas.
	 */
	private int victimasEvaluadas = 0;
	
	/**
	 * Lista de todas las victimas que necesitan ser rescatadas.
	 */
	public List<String> victimasArescatar;
	
	/**
	 * Valor booleano que indica que se han evaluado todas las victimas.
	 */
	public boolean finalizadaTodasEvaluaciones;
	
	/**
	 * Control de los robots ya asignados al rescate de una victima.
	 */
	public Map<String, Boolean> robotsAsignados;


	/**
	 * Constructora de la clase.
	 * @param robots Lista de los agentes rescatadores.
	 */
	public ControlEvaluacionVictimas(List<String> robots){
		//mineros
		this.victimasArescatar = new ArrayList<String>();
		this.proximaVictima = null;
		victimasEvaluadas=0;
		finalizadaTodasEvaluaciones=false;
		
		//robots
		this.robotsAsignados = new HashMap<String, Boolean>();
		for(String s: robots){
			this.robotsAsignados.put(s, new Boolean(false));
		}
	}
	
	/**
	 * Funcion que nos consultar la proxima victima a ser asignada.
	 * @return Identificador de la proxima victima a ser asignada.
	 */
	public String getProximaVictima() {
		return proximaVictima;
	}
	

	/**
	 * Funcion que nos permite anadir una victima a la lista de victimas a ser rescatadas.
	 * @param s Identificador de la victima.
	 */
	public void addVictima(String s){
		this.victimasArescatar.add(s);
			
		finalizadaTodasEvaluaciones = false;
		Collections.sort(this.victimasArescatar);
		
		this.proximaVictima = this.victimasArescatar.get(0);
	}
	
	/**
	 * Funcion que nos permite eliminar una victima de la lista de victimas a ser rescatadas.
	 * Esta función se invoca al asignar un rescatador a la victima.
	 * @param s Identificador de la victima.
	 */
	public void eliminaVictima(String s){
		this.victimasArescatar.remove(s);
		if(this.victimasArescatar.size() != 0)
			this.proximaVictima = victimasArescatar.get(0);
	}
	
	/**
	 * Funcion que nos permite marcar como finalizada la evaluacion de una victima por parte de todos los robots.
	 * Esta función se invoca al asignar un rescatador a la victima.
	 * @param minero Identificador de la victima.
	 */
	public void informarEvaluacionFinalizada(String minero) {
		victimasEvaluadas ++;
		finalizadaTodasEvaluaciones = victimasEvaluadas == this.victimasArescatar.size();
	}
	
	/**
	 * Funcion que nos permite marcar como asignado a un agente rescatador.
	 * Esta función se invoca al asignar un rescatador a la victima.
	 * @param robotId Identificador del agente rescatador.
	 */
	public void setRobotAsignado(String robotId){
		this.robotsAsignados.put(robotId, true);
	}
	
	/**
	 * Funcion que nos permite marcar como No asignado a un agente rescatador.
	 * Esta función se invoca cuando un robot termina el rescate de una victima.
	 * @param robotId Identificador del agente rescatador.
	 */
	public void unsetRobotAsignado(String robotId){
		this.robotsAsignados.put(robotId, false);
	}
	
	/**
	 * Funcion que nos permite consultar si un agente rescatador es asignado o no.
	 * @param s Identificador del agente rescatador.
	 * @return Verdadero si el robot esta asignado. Falso en caso contrario.
	 */
	public boolean isRobotAsigned(String s){
		Boolean b = this.robotsAsignados.get(s);
		if(b== null)
			System.err.println(s+"\n"+this.proximaVictima+" "+this.victimasEvaluadas+"\n"+this.robotsAsignados);
		assert(b!=null);
		return (b);

	}

	/**
	 * Funcion que nos permite consultar el numero de victimas a rescatar.
	 * @return El numero de victimas a rescatar.
	 */
	public int getNumVictimasARescatar(){
		return this.victimasArescatar.size();
	}

	/**
	 * Funcion que nos permite consultar el numero de rescatadores que tienen una victima asignada.
	 * @return El numero de rescatadores que tienen una victima asignada.
	 */
	public int getNumRobotsOcupados() {
		int total = 0;
		
		Collection<Boolean> c = this.robotsAsignados.values();
		for(Boolean b: c){
			if(b) total++;
		}
		
		return total;
	}
}
