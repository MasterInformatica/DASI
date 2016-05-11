package icaro.aplicaciones.MRS.informacion;

public interface Victima {

	public String getTipo();
	public Coordenada getCoordenadasIniciales();
	public void setCoordenadasIniciales(Coordenada coordenadasIniciales);
	public void setTipo(String tipo) ;
	
	public String getName();
}
