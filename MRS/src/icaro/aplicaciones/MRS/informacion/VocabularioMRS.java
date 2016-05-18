package icaro.aplicaciones.MRS.informacion;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;

public class VocabularioMRS extends NombresPredefinidos{
	// RUTAS de directorios
	public static String RutaArte = "MRS/images";
	public static String RutaArteBG = "MRS/images/mapa";
	public static String RutaEscenarioCableado = "MRS/escenarios/Escenario1.xml";
	
	
	// Inputs del automata Iniciador
	public static String InputIniciaSimulacion = "iniciaSimulacion";
	public static String InputCambioFicheroEscenario = "cambioFichero";

	
	// Mensajes
	public static String MensajeAyuda = "ayuda";
	
	// TAGs y ATTRs XML
	public static String TAG_PERSISTENCIA = "PersistenciaMRS";
	public static String TAG_MAPA = "Mapa";
	public static String ATTR_COLS = "cols";
	public static String ATTR_ROWS = "rows";
	public static String TAG_CELDA = "Celda";
	public static String ATTR_tipoCelda = "tipo";
	public static String TAG_listRobot = "Rescatadores";
	public static String TAG_Robot = "Rescatador";
	public static String TAG_listVictim = "Mineros";
	public static String TAG_Victim = "Minero";
	public static String ATTR_AgTipo = "tipo";
	public static String ATTR_AgName = "name";
	public static String ATTR_AgStartX = "startX";
	public static String ATTR_AgStartY = "startY";
}
