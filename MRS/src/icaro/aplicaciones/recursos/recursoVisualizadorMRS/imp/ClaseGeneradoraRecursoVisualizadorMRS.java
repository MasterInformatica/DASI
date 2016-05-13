package icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp;

import icaro.aplicaciones.MRS.informacion.Coordenada;
import icaro.aplicaciones.MRS.informacion.Mapa;
import icaro.aplicaciones.MRS.informacion.Robot;
import icaro.aplicaciones.MRS.informacion.Victima;
import icaro.aplicaciones.recursos.recursoVisualizadorMRS.ItfUsoRecursoVisualizadorMRS;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.io.File;
import java.util.List;

/**
 * 
 * @author friker
 *
 */
public class ClaseGeneradoraRecursoVisualizadorMRS extends ImplRecursoSimple
		implements ItfUsoRecursoVisualizadorMRS {

	private ControladorVisorSimulador controladorUI;
		
	private String recursoId;

	private String identAgenteaReportar;
	private NotificadorEventos notificador;
	
	public ClaseGeneradoraRecursoVisualizadorMRS(String idRecurso) throws Exception {

		super(idRecurso);
		recursoId = idRecurso;
		try {
			
			notificador = new NotificadorEventos(recursoId, null);
			
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
	
	@Override
	public void mostrarVictimaRescatada(String VictimaId) throws Exception{
		throw new Error("NO SE LLAMA A mostrarVictimaRescatada!");
	}
	
	@Override
	public boolean mueveRobot(String idAgente, Coordenada coordActuales) throws Exception{
		return controladorUI.mueveRobot(idAgente,coordActuales);
	}
	@Override
	public boolean mueveVictima(String idAgente, Coordenada coordActuales) throws Exception {
		return controladorUI.mueveVictima(idAgente,coordActuales);
	}

	
	@Override
	public void termina() {
		trazas.aceptaNuevaTraza(
				new InfoTraza(this.recursoId, "Terminando recurso" + this.recursoId + " ....", InfoTraza.NivelTraza.debug));
		controladorUI.termina();
		super.termina();
	}

	@Override
	public void inicializarDestinoRobot(String id, Coordenada coordsAct, String idDest, Coordenada coordsDestino,
			double VelocidadRobot) {	
		controladorUI.mueveVictima(id,new Coordenada(coordsDestino));	
		controladorUI.mueveRobot(idDest,new Coordenada(coordsAct));	
	}

	@Override
	public void setMapa(Mapa mapa) throws Exception {
		controladorUI.setMapa(mapa);
		trazas.aceptaNuevaTraza(new InfoTraza(this.recursoId,
				"Escenario (Mapa) añadido al visor",InfoTraza.NivelTraza.debug));
	}

	@Override
	public void setRobots(List<Robot> listaRobots) {
		controladorUI.setRobots(listaRobots);
		trazas.aceptaNuevaTraza(new InfoTraza(this.recursoId,
				"Robots añadido al visor",InfoTraza.NivelTraza.debug));
		
	}

	@Override
	public void setVictimas(List<Victima> listaVictimas) {
		controladorUI.setVictimas(listaVictimas);
		trazas.aceptaNuevaTraza(new InfoTraza(this.recursoId,
				"Victimas añadido al visor",InfoTraza.NivelTraza.debug));
		
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
		// TODO JESUS bloquear seleccion de escenario
		//La idea de este método es que te llamo cuando el esenario es valido, y 
		//tu bloquees de alguna manera para que no pueda cambiarlo durante la ejecucion.
		trazas.aceptaNuevaTraza(new InfoTraza(this.recursoId,
				"Informe de Escenario valido recibido",InfoTraza.NivelTraza.info));
	}

	@Override
	public NotificadorEventos getNotificadorEventos() throws Exception {
		return notificador;
	}
	
	public void notificar(String event) {
		notificador.notificar(event);
	}
	
	public void notificar(String event,String s) {
		notificador.notificar(event,s);
	}
	
	@Override
	public void informarBloqueo(Coordenada c) {
		//Cuando el robot se encuentra con una roca en el camino, informa de que la ha encontrado, por si se quiere
		//cambiar la forma de visualizarlo o no
		controladorUI.informarBloqueo(c);
	}	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5349292996954794349L;

	@Override
	public void setAgenteIniciador(String nombreAgente) {
		notificador.setAgente(nombreAgente);
	}



	
}

