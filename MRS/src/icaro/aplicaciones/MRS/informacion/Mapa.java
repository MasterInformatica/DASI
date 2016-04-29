package icaro.aplicaciones.MRS.informacion;

import java.util.ArrayList;

public class Mapa {
	private int rows, cols;
	private TipoCelda[][] mapa;

	public Mapa(int cols, int rows){
		this.cols=cols;
		this.rows=rows;
		
		this.mapa = new TipoCelda[rows][cols];
	
		for(int i=0; i<rows; i++)
			for(int j=0; j<cols; j++)
				mapa[i][j] = TipoCelda.PARED;
	}
	
	public Mapa(Mapa toCopy, boolean olvidarEscombros){
		this.cols = toCopy.getNumCols();
		this.rows = toCopy.getNumRows();
		
		this.mapa = new TipoCelda[rows][cols];
		TipoCelda[][] map = toCopy.getMapa();
		
		for(int i=0; i<rows; i++)
			for(int j=0; j<cols; j++){
				if ((map[i][j] == TipoCelda.ESCOMBRO) && (olvidarEscombros))
					mapa[i][j] = TipoCelda.PASILLO;
				else
					mapa[i][j] = map[i][j];
			}
	}
	
	/* Devuelve un array list con los obstaculos alrededor de la posición pasada
	 * por parámetro.
	 * Si no hay obstaculos, puede devolver null o un array list de tamaño 0.   */
	public ArrayList<Coordenada> getObstaculos(Coordenada c){
		return this.getObstaculos(c.getX(), c.getY());
	}

	public ArrayList<Coordenada> getObstaculos(int x, int y){
		ArrayList<Coordenada> ret = new ArrayList<Coordenada>();
		
		if(x>0 && this.mapa[x-1][y] == TipoCelda.ESCOMBRO)
			ret.add(new Coordenada(x-1, y));
		if(x+1<rows && this.mapa[x+1][y] == TipoCelda.ESCOMBRO)
			ret.add(new Coordenada(x+1, y));
		if(y>0 && this.mapa[x][y-1] == TipoCelda.ESCOMBRO)
			ret.add(new Coordenada(x, y-1));
		if(y+1<cols && this.mapa[x][y+1] == TipoCelda.ESCOMBRO)
			ret.add(new Coordenada(x, y+1));
		
		return ret;
	}
	
	
	
	public TipoCelda[][] getMapa(){
		return mapa;
	}
	
	public int getNumRows(){
		return rows;
	}
	public int getNumCols(){
		return cols;
	}
	
	public TipoCelda getCoord(int x, int y){
		return this.mapa[x][y];
	}
	
	public void setCoord (int row , int col, TipoCelda t){
		this.mapa[row][col]=t;
	}
	
	private String tipocelda2str(TipoCelda t){
		switch(t){
		case ESCOMBRO:
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
	public String toString(){
		String str =" x:"+rows+" - y:"+cols+"\n";
		for(int i=0; i<rows; i++){
			for(int j=0; j<cols; j++){
				str += tipocelda2str(mapa[i][j]);
			}
			str+="\n";
		}
		return str;
	}
	
}
