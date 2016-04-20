package mineros;

public class Mapa {
	private int rows, cols;
	private TipoCelda[][] mapa;

	public Mapa(int cols, int rows){
		this.cols=cols;
		this.rows=rows;
		
		this.mapa = new TipoCelda[cols][rows];
	
		for(int i=0; i<cols; i++)
			for(int j=0; j<rows; j++)
				mapa[i][j] = TipoCelda.PARED;
		

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
		this.mapa[col][row]=t;
	}
	
	private String tipocelda2str(TipoCelda t){
		switch(t){
		case ESCOMBRO:
			return "^";
		case PARED:
			return "#";
		case PASILLO:
			return "·";
		default:
			return "";
		
		}
	}
	 
	@Override
	public String toString(){
		String str =" x:"+cols+" - y:"+rows+"\n";
		for(int i=0; i<rows; i++){
			for(int j=0; j<cols; j++){
				str += tipocelda2str(mapa[j][i]);
			}
			str+="\n";
		}
		return str;
	}
	
}
