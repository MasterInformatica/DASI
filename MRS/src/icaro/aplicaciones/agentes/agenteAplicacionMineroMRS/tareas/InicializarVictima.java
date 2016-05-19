package icaro.aplicaciones.agentes.agenteAplicacionMineroMRS.tareas;


import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/** 
 * Tarea de Minero <br/>
 * Encargada de:
 *   - Inicializar foco
 *   - Inicializa componentes internos
 *   - Crear primer objetivo
 *   @author Luis Costero
 */
public class InicializarVictima extends TareaSincrona {


	
	@Override
	public void ejecutar(Object... params) {
		Focus f = new Focus();
		MisObjetivos mo = new MisObjetivos();

		//Creamos el objetivo, y lo anyadimos y focalizamos.
		/*Objetivo o = new ConocerEquipo();
		mo.addObjetivo(o);
		f.setFoco(o);
		Objetivo o = new PedirAyuda();
		f.setFoco(o);
		*/
		this.getEnvioHechos().insertarHechoWithoutFireRules(mo);
		this.getEnvioHechos().insertarHecho(f);
		
		
		// Informar mediante trazas
		trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;

		trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente,
				"Inicializadas las reglas. Insertado nuevo objetivo",
				InfoTraza.NivelTraza.info));
		
	}	
}
