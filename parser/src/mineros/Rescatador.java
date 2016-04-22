package mineros;

public class Rescatador implements Robot {

	
	public Rescatador (String tipo, Coordenada coordenadasIniciales ) {
		this.tipo=tipo;
		this.coordenadasIniciales = coordenadasIniciales;
	}
	private Coordenada coordenadasIniciales;
	private String tipo;
	
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
