package icaro.aplicaciones.recursos.recursoPersistenciaMRS;

import icaro.aplicaciones.MRS.informacion.Escenario;
import icaro.aplicaciones.MRS.informacion.TipoCelda;

import java.io.File;
//import icaro.aplicaciones.MRS.informacion.Mapa;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoRecursoPersistenciaMRS extends ItfUsoRecursoSimple{
	public Escenario parseEscenario(File file) throws Exception;
	public void escenarioToXML(Escenario escenario, File file) throws Exception;
	public Escenario getEscenario() throws Exception;
	public void cambioEstado(String st) throws Exception;
}
