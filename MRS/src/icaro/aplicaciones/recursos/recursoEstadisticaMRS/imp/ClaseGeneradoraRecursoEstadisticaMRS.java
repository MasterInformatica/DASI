package icaro.aplicaciones.recursos.recursoEstadisticaMRS.imp;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import icaro.aplicaciones.recursos.recursoEstadisticaMRS.ItfUsoRecursoEstadisticaMRS;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
/**
 * Clase Generadora del Recurso EstadisticaMRS
 * @author Hristo Ivanov
 */
public class ClaseGeneradoraRecursoEstadisticaMRS extends ImplRecursoSimple
		implements ItfUsoRecursoEstadisticaMRS {

	private static final long serialVersionUID = -3439155811399562462L;
	
	/**
	 * Hora del Incio del rescate
	 */
	private long inicioDeRescate;
	/**
	 * Hora del Fin del rescate
	 */
	private long finDeRescate;
	
	/**
	 * Numero de Rescatadores
	 */
	private int numeroDeRescatadores;
	/**
	 * Numero de Victimas
	 */
	private int numeroDeVictimas;
	/**
	 * Numero de Victimas Rescatadas
	 */
	private int numeroDeVictimasRescatadas;
	
	/**
	 * Cantidad de movimientos de cada rescatador
	 */
	private Map<String, Integer>movimientosPorRescatador;
	
	/**
	 * Tiempo Maximo que ha tardado un rescate
	 */
	private long tiempoMaximoDeRecate;
	/**
	 * Tiempo Minimo que ha tardado un rescate
	 */
	private long tiempoMinimoDeRecate;
	/**
	 * Tiempo Medio que ha tardado un rescate
	 */
	private float timepoMedioDeRecate;
	/**
	 * Tiempo Total que ha tardado todo el rescate
	 */
	private long tiempoDeRecateAcumulado;
	
	/**
	 * Numero de obstaculos descubiertos
	 */
	private int numeroDeObstaculosEncontrados;
	
	/**
	 * Constructora del recurso EstadisticasMRS
	 * @param idRecurso nombre del recurso
	 * @throws RemoteException
	 */
	public ClaseGeneradoraRecursoEstadisticaMRS(String idRecurso) throws RemoteException {
		super(idRecurso);
	}

	@Override
	public void iniciarRecate(
			int numeroDeRescatadores,
			int numeroDeVictimas,
			List<String> listaDeRecadatores
	) throws Exception {
		this.inicioDeRescate = System.currentTimeMillis();
		this.numeroDeRescatadores = numeroDeRescatadores;
		this.numeroDeVictimas = numeroDeVictimas;
		this.movimientosPorRescatador = new HashMap<String, Integer>();
		for(String name : listaDeRecadatores)
			this.movimientosPorRescatador.put(name, 0);
	}


	@Override
	public void finalizarRescate() throws Exception {
		this.finDeRescate = System.currentTimeMillis();}

	
	@Override
	public void notificarRescate() throws Exception {
		this.numeroDeVictimasRescatadas += 1;
		long tiempoDeRescateActual = System.currentTimeMillis() - this.inicioDeRescate;
		this.tiempoDeRecateAcumulado += tiempoDeRescateActual;
		this.timepoMedioDeRecate = this.tiempoDeRecateAcumulado / this.numeroDeVictimasRescatadas;
		
		this.tiempoMaximoDeRecate = tiempoDeRescateActual;
		if(this.tiempoMinimoDeRecate == 0)
			this.tiempoMinimoDeRecate = tiempoDeRescateActual;
	}


	@Override
	public void notificarObstaculo() throws Exception {
		this.numeroDeObstaculosEncontrados += 1;}


	@Override
	public void notificarMovimineto(String rescatador) throws Exception {
		int aux = 1 + this.movimientosPorRescatador.get(rescatador);
		this.movimientosPorRescatador.put(rescatador, aux);}
	
		
	@Override
	public void mostrarEstadisticas() throws Exception {
		@SuppressWarnings("unused")
		VisorEstadistica visor 
			= new VisorEstadistica(
					inicioDeRescate,
					finDeRescate,
					numeroDeRescatadores,
					numeroDeVictimas,
					numeroDeVictimasRescatadas,
					movimientosPorRescatador,
					tiempoMaximoDeRecate,
					tiempoMinimoDeRecate,
					timepoMedioDeRecate,
					numeroDeObstaculosEncontrados		
			);
	}
}