/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.imp;

import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.ItfUsoMovimientoCtrl;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 * 
 * @author FGarijo
 */

public class RobotEnMovimiento extends EstadoAbstractoMovRobot implements
		ItfUsoMovimientoCtrl {

	public RobotEnMovimiento(MaquinaEstadoMovimientoCtrl maquinaEstados) {
		super(
				maquinaEstados,
				MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot.RobotEnMovimiento);

	}

	@Override
	public synchronized boolean estamosEnDestino(String destinoId) {
		return false;
	}

	@Override
	public synchronized void moverAdestino(String identdest, Coordinate coordDestino, float velocidadCrucero) {
		if (identdest.equals(identDestino)) {
			this.trazas.trazar(this.identComponente,
					" Se esta avanzando hacia el destino ",
					InfoTraza.NivelTraza.debug);
			if (velocidadCrucero <= 0)
				trazas.trazar(
						identComponente,
						"La velocidad debe ser mayor que cero. Se ignora la operacion",
						InfoTraza.NivelTraza.error);
			else
				this.velocidadCrucero = velocidadCrucero;
		} else { // cambair destino
			maquinaEstados.cambiaDestino(identdest, coordDestino);
		}
	}

	@Override
	public void cambiaVelocidad(float nuevaVelocidadCrucero) {
		this.velocidadCrucero = nuevaVelocidadCrucero;
	}

	@Override
	public synchronized void cambiaDestino(String identdest,
			Coordinate coordDestino) {

		if (identdest.equals(identDestino))
			this.trazas.trazar(this.identComponente,
					" Se esta avanzando hacia el destino ",
					InfoTraza.NivelTraza.debug);
		else
			maquinaEstados.cambiaDestino(identdest, coordDestino);
	}

	@Override
	public void parar() {
		this.maquinaEstados.parar();
		this.maquinaEstados
				.cambiarEstado(MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot.RobotParado);
	}

	@Override
	public void continuar() {
		this.trazas.trazar(this.identComponente,
				" ignoro la operacion porque estoy parado ",
				InfoTraza.NivelTraza.debug);
	}

	@Override
	public void bloquear() {
		if (monitorizacionLlegadaDestino != null)
			this.monitorizacionLlegadaDestino.finalizar();
		this.maquinaEstados
				.cambiarEstado(
						MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot.RobotBloqueado)
				.parar();
	}

	@Override
	public Coordinate getCoordenadasActuales() {
		return this.monitorizacionLlegadaDestino.getCoordRobot();
	}

	@Override
	public String getIdentEstadoMovRobot() {
		return MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot.RobotEnMovimiento
				.name();
	}

	@Override
	public boolean paradoEnDestino(String identDestino) {
		throw new UnsupportedOperationException("Not supported yet."); 
	}
}
