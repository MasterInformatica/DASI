package icaro.aplicaciones.MRS.informacion;

public class SolicitudAyuda {

	private Victima v;
	public SolicitudAyuda(Victima vic){
		v = vic;
	}
	
	public Victima getVictima(){
		return v;
	}
	@Override
	public String toString() {
		return v.toString();
	}
}
