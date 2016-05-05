package icaro.aplicaciones.MRS.informacion;

public class Rescatador implements Robot {
	
	private Coordenada coordenadasIniciales;
	private String tipo;
	private String nombre;
	
	public Rescatador (String tipo, Coordenada coordenadasIniciales, String name ) {
		this.tipo=tipo;
		this.coordenadasIniciales = coordenadasIniciales;
		this.nombre = name;
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
	
	public String getName(){
		return this.nombre;
	}
}
