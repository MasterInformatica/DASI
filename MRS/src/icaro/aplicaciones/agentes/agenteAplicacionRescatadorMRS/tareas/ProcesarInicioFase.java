package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.tareas;

import java.util.concurrent.PriorityBlockingQueue;

import icaro.aplicaciones.MRS.informacion.InicioEstado;
import icaro.aplicaciones.MRS.informacion.Robot;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos.ConocerEquipo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

/**
 * Controlar los cambios de fase de la simulacion, [ST_Inicio, ST_Fin, ST_NuevoEscenario].
 */
public class ProcesarInicioFase extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		/* 
		 * params[0] -> nombre del agente
		 * params[1] -> yo
		 * params[2] -> fc
		 * params[3] -> MisObjetivos
		 * --------------------------------
		 * params[4] -> nueva Fase
		 */
		
		String name = (String) params[0];
		Robot yo = (Robot) params[1];
		Focus f = (Focus) params[2];
		MisObjetivos mo = (MisObjetivos) params[3];
		InicioEstado ie = (InicioEstado) params[4];
		
		comunicator = this.getComunicator();
				
		switch(ie.getEstadoIniciado()){
		case InicioEstado.ST_NuevoEscenario:
			
			PriorityBlockingQueue<Objetivo> pqO = mo.getMisObjetivosPriorizados();
			while(!pqO.isEmpty()){
				Objetivo oo = pqO.poll();
				this.getEnvioHechos().eliminarHechoWithoutFireRules(oo);
				mo.eliminarObjetivo(oo);
			}
			
			Objetivo o = new ConocerEquipo();
			mo.addObjetivo(o);
			f.setFoco(o);

			this.getEnvioHechos().insertarHechoWithoutFireRules(o);
			this.getEnvioHechos().actualizarHechoWithoutFireRules(mo);
			this.getEnvioHechos().actualizarHecho(f);
			break;
		case InicioEstado.ST_Inicio:
			// LUISMA JESUS Robots Cambio de Fase a Inicio 
		case InicioEstado.ST_Fin:
			// LUISMA JESUS Robots Cambio de Fase a Fin
		default:
			break;
		}
		this.getEnvioHechos().eliminarHecho(ie);
	}

}
