package icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

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
	private boolean[][] Map;
	private int cols = 5;
	private int rows = 5;
	private boolean isVisible;
	public VisorEscenario() throws IOException{
		isVisible = false;
		setTitle("MRS - Simulator");
		Random  rnd = new Random();
		Map = new boolean[rows][cols];
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				Map[i][j] =  (rnd.nextInt()>0);
			}
		}
		initComponentes();
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	public void mostrar(){
		if(isVisible)
			return;
		isVisible = true;
		setVisible(true);
	}
	private void printMap(){
		for (int i =  0; i < rows; i++){
			for (int j = 0; j < cols; j++){
				if(Map[i][j])
					System.out.print("#");
				else
					System.out.print(".");
			}
			System.out.print("\n");
		}
	}
	private void initComponentes() throws IOException{
		setLayout(new BorderLayout());
		MapaPanel = new JPanel();
		MapaPanel.setLayout(new GridLayout(rows,cols,0,0));
		String path; 

		ComponenteBotonMapa labels[][] = new ComponenteBotonMapa[rows][cols];
		printMap();
		for (int i =  0; i < rows; i++){
			for (int j = 0; j < cols; j++){
				int t = getType(i,j);
				path = getIcono(t);
				//iconoFondo.setBorder(BorderFactory.createEmptyBorder());
				labels[i][j] = new ComponenteBotonMapa(path);
				MapaPanel.add(labels[i][j]);
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
		if(!Map[i][j])
			return t;
		if(i > 0 && Map[i-1][j])
			t|=0b0001;
		if(j > 0 && Map[i][j-1])
			t|=0b0010;
		if(i < rows-1 && Map[i+1][j])
			t|=0b0100;
		if(j < cols-1 && Map[i][j+1])
			t|=0b1000;
		if(t==0)
			t = 16;
		System.out.println("("+i+", "+j+") ->"+t);
		return t;
	}
	private String getIcono(int type){
		switch (type){
		case 0://nada
		case 1://
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
		case 15:
		case 16:
			return rutaArteEscenario+"mapa/"+type+".png";
		default:
			return rutaArteEscenario+"error.png";
		}
	}
	
	
	
	public static void main(String args[]){
		try {
			VisorEscenario ve = new VisorEscenario();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -418573958565443751L;
}
