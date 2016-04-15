package icaro.aplicaciones.recursos.recursoVisualizadorMRS;

import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.EscenarioSimulacionRobtsVictms;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoRecursoVisualizadorMRS extends ItfUsoRecursoSimple{
	public void obtenerEscenarioSimulacion() throws Exception;

	public void mostrarEscenarioMovimiento() throws Exception;
}
