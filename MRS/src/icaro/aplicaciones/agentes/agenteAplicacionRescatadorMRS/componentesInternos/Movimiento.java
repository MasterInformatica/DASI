package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.componentesInternos;

import java.util.ArrayList;

import icaro.aplicaciones.MRS.informacion.Coordenada;
import icaro.aplicaciones.MRS.informacion.Rescatador;
import icaro.aplicaciones.MRS.informacion.RobotStatus;
import icaro.aplicaciones.recursos.recursoEstadisticaMRS.ItfUsoRecursoEstadisticaMRS;
import icaro.aplicaciones.recursos.recursoPersistenciaMRS.ItfUsoRecursoPersistenciaMRS;
import icaro.aplicaciones.recursos.recursoPlanificadorMRS.ItfUsoRecursoPlanificadorMRS;
import icaro.aplicaciones.recursos.recursoVisualizadorMRS.ItfUsoRecursoVisualizadorMRS;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ItfProcesadorObjetivos;

/**
 * Componente interno de movimiento del agente rescatados
 * @author Hristo Ivanov
 * @author Luis Maria Costero Valero
 */
public class Movimiento extends Thread{
	
	/**
	 * Coordena destino que el agente debe alcanzar.
	 */
	public Coordenada destino;
	
	/**
	 * Referencia a si mismo, al objeto que representa el rescatador.
	 */
	public Rescatador yo;
	
	/**
	 * Valor booleano que indica si hemos o no alcanado el objetivo.
	 */
	public boolean alcanzado;
	
	/**
	 * Identificador del agente minero que estamos guiando hacia la salida.
	 * Al alcanzar el minero objetivo, el robot se dispone a llevar a este hacia
	 * la salida. Este identificador es utilizado para mover al agente minero.
	 */
	public String mineroMvto = null;
	
	/**
	 * Referencia al recurso de planificación de rutas.
	 */
	public ItfUsoRecursoPlanificadorMRS itfusoRecPlanRuta;
	
	/**
	 * Referencia al recurso de visualizacion.
	 */
	public ItfUsoRecursoVisualizadorMRS itfusoRecVisualizador;
	
	/**
	 * Referencia al recurso de persistencia.
	 */
	public ItfUsoRecursoPersistenciaMRS itfusoRecPersistencia;
	
	/**
	 * Referencia al recurso de estadistica.
	 */
	public ItfUsoRecursoEstadisticaMRS  itfusoRecEstadistica;
	
	/**
	 * Referencia a la base de conocimiento del agente. 
	 */
	public ItfProcesadorObjetivos itfHechos;
	
	/**
	 * Constructora del componente interno Movimiento
	 * @param yo Referencia a si mismo, al objeto que representa el rescatador.
	 * @param itfusoRecPlanRuta Referencia al recurso de planificación de rutas.
	 * @param itfusoRecVisualizador Referencia al recurso de visualizacion.
	 * @param itfusoRecPersistencia Referencia al recurso de persistencia.
	 * @param itfusoRecEstadistica Referencia al recurso de estadistica.
	 */
	public Movimiento(	Rescatador yo,
						ItfUsoRecursoPlanificadorMRS itfusoRecPlanRuta,
						ItfUsoRecursoVisualizadorMRS itfusoRecVisualizador,
						ItfUsoRecursoPersistenciaMRS itfusoRecPersistencia,
						ItfUsoRecursoEstadisticaMRS  itfusoRecEstadistica
	){
		this. yo = yo;
		this.itfusoRecPlanRuta = itfusoRecPlanRuta;
		this.itfusoRecVisualizador = itfusoRecVisualizador;
		this.itfusoRecPersistencia = itfusoRecPersistencia;
		this.itfusoRecEstadistica = itfusoRecEstadistica;
		
		this.alcanzado = true;
	}
	
