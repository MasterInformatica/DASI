package icaro.aplicaciones.MRS.informacion;

/**
 * Coordenadas de tipo entero
 * 
 * @author Hristo Ivanov
 */
public class Coordenada {
	/**
	 * Posicion x
	 */
	public int x;

	/**
	 * Posicion y
	 */
	public int y;

	@Override
	public String toString() {
		return "( " + x + " , " + y + " )";
	}

	/**
	 * Constructora
	 * 
	 * @param x
	 * @param y
	 */
	public Coordenada(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Constructor copia
	 * 
	 * @param c
	 */
	public Coordenada(Coordenada c) {
		this.x = c.x;
		this.y = c.y;
	}

	/**
	 * Devuelve la coordenada x
	 * 
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * Cambia la coordenada x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Devuelve la coordenada y
	 * 
	 * @return
	 */
	public int getY() {
		return y;
	}

	/**
	 * Cambia la coordenada y
	 */
	public void setY(int y) {
		this.y = y;
	}

}
