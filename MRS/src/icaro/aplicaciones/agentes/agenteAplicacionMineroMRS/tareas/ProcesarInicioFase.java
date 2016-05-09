package icaro.aplicaciones.agentes.agenteAplicacionMineroMRS.tareas;

import java.util.concurrent.PriorityBlockingQueue;

import icaro.aplicaciones.MRS.informacion.InicioEstado;
import icaro.aplicaciones.MRS.informacion.Victima;
import icaro.aplicaciones.agentes.agenteAplicacionMineroMRS.objetivos.PedirAyuda;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

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
		Victima yo = (Victima) params[1];
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
			f.setFoco(null);
			this.getEnvioHechos().actualizarHechoWithoutFireRules(mo);
			this.getEnvioHechos().actualizarHechoWithoutFireRules(f);
			break;
		case InicioEstado.ST_Inicio:
			Objetivo o = new PedirAyuda();
			mo.addObjetivo(o);
			f.setFoco(o);

			this.getEnvioHechos().insertarHechoWithoutFireRules(o);
			this.getEnvioHechos().actualizarHechoWithoutFireRules(mo);
			this.getEnvioHechos().actualizarHechoWithoutFireRules(f);
		case InicioEstado.ST_Fin:
			// LUISMA JESUS Victimas Cambio de Fase a Fin
		default:
			break;
		}
		this.getEnvioHechos().eliminarHecho(ie);
	}

}
