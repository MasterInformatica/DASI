package icaro.aplicaciones.agentes.agenteAplicacionIniciadorMRS.comportamiento;


import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import icaro.aplicaciones.MRS.informacion.Mapa;
import icaro.aplicaciones.Rosace.informacion.FinSimulacion;
import icaro.aplicaciones.Rosace.informacion.OrdenCentroControl;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.recursos.recursoPlanificadorRuta.ItfUsoRecursoPlanificadorRuta;
import icaro.aplicaciones.recursos.recursoVisualizadorMRS.ItfUsoRecursoVisualizadorMRS;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
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
	
	private File ficheroEscenario;
	private Mapa mapa;

	
	//CHAPUZA PARA QUE ESTO FUNCIONE
	private ArrayList identsAgtesEquipo;
	public AccionesSemanticasAgenteAplicacionIniciadorMRS(){
		this.identsAgtesEquipo = new ArrayList();
		this.identsAgtesEquipo.add("rescate1RobotAsignador");
		this.identsAgtesEquipo.add("rescate1RobotSubordinado1");
	}
	
	
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
		
		try {
			this.ficheroEscenario = this.itfVisualizadorMRS.getFicheroEscenario();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
		//LUISMA: Aquí llamar a la interfaz de persistencia para parsear el fichero
		//this.escenario = this.ItfPersistenciaMRS.parseFile(this.ficheroEscenario)
		this.mapa = new Mapa();
		
		Random randomGenerator = new Random();
		      
		if( randomGenerator.nextInt(2) == 0){
			try {
				this.itfVisualizadorMRS.informaErrorEscenario("El escenario elegido no es válido");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			trazas.trazar(this.getNombreAgente(),  "Validación incorrecta", NivelTraza.debug);
			
			this.informaraMiAutomata("leerFicheroTimeOut", null);
		} else {
			trazas.trazar(this.getNombreAgente(),  "Validación correcta", NivelTraza.debug);
			
			this.informaraMiAutomata("activaEsperaSimulacion", null);
			
		}
		
	}
	
	public void generarSimulacion(){ //input=generaSimulacion --> esperandoPlay
		try {
			this.itfVisualizadorMRS.escenarioElegidoValido();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
	public void iniciarSimulacion(){ //input=iniciaSimulacion --> enEjecucion
		//AQUI HABRIA QUE ENVIAR LAS ORDENES A LOS ROBOTS. EN EL AGENTE
		//ORIGINAL LO HACE EN EL MÉTODO sendSequenceOfSimulatedVictimsToRobotTeam
	}
	
	
	//--------------------------------------------------------------------------
	// Estado enEjecucion 
	//--------------------------------------------------------------------------
	public void FinSimulacion(){ //input=finSimulacion --> finalizandoSimulacion
		//AQUI HABRIA QUE ENVIAR LA SEÑAL DE FIN A LOS ROBOTS.
		//lO DE ABAJO ES UNA COPIA BARATA DEL AGENTE ORIGINAL
		try {
			FinSimulacion finalSimulacion = new FinSimulacion();
			comunicator.informaraGrupoAgentes(finalSimulacion,
					identsAgtesEquipo);
			trazas.aceptaNuevaTraza(new InfoTraza(
					this.nombreAgente,
					"Se notifica el fin de la simulacion a los agentes del Equipo:identsAgtesEquipo->"
							+ identsAgtesEquipo, InfoTraza.NivelTraza.info));
		} catch (Exception e) {
			e.printStackTrace();
		}
	
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
