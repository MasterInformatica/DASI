package icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import icaro.aplicaciones.MRS.informacion.Coordenada;
import icaro.aplicaciones.MRS.informacion.Escenario;
import icaro.aplicaciones.MRS.informacion.Mapa;
import icaro.aplicaciones.MRS.informacion.Robot;
import icaro.aplicaciones.MRS.informacion.TipoCelda;
import icaro.aplicaciones.MRS.informacion.Victima;
import icaro.aplicaciones.MRS.informacion.VocabularioMRS;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * Clase Ventana del visor
 * @author Jesus Domenech
 */
public class VisorEscenario extends JFrame {
	/**
	 * Ruta de los escenarios XML por defecto
	 */
	private String rutaEscenarios = "MRS/escenarios/";
	/**
	 * Elemento JSWING
	 * Contenedor Raiz de los mapas
	 */
	private JRootPane MapaPanel;
	/**
	 * Elemento JSWING
	 * Contendor de los botones de control
	 */
	private JPanel ControlButtons;
	/**
	 * Elemento JSWING
	 * Array de botones
	 */
	private ComponenteBotonMapa botonesMapa[][];
	/**
	 * Elemento JSWING
	 * Barra del menu de opciones
	 */
	private JMenuBar menuBar;
	/**
	 * Elemento JSWING
	 * Menu de gestion de archivos
	 */
	private JMenu MenuFile;
	/**
	 * Elemento JSWING
	 * Ventana de seleccion de archivos
	 */
	private JFileChooser fileChooser;

	/**
	 * Archivo seleccionado, esperando a ser solicitado
	 */
	private File filechoosed;
	
	/**
	 * Escenario actual
	 */
	private Escenario esc;
	
	/**
	 * Registro de posiciones de los agentes
	 */
	private HashMap<String, Coordenada> posicionAgentes;

	/**
	 * Indica si la venta es visible o no
	 */
	private boolean isVisible;

	/**
	 * referencia al controlador de la vista
	 */
	private ControladorVisorSimulador controlador;

	/**
	 * Referencia unica al elemento vista
	 */
	public static VisorEscenario me;
	
	/**
	 * Constructora de la ventana recibiendo el controlador
	 * @param control controlador de la ventana
	 * @throws Exception
	 */
	public VisorEscenario(ControladorVisorSimulador control) throws Exception{
		me = this;
		controlador = control;
		build();
	}

	/**
	 * Dibuja un agente en una posicion, comprobando si existe para elimnarlo primero
	 * @param idAgente nombre del agente
	 * @param coord posicion donde se quiere dibujar
	 * @param tipo tipo de agente (Victima o Robot) (Minero o Rescatador)
	 * @return devuelve <code>true</code> si lo ha podido mover
	 */
	public boolean mueveAgente(String idAgente, Coordenada coord, String tipo) {
		// Get y remove Current position
		if(posicionAgentes.containsKey(idAgente)){
			Coordenada org_coord = posicionAgentes.get(idAgente);
			eliminaAgente(idAgente,org_coord);
		}
		// Set y draw new position
		return dibujaAgente(idAgente,coord, tipo);
	}
	
	/**
	 * Cambia el mapa actual
	 * @param mapa nuevo mapa
	 */
	public void setMapa(Mapa mapa) {
		esc.setMapa(mapa);
		buildMap();
	}
	
	/**
	 * Cambia la lista de los Robots
	 * @param listaRobots nueva lista de Robots
	 */
	public void setRobots(List<Robot> listaRobots) {
		esc.setListR(listaRobots);
		for(Robot r : listaRobots){
			System.err.println("Print: "+r.getName());
			mueveAgente(r.getName(),r.getCoordenadasIniciales(),"Robot");
		}
	}

	/**
	 * Cambia la lista de las victimas
	 * @param listaVictimas nueva lista de Victimas
	 */
	public void setVictimas(List<Victima> listaVictimas) {
		esc.setListV(listaVictimas);
		for(Victima v : listaVictimas){
			mueveAgente(v.getName(),v.getCoordenadasIniciales(),"Miner");
		}
	}
	
	/**
	 * Muestra la ventana
	 */
	public void mostrar(){
		if(isVisible)
			return;
		isVisible = true;
		setVisible(true);
	}
	
	/**
	 * Finaliza la ventana
	 */
	public void termina() {
		this.dispose();
	}
	
	/**
	 * Devuelve un archivo, si es null lo solicita
	 * @return Fichero de escenario solicitado
	 */
	public File getFicheroEscenario(){
		if (filechoosed == null)
			filechoosed = solicitarSeleccionFichero();
		return filechoosed;
	}
	
