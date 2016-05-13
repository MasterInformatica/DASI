package icaro.aplicaciones.MRS.informacion;

public interface Robot {
	
	public String getTipo();
	public Coordenada getCoordenadasIniciales();
	public void setCoordenadasIniciales(Coordenada coordenadasIniciales);
	public void setTipo(String tipo) ;
	
	public String getName();
	
	public void SetStatus(int st);
	public int getStatus();
}