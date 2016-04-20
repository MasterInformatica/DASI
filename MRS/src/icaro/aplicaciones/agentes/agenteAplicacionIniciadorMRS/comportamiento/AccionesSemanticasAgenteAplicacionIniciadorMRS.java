package icaro.aplicaciones.agentes.agenteAplicacionIniciadorMRS.comportamiento;


import icaro.aplicaciones.MRS.informacion.Mapa;
import icaro.aplicaciones.recursos.recursoPlanificadorRuta.ItfUsoRecursoPlanificadorRuta;
import icaro.aplicaciones.recursos.recursoVisualizadorMRS.ItfUsoRecursoVisualizadorMRS;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;

//Other imports used by this Agent
//#start_nodespecialImports:specialImports <--specialImports-- DO NOT REMOVE THIS
//#end_nodespecialImports:specialImports <--specialImports-- DO NOT REMOVE THIS

public class AccionesSemanticasAgenteAplicacionIniciadorMRS
		extends AccionesSemanticasAgenteReactivo {

	private ItfUsoConfiguracion           itfconfig;
	private ItfUsoRecursoVisualizadorMRS  itfVisualizadorMRS;
	private ItfUsoRecursoPlanificadorRuta itfPlanificadorRuta;
	
	/*@ */
	private Mapa mapa;
	/*@ */
	
	
	
	//--------------------------------------------------------------------------
	public void AccionComenzar(){
		try{
			this.itfconfig = (ItfUsoConfiguracion) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.NOMBRE_ITF_USO_CONFIGURACION);
			
			this.itfVisualizadorMRS = (ItfUsoRecursoVisualizadorMRS) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_USO + "RecursoVisualizadorMRS1");
			this.itfVisualizadorMRS.setIdentAgenteAReportar(this.nombreAgente);
			
			this.itfPlanificadorRuta = (ItfUsoRecursoPlanificadorRuta) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_USO + "RecursoPlanificadorRuta1");
			
			
			
			//1 - mostrarVisorControl
			//2 - dame fichero
			//3 - (si error, avisar).
		} catch(Exception e){
			e.printStackTrace();
		}
		
		trazas.trazar(this.getNombreAgente(),
					  "Accion AccionCOmenzar completada", NivelTraza.debug);
	}
	
	
	
	/* --- METODOS AUXILIARES --- */
	@Override
	public void clasificaError() {
		// TODO Auto-generated method stub
	}


}
