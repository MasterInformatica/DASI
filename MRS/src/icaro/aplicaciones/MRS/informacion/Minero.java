package icaro.aplicaciones.MRS.informacion;


public class Minero implements Victima {
	
	private Coordenada coordenadasIniciales;
	private String tipo;
	private String nombre;
	
	public Minero (String tipo, Coordenada coordenadasIniciales, String nombre ) {
		this.tipo=tipo;
		this.coordenadasIniciales = coordenadasIniciales;
		this.nombre = nombre;
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

	@Override
	public String getName() {
		return this.nombre;
	}

}