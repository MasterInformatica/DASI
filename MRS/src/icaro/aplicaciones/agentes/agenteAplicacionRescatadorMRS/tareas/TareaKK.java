package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.tareas;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.aplicaciones.MRS.informacion.*;

public class TareaKK extends TareaSincrona {

	/** 
	 * Tarea dummy para hacer pruebas
	 */
	
	@Override
	public void ejecutar(Object... params) {
		// Informar mediante trazas
		trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;

		trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente,
				"Ejecutar tarea CACA",
				InfoTraza.NivelTraza.info));

	}	
}
