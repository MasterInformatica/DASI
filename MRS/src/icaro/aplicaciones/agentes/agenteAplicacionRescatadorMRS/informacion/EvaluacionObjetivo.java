package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import icaro.aplicaciones.MRS.informacion.Victima;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

public class EvaluacionObjetivo extends Objetivo {
	public String victimaName = null;
	public Victima victimaObjetivo = null;	
	public boolean finalizadaEvaluacion = false;
	public List<ParEvaluacion> evaluaciones = null;

	private int numeroEvaluacionesEsperadas;

	private int idxMejorRobot = 0;
	
	
	public String getVictimaName() {
		return victimaName;
	}


	public Victima getVictimaObjetivo() {
		return victimaObjetivo;
	}


	public boolean isFinalizadaEvaluacion() {
		return finalizadaEvaluacion;
	}

	public boolean getFinalizadaEvaluacion(){
		return finalizadaEvaluacion;
	}

	public int getNumeroEvaluacionesEsperadas() {
		return numeroEvaluacionesEsperadas;
	}


	public EvaluacionObjetivo(Victima v, int numVotosObjetivo){
		this.victimaObjetivo = v;
		this.victimaName = v.getName();
		this.evaluaciones = new ArrayList<ParEvaluacion>();
		
		this.finalizadaEvaluacion = false;
		this.numeroEvaluacionesEsperadas = numVotosObjetivo;
		
		idxMejorRobot = 0;
	}
	
	
	public void addEvaluacion(String evaluador, int puntuacion){
		assert(finalizadaEvaluacion==false);
		
		this.evaluaciones.add(new ParEvaluacion(evaluador, puntuacion));
		
		if(this.evaluaciones.size() == this.numeroEvaluacionesEsperadas)
			this.finalizadaEvaluacion = true;
	}
	
	public String getMejorRobot(){
		Collections.sort(this.evaluaciones);
		return this.evaluaciones.get(this.idxMejorRobot).evaluador;
	}
	
	public String getNextMejorRobot() {
		if(this.idxMejorRobot >= this.evaluaciones.size()-1)
			return null;
		return this.evaluaciones.get(++this.idxMejorRobot).evaluador;
	}
	
	protected class ParEvaluacion implements Comparable<ParEvaluacion>{
		private String evaluador;
		private int evaluacion;
		
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
