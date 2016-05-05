package icaro.aplicaciones.agentes.agenteAplicacionMineroMRS.tareas;


import icaro.aplicaciones.agentes.agenteAplicacionMineroMRS.objetivos.PedirAyuda;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;


public class InicializarVictima extends TareaSincrona {

	/** 
	 * Encargada de:
	 *   - Inicializar foco
	 *   - Inicializa componentes internos (??)
	 *   - Crear primer objetivo
	 */
	
	@Override
	public void ejecutar(Object... params) {
		Focus f = new Focus();
		//MisObjetivos mo = new MisObjetivos();

		//Creamos el objetivo, y lo anyadimos y focalizamos.
		Objetivo o = new PedirAyuda();
		f.setFoco(o);
		
		this.getEnvioHechos().insertarHechoWithoutFireRules(o);
		this.getEnvioHechos().insertarHecho(f);
		
		
		// Informar mediante trazas
		trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;

		trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente,
				"Inicializadas las reglas. Insertado nuevo objetivo",
				InfoTraza.NivelTraza.info));
		
	}	
}
