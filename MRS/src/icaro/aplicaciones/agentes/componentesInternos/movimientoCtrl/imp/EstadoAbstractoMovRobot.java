/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.imp;

import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.ItfUsoMovimientoCtrl;
import icaro.aplicaciones.recursos.recursoPersistenciaMRS.ItfUsoRecursoPersistenciaMRS;
import icaro.aplicaciones.recursos.recursoPlanificadorRuta.ItfUsoRecursoPlanificadorRuta;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.ItfUsoRecursoVisualizadorEntornosSimulacion;
import icaro.aplicaciones.recursos.recursoVisualizadorMRS.ItfUsoRecursoVisualizadorMRS;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Informe;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ItfProcesadorObjetivos;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 * 
 * @author FGarijo
 */
public abstract class EstadoAbstractoMovRobot implements ItfUsoMovimientoCtrl {

	public static enum EstadoMovimientoRobot {
		Indefinido, RobotParado, RobotEnMovimiento, RobotBloqueado, RobotavanceImposible, enDestino, error
	}

	// Nombres de las clases que implementan estados del recurso interno
	public static enum EvalEnergiaRobot {
		sinEnergia, energiaSuficiente, EnergiaJusta, EnergiaInsuficiente
	}

	public EstadoAbstractoMovRobot estadoActual;
	public MaquinaEstadoMovimientoCtrl maquinaEstados;
	public String identAgente;
	public volatile Coordinate robotposicionActual;
	public volatile Coordinate destinoCoord;
	public double distanciaDestino;
	protected float velocidadCrucero;
	public ItfProcesadorObjetivos itfProcObjetivos;
	protected HebraMonitorizacionLlegada monitorizacionLlegadaDestino;
	public ItfUsoRecursoTrazas trazas;
	public String identComponente;
	public String identEstadoActual;
	public String identDestino;
	public ItfUsoRecursoVisualizadorEntornosSimulacion itfusoRecVisSimulador;
	public ItfUsoRecursoVisualizadorMRS itfusoRecVisMRS;
	public ItfUsoRecursoPlanificadorRuta itfusoRecPlanRuta;
	public ItfUsoRecursoPersistenciaMRS itfusoRecPersistencia;

	public EstadoAbstractoMovRobot(MaquinaEstadoMovimientoCtrl maquinaEstds,
			MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot identEstadoAcrear) {
		if (maquinaEstds == null) {
			estadoActual = null;
			trazas.trazar(this.getClass().getSimpleName(),
					" Error al crear el estado  " + identEstadoAcrear
							+ " La maquinaEstds es null   ",
					InfoTraza.NivelTraza.error);
		} else {
			maquinaEstados = maquinaEstds;
			// estadoActual = this;
			identEstadoActual = identEstadoAcrear.name();
		}
	}

	public void inicializar(ItfProcesadorObjetivos itfProcObj) {
		identAgente = itfProcObj.getAgentId();
		itfProcObjetivos = itfProcObj;
		identComponente = identAgente + "." + this.getClass().getSimpleName();
		trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;

	}

	public void inicializar(ItfProcesadorObjetivos itfProcObj,
			ItfUsoRecursoVisualizadorEntornosSimulacion itfVisSimul,
			ItfUsoRecursoVisualizadorMRS itfVisualMRS,
			ItfUsoRecursoPlanificadorRuta itfPlanRuta,
			ItfUsoRecursoPersistenciaMRS itfPersistencia) {
		identAgente = itfProcObj.getAgentId();
		itfProcObjetivos = itfProcObj;
		identComponente = identAgente + "." + this.getClass().getSimpleName();
		trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
		itfusoRecVisSimulador = itfVisSimul;
		itfusoRecVisMRS = itfVisualMRS;
		itfusoRecPlanRuta = itfPlanRuta;
		itfusoRecPersistencia = itfPersistencia;
		
		maquinaEstados.inicializar(itfProcObj, itfVisSimul, itfVisualMRS,itfPlanRuta, itfPersistencia);

	}



	public void inicializarInfoMovimiento(Coordinate coordInicial,
			float velocidadInicial) {

		maquinaEstados
				.inicializarInfoMovimiento(coordInicial, velocidadInicial);
	}

	public void moverAdestino(String identDest, Coordinate coordDestino,
			float velocidadCrucero) {
		estadoActual.moverAdestino(identDest, coordDestino, velocidadCrucero);
		this.identDestino = identDest;
	}

	
	public abstract void cambiaVelocidad(float nuevaVelocidadCrucero);

	
	public synchronized void cambiaDestino(String identDest,
			Coordinate coordDestino) {
		
		maquinaEstados.getEstadoActual().cambiaDestino(identDest, coordDestino);
		this.identDestino = identDest;
	}

	public synchronized void parar() {
		maquinaEstados.getEstadoActual().parar();
	}

	public void continuar() {
		maquinaEstados.getEstadoActual().continuar();
	}

	public abstract boolean estamosEnDestino(String identDest);


	public synchronized void imposibleAvanzarADestino() {
		maquinaEstados
				.cambiarEstado(MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot.RobotBloqueado);
	}

	public synchronized Coordinate getCoordenadasActuales() {
		return robotposicionActual = maquinaEstados.getCoordenadasActuales();
	}

	public synchronized void setCoordenadasActuales(Coordinate nuevasCoordenadas) {
		if (nuevasCoordenadas != null) {
			robotposicionActual.x = nuevasCoordenadas.x;
			robotposicionActual.y = nuevasCoordenadas.y;
			robotposicionActual.z = nuevasCoordenadas.z;
			maquinaEstados.setCoordenadasActuales(nuevasCoordenadas);
		}

	}

	public EstadoAbstractoMovRobot getEstadoActual() {

		return maquinaEstados.getEstadoActual();
	}

	@Override
	public String getIdentEstadoMovRobot() {

		return maquinaEstados.getIdentEstadoMovRobot();
	}
}
