package icaro.aplicaciones.agentes.agenteAplicacionIniciadorMRS.comportamiento;


import java.io.File;
import icaro.aplicaciones.MRS.informacion.Escenario;
import icaro.aplicaciones.MRS.informacion.InicioEstado;
import icaro.aplicaciones.MRS.informacion.Rescatador;
import icaro.aplicaciones.MRS.informacion.Robot;
import icaro.aplicaciones.MRS.informacion.Victima;
import icaro.aplicaciones.MRS.informacion.ListaIds;
import icaro.aplicaciones.recursos.recursoEstadisticaMRS.ItfUsoRecursoEstadisticaMRS;
import icaro.aplicaciones.recursos.recursoPersistenciaMRS.ItfUsoRecursoPersistenciaMRS;
import icaro.aplicaciones.recursos.recursoPlanificadorMRS.ItfUsoRecursoPlanificadorMRS;
import icaro.aplicaciones.recursos.recursoVisualizadorMRS.ItfUsoRecursoVisualizadorMRS;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;

//Other imports used by this Agent
//#start_nodespecialImports:specialImports <--specialImports-- DO NOT REMOVE THIS
//#end_nodespecialImports:specialImports <--specialImports-- DO NOT REMOVE THIS


/**
 * Agente Iniciador del Simulador <br/>
 * Se trata de un Agente Reactivo, basado en un automata (<code>automata.xml</code>)
 * las operaciones se realizan en las transiciones del automata. <br/>
 * 
 * Tiene 6 estados:<br/>
 * - estadoInicial: comunica los recursos y agentes entre si. E inicializa todos los componentes<br/>
 * - esperandoEscenario: obtiene el escenario de la simulacion, lo valida y notifica a los componentes<br/>
 * - esperandoPlay: espera el evento de comienzo de la simulacion, admite cambiar de escenario <br/>
 * - enEjecucion: espera el evento de fin de la simulacion <br/>
 * - finalizandoSimulacion: finaliza la simulacion mostrando estadisticas y parando los componentes internos de los agentes<br/>
 * - estadoFinal: ultimo estado del automata se considera acabado el simulador<br/>
 * @author Luis Costero
 */
public class AccionesSemanticasAgenteAplicacionIniciadorMRS
		extends AccionesSemanticasAgenteReactivo {


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
	/**
	 * Transicion que se realiza al recibir el evento de inicio de la simulacion, nos 
	 * lleva estado del automata enEjecucion. Y notifica el inicio de la simulacion. 
	 */
	public void iniciarSimulacion(){ //input=iniciaSimulacion --> enEjecucion
		ListaIds lr = new ListaIds(this.escenario.getListaRobots());
		ListaIds lm = new ListaIds(this.escenario.getListaVictimas());
		try {
			this.itfEstadisticaMRS.iniciarRecate(lr.size(), lm.size(), lr.nombres);
		}catch(Exception e){e.printStackTrace();}
		
		informarTodosNuevoEstado(InicioEstado.ST_Inicio);
	}
	
	/**
	 * Transicion que surge al recibir un cambio de fichero antes de comenzar la simulacion.
	 */
	public void cambiarFichero(){ //input=cambioFichero --> esperandoEscenario
		// NO HACE NADA solo va al otro estado.
		// si se add a mas estados podria tener que parar la ejecucion
		this.informaraMiAutomata("leerFicheroTimeOut", null);
	}
	
	
	
	//--------------------------------------------------------------------------
	// Estado enEjecucion 
	//--------------------------------------------------------------------------
	/**
	 * Transicion realizada al recibir el evento de fin de simulacion. Notifica a todos
	 * el cambio de estado de la simulacion y muestra las estadisticas.
	 */
	public void FinSimulacion(){ //input=finSimulacion --> finalizandoSimulacion
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
	/**
	 * Transicion para finalizar el agente.
	 */
	public void AcabarDelTodo(){ //input=acabarDelTodo --> estadoFinal
		
	}
	

	
	/*--------------------------
	  --- METODOS AUXILIARES ---
	  --------------------------*/ 
	@Override
	public void clasificaError() {
		// TODO LUISMA Auto-generated method stub
	}

	/**
	 * Envia un mensaje broadcast a todos los agentes y recursos notificando 
	 * un cambio de estado de la simulacion. Es diferente a los cambios de 
	 * estado del automata de este agente.
	 * @param st nuevo estado definido en <code>InicioEstado</code>
	 */
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
			e.printStackTrace();
		}

	}

}
