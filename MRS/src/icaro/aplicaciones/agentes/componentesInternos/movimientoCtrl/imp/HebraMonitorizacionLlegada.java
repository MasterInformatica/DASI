package icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.imp;

import icaro.aplicaciones.MRS.informacion.Coordenada;
import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.recursos.recursoPlanificadorRuta.ItfUsoRecursoPlanificadorRuta;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.ItfUsoRecursoVisualizadorEntornosSimulacion;
import icaro.aplicaciones.recursos.recursoVisualizadorMRS.ItfUsoRecursoVisualizadorMRS;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Informe;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ItfProcesadorObjetivos;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.openide.util.Exceptions;

/**
 * Clase interna que se encarga de generar eventos de monitorizacin cada cierto
 * tiempo
 * 
 * 
 */
public class HebraMonitorizacionLlegada extends Thread {
	/**
	 * Milisegundos que esperar antes de lanzar otra monitorizacin
	 * 
	 * @uml.property name="milis"
	 */
	protected long milis;

	/**
	 * Indica cundo debe dejar de monitorizar
	 * 
	 * @uml.property name="finalizar"
	 */
	protected volatile boolean finalizar = false;

	/**
	 * Agente reactivo al que se pasan los eventos de monitorizacin
	 * 
	 * @uml.property name="agente"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	public MaquinaEstadoMovimientoCtrl controladorMovimiento;
	private Logger log = Logger.getLogger(this.getClass().getSimpleName());
	/**
	 * Evento a producir
	 * 
	 * @uml.property name="evento"
	 */
	private String identDestino, identRobot;
	private volatile Coordinate coordActuales;
	private volatile Coordinate coordDestino;
	
	private double velocidadRobot; // en metros por segundo
	
	private volatile double pendienteRecta;

	private boolean pendienteInfinita = false;

	private volatile boolean enDestino = false;
	private int dirX = 0, dirY = 0;
	private int intervaloEnvioInformesMs;
	private int distanciaRecorridaEnIntervaloInformes;
	private long tiempoParaAlcanzarDestino = 3000;
	public ItfUsoRecursoVisualizadorEntornosSimulacion itfusoRecVisSimulador;
	public ItfUsoRecursoVisualizadorMRS                itfusoRecVisMRS;
	public ItfProcesadorObjetivos itfProcObjetivos;
	public ItfUsoRecursoPlanificadorRuta itfusoRecPlanRuta;


	/**
	 * Constructor
	 * @param itfUsoRecVisMRS 
	 * 
	 * @param milis
	 *            Milisegundos a esperar entre cada monitorizacion
	 */
	public HebraMonitorizacionLlegada(String idRobot,
			MaquinaEstadoMovimientoCtrl contrMovimiento,
			ItfUsoRecursoVisualizadorEntornosSimulacion itfRecVisSimulador, 
			ItfUsoRecursoVisualizadorMRS itfRecVisMRS,
			ItfUsoRecursoPlanificadorRuta itfRecPlanRuta) {
		
		super("HebraMonitorizacion " + idRobot);
		controladorMovimiento = contrMovimiento;
		this.itfusoRecVisSimulador = itfRecVisSimulador;
		this.itfusoRecVisMRS = itfRecVisMRS;
		this.itfusoRecPlanRuta = itfRecPlanRuta;
		
		identRobot = idRobot;
		itfProcObjetivos = contrMovimiento.itfProcObjetivos;
	}
	

	public synchronized void inicializarDestino(String idDestino,
			Coordinate coordRobot, Coordinate coordDest, double velocidad) {
	
		coordActuales = coordRobot;
		coordDestino = coordDest;
		velocidadRobot = velocidad;
		identDestino = idDestino;

		log.debug("Coord Robot " + identRobot + " iniciales -> ("
				+ this.coordActuales.getX() + " , " + this.coordActuales.getY()
				+ ")");
		log.debug("Coord Robot " + identRobot + " destino -> ("
				+ this.coordDestino.getX() + " , " + this.coordDestino.getY()
				+ ")");
		this.setDaemon(true);

		
		this.finalizar = false;
		this.enDestino = false;

		double incrX = (coordDestino.getX() - coordActuales.getX());
		double incrY = (coordDestino.getY() - coordActuales.getY());
	
		dirX = (incrX > 0) ? 1 : -1;
		dirY = (incrY > 0) ? 1 : -1;

		if (incrX == 0 && incrY != 0) {
			pendienteInfinita = true;
		} else if (incrX == 0 && incrY == 0)
			finalizar = true;
		else {
			pendienteRecta = (float) Math.abs(incrY / incrX);

		}
		
		intervaloEnvioInformesMs = (int) velocidadRobot * 50;
		distanciaRecorridaEnIntervaloInformes = 1;

		
		this.setCoordDestino(coordDestino);

	}

	/**
	 * Termina la monitorizacin
	 */
	public synchronized void finalizar() {
		this.finalizar = true;
		this.notifyAll();

	}

	
	public synchronized void setCoordRobot(Coordinate robotCoord) {
		this.coordActuales = robotCoord;
	}

