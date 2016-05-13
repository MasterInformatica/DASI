package icaro.aplicaciones.agentes.agenteAplicacionMineroMRS.tareas;


import icaro.aplicaciones.MRS.informacion.*;
import icaro.aplicaciones.agentes.agenteAplicacionMineroMRS.objetivos.EsperarAuxilio;
import icaro.aplicaciones.agentes.agenteAplicacionMineroMRS.objetivos.PedirAyuda;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;


public class EnviarPeticionAyuda extends TareaSincrona {

	@Override
	public void ejecutar(Object... params) {
		/* params[0] -> nombre del agente
		 * params[1] -> el objeto de tipo ListaIds
		 * params[2] -> Objetivo actual
		 * params[3] -> foco
		 * params[4] -> yo
		 * params[5] -> MisObjetivos
		 */
		
		String name = (String) params[0];
		ListaIds lr = (ListaIds) params[1];
		Objetivo o = (Objetivo) params[2];
		Focus f = (Focus) params[3];
		Victima yo = (Victima) params[4];
		MisObjetivos mo = (MisObjetivos) params[5];
		
		comunicator = this.getComunicator();
		SolicitudAyuda msj = new SolicitudAyuda(yo);
		sendBroadcast(lr,msj);
		
		o.setSolved();
		this.getEnvioHechos().actualizarHechoWithoutFireRules(o);
		
		EsperarAuxilio o2 = new EsperarAuxilio();
		o2.setSolving();
		mo.addObjetivo(o2);
		this.getEnvioHechos().actualizarHechoWithoutFireRules(mo);
		this.getEnvioHechos().insertarHecho(o2);
		
		
		// Informar mediante trazas
		trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;

		trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente,
				"Envia Peticion de ayuda a los Robots",
				InfoTraza.NivelTraza.info));
		
	}
	
	private void sendBroadcast(ListaIds lr, Object msj){
		for(String name : lr.getNames()){
			comunicator.enviarInfoAotroAgente(msj,name);
		}
	
	}
	
}
