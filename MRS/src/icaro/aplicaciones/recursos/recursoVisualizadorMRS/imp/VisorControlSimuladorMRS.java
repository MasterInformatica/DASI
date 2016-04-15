package icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp;

import java.awt.*;

import javax.swing.*;
/**
 * Esta clase gestiona la visualizacion de la ventana 
 * de configuracion. 
 * @author jdomenec
 */
public class VisorControlSimuladorMRS extends JFrame{
	//TODO : //private ControladorVisorSimulador controlador;
	
	VisorControlSimuladorMRS(){
		initComponentes();
	}
	
	//TODO : Constructor VisorControlSimu recibiendo Controladora del visor
	/*VisorControlSimuladorMRS(ControladorVisorSimulador control){
	 controlador = control;
	 initComponentes();
		
	}*/
	
	
	/**
	 * Este metodo crea la interfaz inicial y enlaza las acciones de los 
	 * diferentes componentes de la misma.
	 * TODO: Definir distribucion y contenido
	 */
	private void initComponentes(){
		setLayout(new BorderLayout()); //Crea las secciones N,S,E,W,C
		
	}
	
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4664803528791128411L;

}
