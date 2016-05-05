package icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import icaro.aplicaciones.MRS.informacion.Coordenada;
import icaro.aplicaciones.MRS.informacion.Mapa;
import icaro.aplicaciones.MRS.informacion.TipoCelda;
import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp.NotificadorEventos.Eventos;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

public class VisorEscenario extends JFrame {


	//
	private String rutaEscenarios = "MRS/escenarios/";
	// SWING elements
	private JRootPane MapaPanel;
	private JPanel ControlButtons;
	private ComponenteBotonMapa botonesMapa[][];
	private JMenuBar menuBar;
	private JMenu MenuFile;
	// Logica (Modelo)
	private File filechoosed;
	//private boolean[][] Map;
	private Mapa Map;
	private HashMap<String, Coordinate> posicionAgentes;

	private int cols;// = 25;
	private int rows;// = 25;

	private boolean isVisible;

	private ControladorVisorSimulador controlador;
	private JFileChooser fileChooser;
	private JMenuItem menu_Load;
	
	
	public VisorEscenario() throws Exception{
		build();
	}
	
	public VisorEscenario(ControladorVisorSimulador control) throws Exception{
		controlador = control;
		build();
	}

	public boolean mueveAgente(String idAgente, Coordinate coord, String tipo) {
		// Get y remove Current position
		if(posicionAgentes.containsKey(idAgente)){
			Coordinate org_coord = posicionAgentes.get(idAgente);
			eliminaAgente(idAgente,org_coord);
		}
		// Set y draw new position
		return dibujaAgente(idAgente,coord, tipo);
	}
	
	public void setMapa(Mapa mapa) {
		Map = mapa;
		rows = Map.getNumRows();
		cols = Map.getNumCols();
		buildMap();
	}
	
	public void mostrar(){
		if(isVisible)
			return;
		isVisible = true;
		setVisible(true);
	}
	
	public void termina() {
		this.dispose();
	}
	
	public File getFicheroEscenario(){
		if (filechoosed == null)
			filechoosed = solicitarSeleccionFichero();
		return filechoosed;
	}
	
	public void errorFileEscenario() {
		filechoosed = null;
	}
	
	public void muestraError(String string, String string2) {
		JOptionPane.showMessageDialog(null,string2,string,0,null);
	}
	
	public void informarBloqueo(Coordenada c) {
		botonesMapa[c.getX()][c.getY()].addElement("escombro","Piedra");
	}
	
	//-----------------------------PRIVATE--------------------------------
	
	/**
	 * constructora, metodo para ser reutilizado.
	 * Establece el titulo, inicializa las variables
	 * Inicializa los componentes
	 * @throws Exception
	 */
	private void build(){
		isVisible = false;
		setTitle("MRS - Simulator");
		posicionAgentes = new HashMap<String,Coordinate>();
		initComponentes();
	}
	
