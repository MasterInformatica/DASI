package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.tareas;


import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/** 
 * Encargada de:
 *   - Inicializar foco.
 *   - Inicializa componentes internos.
 */
public class InicializarRobot extends TareaSincrona {
	
	@Override
	public void ejecutar(Object... params) {
		Focus f = new Focus();
		MisObjetivos mo = new MisObjetivos();

		//Creamos el objetivo, y lo anyadimos y focalizamos.
		/*Objetivo o = new ConocerEquipo();
		f.setFoco(o);
		mo.addObjetivo(o);
		
		this.getEnvioHechos().insertarHechoWithoutFireRules(o);*/
		this.getEnvioHechos().insertarHechoWithoutFireRules(mo);
		this.getEnvioHechos().insertarHecho(f);
		
		
		// Informar mediante trazas
		trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;

		trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente,
				"Inicializadas las reglas. Insertado nuevo objetivo",
				InfoTraza.NivelTraza.info));
		
	}	
}
