package icaro.aplicaciones.recursos.recursoEstadisticaMRS.imp;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JRootPane;

import icaro.aplicaciones.recursos.recursoEstadisticaMRS.ItUsoRecursoEstadisticaMRS;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;

public class ClaseGeneradoraRecursoEstadisticaMRS extends ImplRecursoSimple
		implements ItUsoRecursoEstadisticaMRS {

	private static final long serialVersionUID = -3439155811399562462L;
	
	private long inicioDeRescate;//
	private long finDeRescate;//
	
	private int numeroDeRescatadores;//
	private int numeroDeVictimas;//
	private int numeroDeVictimasRescatadas;//
	
	private Map<String, Integer>movimientosPorRescatador;//
	
	private long tiempoMaximoDeRecate;//
	private long tiempoMinimoDeRecate;//
	private float timepoMedioDeRecate;//
	private long tiempoDeRecateAcumulado;
	
	private int numeroDeObstaculosEncontrados;//
	
	public ClaseGeneradoraRecursoEstadisticaMRS(String idRecurso) throws RemoteException {
		super(idRecurso);
	
		// TODO remove Hack
		try {
			ArrayList aux = new ArrayList<String>();
			aux.add("Rescatador1"); aux.add("Rescatador2"); aux.add("Rescatador3");
			this.iniciarRecate(3, 3, aux);
			this.finalizarRescate();
			
			this.mostrarEstadisticas();
		}catch(Exception e){e.printStackTrace();}
		// TODO remove Hack
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
	public void nitificarObstaculo() throws Exception {
		this.numeroDeObstaculosEncontrados += 1;}


	@Override
	public void notificarMovimineto(String rescatador) throws Exception {
		int aux = 1 + this.movimientosPorRescatador.get(rescatador);
		this.movimientosPorRescatador.put(rescatador, aux);}
	
		
	@Override
	public void mostrarEstadisticas() throws Exception {
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