	private boolean dibujaAgente(String idAgente, Coordinate coord, String tipo){
		int x = (int) coord.getX();
		int y = (int) coord.getY();
		if( x < 0 || y < 0 || x >= rows || y >= cols)
			return false;
		botonesMapa[x][y].dibujaAgente(idAgente,tipo);
		posicionAgentes.put(idAgente, new Coordinate(coord));
		return true;
	}
	
	
	private void eliminaAgente(String idAgente, Coordinate coord){
		int x = (int) coord.getX();
		int y = (int) coord.getY();
		if( x < 0 || y < 0 || x >= rows || y >= cols)
			return;
		botonesMapa[x][y].eliminaAgente(idAgente);
		posicionAgentes.remove(idAgente);
	}

	
	private void initComponentes() {
		setLayout(new BorderLayout());
		
		MapaPanel = new JRootPane();
		
		initEmptyMap();
		initControlButtons();
		initFileChooser();
		initMenu();		
		Container c = getContentPane();
        c.setBackground(Color.YELLOW);
		Dimension d = new Dimension(800,600);
        c.setPreferredSize(d);
        setResizable(false);
		pack();
	}
	private void initControlButtons(){
		ControlButtons = new JPanel();
		ControlButtons.setLayout(new FlowLayout());
		JButton start_stop = new JButton("Start");
		start_stop.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.notificar(NotificadorEventos.Eventos.BOTON_START);
			}
		});
		JButton restart = new JButton("reinicializar");
		restart.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.notificar(NotificadorEventos.Eventos.BOTON_REINICIAR);
			}
		});
		ControlButtons.add(start_stop);
		ControlButtons.add(restart);
		add(ControlButtons,BorderLayout.SOUTH);
	}
	
	private void initMenu(){
		menuBar = new JMenuBar();
		MenuFile = new JMenu("File");
		MenuFile.setMnemonic(KeyEvent.VK_F);
		MenuFile.getAccessibleContext().setAccessibleDescription(
		        "Load,save, modify files...");
		menuBar.add(MenuFile);	
		menu_Load = new JMenuItem("Load",KeyEvent.VK_L);
		menu_Load.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				filechoosed = solicitarSeleccionFichero();
				if(filechoosed != null){
					notificar(NotificadorEventos.Eventos.ARCHIVO_CAMBIADO);
				}
			}
		});
		JMenuItem menu_save = new JMenuItem("Save",KeyEvent.VK_S);
		menu_save.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				notificar(NotificadorEventos.Eventos.MENU_SALVAR);
			}
		});
		JMenuItem menu_exit = new JMenuItem("Exit",KeyEvent.VK_E);
		menu_exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				notificar(NotificadorEventos.Eventos.MENU_SALIR);
			}
		});
		MenuFile.add(menu_Load);
		MenuFile.add(menu_save);
		MenuFile.addSeparator();
		MenuFile.add(menu_exit);
		setJMenuBar(menuBar);
	}
	
	private void notificar(Eventos event) {
		controlador.notificar(event);
	}
	private void notificar(Eventos event, String s) {
		controlador.notificar(event, s);
	}

	private void initFileChooser(){
		fileChooser = new JFileChooser(rutaEscenarios);
		fileChooser.setDialogTitle("Seleccion de escenario");
	}
	
	private void initEmptyMap(){
		MapaPanel.setContentPane(new JPanel());
		add(MapaPanel,BorderLayout.CENTER);
	}

	
	private void buildMap(){
		JPanel mapita = new JPanel();
		mapita.setLayout(new GridLayout(rows,cols,0,0));

		botonesMapa = new ComponenteBotonMapa[rows][cols];
		for (int i =  0; i < rows; i++){
			for (int j = 0; j < cols; j++){
				botonesMapa[i][j] = new ComponenteBotonMapa(getType(i,j));
				if(TipoCelda.ESCOMBRO ==Map.getCoord(i,j))
					botonesMapa[i][j].addElement("escombro", "NOPiedra");
				mapita.add(botonesMapa[i][j]);
			}
		}
		MapaPanel.setContentPane(mapita);
		MapaPanel.validate();
		MapaPanel.repaint();
	}
	
	private File solicitarSeleccionFichero() {
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

	
	private int getType(int i, int j){
		int t = 0b0000;	
		if(Map.getCoord(i,j)==TipoCelda.PARED)
			return t;
		if(Map.getCoord(i,j)==TipoCelda.PARED)
			return t;
		if(i > 0 && Map.getCoord(i-1,j)!=TipoCelda.PARED)
			t|=0b0001;
		if(j > 0 && Map.getCoord(i,j-1)!=TipoCelda.PARED)
			t|=0b0010;
		if(i < rows-1 && Map.getCoord(i+1,j)!=TipoCelda.PARED)
			t|=0b0100;
		if(j < cols-1 && Map.getCoord(i,j+1)!=TipoCelda.PARED)
			t|=0b1000;
		if(t==0)
			t = 16;
		return t;
	}
	

	
	//-----------------------------TEST--------------------------------
	
	public static void main(String args[]){
		try {
			VisorEscenario ve = new VisorEscenario();
			ve.setVisible(true);
			int j = 2;
			for(int i = 0; i< 100000; i++) j = (j*j) %1000;
			//ve.buildMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -418573958565443751L;








}
