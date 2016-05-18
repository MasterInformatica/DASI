package icaro.aplicaciones.recursos.recursoEstadisticaMRS;

import java.util.List;

import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoRecursoEstadisticaMRS extends ItfUsoRecursoSimple{
	// Al empezar el recate.
	public void iniciarRecate(int numeroDeRescatadores, int numeroDeVictimas, List<String> listaDeRecadatore) throws Exception;
	// Al finalizar el rescate.
	public void finalizarRescate() throws Exception;
	// Al rescatar una victima.
	public void notificarRescate() throws Exception;
	// Al encontrarse con un obstaculo.
	public void notificarObstaculo() throws Exception;
	// Al moverse.
	public void notificarMovimineto(String rescatador) throws Exception;
	
	// Al finalizar el rescate, a fin de mostrar las estadisticas.
	public void mostrarEstadisticas() throws Exception;
}
