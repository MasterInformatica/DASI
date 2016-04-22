package mineros;


public class Minero implements Victima {
	
	private Coordenada coordenadasIniciales;
	private String tipo;
	
	public Minero (String tipo, Coordenada coordenadasIniciales ) {
		this.tipo=tipo;
		this.coordenadasIniciales = coordenadasIniciales;
	}

	
	public Coordenada getCoordenadasIniciales() {
		return coordenadasIniciales;
	}
	public void setCoordenadasIniciales(Coordenada coordenadasIniciales) {
		this.coordenadasIniciales = coordenadasIniciales;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public String toString(){
		return ""+tipo+": "+coordenadasIniciales+" ";
	}
}