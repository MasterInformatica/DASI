package icaro.aplicaciones.MRS.informacion;

import icaro.aplicaciones.Rosace.informacion.Coordinate;

public class Coordenada {
	public int x;
	public int y;
	
	
	public String toString(){
	
		return "( " + x + " , " + y + " )";
	}
	
	
	
	public Coordenada(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Coordenada(Coordenada c){
		this.x = c.x;
		this.y = c.y;
	}
	
	public Coordenada(Coordinate c) {
		this.x = (int) c.getX();
		this.y = (int) c.getY();
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	
}
