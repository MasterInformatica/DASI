package icaro.aplicaciones.MRS.informacion;

import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.componentesInternos.Movimiento;
import icaro.aplicaciones.recursos.recursoEstadisticaMRS.ItfUsoRecursoEstadisticaMRS;
import icaro.aplicaciones.recursos.recursoPersistenciaMRS.ItfUsoRecursoPersistenciaMRS;
import icaro.aplicaciones.recursos.recursoPlanificadorMRS.ItfUsoRecursoPlanificadorMRS;
import icaro.aplicaciones.recursos.recursoVisualizadorMRS.ItfUsoRecursoVisualizadorMRS;

/**
 * Modelo, Rescatador
 * 
 * @author Jesus Domenech
 * @author Veronica Chamorro
 */
public class Rescatador implements Robot {

	/**
	 * Coordenadas originales
	 */
	private Coordenada coordenadasIniciales;

	/**
	 * Coordenadas actuales
	 */
	private Coordenada coordenadasActuales;
	/**
	 * Tipo de Robot, "R"
	 */
	private String tipo;
	/**
	 * id del agente
	 */
	private String nombre;
	/**
	 * Estado <code> RobotStatus</code> Es un entero para poder trabajar desde
	 * drools
	 * 
	 */
	private int estado;

	/**
	 * Referencia al componente de movimiento del roboto
	 */
	public Movimiento compInternoMovimineto;

	/**
	 * Constructor del Rescatador
	 * 
	 * @param tipo
	 *            "R"
	 * @param coordenadasIniciales
	 *            posicion inicial
	 * @param name
	 *            id del agente
	 */
	public Rescatador(String tipo, Coordenada coordenadasIniciales, String name) {
		this.tipo = tipo;
		this.coordenadasIniciales = coordenadasIniciales;
		this.coordenadasActuales = this.coordenadasIniciales;
		this.nombre = name;

		this.estado = RobotStatus.PARADO;

		this.compInternoMovimineto = null;
	}

	public void initCompIntMovimineto(ItfUsoRecursoPlanificadorMRS itfusoRecPlanRuta,
			ItfUsoRecursoVisualizadorMRS itfusoRecVisualizador, ItfUsoRecursoPersistenciaMRS itfusoRecPersistencia,
			ItfUsoRecursoEstadisticaMRS itfusoRecEstadistica) {
		this.compInternoMovimineto = new Movimiento(this, itfusoRecPlanRuta, itfusoRecVisualizador,
				itfusoRecPersistencia, itfusoRecEstadistica);
		this.compInternoMovimineto.start();
	}

	@Override
	public Coordenada getCoordenadasIniciales() {
		return coordenadasIniciales;
	}

	@Override
	public void setCoordenadasIniciales(Coordenada coordenadasIniciales) {
		this.coordenadasIniciales = coordenadasIniciales;
	}

	/**
	 * devuelve las coordenadas actuales del robot
	 * 
	 * @return coodenadas
	 */
	public Coordenada getCoordenadasActuales() {
		return this.coordenadasActuales;
	}

	/**
	 * Cambia las coordenadas actuales del robot
	 * 
	 * @param coordenadasActuales
	 *            nueva posicion
	 */
	public void move(Coordenada coordenadasActuales) {
		this.coordenadasActuales = coordenadasActuales;
	}

	@Override
	public String getTipo() {
		return tipo;
	}

	@Override
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "" + tipo + ": " + coordenadasIniciales + " ";
	}

	@Override
	public String getName() {
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
