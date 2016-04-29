package icaro.aplicaciones.agentes.agenteAplicacionIniciadorMRS.comportamiento;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import icaro.aplicaciones.MRS.informacion.Escenario;
import icaro.aplicaciones.MRS.informacion.Mapa;
import icaro.aplicaciones.MRS.informacion.Robot;
import icaro.aplicaciones.Rosace.informacion.FinSimulacion;
import icaro.aplicaciones.Rosace.informacion.OrdenCentroControl;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.recursos.recursoPersistenciaMRS.ItfUsoRecursoPersistenciaMRS;
import icaro.aplicaciones.recursos.recursoPlanificadorRuta.ItfUsoRecursoPlanificadorRuta;
import icaro.aplicaciones.recursos.recursoVisualizadorMRS.ItfUsoRecursoVisualizadorMRS;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ItfProcesadorObjetivos;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;

//Other imports used by this Agent
//#start_nodespecialImports:specialImports <--specialImports-- DO NOT REMOVE THIS
//#end_nodespecialImports:specialImports <--specialImports-- DO NOT REMOVE THIS



public class AccionesSemanticasAgenteAplicacionIniciadorMRS
		extends AccionesSemanticasAgenteReactivo {

	private ItfUsoConfiguracion           itfconfig;
	private ItfUsoRecursoVisualizadorMRS  itfVisualizadorMRS;
	private ItfUsoRecursoPlanificadorRuta itfPlanificadorRuta;
	private ItfUsoRecursoPersistenciaMRS  itfPersistenciaMRS;
	
	private ItfProcesadorObjetivos        itfProcObjetivos;
	
	private File ficheroEscenario;
	private Escenario escenario;

	
	//--------------------------------------------------------------------------
	// Estado inicial 
	//--------------------------------------------------------------------------
	public void AccionComenzar(){
		try{
			//Referencias a las interfaces de los recursos
			this.itfconfig = (ItfUsoConfiguracion) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.NOMBRE_ITF_USO_CONFIGURACION);
			
			
			
			this.itfVisualizadorMRS = (ItfUsoRecursoVisualizadorMRS) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_USO + "RecursoVisualizadorMRS1");
			this.itfVisualizadorMRS.setIdentAgenteAReportar(this.nombreAgente);
			
			this.itfPlanificadorRuta = (ItfUsoRecursoPlanificadorRuta) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_USO + "RecursoPlanificadorRuta1");
			
			this.itfPersistenciaMRS = (ItfUsoRecursoPersistenciaMRS) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_USO + "RecursoPersistenciaMRS1");
			
			
			//Comienzo ventana gráfica
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
		
		try {
			this.ficheroEscenario = this.itfVisualizadorMRS.getFicheroEscenario();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(ficheroEscenario==null){
//			trazas.trazar(this.getNombreAgente(),  "Fichero leido null", NivelTraza.debug);
			this.generarTimeOutInterno(tEspera, "leerFicheroTimeOut", this.getNombreAgente(),
			     					   this.itfUsoPropiadeEsteAgente);
		}else{
			trazas.trazar(this.getNombreAgente(),  "Fichero leido. Procediendo a validar", NivelTraza.debug);
			this.informaraMiAutomata("validaFichero", null);
		}
	}

	public void checkFile(){
		boolean error = false;
		try{
			this.escenario = this.itfPersistenciaMRS.parseEscenario(this.ficheroEscenario);
			
			//Aquí el escenario está bien construido, sino estaríamos en el catch
			trazas.trazar(this.getNombreAgente(),  "Validación correcta", NivelTraza.debug);
			this.informaraMiAutomata("generaSimulacion", null);			
		} catch(Exception e){ 
			error = true;
			trazas.trazar(this.getNombreAgente(),  "Validación incorrecta", NivelTraza.debug);
			
		}
		
		if(error){
			try {
				this.itfVisualizadorMRS.informaErrorEscenario("Error al leer el fichero de escenario. El escenario elegido no es válido");
				this.informaraMiAutomata("leerFicheroTimeOut", null);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void generarSimulacion(){ //input=generaSimulacion --> esperandoPlay
		try {
			this.itfVisualizadorMRS.escenarioElegidoValido();

			this.itfVisualizadorMRS.mostrarEscenarioMovimiento(this.escenario.getMapa());
			this.itfPlanificadorRuta.setMapa(this.escenario.getMapa());
			
			List<Robot> robots = this.escenario.getListaRobots();
			int i=0;
			for(Robot r : robots){
				(new TareaIniciadoraAgentes()).ejecutar(i);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	//--------------------------------------------------------------------------
	// Estado esperandoPlay 
	//--------------------------------------------------------------------------
	public void iniciarSimulacion(){ //input=iniciaSimulacion --> enEjecucion
		//AQUI HABRIA QUE ENVIAR LAS ORDENES A LOS ROBOTS. EN EL AGENTE
		//ORIGINAL LO HACE EN EL MÉTODO sendSequenceOfSimulatedVictimsToRobotTeam
	}
	
	
	//--------------------------------------------------------------------------
	// Estado enEjecucion 
	//--------------------------------------------------------------------------
	public void FinSimulacion(){ //input=finSimulacion --> finalizandoSimulacion
		//AQUI HABRIA QUE ENVIAR LA SEÑAL DE FIN A LOS ROBOTS.
	
	}
	
	//--------------------------------------------------------------------------
	// Estado finalizandoSimulacion 
	//--------------------------------------------------------------------------
	public void AcabarDelTodo(){ //input=acabarDelTodo --> estadoFinal
		//Este estado representa un momento en el que el sistema ha acabado de simular,
		//pero no está en el estado final. Se podría utilizar para mostrar estadísticas
		//y ese tipo de cosas.
	}
	

	
	/*--------------------------
	  --- METODOS AUXILIARES ---
	  --------------------------*/ 
	@Override
	public void clasificaError() {
		// TODO Auto-generated method stub
	}


}
