package icaro.aplicaciones.MRS.informacion;

/**
 * Modelo, Minero
 * 
 * @author Jesus Domenech
 * @author Veronica Chamorro
 */
public class Minero implements Victima {

	/**
	 * Posicion del Minero
	 */
	private Coordenada coordenadasIniciales;

	/**
	 * Tipo de Victima, siempre "M"
	 */
	private String tipo;

	/**
	 * Nombre del Agente
	 */
	private String nombre;

	/**
	 * Constructora de Mineros
	 * 
	 * @param tipo
	 *            "M"
	 * @param coordenadasIniciales
	 *            posicion original
	 * @param nombre
	 *            nombre del agente
	 */
	public Minero(String tipo, Coordenada coordenadasIniciales, String nombre) {
		this.tipo = tipo;
		this.coordenadasIniciales = coordenadasIniciales;
		this.nombre = nombre;
	}

	@Override
	public Coordenada getCoordenadasIniciales() {
		return coordenadasIniciales;
	}

	@Override
	public void setCoordenadasIniciales(Coordenada coordenadasIniciales) {
		this.coordenadasIniciales = coordenadasIniciales;
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

}