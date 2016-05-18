package icaro.aplicaciones.MRS.informacion;

import java.util.ArrayList;

/**
 * Modelo, Mapa
 * 
 * @author Jesus Domenech
 * @author Veronica Chamorro
 */
public class Mapa {
	/**
	 * Numero de filas
	 */
	private int rows;
	/**
	 * Numero de columnas
	 */
	private int cols;
	/**
	 * Array de Celdas del mapa
	 */
	private TipoCelda[][] mapa;

	/**
	 * Constructora
	 * 
	 * @param cols
	 *            numero de columnas
	 * @param rows
	 *            numero de filas
	 */
	public Mapa(int cols, int rows) {
		this.cols = cols;
		this.rows = rows;

		this.mapa = new TipoCelda[rows][cols];

		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				mapa[i][j] = TipoCelda.PARED;
	}

	/**
	 * Constructor copia Transforma Escombros desconocidos en pasillo
	 * 
	 * @param toCopy
	 */
	public Mapa(Mapa toCopy) {
		this.cols = toCopy.getNumCols();
		this.rows = toCopy.getNumRows();

		this.mapa = new TipoCelda[rows][cols];
		TipoCelda[][] map = toCopy.getMapa();

		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++) {
				if (map[i][j] == TipoCelda.ESCOMBRO_UNK)
					mapa[i][j] = TipoCelda.PASILLO;
				else
					mapa[i][j] = map[i][j];
			}
	}

	/**
	 * Devuelve un array list con los obstaculos alrededor de la posicion pasada
	 * por parametro. Si no hay obstaculos, puede devolver null o un array list
	 * de size 0.
	 * 
	 * @param c
	 *            posicion
	 */
	public ArrayList<Coordenada> getObstaculos(Coordenada c) {
		return this.getObstaculos(c.getX(), c.getY());
	}

	/**
	 * Devuelve un array list con los obstaculos alrededor de la posicion pasada
	 * por parametro. Sin encapsular en Coordenada
	 * 
	 * @param x
	 *            posicion x
	 * @param y
	 *            posicion y
	 * @return
	 */
	public ArrayList<Coordenada> getObstaculos(int x, int y) {
		ArrayList<Coordenada> ret = new ArrayList<Coordenada>();

		if (x > 0 && this.mapa[x - 1][y] == TipoCelda.ESCOMBRO_UNK)
			ret.add(new Coordenada(x - 1, y));
		if (x + 1 < rows && this.mapa[x + 1][y] == TipoCelda.ESCOMBRO_UNK)
			ret.add(new Coordenada(x + 1, y));
		if (y > 0 && this.mapa[x][y - 1] == TipoCelda.ESCOMBRO_UNK)
			ret.add(new Coordenada(x, y - 1));
		if (y + 1 < cols && this.mapa[x][y + 1] == TipoCelda.ESCOMBRO_UNK)
			ret.add(new Coordenada(x, y + 1));

		return ret;
	}

	/**
	 * Devuelve el mapa en formato array de celdas
	 * 
	 * @return
	 */
	public TipoCelda[][] getMapa() {
		return mapa;
	}

	/**
	 * Devuelve el numero de filas
	 * 
	 * @return
	 */
	public int getNumRows() {
		return rows;
	}

	/**
	 * Devuelve el numero de columnas
	 * 
	 * @return
	 */
	public int getNumCols() {
		return cols;
	}

	/**
	 * Devuelve el tipo de una celda concreta
	 * 
	 * @param x
	 *            posicion de la celda
	 * @param y
	 * @return
	 */
	public TipoCelda getCoord(int x, int y) {
		return this.mapa[x][y];
	}

	/**
	 * Cambia una celda de tipo
	 * 
	 * @param row
	 *            posicion x de la celda
	 * @param col
	 *            posicion y de la celda
	 * @param t
	 *            nuevo tipo de celda
	 */
	public void setCoord(int row, int col, TipoCelda t) {
		this.mapa[row][col] = t;
	}

	/**
	 * Convierte en string el <code>TipoCelda</code> en una version simplificada
	 * 
	 * @param t
	 *            tipo celda
	 * @return
	 */
	private String tipocelda2str(TipoCelda t) {
		switch (t) {
		case ESCOMBRO_UNK:
			return "^";
		case PARED:
			return "#";
		case PASILLO:
			return "-";
		default:
			return "";

		}
	}

	@Override
	public String toString() {
		String str = " x:" + rows + " - y:" + cols + "\n";
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				str += tipocelda2str(mapa[i][j]);
			}
			str += "\n";
		}
		return str;
	}
}
