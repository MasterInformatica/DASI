package icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 * Esta clase gestiona la visualizacion de la ventana 
 * de configuracion. 
 * @author jdomenec
 */
public class VisorControl extends JFrame{
	
	private ControladorVisorSimulador controlador;

	public VisorControl(){
		build();
	}
	
	//TODO : Constructor VisorControlSimu recibiendo Controladora del visor
	public VisorControl(ControladorVisorSimulador control){
		controlador = control;
		build();
	}
	
	private void build(){
		setTitle("MRS - Control");
		rutaPersistencia = "persitenciaMRS/";
		initComponentes();
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	/**
	 * Este metodo crea la interfaz inicial y enlaza las acciones de los 
	 * diferentes componentes de la misma.
	 * TODO: Definir distribucion y contenido
	 */
	private void initComponentes(){
		setLayout(new BorderLayout()); //Crea las secciones N,S,E,W,C
		fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Seleccion de escenario");
		fileChooser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				//jFileChooser1ActionPerformed(evt);
			}
		});
		
		JButton boton_file = new JButton("file");
		boton_file.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				solicitarSeleccionFichero();
			}
			
		});
		add(boton_file,BorderLayout.CENTER);
	}
	
	public void termina() {
		this.dispose();
	}
	
	
	
	/**
	 * Ficheros
	 */
	private JFileChooser fileChooser;
	private String rutaPersistencia;
	public File solicitarSeleccionFichero() {
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"ficheros xml", "xml", "txt");
		fileChooser.setFileFilter(filter);
		File dir = fileChooser.getCurrentDirectory();
		int returnVal = fileChooser.showOpenDialog(this);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(dir);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		} else
			return null; // no ha seleccionado nada
	}
	
	public static void main(String args[]){
		try {
			VisorControl vc = new VisorControl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	
	private static final long serialVersionUID = -4664803528791128411L;

	

}
