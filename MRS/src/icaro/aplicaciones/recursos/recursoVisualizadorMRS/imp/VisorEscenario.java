package icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import icaro.aplicaciones.MRS.informacion.Mapa;
import icaro.aplicaciones.MRS.informacion.TipoCelda;
import icaro.aplicaciones.Rosace.informacion.Coordinate;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VisorEscenario extends JFrame {

	//ARTE
	private String rutaArteEscenario = "images/";
	
	// SWING elements
	private JPanel MapaPanel;
	
	// Logica (Modelo)
	//private boolean[][] Map;
	private Mapa Map;
	private ComponenteBotonMapa botonesMapa[][];
	private HashMap<String, Coordinate> posicionAgentes;
	private int cols = 25;
	private int rows = 25;
	private boolean isVisible;

	private ControladorVisorSimulador controlador;
	
	
	public VisorEscenario() throws Exception{
		build();
	}
	
	//TODO : Constructor VisorControlSimu recibiendo Controladora del visor
	public VisorEscenario(ControladorVisorSimulador control) throws Exception{
		controlador = control;
		build();
	}
	
	private void build() throws Exception{
		isVisible = false;
		setTitle("MRS - Simulator");
		// TODO remove random Map build
		/*Random  rnd = new Random();
		Map = new boolean[rows][cols];
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				Map[i][j] =  (rnd.nextInt()>0);
			}
		}*/
		posicionAgentes = new HashMap<String,Coordinate>();
		//initComponentes();
		//setVisible(true);
	}
	
	
	public boolean mueveAgente(String idAgente, Coordinate coord) {
		// Get y remove Current position
		if(posicionAgentes.containsKey(idAgente)){
			Coordinate org_coord = posicionAgentes.get(idAgente);
			eliminaAgente(idAgente,org_coord);
		}
		// Set y draw new position
		
		return dibujaAgente(idAgente,coord);
	}
	
	private boolean dibujaAgente(String idAgente, Coordinate coord){
		int x = (int) coord.getX();
		int y = (int) coord.getY();
		if( x < 0 || y < 0 || x >= rows || y >= cols)
			return false;
		botonesMapa[x][y].dibujaAgente(idAgente);
		posicionAgentes.put(idAgente, coord);
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
	
	public void mostrar(Mapa mapa){
		Map = mapa;
		initComponentes();
		if(isVisible)
			return;
		isVisible = true;
		setVisible(true);
	}
	
	private void printMap(){
		for (int i =  0; i < rows; i++){
			for (int j = 0; j < cols; j++){
				if(Map.getCoord(i,j) == TipoCelda.ESCOMBRO)
					System.out.print("*");
				else if(Map.getCoord(i,j) == TipoCelda.PASILLO)
					System.out.print("·");
				else
					System.out.print("#");
			}
			System.out.print("\n");
		}
	}
	
	private void initComponentes() {
		setLayout(new BorderLayout());
		MapaPanel = new JPanel();
		MapaPanel.setLayout(new GridLayout(rows,cols,0,0));
		String path; 

		botonesMapa = new ComponenteBotonMapa[rows][cols];
		for (int i =  0; i < rows; i++){
			for (int j = 0; j < cols; j++){
				int t = getType(i,j);
				path = getIcono(t);
				botonesMapa[i][j] = new ComponenteBotonMapa(path);
				MapaPanel.add(botonesMapa[i][j]);
			}
		}
		Container c = getContentPane();
        c.setBackground(Color.YELLOW);
		Dimension d = new Dimension(600,600);
        c.setPreferredSize(d);
		add(MapaPanel,BorderLayout.CENTER);
		pack();
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
	
	private String getIcono(int type){
		if(type >=0 && type <=16)
			return rutaArteEscenario+"mapa/"+type+".png";
		else
			return rutaArteEscenario+"error.png";
	}
	
	public void termina() {
		this.dispose();
	}
	
	public static void main(String args[]){
		try {
			VisorEscenario ve = new VisorEscenario();
			ve.printMap();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -418573958565443751L;


	public void setMapa(Mapa mapa) {
		Map = mapa;
	}

}
