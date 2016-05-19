package icaro.aplicaciones.MRS.informacion;

import icaro.aplicaciones.MRS.informacion.Mapa;
import icaro.aplicaciones.MRS.informacion.Robot;
import icaro.aplicaciones.MRS.informacion.Victima;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo, Escenario
 * 
 * @author Jesus Domenech
 * @author Veronica Chamorro
 */
public class Escenario {
	/**
	 * Referencia al Mapa
	 */
	private Mapa mapa;
	/**
	 * Lista de Victimas
	 */
	private List<Victima> listaVictimas;
	/**
	 * Lista de Robots
	 */
	private List<Robot> listaRobots;

	/**
	 * Constructora del Escenario
	 */
	public Escenario() {
		listaVictimas = new ArrayList<Victima>();
		listaRobots = new ArrayList<Robot>();
	}

	/**
	 * Devuelve la lista de Victimas
	 * 
	 * @return
	 */
	public List<Victima> getListaVictimas() {
		return listaVictimas;
	}

	/**
	 * Inserta una victima al escenario
	 * 
	 * @param v
	 *            victima a insertar
	 */
	public void addVictima(Victima v) {
		listaVictimas.add(v);
	}

	/**
	 * Devuelve la lista de Robots
	 * 
	 * @return
	 */
	public List<Robot> getListaRobots() {
		return listaRobots;
	}

	/**
	 * Devuelve la lista de Robots de un tipo concreto
	 * 
	 * @param tipo
	 *            robot buscado
	 * @return lista de Robots del tipo
	 */
	public List<Robot> getListaRobotTipo(String tipo) {
		ArrayList<Robot> lr = new ArrayList<Robot>();

		for (Robot r : this.listaRobots) {
			if (r.getTipo().equals(tipo))
				lr.add(r);
		}

		return lr;
	}

	/**
	 * Inserta un robot al escenario
	 * 
	 * @param r
	 *            robot a insertar
	 */
	public void addRobot(Robot r) {
		listaRobots.add(r);
	}

	/**
	 * Devuelve el mapa del Escenario
	 * 
	 * @return mapa del escenario
	 */
	public Mapa getMapa() {
		return mapa;
	}

	/**
	 * Cambia el mapa del Escenario
	 * 
	 * @param mapa
	 *            nuevo mapa
	 */
	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}

	/**
	 * Devuelve un robot segun su id
	 * 
	 * @param s
	 *            id del robot buscado
	 * @return robot buscado
	 */
	public Robot getRobot(String s) {
		for (Robot r : this.listaRobots) {
			if (r.getName().equals(s))
				return r;
		}

		return null;
	}

	/**
	 * Devuelve una victima segun su id
	 * 
	 * @param s
	 *            id de la victima buscada
	 * @return victima buscada
	 */
	public Victima getVictima(String s) {
		for (Victima v : this.listaVictimas) {
			if (v.getName().equals(s))
				return v;
		}

		return null;
	}

	/**
	 * Cambia la lista de Robots
	 * 
	 * @param listaRobots2
	 *            nueva lista de robots
	 */
	public void setListR(List<Robot> listaRobots2) {
		this.listaRobots = listaRobots2;

	}

	/**
	 * Cambia la lista de Victimas
	 * 
	 * @param listaVictimas2
	 *            nueva lista de victimas
	 */
	public void setListV(List<Victima> listaVictimas2) {
		this.listaVictimas = listaVictimas2;

	}
}