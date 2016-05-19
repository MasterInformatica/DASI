package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import icaro.aplicaciones.MRS.informacion.Victima;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

/**
 * Clase que respresenta la evaluacion de una victima por parte de los rescatadores. 
 * @author Luis Maria Costero Valero
 */
public class EvaluacionObjetivo extends Objetivo {
	
	/**
	 * El identificador del agente victima.
	 */
	public String victimaName = null;
	
	/**
	 * Referencia al objeto que representa la victima.
	 */
	public Victima victimaObjetivo = null;	
	
	/**
	 * Valor booleano que indica si la evaluacion de la victima ha terminado.
	 */
	public boolean finalizadaEvaluacion = false;

	/**
	 * Lista con las evaluaciones de los agentes rescatadores.
	 */
	public List<ParEvaluacion> evaluaciones = null;

	/**
	 * Entero que especifica el numero de evaluaciones que deben recibirse.
	 */
	private int numeroEvaluacionesEsperadas;

	/**
	 * Entero que especifica el indice del rescatador que esta mas cerca de la victima,
     * el rescatador tambien debe estar libre.
	 */
	private int idxMejorRobot = 0;
	
	
	/**
	 * Funcion que nos permite consultar el identificador del agente victima asociado a este objeto.
     * @return El identificador del agente victima.
	 */
	public String getVictimaName() {
		return victimaName;
	}


	/**
	 * Funcion que nos permite consultar el objeto Victima asociado a este objeto.
     * @return El objeto Victima.
	 */
	public Victima getVictimaObjetivo() {
		return victimaObjetivo;
	}


	/**
	 * Funcion que nos permite consultar el estado de la evaluacion.
     * @return Verdadero en caso de haber terminado la evaluacion. Falso en caso contrario.
	 */
	public boolean getFinalizadaEvaluacion(){
		return finalizadaEvaluacion;
	}

	/**
	 * Funcion que nos permite consultar el numero de evaluaciones esperadas.
     * @return Entero que indica el numero numero de evaluaciones esperadas.
	 */
	public int getNumeroEvaluacionesEsperadas() {
		return numeroEvaluacionesEsperadas;
	}


	/**
	 * Constructora de la clase.
	 * @param v Referencia al objeto Victima.
	 * @param numVotosObjetivo Numero de evaluaciones esperadas.
	 */
	public EvaluacionObjetivo(Victima v, int numVotosObjetivo){
		this.victimaObjetivo = v;
		this.victimaName = v.getName();
		this.evaluaciones = new ArrayList<ParEvaluacion>();
		
		this.finalizadaEvaluacion = false;
		this.numeroEvaluacionesEsperadas = numVotosObjetivo;
		
		idxMejorRobot = 0;
	}
	
	
	/**
	 * Funcion que nos permite añadir una evaluacion a la lista de evaluaciones.
     * @param evaluador El identificador del agente rescatador al que pertenece la evaluacion.
     * @param puntuacion El valor numerico de la puntuacion.
	 */
	public void addEvaluacion(String evaluador, int puntuacion){
		assert(finalizadaEvaluacion==false);
		
		if (puntuacion != 0)
			this.evaluaciones.add(new ParEvaluacion(evaluador, puntuacion));
		else
			this.evaluaciones.add(new ParEvaluacion(evaluador, Integer.MAX_VALUE));

		if(this.evaluaciones.size() == this.numeroEvaluacionesEsperadas)
			this.finalizadaEvaluacion = true;
	}
	

	/**
	 * Funcion que nos permite consultar el mejor robot para rescatar a la victima.
     * return El identificador del agente rescatador que esta mas cerca de la victima.
	 */
	public String getMejorRobot(){
		Collections.sort(this.evaluaciones);
		return this.evaluaciones.get(this.idxMejorRobot).evaluador;
	}
	
	/**
	 * Funcion que nos permite consultar el siguiente mejor robot para rescatar a la victima.
	 * Esta funcion es util porque el rescatador optimo puede ya estar ocuppado rescatando una victima. 
     * return El identificador del siguiente agente rescatador que esta mas cerca de la victima.
	 */
	public String getNextMejorRobot() {
		if(this.idxMejorRobot >= this.evaluaciones.size()-1)
			return null;
		return this.evaluaciones.get(++this.idxMejorRobot).evaluador;
	}
	
    /**
     * Clase que respresenta un par <(agente rescatador), (evaluacion)>
     * @author Luis Maria Costero Valero
     */
	protected class ParEvaluacion implements Comparable<ParEvaluacion>{

        /**
         * Identificador del agente rescatador.
         */
		private String evaluador;

        /**
         * Valor numerico de la evaluacion realizada por el agente rescatador.
         */
		private int evaluacion;
		
        /**
         * Constructora de la clase.
         * @param s Identificador del agente rescatador.
         * @param i Valor numerico de la evaluacion realizada por el agente rescatador.
         */
		public ParEvaluacion(String s, int i){
			this.evaluador = s;
			this.evaluacion = i;
		}
		
		@Override
		public String toString(){
			return "(" + evaluador + " --> " + evaluacion + ")" + "\n";
		}
		
		@Override
	    public int compareTo(ParEvaluacion another) {
	        if (this.evaluacion == another.evaluacion){
	        	return this.evaluador.compareTo(another.evaluador);
	        }else if (this.evaluacion < another.evaluacion){
	            return -1;
	        }else{
	        	return 1;
	        }
	    }
	}
	
	@Override
	public String toString(){
		String aux = this.victimaName + ":\n";
		for (ParEvaluacion a1 : this.evaluaciones)
			aux += "\t" + a1.toString();
		return aux;
	}
	
}