	@Override
	public synchronized void run() {
		while(true){
			try{ Thread.sleep((long) (0.2*1e3));
			}catch (InterruptedException e) {e.printStackTrace();}
			
			if (!alcanzado){//destino != null){
				if ((destino.getX() != yo.getCoordenadasActuales().getX()) ||
						(destino.getY() != yo.getCoordenadasActuales().getY())){
					
					ArrayList <Coordenada> ruta = null;
					try{
						ruta = itfusoRecPlanRuta.getRuta(yo.getCoordenadasActuales(), destino);
					}catch (Exception e){ e.printStackTrace();}
					
					Coordenada siguientePaso = null;
					
					try{
						siguientePaso = ruta.get(1);
					}catch(Exception e){
						System.err.println("VÃ­ctima Inalcanzable: "+mineroMvto);
						
					}
					
					if(siguientePaso != null){
						yo.move(siguientePaso);
						try {
							itfusoRecVisualizador.mueveRobot(yo.getName(), yo.getCoordenadasActuales());
							if(mineroMvto != null)
								itfusoRecVisualizador.mueveVictima(mineroMvto, yo.getCoordenadasActuales());
						}catch(Exception e){e.printStackTrace();}
						
						// Notificar al recurso de estadistica del movimiento.
						try {
							this.itfusoRecEstadistica.notificarMovimineto(this.yo.getName());
						}catch(Exception e){e.printStackTrace();}
					}
					
					this.checkBloqueos();
					
					if ((destino.getX() == yo.getCoordenadasActuales().getX()) &&
							(destino.getY() == yo.getCoordenadasActuales().getY()))
						alcanzarDestino();
				}else{
					alcanzarDestino();
				}
			}
		}
	}
	
	/**
	 * Fija el objeto al que el robot debe dirigirse.
	 * Fijado el nuevo destino el agente se pone en movimiento automaticamente.
	 * @param destino La coordenada de destino.
	 */
	public void setDestino(Coordenada destino){
		int st = this.yo.getStatus();
		if(st == RobotStatus.PARADO){
			this.yo.SetStatus(RobotStatus.HACIA_MINERO);
			mineroMvto = null;
		}else if(st==RobotStatus.CON_MINERO)
			this.yo.SetStatus(RobotStatus.HACIA_SALIDA);
			
		this.destino = destino;
		this.alcanzado = false;
	}
	
	/**
	 * Fija el agente minero que estamos guiando hacia la salida.
	 * @param m Identificador del agente minero que estamos guiando hacia la salida.
	 */
	public void setMinero(String m){
		this.mineroMvto = m;
	}
	
	/**
	 * Funcion que se invoca al alcanzar el destino.
	 */
	private void alcanzarDestino(){
		this.alcanzado = true;
		
		
		int st = this.yo.getStatus();
		if(st == RobotStatus.HACIA_MINERO){
			this.yo.SetStatus(RobotStatus.CON_MINERO);
		}
		else if(st==RobotStatus.HACIA_SALIDA){
			mineroMvto = null;
			this.yo.SetStatus(RobotStatus.PARADO);
		}
		
		this.itfHechos.actualizarHecho(this.yo);
	}
	
	/**
	 * El agente rescatador comprueba la presencia de derrumbes.
	 */
	private void checkBloqueos(){
		Coordenada c = new Coordenada(yo.getCoordenadasActuales());
		ArrayList<Coordenada> bloqueos = null;
		try {
			bloqueos = this.itfusoRecPersistencia.getEscenario().getMapa().getObstaculos(c);
		}catch(Exception e1){e1.printStackTrace();}
		

		if(bloqueos != null && bloqueos.size()>0){
			for(Coordenada cc : bloqueos){
				try {
					if(this.itfusoRecPlanRuta.informarBloqueo(cc))
						this.itfusoRecEstadistica.notificarObstaculo();
					
					this.itfusoRecVisualizador.informarBloqueo(cc);
				}catch(Exception e){e.printStackTrace();}
			}
		}
	}

	/**
	 * Fija la referencia a la base de conocimiento del agente.
	 * @param envioHechos Referencia a la base de conocimiento del agente.
	 */
	public void setItHechos(ItfProcesadorObjetivos envioHechos) {
		this.itfHechos = envioHechos;
	}
}
