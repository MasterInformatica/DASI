package icaro.aplicaciones.recursos.recursoPersistenciaMRS;

import icaro.aplicaciones.MRS.informacion.Escenario;

import java.io.File;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * Interfaz del Recurso PersistenciaMRS
 * @author Jesus Domenech
 * @author Veronica Chamorro
 */
public interface ItfUsoRecursoPersistenciaMRS extends ItfUsoRecursoSimple{
	
	/**
	 * Dado un fichero, lo parsea y devuelve un escenario
	 * @param file Fichero a parsear
	 * @return devuelve el escenario obtenido
	 * @throws Exception Lanza un error si falla el parser
	 */
	public Escenario parseEscenario(File file) throws Exception;
	
	/**
	 * Convierte un escenario en un archivo XML
	 * @param escenario Escenario a convertir
	 * @param file Fichero donde quiere guardarse el XML
	 * @throws Exception
	 */
	public void escenarioToXML(Escenario escenario, File file) throws Exception;
	
	/**
	 * Devuelve el ultimo escenario Parseado
	 * @return ultimo escenario Parseado
	 * @throws Exception
	 */
	public Escenario getEscenario() throws Exception;
	
	/**
	 * Para recibir notificaciones de cambio de Estado.
	 * No reacciona.
	 * @param st Estado de tipo <code>InicioEstado</code>
	 * @throws Exception
	 */
	public void cambioEstado(String st) throws Exception;
}