	/**
	 * Invalida el fichero seleccionado
	 */
	public void errorFileEscenario() {
		filechoosed = null;
	}
	
	/**
	 * Muestra un dialogo de error
	 * @param string Titulo
	 * @param string2 Contenido del error
	 */
	public void muestraError(String string, String string2) {
		JOptionPane.showMessageDialog(null,string2,string,0,null);
	}
	
	/**
	 * Hace un cambio visual ante un obstaculo descubierto por los robots
	 * @param c coordenadas del obstaculo
	 */
	public void informarBloqueo(Coordenada c) {
		botonesMapa[c.getX()][c.getY()].addElement("escombro","Piedra");
	}
	
	/**
	 * Notifica a todos los elementos de la ventana que hay un cambio de estado en la simulacion
	 * @param st
	 */
	public void cambioEstado(String st) {
		for(ComponenteBotonMapa[] barr : botonesMapa){
			for(ComponenteBotonMapa b : barr){
				b.cambioEstado(st);
			}
		}
	}
	
	/**
	 * constructora, metodo para ser reutilizado.
	 * Establece el titulo, inicializa las variables
	 * Inicializa los componentes
	 * @throws Exception
	 */
	private void build(){
		isVisible = false;
		esc = new Escenario();
		setTitle("MRS - Simulator");
		posicionAgentes = new HashMap<String,Coordenada>();
		initComponentes();
		centerFrame();
	}
	
	/**
	 * Centra la ventana en la pantalla
	 */
	private void centerFrame() {

        Dimension windowSize = getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();

        int dx = centerPoint.x - windowSize.width / 2;
        int dy = centerPoint.y - windowSize.height / 2;    
        setLocation(dx, dy);
	}
	
	/**
	 * Dibuja un agente en una posicion
	 * @param idAgente nombre del agente
	 * @param coord posicion donde dibujar
	 * @param tipo Minero o Rescatador
	 * @return devuelve si ha conseguido pintarlo
	 */
	private boolean dibujaAgente(String idAgente, Coordenada coord, String tipo){
		int x = (int) coord.getX();
		int y = (int) coord.getY();
		if( x < 0 || y < 0 || x >= esc.getMapa().getNumRows() || y >= esc.getMapa().getNumCols())
			return false;
		botonesMapa[x][y].dibujaAgente(idAgente,tipo);
		posicionAgentes.put(idAgente, new Coordenada(coord));
		return true;
	}
	
	/**
	 * Elimina un agente de una posicion concreta. Si no existe en esa celda no ocurre nada.
	 * @param idAgente nombre del agente
	 * @param coord posicion de donde se quiere borrar
	 */
	private void eliminaAgente(String idAgente, Coordenada coord){
		int x = (int) coord.getX();
		int y = (int) coord.getY();
		if( x < 0 || y < 0 || x >= esc.getMapa().getNumRows() || y >= esc.getMapa().getNumCols())
			return;
		botonesMapa[x][y].eliminaAgente(idAgente);
		posicionAgentes.remove(idAgente);
	}

	/**
	 * Inicializa todos los componentes del JSWING
	 */
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
	
