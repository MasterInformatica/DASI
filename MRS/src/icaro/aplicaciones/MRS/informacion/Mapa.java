package icaro.aplicaciones.MRS.informacion;

public class Mapa {
	private int sx=5, sy=5;
	private TipoCelda[][] mapa;

	public TipoCelda[][] getMapa(){
		return mapa;
	}
	
	public int getNumCols(){
		return sx;
	}
	
	public int getNumRows(){
		return sy;
	}
	public TipoCelda getCoord(int x, int y){
		return this.mapa[x][y];
	}
	
	public Mapa(){
		this.mapa = new TipoCelda[this.sx][this.sy];
	
		for(int i=0; i<sx; i++)
			for(int j=0; j<sy; j++)
				mapa[i][j] = TipoCelda.PARED;
		
		for(int i=0; i<sx; i+=2)
			for(int j=0; j<sy; j++)
				mapa[i][j] = TipoCelda.PASILLO;
		
		for(int i=0; i<sx; i++)
			for(int j=0; j<sy; j+=2)
				mapa[i][j] = TipoCelda.PASILLO;
	}
	
}