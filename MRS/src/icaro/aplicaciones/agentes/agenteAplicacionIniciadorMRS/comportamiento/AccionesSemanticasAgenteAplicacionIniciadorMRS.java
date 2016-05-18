package icaro.aplicaciones.agentes.agenteAplicacionIniciadorMRS.comportamiento;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import icaro.aplicaciones.MRS.informacion.Escenario;
import icaro.aplicaciones.MRS.informacion.InicioEstado;
import icaro.aplicaciones.MRS.informacion.Mapa;
import icaro.aplicaciones.MRS.informacion.Rescatador;
import icaro.aplicaciones.MRS.informacion.Robot;
import icaro.aplicaciones.MRS.informacion.Victima;
import icaro.aplicaciones.MRS.informacion.ListaIds;
import icaro.aplicaciones.recursos.recursoEstadisticaMRS.ItfUsoRecursoEstadisticaMRS;
import icaro.aplicaciones.recursos.recursoPersistenciaMRS.ItfUsoRecursoPersistenciaMRS;
import icaro.aplicaciones.recursos.recursoPlanificadorMRS.ItfUsoRecursoPlanificadorMRS;
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


/**
 * Agente Iniciador del Simulador
 * @author Luis Costero
 */
public class AccionesSemanticasAgenteAplicacionIniciadorMRS
		extends AccionesSemanticasAgenteReactivo {

	/**
	 * Interfaz de uso de la Configuracion de Icaro
	 */
	private ItfUsoConfiguracion           itfconfig;
	
	/**
	 * Interfaz del Recurso VisualizadorMRS
	 */
	private ItfUsoRecursoVisualizadorMRS  itfVisualizadorMRS;
	
	/**
	 * Interfaz del Recurso PlanificadorMRS
	 */
	private ItfUsoRecursoPlanificadorMRS  itfPlanificadorMRS;
	
	/**
	 * Interfaz del Recurso PersistenciaMRS
	 */
	private ItfUsoRecursoPersistenciaMRS  itfPersistenciaMRS;
	
	/**
	 * Interfaz del Recurso EstadisticaMRS
	 */
	private ItfUsoRecursoEstadisticaMRS   itfEstadisticaMRS;
	
	/**
	 * Interfaz del Procesador de Objetivos
	 */
	private ItfProcesadorObjetivos        itfProcObjetivos;
	
	/**
	 * Fichero que contiene el escenario
	 */
	private File ficheroEscenario;
	
	/**
	 * Escenario de la simulacion
	 */
	private Escenario escenario;

	
	//--------------------------------------------------------------------------
	// Estado inicial 
	//--------------------------------------------------------------------------
	/**
	 * Transicion que se realiza al principio, obtiene todas las interfaces
	 * y prepara el entorno de simulacion
	 */
	public void AccionComenzar(){
		try{
			//Referencias a las interfaces de icaro
			this.itfconfig = (ItfUsoConfiguracion) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.NOMBRE_ITF_USO_CONFIGURACION);
			
			// Referencias a las interfaces de los recursos
			this.itfVisualizadorMRS = (ItfUsoRecursoVisualizadorMRS) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_USO + "RecursoVisualizadorMRS1");
			this.itfVisualizadorMRS.setAgenteIniciador(this.nombreAgente);
			
			this.itfPlanificadorMRS = (ItfUsoRecursoPlanificadorMRS) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_USO + "RecursoPlanificadorMRS1");
			
			this.itfPersistenciaMRS = (ItfUsoRecursoPersistenciaMRS) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_USO + "RecursoPersistenciaMRS1");
			
			this.itfEstadisticaMRS = (ItfUsoRecursoEstadisticaMRS) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_USO + "RecursoEstadisticaMRS1");
			
			this.itfVisualizadorMRS.setItf(itfPersistenciaMRS, itfPlanificadorMRS);
			//Comienzo ventana gráfica
			this.itfVisualizadorMRS.muestraVentana();

		} catch(Exception e){
			e.printStackTrace();
		}
				
		trazas.trazar(this.getNombreAgente(),
					  "Accion AccionComenzar completada", NivelTraza.debug);
	}
	
	
	//--------------------------------------------------------------------------
	// Estado esperandoEscenario 
	//--------------------------------------------------------------------------
	/**
	 * Transicion que pide un archivo cada cierto tiempo
	 */
	public void getFicheroTimeOut(){
		int tEspera = 1000; //milisegundos
		
		try {
			this.ficheroEscenario = this.itfVisualizadorMRS.getFicheroEscenario();
		} catch (Exception e) {
			e.printStackTrace();
		}

			trazas.trazar(this.getNombreAgente(),  "Fichero leido. Procediendo a validar", NivelTraza.debug);
			this.informaraMiAutomata("validaFichero", null);
	}

	/**
	 * Transicion que comprueba un archivo
	 */
	public void checkFile(){
		boolean error = false;
		try{
			this.escenario = this.itfPersistenciaMRS.parseEscenario(this.ficheroEscenario);
			for(Robot robot : this.escenario.getListaRobotTipo("R")){
				((Rescatador) robot).initCompIntMovimineto(itfPlanificadorMRS, itfVisualizadorMRS, itfPersistenciaMRS, itfEstadisticaMRS);
			}
			trazas.trazar(this.getNombreAgente(),  "Validación correcta", NivelTraza.debug);
			this.informaraMiAutomata("generaSimulacion", null);			
		} catch(Exception e){ 
			error = true;
			e.printStackTrace();
			trazas.trazar(this.getNombreAgente(),  "Validación incorrecta " + e.getStackTrace(), NivelTraza.error);		
		}
		
		if(error){
			try {
				this.itfVisualizadorMRS.informaErrorEscenario("Error al leer el fichero de escenario. El escenario elegido no es válido");
				//this.informaraMiAutomata("leerFicheroTimeOut", null);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Transicion que genera el entorno a la espera de iniciar la simulacion
	 */
	public void generarSimulacion(){ //input=generaSimulacion --> esperandoPlay
		try {
			this.itfVisualizadorMRS.escenarioElegidoValido();

			this.itfPlanificadorMRS.setMapa(this.escenario.getMapa());
			this.itfVisualizadorMRS.setMapa(this.escenario.getMapa());
			this.itfVisualizadorMRS.setRobots(this.escenario.getListaRobots());
			this.itfVisualizadorMRS.setVictimas(this.escenario.getListaVictimas());
			/* Informamos a todos los robots del nombre del resto (incluidos ellos mismos) */
			comunicator = this.getComunicator();
			assert(comunicator != null);

			ListaIds lr = new ListaIds(this.escenario.getListaRobots());
			ListaIds lm = new ListaIds(this.escenario.getListaVictimas());
			informarTodosNuevoEstado(InicioEstado.ST_NuevoEscenario);
			for(String s : lr.getNames()){
				ListaIds lc = new ListaIds(lr);
				comunicator.enviarInfoAotroAgente(lc, s);
				comunicator.enviarInfoAotroAgente(this.escenario.getRobot(s), s);
			}
			
			for(String min: lm.getNames()){
				ListaIds lc = new ListaIds(lr);
				comunicator.enviarInfoAotroAgente(lc, min);
				comunicator.enviarInfoAotroAgente(this.escenario.getVictima(min), min);
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
		ListaIds lr = new ListaIds(this.escenario.getListaRobots());
		ListaIds lm = new ListaIds(this.escenario.getListaVictimas());
		try {
			this.itfEstadisticaMRS.iniciarRecate(lr.size(), lm.size(), lr.nombres);
		}catch(Exception e){e.printStackTrace();}
		
		informarTodosNuevoEstado(InicioEstado.ST_Inicio);
	}
	
	public void cambiarFichero(){ //input=cambioFichero --> esperandoEscenario
		// NO HACE NADA solo va al otro estado.
		// si se add a mas estados podria tener que parar la ejecucion
		this.informaraMiAutomata("leerFicheroTimeOut", null);
	}
	
	
	
	//--------------------------------------------------------------------------
	// Estado enEjecucion 
	//--------------------------------------------------------------------------
	public void FinSimulacion(){ //input=finSimulacion --> finalizandoSimulacion
		//AQUI HABRIA QUE ENVIAR LA SEÑAL DE FIN A LOS ROBOTS.
		try {
			this.itfEstadisticaMRS.finalizarRescate();
			this.itfEstadisticaMRS.mostrarEstadisticas();
			
			this.itfVisualizadorMRS.cambioEstado(InicioEstado.ST_Fin);
		}catch(Exception e){e.printStackTrace();}
		
		informarTodosNuevoEstado(InicioEstado.ST_Fin);
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

	private void informarTodosNuevoEstado(String st){
		InicioEstado ie = new InicioEstado(st);
		for(Robot r : this.escenario.getListaRobots()){
			comunicator.enviarInfoAotroAgente(ie, r.getName());
		}
		
		for(Victima v: this.escenario.getListaVictimas()){
			comunicator.enviarInfoAotroAgente(ie, v.getName());
		}
		try {
			this.itfVisualizadorMRS.cambioEstado(st);
			this.itfPersistenciaMRS.cambioEstado(st);
			this.itfPlanificadorMRS.cambioEstado(st);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
