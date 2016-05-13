package icaro.aplicaciones.MRS.informacion;

import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.componentesInternos.Movimiento;
import icaro.aplicaciones.recursos.recursoPersistenciaMRS.ItfUsoRecursoPersistenciaMRS;
import icaro.aplicaciones.recursos.recursoPlanificadorMRS.ItfUsoRecursoPlanificadorMRS;
import icaro.aplicaciones.recursos.recursoVisualizadorMRS.ItfUsoRecursoVisualizadorMRS;

public class Rescatador implements Robot {
	
	private Coordenada coordenadasIniciales;
	private Coordenada coordenadasActuales;
	private String tipo;
	private String nombre;
	private int estado;
	
	public Movimiento compInternoMovimineto;
	
	public Rescatador (String tipo, Coordenada coordenadasIniciales, String name ) {
		this.tipo=tipo;
		this.coordenadasIniciales = coordenadasIniciales;
		this.coordenadasActuales = this.coordenadasIniciales;
		this.nombre = name;
		
		this.estado = RobotStatus.PARADO;
		
		this.compInternoMovimineto = null;
	}
	
	public void initCompIntMovimineto(
			ItfUsoRecursoPlanificadorMRS itfusoRecPlanRuta,
			ItfUsoRecursoVisualizadorMRS itfusoRecVisualizador,
			ItfUsoRecursoPersistenciaMRS itfusoRecPersistencia
	){
		this.compInternoMovimineto = new Movimiento(
				this,
				itfusoRecPlanRuta,
				itfusoRecVisualizador,
				itfusoRecPersistencia
		);
		this.compInternoMovimineto.start();
	}
	
	public Coordenada getCoordenadasIniciales() {
		return coordenadasIniciales;
	}

	public void setCoordenadasIniciales(Coordenada coordenadasIniciales) {
		this.coordenadasIniciales = coordenadasIniciales;
	}
	
	public Coordenada getCoordenadasActuales() {
		return this.coordenadasActuales;
	}

	public void move(Coordenada coordenadasActuales) {
		this.coordenadasActuales = coordenadasActuales;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public String toString(){
		return ""+tipo+": "+coordenadasIniciales+" ";
	}
	
	public String getName(){
		return this.nombre;
	}

	@Override
	public void SetStatus(int st) {
		this.estado = st;	
	}
	
	@Override
	public int getStatus() {
		return this.estado;
	}
}
