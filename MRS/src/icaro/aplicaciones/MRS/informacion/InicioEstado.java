package icaro.aplicaciones.MRS.informacion;

public class InicioEstado {
	public final static String ST_NuevoEscenario 	= "ST_NuevoEscenario";
	public final static String ST_Inicio 			= "ST_Incio";
	public final static String ST_Fin 				= "ST_Fin";
	public final static String ST_ 					= "ST_";
	public String estadoIniciado;
	
	public InicioEstado(String st){
		estadoIniciado = st;
	}
	
	public String getEstadoIniciado() {
		return estadoIniciado;
	}
}
