package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion;

import java.util.ArrayList;
import java.util.List;

import icaro.aplicaciones.MRS.informacion.Victima;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

public class EvaluacionObjetivo extends Objetivo {
	public String victimaName = null;
	public Victima victimaObjetivo = null;	
	public boolean finalizadaEvaluacion = false;
	public List<ParEvaluacion> evaluaciones = null;

	private int numeroEvaluacionesEsperadas;

	
	
	
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
	}
	
	
	public void addEvaluacion(String evaluador, int puntuacion){
		assert(finalizadaEvaluacion==false);
		
		this.evaluaciones.add(new ParEvaluacion(evaluador, puntuacion));
		
		if(this.evaluaciones.size() == this.numeroEvaluacionesEsperadas)
			this.finalizadaEvaluacion = true;
	}
	
	public String getMejorRobot(){
		return this.evaluaciones.get(0).evaluador;
	}
	
	protected class ParEvaluacion{
		private String evaluador;
		private int evaluacion;
		
		public ParEvaluacion(String s, int i){
			this.evaluador = s;
			this.evaluacion = i;
		}
	}
}
