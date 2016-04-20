package icaro.aplicaciones.agentes.agenteAplicacionIniciadorMRS.comportamiento;


import java.io.File;
import java.util.Random;

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
	
	private File ficheroEscenario;
	private Mapa mapa;

	
	
	
	//--------------------------------------------------------------------------
	// Estado inicial 
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
			
			
			this.itfVisualizadorMRS.muestraVentanaControl();

		} catch(Exception e){
			e.printStackTrace();
		}
				
		trazas.trazar(this.getNombreAgente(),
					  "Accion AccionComenzar completada", NivelTraza.debug);
				
		
		//Ejecutamos la accion de preguntar fichero en el siguiente estado
		this.informaraMiAutomata("leerFicheroTimeOut", null);
	}
	
	
	//--------------------------------------------------------------------------
	// Estado esperandoEscenario 
	//--------------------------------------------------------------------------

	public void getFicheroTimeOut(){
		int tEspera = 1000; //milisegundos
		
		this.ficheroEscenario = this.itfVisualizadorMRS.getFicheroEscenario();
		
		if(ficheroEscenario==null){
			trazas.trazar(this.getNombreAgente(),  "Fichero leido null", NivelTraza.debug);
			this.generarTimeOutInterno(tEspera, "leerFicheroTimeOut", this.getNombreAgente(),
			     					   this.itfUsoPropiadeEsteAgente);
		}else{
			trazas.trazar(this.getNombreAgente(),  "Fichero leido. Procediendo a validar", NivelTraza.debug);
			this.informaraMiAutomata("validaFichero", null);
		}
	}

	public void checkFile(){
		//LUISMA: Aquí llamar a la interfaz de persistencia para parsear el fichero
		//this.escenario = this.ItfPersistenciaMRS.parseFile(this.ficheroEscenario)
		this.mapa = new Mapa();
		
		Random randomGenerator = new Random();
		      
		if( randomGenerator.nextInt(2) == 0){
			this.itfVisualizadorMRS.informaErrorEscenario("El escenario elegido no es válido");
			trazas.trazar(this.getNombreAgente(),  "Validación incorrecta", NivelTraza.debug);
			
			this.informaraMiAutomata("leerFicheroTimeOut", null);
		} else {
			trazas.trazar(this.getNombreAgente(),  "Validación correcta", NivelTraza.debug);
			
			this.informaraMiAutomata("activaEsperaSimulacion", null);
			
		}
		
	}
	
	
	//Este metodo es el encargado de cambiar al siguiente estado
	public void generarSimulacion(){
		this.itfVisualizadorMRS.escenarioElegidoValido();
		
		//Recogeriamos el mpaa del escenario, configurar robots, agentes, ...
		try {
			this.itfVisualizadorMRS.mostrarEscenarioMovimiento(this.mapa);
			this.itfPlanificadorRuta.setMapa(this.mapa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//--------------------------------------------------------------------------
	// Estado esperandoPlay 
	//--------------------------------------------------------------------------
	
	
	
	/*--------------------------
	  --- METODOS AUXILIARES ---
	  --------------------------*/ 
	@Override
	public void clasificaError() {
		// TODO Auto-generated method stub
	}


}
