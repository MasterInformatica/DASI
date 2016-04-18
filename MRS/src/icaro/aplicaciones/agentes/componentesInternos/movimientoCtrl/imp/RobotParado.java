/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.imp;

import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.ItfUsoMovimientoCtrl;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Informe;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author FGarijo
 */
public class RobotParado extends EstadoAbstractoMovRobot implements
		ItfUsoMovimientoCtrl {
	private Semaphore semaforo;

	public RobotParado(MaquinaEstadoMovimientoCtrl maquinaEstados) {

		super(maquinaEstados,
				MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot.RobotParado);
		semaforo = new Semaphore(1);
	}

	@Override
	public void inicializarInfoMovimiento(Coordinate coordInicial,
			float velocidadInicial) {
		this.robotposicionActual = coordInicial;
		this.maquinaEstados.setCoordenadasActuales(coordInicial);
		this.maquinaEstados.setVelocidadInicial(velocidadInicial);
	}

	@Override
	public void moverAdestino(String identDest, Coordinate coordDestino,
			float velocidadCrucero) {
		try {
			semaforo.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if (coordDestino != null) {
			if (identDest.equals(identDestino))
				this.trazas.trazar(this.identComponente,
						" No ha variado el destino en el que estoy : "
								+ robotposicionActual + "  Destino :"
								+ identDest, InfoTraza.NivelTraza.error);
			else {
				this.destinoCoord = coordDestino;
				this.identDestino = identDest;
				if (velocidadCrucero > 0) {
					this.velocidadCrucero = velocidadCrucero;
		
					this.robotposicionActual = this.maquinaEstados
							.getCoordenadasActuales();

					if (monitorizacionLlegadaDestino != null)
						monitorizacionLlegadaDestino.finalizar();
					trazas.trazar(identComponente,
							"Estoy parado en la posicion : "
									+ robotposicionActual
									+ "  Me muevo al destino  : "
									+ identDestino + " Coordenadas:  "
									+ destinoCoord, InfoTraza.NivelTraza.error);
					this.monitorizacionLlegadaDestino = new HebraMonitorizacionLlegada(
							this.identAgente, maquinaEstados,
							this.itfusoRecVisSimulador, this.itfusoRecVisMRS);
					
					monitorizacionLlegadaDestino.inicializarDestino(
							this.identDestino, robotposicionActual,
							this.destinoCoord, this.velocidadCrucero);
					// monitorizacionLlegadaDestino.start();
					monitorizacionLlegadaDestino.start();
					this.maquinaEstados
							.cambiarEstado(MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot.RobotEnMovimiento);
				} else
					trazas.trazar(
							identComponente,
							"La velocidad debe ser mayor que cero. Se ignora la operacion",
							InfoTraza.NivelTraza.error);

			}
		}
		this.semaforo.release();
	}

	@Override
	public synchronized boolean estamosEnDestino(String destinoId) {
		return (destinoId.equals(identDestino));
	}

	@Override
	public void cambiaVelocidad(float nuevaVelocidadCrucero) {
		this.velocidadCrucero = nuevaVelocidadCrucero;
	}

	@Override
	public void cambiaDestino(String identDest, Coordinate coordDestino) {
		this.destinoCoord = coordDestino;
		this.identDestino = identDest;
	}

	@Override
	public void parar() {
		trazas.trazar(identAgente,
				"Se recibe orden de parada cuando El robot esta en el estado :"
						+ identEstadoActual, InfoTraza.NivelTraza.debug);

	}

	@Override
	public void bloquear() {
		this.maquinaEstados
				.cambiarEstado(MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot.RobotBloqueado);
	}

	@Override
	public void continuar() {
		if (distanciaDestino > 0) {
			estadoActual = this.maquinaEstados
					.cambiarEstado(MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot.RobotEnMovimiento);
			this.maquinaEstados.moverAdestino(identDestino, destinoCoord,
					velocidadCrucero);
		}
		this.trazas.trazar(this.identAgente + "."
				+ this.getClass().getSimpleName(),
				" ignoro la operacion porque estoy parado ",
				InfoTraza.NivelTraza.debug);
	}

	@Override
	public Coordinate getCoordenadasActuales() {
		if (monitorizacionLlegadaDestino != null)
			return this.monitorizacionLlegadaDestino.getCoordRobot();
		else
			return robotposicionActual;
	}

	@Override
	public String getIdentEstadoMovRobot() {
		return MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot.RobotParado
				.name();
	}

	@Override
	public EstadoAbstractoMovRobot getEstadoActual() {
		return maquinaEstados.getEstadoActual();

	}

	@Override
	public boolean paradoEnDestino(String identDestino) {
		throw new UnsupportedOperationException("Not supported yet."); 
	}
}
