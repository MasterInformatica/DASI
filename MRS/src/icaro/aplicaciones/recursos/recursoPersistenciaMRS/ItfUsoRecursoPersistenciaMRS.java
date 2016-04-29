package icaro.aplicaciones.recursos.recursoPersistenciaMRS;

import icaro.aplicaciones.MRS.informacion.Escenario;
import java.io.File;
//import icaro.aplicaciones.MRS.informacion.Mapa;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoRecursoPersistenciaMRS extends ItfUsoRecursoSimple{
	public Escenario parseEscenario(File file) throws Exception;
	public Escenario getEscenario() throws Exception;
}
