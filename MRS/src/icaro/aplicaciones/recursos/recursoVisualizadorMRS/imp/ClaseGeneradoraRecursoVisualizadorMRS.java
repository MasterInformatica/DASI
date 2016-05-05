package icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp;

import icaro.aplicaciones.MRS.informacion.Coordenada;
import icaro.aplicaciones.MRS.informacion.Mapa;
import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.recursos.recursoVisualizadorMRS.ItfUsoRecursoVisualizadorMRS;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.io.File;

/**
 * 
 * @author friker
 *
 */
public class ClaseGeneradoraRecursoVisualizadorMRS extends ImplRecursoSimple
		implements ItfUsoRecursoVisualizadorMRS {

	private ControladorVisorSimulador controladorUI;
		
	//private NotificadorInfoUsuarioSimulador notifEvt;
	private String recursoId;
	
	public ClaseGeneradoraRecursoVisualizadorMRS(String idRecurso) throws Exception {

		super(idRecurso);
		recursoId = idRecurso;
		try {
			
			//-notifEvt = new NotificadorInfoUsuarioSimulador(recursoId, identAgenteaReportar);
			// un agente debe decirle al recurso a quien debe reportar . Se
			// puede poner el agente a reportar fijo
			// visorEscenarios = new VisorEscenariosRosace3();
			// visorEscenarios = new VisorEscenariosRosace();
			// ventanaControlCenterGUI = new ControlCenterGUI4(notifEvt);
			//-controladorIUSimulador = new ControladorVisualizacionSimulRosace(notifEvt);
			controladorUI = new ControladorVisorSimulador(this);
			trazas.aceptaNuevaTraza(new InfoTraza(idRecurso, "El constructor de la clase generadora del recurso "
					+ idRecurso + " ha completado su ejecucion ....", InfoTraza.NivelTraza.debug));

		} catch (Exception e) {
			this.trazas.trazar(recursoId, "-----> Se ha producido un error en la creacion del recurso : " + e.getMessage(),
					InfoTraza.NivelTraza.error);
			this.itfAutomata.transita("error");
			throw e;
		}
	}
	
	//public void obtenerEscenarioSimulacion(String modOrganizativo, int numRobots) throws Exception {
	//public void obtenerEscenarioSimulacion() throws Exception {
		//this.controladorIUSimulador.peticionObtenerEscenarioSimulacion(modOrganizativo, numRobots);
		// EscenarioSimulacionRobtsVictms escenarioActual= null;
		// int numerointentos = 0;int maxIntentos = 2;
		// while ( numerointentos<maxIntentos && escenarioActual==null ){
		// escenarioActual =
		// controladorIUSimulador.obtenerEscenarioSimulacion(modOrganizativo,numRobots
		// );
		//
		// numerointentos++;
		// }
		// this.notifEvt.informaraOtroAgenteReactivo(new
		// InfoContEvtMsgAgteReactivo("escenarioDefinidoPorUsuario",
		// escenarioActual), identAgenteaReportar);
	//}
	
	
	@Override
	public void mostrarVictimaRescatada(String VictimaId) throws Exception{
		throw new Error("NO SE LLAMA A mostrarVictimaRescatada!");
		
	}
	
	@Override
	public boolean mueveAgente(String idAgente, Coordinate coord) throws Exception{
		return controladorUI.mueveRobot(idAgente,coord);
		
	}
	
	// Fragmento de codigo para mostrar por la ventana de trazas de este recurso
	// un mensaje trazas.aceptaNuevaTraza(new InfoTraza(this.idRecurso,"Mensaje mostrado en
	// la ventana de trazas del recurso ....",InfoTraza.NivelTraza.debug));
	//@Override
	/*public void mostrarEscenarioMovimiento(Mapa mapa) throws Exception{
		controladorUI.setMapa(mapa);
		controladorUI.mostrarEscenarioMovimiento();
		
	}*/
		
		
	@Override
	public void termina() {
		trazas.aceptaNuevaTraza(
				new InfoTraza(this.recursoId, "Terminando recurso" + this.recursoId + " ....", InfoTraza.NivelTraza.debug));
		controladorUI.termina();
		super.termina();
	}

	@Override
	public void inicializarDestinoRobot(String id, Coordinate coordsAct, String idDest, Coordinate coordsDestino,
			double VelocidadRobot) {	
		controladorUI.mueveVictima(id,new Coordinate(coordsDestino));	
		controladorUI.mueveRobot(idDest,new Coordinate(coordsAct));	
	}

	@Override
	public void setMapa(Mapa mapa) throws Exception {
		controladorUI.setMapa(mapa);
		trazas.aceptaNuevaTraza(new InfoTraza(this.recursoId,
				"Escenario (Mapa) añadido al visor",InfoTraza.NivelTraza.debug));
	}

	
	//*************************************************************
	//*** : *******************************************************
	//*************************************************************
	@Override
	public void muestraVentana()  throws Exception {
		controladorUI.mostrarEscenario();
	}

	@Override
	public File getFicheroEscenario() throws Exception {
		return controladorUI.getFicheroEscenario();
	}

	@Override
	public void informaErrorEscenario(String string) throws Exception {
		controladorUI.muestaError("Error al mostrar escenario",string);
		controladorUI.errorFileEscenario();
		
	}

	@Override
	public void escenarioElegidoValido()  throws Exception{
		//La idea de este método es que te llamo cuando el esenario es valido, y 
		//tu bloquees de alguna manera para que no pueda cambiarlo durante la ejecucion.
		trazas.aceptaNuevaTraza(new InfoTraza(this.recursoId,
				"Informe de Escenario valido recibido",InfoTraza.NivelTraza.info));
	}

	public void notificarBotonStartPulsado() {
		
		controladorUI.muestaError("No implementado","notificarBotonStartPulsado() No implementado en ClaseGeneradoraRecursoVisualizadorMRS");
	}

	@Override
	public void informarBloqueo(Coordenada c) {
		//Cuando el robot se encuentra con una roca en el camino, informa de que la ha encontrado, por si se quiere
		//cambiar la forma de visualizarlo o no
		controladorUI.informarBloqueo(c);
	}	
	
	/*****************************************************************
	 *********  NOTA   ***********************************************
	 *****************************************************************
	 *
	 * Para desencadenar la transición de iniciar simulacion al pulsar el boton
	 * de play, hay que llamar al agente de una forma super rara.
	 * Fijarse en la clase NotificadorInfoUsuarioSimulador para ver como lo hace.
	 * Tener en cuenta que ahora no se llama "SendSequenceOf....", sino
	 * "iniciaSimulacion".
	 *
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5349292996954794349L;
	
	
}



/*		@Override
		public void setIdentAgenteAReportar(String identAgenteAReportar){
			super.setIdentAgenteAReportar(identAgenteAReportar);
			//notifEvt.setIdentAgenteAReportar(identAgenteAReportar);
		}
	*/
	
/*
	@Override
	public void mostrarVentanaControlSimulador(String rutaFicheroEscenario) throws Exception {
		// ventanaControlCenterGUI.setVisible(true);
		// debe devolver un booleano cuando no se pueda abrir el fichero por las
		// causas que sea
		identFicheroEscenarioSimulacion = rutaFicheroEscenario;
		if (!controladorIUSimulador.abrirVisorConEscenario(identFicheroEscenarioSimulacion))
			trazas.aceptaNuevaTraza(new InfoTraza(recursoId,
					"El escenario  : " + identFicheroEscenarioSimulacion + " no existe o no se puede abrir ",
					InfoTraza.NivelTraza.error));
		// controladorIUSimulador.abrirVisorMovimientoConEscenario(identFicheroEscenarioSimulacion);
	}
*/
	/*
	@Override
	public void mostrarVentanaControlSimulador(EscenarioSimulacionRobtsVictms escenarioSimulacion) throws Exception {
		// ventanaControlCenterGUI.setVisible(true);
		// debe devolver un booleano cuando no se pueda abrir el fichero por las
		// causas que sea
		controladorIUSimulador.abrirVisorConEscenarioComp(escenarioSimulacion);
		// if ( escenarioSimulacion !=null)
		// trazas.aceptaNuevaTraza(new InfoTraza(recursoId, "Se abre el
		// escenario : " + escenarioSimulacion.getIdentEscenario() ,
		// InfoTraza.NivelTraza.info));
		// controladorIUSimulador.abrirVisorMovimientoConEscenario(identFicheroEscenarioSimulacion);
	}
*/
	/*
	@Override
	public boolean escenarioSimulacionDefinido() throws Exception {
		return controladorIUSimulador.hayEscenarioAbierto();
	}
*/

/*
	@Override
	public void notificarRecomendacion(String titulo, String motivo, String recomendacion) throws Exception {
		this.controladorIUSimulador.notifRecomendacionUsuario(titulo, motivo, recomendacion);
	}
*/
	/*
	@Override
	public void crearEInicializarVisorGraficaEstadisticas(String tituloVentanaVisor, String tituloLocalGrafico,
			String tituloEjeX, String tituloEjeY) throws Exception {
		PlotOrientation orientacionPlot = PlotOrientation.VERTICAL;
		boolean incluyeLeyenda = true;
		boolean incluyeTooltips = true;

		Color colorChartBackgroundPaint = Color.white;
		Color colorChartPlotBackgroundPaint = Color.lightGray;
		Color colorChartPlotDomainGridlinePaint = Color.white;
		Color colorChartPlotRangeGridlinePaint = Color.white;
		// VisualizacionJfreechart visualizadorJFchart = new
		// VisualizacionJfreechart("tituloVenanaVisor");
		visualizadorJFchart = new VisualizacionJfreechart(tituloVentanaVisor);
		visualizadorJFchart.inicializacionJFreeChart(tituloLocalGrafico, tituloEjeX, tituloEjeY, orientacionPlot,
				incluyeLeyenda, incluyeTooltips, false);
		visualizadorJFchart.setColorChartBackgroundPaint(colorChartBackgroundPaint);
		visualizadorJFchart.setColorChartPlotBackgroundPaint(colorChartPlotBackgroundPaint);
		visualizadorJFchart.setColorChartPlotDomainGridlinePaint(colorChartPlotDomainGridlinePaint);
		visualizadorJFchart.setColorChartPlotRangeGridlinePaint(colorChartPlotRangeGridlinePaint);

	}
*/
	
/*
	@Override
	public void crearVisorGraficasLlegadaYasignacionVictimas(int numeroRobotsSimulacion,
			int numeroVictimasDiferentesSimulacion, int intervaloSecuencia, String identificadorEquipo)
			throws Exception {
		String tituloVentanaVisor = "Simulacion: " + numeroRobotsSimulacion + " R; "
				+ numeroVictimasDiferentesSimulacion + " Vic; " + intervaloSecuencia + " mseg ; " + " tipo simulacion->"
				+ identificadorEquipo;
		String tituloLocalGrafico = "Victim's Notification and Assignment to Team members ";
		String tituloEjeX = "Number of Victim's Notifications";
		String tituloEjeY = "Time in miliseconds";
		crearEInicializarVisorGraficaEstadisticas(tituloVentanaVisor, tituloLocalGrafico, tituloEjeX, tituloEjeY);
		mostrarVisorGraficaEstadisticas();
	}
*//*
	@Override
	public void crearVisorGraficasTiempoAsignacionVictimas(int numeroRobotsSimulacion,
			int numeroVictimasDiferentesSimulacion, int intervaloSecuencia, String identificadorEquipo)
			throws Exception {
		String tituloVentanaVisor = "Simulacion: " + numeroRobotsSimulacion + " R; "
				+ numeroVictimasDiferentesSimulacion + " Vic; " + intervaloSecuencia + " mseg ; " + " tipo simulacion->"
				+ identificadorEquipo;
		String tituloLocalGrafico = "Elapsed Time to Assign a New Victim";
		String tituloEjeX = "Number of Victim's Notifications";
		String tituloEjeY = "Time in miliseconds";
		crearEInicializarVisorGraficaEstadisticas(tituloVentanaVisor, tituloLocalGrafico, tituloEjeX, tituloEjeY);
		mostrarVisorGraficaEstadisticas();
	}
*//*
	@Override
	public void visualizarLlegadaYasignacionVictimas(ArrayList<PuntoEstadistica> llegada,
			ArrayList<PuntoEstadistica> asignacion) throws Exception {
		String tituloSerieLlegadaVictimas = "Notification Time";
		int indexSerieLlegadaVictimas = 1;
		String tituloSerieasignacionVictimas = "Assignment Time";
		int indexSerieasignacionVictimas = 2;
		aniadirSerieAVisorGraficaEstadisticas(tituloSerieLlegadaVictimas, indexSerieLlegadaVictimas, llegada);
		aniadirSerieAVisorGraficaEstadisticas(tituloSerieasignacionVictimas, indexSerieasignacionVictimas, asignacion);
	}
*//*
	@Override
	public void visualizarTiempoAsignacionVictimas(ArrayList<PuntoEstadistica> elapsed) throws Exception {
		String tituloSerieElapasedVictimas = "Elapsed Time";
		int indexSerieElapasedVictimas = 1;
		aniadirSerieAVisorGraficaEstadisticas(tituloSerieElapasedVictimas, indexSerieElapasedVictimas, elapsed);

	}
*//*
	@Override
	public void mostrarVisorGraficaEstadisticas() throws Exception {
		visualizadorJFchart.showJFreeChart(coordX, coordY);
		coordX = 10 * coordX; // coordY= 5*coordY;
	}

	@Override
	public void aniadirSerieAVisorGraficaEstadisticas(String tituloSerie, int indexSerie,
			ArrayList<PuntoEstadistica> puntosEstadistica) throws Exception {

		XYSeries serie;

		serie = new XYSeries(tituloSerie);

		for (int i = 0; i < puntosEstadistica.size(); i++) {

			serie.add(puntosEstadistica.get(i).getX(), puntosEstadistica.get(i).getY());
		}

		// serie.add(1.0, 1.5);

		visualizadorJFchart.addSerie(indexSerie, Color.green, serie);

	}
*/



/*
	@Override
	public void mostrarResultadosFinSimulacion() throws Exception {
		String directorioTrabajo = System.getProperty("user.dir"); // Obtener
																	// directorio
																	// de
																	// trabajo
		String nombreFicheroAsignVictim = "asigVictimasObjetos";
		String directorioPersistencia = VocabularioRosace.IdentDirectorioPersistenciaSimulacion + "/";
		String identFicheroInfoAsigVictimasObj = directorioPersistencia + nombreFicheroAsignVictim + ".tmp";
		// String identFicheroInfoAsigVictimasXML = directorioPersistencia +
		// nombreFicheroAsignVictim + ".xml";
		String identFicheroInfoAsigVictimasXML = directorioPersistencia
				+ VocabularioRosace.NombreFicheroSerieInfoAsignacionVictimas + "<TimeTag>.xml";
		String identFicheroSerieAsigVictimasXML = directorioPersistencia
				+ VocabularioRosace.NombreFicheroSerieAsignacion + "<TimeTag>.xml";
		String identFicheroLlegyAsigVictimasXML = directorioPersistencia
				+ VocabularioRosace.NombreFicheroSerieLlegadaYasignacion + "<TimeTag>.xml";
		String msg = "FIN DE LA SIMULACION !!!.\n";
		msg = msg + "Se ha completado la captura de todas las estadisticas para la simulacion actual.\n";
		msg = msg + "Los ficheros de estadisticas se encuentran en el directorio " + directorioTrabajo + "/"
				+ directorioPersistencia + "\n";
		msg = msg + "Los ficheros de estadisticas son los siguientes:\n";
		msg = msg + directorioTrabajo + "/" + identFicheroInfoAsigVictimasXML + "\n";
		msg = msg + directorioTrabajo + "/" + identFicheroSerieAsigVictimasXML + "\n";
		msg = msg + directorioTrabajo + "/" + identFicheroLlegyAsigVictimasXML + "\n";
		// msg = msg + directorioTrabajo + "/" +
		// ConstantesRutasEstadisticas.rutaficheroXMLEstadisticasLlegadaYAsignacionVictimas
		// + "\n";
		// msg = msg + directorioTrabajo + "/estadisticas/" +
		// "EstIntLlegadaYAsignacionVictims" + "FECHA.xml" + "\n";
		JOptionPane.showMessageDialog(null, msg);
	}

	@Override
	public synchronized void mostrarPosicionRobot(String identRobot, Coordinate coordRobot) throws Exception {
		// public synchronized void mostrarPosicionRobot(String identRobot,
		// Coordinate coordRobot,Coordinate coordDestino,String
		// identDestino)throws Exception{
		Integer coordX = (int) coordRobot.getX();
		Integer coordY = (int) coordRobot.getY();
		// if ( Math.abs (coordX-coordDestino.getX())<0.6 && Math.abs
		// (coordY-coordDestino.getY())<0.6){
		// // notificamos llegada a destino
		// this.notifEvt.sendNotificacionLlegadaDestino(identRobot,
		// identDestino);
		// log.debug("Envio notificacion de destino " + identRobot + " en
		// destino -> ("+coordX + " , " + coordY + ")");
		//// Temporizador informeTemp = new Temporizador
		// (500,itfProcObjetivos,informeLlegada);
		// }
		// else {
		if (visorEscenarioMov == null) {
			visorEscenarioMov = controladorIUSimulador.getVisorMovimiento();
			if (visorEscenarioMov == null)
				controladorIUSimulador.peticionAbrirEscenario();
		} else {
			visorEscenarioMov.setVisible(true);
			visorEscenarioMov.cambiarPosicionRobot(identRobot, coordX, coordY);
			// controladorIUSimulador.peticionCambiarPosicionRobot(identRobot,
			// coordX, coordY);
		}

		// visorEscenarios.moverRobot(identRobot, coordX, coordX);

	}

	// @Override
	// public synchronized void mostrarPosicionActualRobot(String
	// identRobot)throws Exception{
	// visorEscenarios.setVisible(true);
	//
	// visorEscenarios.cambiarPosicionRobot(identRobot, coordX, coordY);
	// }
	@Override
	public synchronized void mostrarVictimaRescatada(String identVictima) throws Exception {
		//
		this.controladorIUSimulador.peticionMostrarVictimaRescatada(identVictima);
	}

	@Override
	public void inicializarDestinoRobot(String idRobot, Coordinate coordInicial, String destinoId,
			Coordinate coordDestino, double velocidadInicial) {
		this.coordDestino = coordDestino;
		this.identDestino = destinoId;
		// if (idRobot != null )
		// this.getInstanciaHebraMvto(idRobot).inicializarDestino(idRobot,
		// coordInicial, coordDestino, velocidadInicial);
	}

	@Override
	public synchronized void mostrarMovimientoAdestino(String idRobot, String identDest, Coordinate coordDestino,
			double velocidadCrucero) {
		// if (idRobot != null ){
		// this.visorEscenarios.setVisible(true);
		// this.visorEscenarios.cambiarPosicionRobot(idRobot, coordX, coordX);
		//// visorEscenarios.moverRobot(idRobot, coordX, coordX);
		// }
	}

	// public synchronized void mostrarMovimientoAdestino(String idRobot,String
	// identDest,Coordinate coordDestino, float velocidadCrucero) {
	// if (idRobot != null ){
	// this.visorEscenarios.setVisible(true);
	// HebraMovimiento hebraMov = this.getInstanciaHebraMvto(idRobot);
	// hebraMov.finalizar();
	// hebraMov.inicializarDestino(identDest, hebraMov.getCoordRobot(),
	// coordDestino, velocidadCrucero);
	// hebraMov.run();
	// }
	// // identDestino = identDest;
	// }
	//// private HebraMovimiento getInstanciaHebraMvto(String identRobot) {
	////
	//// if(identRobot != null){
	//// HebraMovimiento hebramov;
	//// if (tablaHebrasMov == null ){
	//// tablaHebrasMov = new HashMap();
	//// hebramov = new HebraMovimiento (identRobot,notifEvt,visorEscenarios);
	//// tablaHebrasMov.put(identRobot, hebramov);
	//// return hebramov;
	//// }else{
	//// hebramov = tablaHebrasMov.get(identRobot);
	//// if (hebramov == null){
	//// hebramov = new HebraMovimiento (identRobot,notifEvt,visorEscenarios);
	//// tablaHebrasMov.put(identRobot, hebramov);
	//// }
	////
	//// trazas.trazar(identRobot, " se crea el monitor de movimiento del robot
	// ", InfoTraza.NivelTraza.debug);
	//// return hebramov;
	//// }
	// }
	// trazas.trazar(identAgenteAReportar, " el identificador del monitor de
	// movimiento del robot debe ser distinto de null ",
	// InfoTraza.NivelTraza.error);
	// return null;
	//
	// }
	@Override
	public void mostrarIdentsEquipoRobots(ArrayList identList) {
		// this.ventanaControlCenterGUI.visualizarIdentsEquipoRobot(identList);
		controladorIUSimulador.peticionVisualizarIdentsRobots(identList);
	}

	@Override
	public void setItfUsoPersistenciaSimulador(ItfUsoRecursoPersistenciaEntornosSimulacion itfUsopersistencia)
			throws Exception {
		this.controladorIUSimulador.setIftRecPersistencia(itfUsopersistencia);
		this.controladorIUSimulador.initModelosYvistas();
	}
*/
	

