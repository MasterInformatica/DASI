package icaro.aplicaciones.MRS.informacion;

import java.util.ArrayList;
import java.util.List;

import icaro.aplicaciones.MRS.informacion.Robot;

/**
 * Modelo, Lista de Ids de Agentes Para poder incorporarla en drools
 * 
 * @author Luis Costero
 */
public class ListaIds {
	/**
	 * Lista real de ids
	 */
	public List<String> nombres = null;
	/**
	 * Size de la lista
	 */
	public int numMineros = 0;

	/**
	 * Constructora
	 */
	public ListaIds() {
		this.nombres = new ArrayList<String>();
		this.numMineros = 0;
	}

	/**
	 * Constructor copia
	 * 
	 * @param l
	 */
	public ListaIds(ListaIds l) {
		this.nombres = new ArrayList<String>(l.nombres);
		this.numMineros = l.numMineros;
	}

	/**
	 * Constructo a partir de un List de java
	 * 
	 * @param l
	 *            List
	 */
	public ListaIds(@SuppressWarnings("rawtypes") List l) {
		this.nombres = new ArrayList<String>();
		this.numMineros = 0;

		for (Object r : l) {
			if (r instanceof Robot) {
				Robot b = (Robot) r;
				this.nombres.add(b.getName());
			} else {
				Victima b = (Victima) r;
				this.nombres.add(b.getName());
			}
		}

		this.numMineros = this.nombres.size();
	}

	/**
	 * Incorpora un Agente a la lista a partir de una victima
	 * 
	 * @param v
	 *            victima
	 */
	public void addVictima(Victima v) {
		if (this.nombres == null) {
			nombres = new ArrayList<String>();
			this.numMineros = 0;
		}

		this.nombres.add(v.getName());
		this.numMineros++;
	}

	/**
	 * Incorpora un Agente a la lista a partir de un robot
	 * 
	 * @param r
	 *            robot
	 */
	public void addRobot(Robot r) {
		if (this.nombres == null) {
			nombres = new ArrayList<String>();
			this.numMineros = 0;
		}

		this.nombres.add(r.getName());
		this.numMineros++;
	}

	/**
	 * Incorpora un Agente segun su id
	 * 
	 * @param s
	 *            id del agente
	 */
	public void addRobot(String s) {
		if (this.nombres == null) {
			nombres = new ArrayList<String>();
			this.numMineros = 0;
		}

		this.nombres.add(s);
	}

	/**
	 * Elimina un agente segun su id;
	 * 
	 * @param s
	 */
	public void deleteRobot(String s) {
		this.nombres.remove(s);
		this.numMineros--;

		// --------------------------
		assert (numMineros >= 0);
		// --------------------------
	}

	/**
	 * Devuelve la lista java
	 * 
	 * @return lista con los ids.
	 */
	public List<String> getNames() {
		return this.nombres;
	}

	/**
	 * Size de la lista
	 * 
	 * @return int size
	 */
	public int size() {
		return this.numMineros;
	}

	@Override
	public String toString() {
		return nombres.toString();
	}
}