	/**
	 * Inicializa los botones de control de la simulacion
	 */
	private void initControlButtons(){
		ControlButtons = new JPanel();
		ControlButtons.setLayout(new FlowLayout());
		JButton start_stop = new JButton("Start");
		start_stop.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.notificar(VocabularioMRS.InputIniciaSimulacion);
			}
		});
		JButton restart = new JButton("reinicializar");
		restart.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.notificar(VocabularioMRS.InputIniciaSimulacion);
			}
		});
		ControlButtons.add(start_stop);
		ControlButtons.add(restart);
		add(ControlButtons,BorderLayout.SOUTH);
	}
	
	/**
	 * Inicializa la barra de menu y sus opciones
	 */
	private void initMenu(){
		menuBar = new JMenuBar();
		MenuFile = new JMenu("File");
		MenuFile.setMnemonic(KeyEvent.VK_F);
		MenuFile.getAccessibleContext().setAccessibleDescription(
		        "Load,save, modify files...");
		menuBar.add(MenuFile);	
		JMenuItem menu_Load = new JMenuItem("Load",KeyEvent.VK_L);
		menu_Load.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				filechoosed = solicitarSeleccionFichero();
				if(filechoosed != null){
					controlador.notificar(VocabularioMRS.InputCambioFicheroEscenario);
				}
			}
		});
		JMenuItem menu_save = new JMenuItem("Save",KeyEvent.VK_S);
		menu_save.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				File f = solicitarFicheroSalvar();
				if(f != null){
					try {
						controlador.saveMap(VisorEscenario.me.esc,f);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		JMenuItem menu_exit = new JMenuItem("Exit",KeyEvent.VK_E);
		menu_exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.notificar(VocabularioMRS.InputIniciaSimulacion);
			}
		});
		MenuFile.add(menu_Load);
		MenuFile.add(menu_save);
		MenuFile.addSeparator();
		MenuFile.add(menu_exit);
		setJMenuBar(menuBar);
	}

	/**
	 * Inicializa el selector de archivos
	 */
	private void initFileChooser(){
		fileChooser = new JFileChooser(rutaEscenarios);
		fileChooser.setDialogTitle("Seleccion de escenario");
	}
	
	/**
	 * Inicializa el contenedor del mapa a vacio
	 */
	private void initEmptyMap(){
		MapaPanel.setContentPane(new JPanel());
		add(MapaPanel,BorderLayout.CENTER);
	}

	/**
	 * Inicializa el contenedor del mapa a uno concreto
	 */
	private void buildMap(){
		if (esc == null || esc.getMapa() == null)
			initEmptyMap();
		JPanel mapita = new JPanel();
		int rows = esc.getMapa().getNumRows();
		int cols = esc.getMapa().getNumCols();
		mapita.setLayout(new GridLayout(rows,cols,0,0));

		botonesMapa = new ComponenteBotonMapa[rows][cols];
		for (int i =  0; i < rows; i++){
			for (int j = 0; j < cols; j++){
				botonesMapa[i][j] = new ComponenteBotonMapa(i,j,getType(i,j));
				botonesMapa[i][j].setOutPoint(this);
				if(TipoCelda.ESCOMBRO_UNK == esc.getMapa().getCoord(i,j))
					botonesMapa[i][j].addElement("PIEDRA", "NOPiedra");
				else if(TipoCelda.ESCOMBRO ==esc.getMapa().getCoord(i,j))
					botonesMapa[i][j].addElement("PIEDRA", "Piedra");
				mapita.add(botonesMapa[i][j]);
			}
		}
		MapaPanel.setContentPane(mapita);
		MapaPanel.validate();
		MapaPanel.repaint();
	}
	
	/**
	 * Pregunta al usuario por un fichero
	 * @return devuelve el fichero seleccionado
	 */
	private File solicitarSeleccionFichero() {
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"ficheros xml", "xml", "txt");
		fileChooser.setFileFilter(filter);
		File dir = fileChooser.getCurrentDirectory();
		int returnVal = fileChooser.showOpenDialog(this);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(dir);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			filechoosed = fileChooser.getSelectedFile();
			return filechoosed;
		} else{
			filechoosed = null;
			return filechoosed; // no ha seleccionado nada
		}
	}
	
	/**
	 * Pregunta al usuario por un destino
	 * @return devuelve el destino selceccionado
	 */
	private File solicitarFicheroSalvar() {
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"ficheros xml", "xml", "txt");
		fileChooser.setFileFilter(filter);
		File dir = fileChooser.getCurrentDirectory();
		int returnVal = fileChooser.showSaveDialog(this);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(dir);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		} else{
			return null; // no ha seleccionado nada
		}
	}

	/**
	 * Devuelve la codificacion de una casilla
	 * @param i coordenada x
	 * @param j coordenada y
	 * @return devuelve la codificacion de la casilla
	 */
	public int getType(int i, int j){
		Mapa Map = esc.getMapa();
		int rows = Map.getNumRows();
		int cols = Map.getNumCols();
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
	
	/**
	 * Cambia una celda a un nuevo tipo avisando a todas las celdas colindantes
	 * @param x posicion x
	 * @param y posicion y
	 * @param t nuevo tipo
	 * @throws Exception
	 */
	public void changeCelda(int x, int y, TipoCelda t) throws Exception{
		esc.getMapa().getMapa()[x][y]=t;
		controlador.changeMap(x,y,t);
		botonesMapa[x][y].changeTo(t, getType(x,y));
		updateCelda(x-1,y-1);
		updateCelda(x  ,y-1);
		updateCelda(x+1,y-1);
		updateCelda(x-1,y);
		updateCelda(x+1,y);
		updateCelda(x-1,y+1);
		updateCelda(x  ,y+1);
		updateCelda(x+1,y+1);
	}
	
	/**
	 * Actualiza una celda sin avisar a sus vecinos
	 * @param x posicion x
	 * @param y posicion y
	 */
	public void updateCelda(int x, int y){
		if(x < 0 || y < 0 || y >= esc.getMapa().getNumCols() || x >= esc.getMapa().getNumRows())
			return;
		botonesMapa[x][y].changeTo(esc.getMapa().getCoord(x, y), getType(x,y));
	}
	
	/**
	 * serial Identificador del componente GUI
	 */
	private static final long serialVersionUID = -418573958565443751L;
}