	public synchronized Coordinate getCoordRobot() {
		return coordActuales;
	}

	
	public synchronized void setCoordDestino(Coordinate destCoord) {
		try {
			
			
			this.coordDestino = destCoord;
			if (itfusoRecVisSimulador != null){
				this.itfusoRecVisSimulador.inicializarDestinoRobot(identRobot,
						coordActuales, identDestino, coordDestino,
						velocidadRobot);
			}
			
			this.itfusoRecVisMRS.inicializarDestinoRobot(identRobot,
					coordActuales, identDestino, coordDestino,
					velocidadRobot);

		} catch (Exception ex) {
			Exceptions.printStackTrace(ex);
		}
	}

	
	public synchronized Coordinate getCoordDestino() {
		return coordDestino;
	}

	public synchronized void setVelocidadRobot(double velRobot) {
		this.velocidadRobot = velRobot;
	}

	

	@Override
	public synchronized void run() {

		log.debug("Coord Robot " + identRobot + " iniciales -> ("
				+ this.coordActuales.getX() + " , " + this.coordActuales.getY()
				+ ")");
		log.debug("Coord Robot " + identRobot + " destino -> ("
				+ this.coordDestino.getX() + " , " + this.coordDestino.getY()
				+ ")");
		

		log.debug("Inicio ciclo de envio de coordenadas  " + identRobot
				+ " en destino -> (" + this.coordActuales.getX() + " , "
				+ this.coordActuales.getY() + ")");
		
		
		while (!this.finalizar && (!enDestino)) {
			try {
				/* Thread.sleep(intervaloEnvioInformesMs); 
				 calcularNuevasCoordenadas(distanciaRecorridaEnIntervaloInformes);
				 
				enDestino = ((coordActuales.getX() - coordDestino.getX())
						* dirX >= 0 && (coordActuales.getY() - coordDestino
						.getY()) * dirY >= 0);
				finalizar = (coordActuales.x < 0.5 || coordActuales.y < 0.5);
				
				if (itfusoRecVisSimulador != null)
					this.controladorMovimiento.setCoordenadasActuales(coordActuales);
								
				//this.itfusoRecVisSimulador.mostrarPosicionRobot(identRobot,	coordActuales);
				this.itfusoRecVisMRS.mueveAgente(identDestino, coordActuales);
				/*/
				
				Thread.sleep((long) (2.0*1e3));
				
				calcularNuevasCoordenadasDiscretas();
				enDestino = (coordActuales.getX() == coordDestino.getX()) &&
						    (coordActuales.getY() == coordDestino.getY());
				finalizar = enDestino;
				
				this.controladorMovimiento.setCoordenadasActuales(coordActuales);
				this.itfusoRecVisMRS.mueveAgente(identDestino, coordActuales);
				this.itfusoRecVisSimulador.mostrarPosicionRobot(identRobot, coordActuales);
				//*/
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		if (enDestino) {
			finalizar = true;
			try {
				Thread.sleep(tiempoParaAlcanzarDestino);
				this.controladorMovimiento.estamosEnDestino(identDestino,
						coordDestino);
				
				log.debug("Coord Robot En thread  " + identRobot
						+ " en destino -> (" + this.coordActuales.getX()
						+ " , " + this.coordActuales.getY() + ")");

				this.controladorMovimiento.setCoordenadasActuales(coordDestino);


				Informe informeLlegada = new Informe(this.identRobot,
						this.identDestino, VocabularioRosace.MsgeLlegadaDestino);

				itfProcObjetivos.insertarHecho(informeLlegada);
				this.notifyAll();
			} catch (Exception ex) {
				log.error(ex);
			}
		}
	}
	
	

	private void calcularNuevasCoordenadas(long incrementoDistancia) {
		if (pendienteInfinita) {

			this.coordActuales.setY(coordActuales.getY() + incrementoDistancia
					* dirY);
		} else {
			// incremmento de x respecto a distancia recorrida
			this.coordActuales.setY(coordActuales.getY() + pendienteRecta
					* incrementoDistancia * dirY);
			this.coordActuales.setX(coordActuales.getX() + incrementoDistancia
					* dirX);
		
		}
	}
	
	private void calcularNuevasCoordenadasDiscretas() {
		ArrayList<Coordenada> ruta = null;
		Coordenada c1 = new Coordenada(coordActuales);
		Coordenada c2 = new Coordenada(coordDestino);
		
		
		try {
			
			System.out.println("====================================\n" + c1);
			System.out.println("====================================\n" + c2);
			ruta = itfusoRecPlanRuta.getRuta(c1, c2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("---->" + ruta);
//		this.coordActuales.setX(this.coordActuales.getX() +1);
		this.coordActuales.setX(ruta.get(1).getX());
		this.coordActuales.setY(ruta.get(1).getY());
		
	
	}
	


}
