package icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import icaro.aplicaciones.MRS.informacion.InicioEstado;
import icaro.aplicaciones.MRS.informacion.TipoCelda;
import icaro.aplicaciones.MRS.informacion.VocabularioMRS;

public class ComponenteBotonMapa extends JButton {
	
	//ARTE
	private static String rutaArteEscenario = VocabularioMRS.RutaArte+"/";
	private static String rutaArteBG = VocabularioMRS.RutaArteBG+"/";
	private static String rutaMin ="miner.png";
	private static String rutaRob ="robot.png";
	private static String rutaPie ="piedra.png";
	final JButton me;
	private ImageIcon bg;
	private CombineIcon ci;
    private MouseMenu mm;
    private String st;
	private VisorEscenario ve;
    private int _x,_y;
	public ComponenteBotonMapa( int x, int y,int type) {
		super();
		_x = x;
		_y = y;
		bg = new ImageIcon(getIcono(type));
		ci = new CombineIcon("background",bg);
		setIcon(ci);
		 me = this;
		
		setMargin(new Insets(0, 0, 0, 0));
		setBorder(new EmptyBorder(0,0,0,0));
		 
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				ComponenteBotonMapa btn = (ComponenteBotonMapa) e.getComponent();
				Dimension size = btn.getSize();
				btn.rescale(size.width,size.height);
			}

		});
		mm = new MouseMenu(InicioEstado.ST_NuevoEscenario,this);
		addMouseListener(mm);
	}
	
	public void cambioEstado(String n_st){
		st = n_st;
		mm.setEstado(n_st);
	}
	public void setOutPoint(VisorEscenario ve){
		this.ve = ve;
	}
	
	private String getIcono(int type){
		if(type >=0 && type <=16)
			return rutaArteBG+type+".png";
		else
			return rutaArteEscenario+"error.png";
	}
	public void removeElement(String id){
		ci.removeIcon(id);
		repaint();
	}
	public void addIcon(String id,ImageIcon ic, Dimension dimension){
		Image img = ic.getImage().getScaledInstance(dimension.width, dimension.height, Image.SCALE_FAST);
		ci.addIcon(id, new ImageIcon(img));
		repaint();
	}
	
	public void addElement(String id,String Type){
		switch(Type){
		case "Miner":
			ci.addIcon(id, new ImageIcon(rutaArteEscenario+rutaMin));
			break;
		case "Robot":
			ci.addIcon(id, new ImageIcon(rutaArteEscenario+rutaRob));
			break;
		case "Escombro":
		case "NOPiedra":
			ci.addIcon("PIEDRA", new ImageIcon(rutaArteEscenario+"NO"+rutaPie));
			break;
		case "Piedra":
			ci.removeIcon("PIEDRA");
			ci.addIcon("PIEDRA", new ImageIcon(rutaArteEscenario+rutaPie));
			break;
		}
		ci.rescale(ci.getIconWidth(), ci.getIconHeight());
		repaint();
	}
	public void rescale(int w, int h){
		ci.rescale(w,h);
		repaint();
	}
	
	public void dibujaAgente(String idAgente,String tipo) {
		addElement(idAgente,tipo);
	}
	public void dibujaAgente2(String idAgente) {
		addElement(idAgente,"Miner");
	}
	public void eliminaAgente(String idAgente) {
		removeElement(idAgente);
	}
	
	public class CombineIcon implements Icon {
	    private Vector<ImageIcon> list;
	    private Vector<String> listId;
	    private Dimension lastSize;

	    public CombineIcon(){
	    	list = new Vector<ImageIcon>();
	    	listId = new Vector<String>();
	    	lastSize = new Dimension(50,50);
	    }
	    public CombineIcon(String id, ImageIcon ic) {
	    	this();
	    	addIcon(id,ic);
	    }
	    
	    public void rescale(int wd, int hg){
	    	Image im;
	    	int w=wd;
	    	try{
	    		im = list.get(0).getImage().getScaledInstance(wd,hg,Image.SCALE_FAST);
	    		list.set(0, new ImageIcon(im));

		    	for(int i = 1; i < list.size(); i++){
		    		im = list.get(i).getImage().getScaledInstance(w,hg,Image.SCALE_FAST);
		    		list.set(i, new ImageIcon(im));
		    	}
		    	lastSize = new Dimension(wd,hg);
	    	}catch(ArrayIndexOutOfBoundsException e){
	    		System.err.println("Error: Intento de solucion ejecutado");
	    		ImageIcon bg = list.get(0);
	    		list = new Vector<ImageIcon>();
	    		list.add(bg);
	    		listId = new Vector<String>();
	    		listId.add("backgroud");
	    		rescale(wd,hg);
	    	}
	    	
	    }
	    
	    public void addIcon(String id,ImageIcon ic){
	    	Image im = ic.getImage().getScaledInstance(lastSize.width,lastSize.height,Image.SCALE_FAST);
	    	list.addElement(new ImageIcon(im));
	    	listId.addElement(id);
	    }
	    
	    public void addIcon(int idx, String id,ImageIcon ic){
	    	Image im = ic.getImage().getScaledInstance(lastSize.width,lastSize.height,Image.SCALE_FAST);
	    	list.add(idx,new ImageIcon(im));
	    	listId.add(idx,id);
	    }
	    
	    public void removeIcon(int idx){
	    	try{
	    		list.remove(idx);
	    		listId.remove(idx);
	    	}catch(Exception e){
	    		
	    	}
	    }
	    
	    public void removeIcon(String id){
	    	Iterator<String> it = listId.iterator();
	    	int i = 0;
	    	while(it.hasNext()){
	    		String s = it.next();
	    		if(s.equals(id)){
	    			removeIcon(i);
	    			break;
	    		}
	    		i++;
	    	}
	    }

	    public int getIconHeight() {
	    	int max = 0;
	    	int h;
	    	for(int i = 0; i < list.size(); i++){
	    		h = list.get(i).getIconHeight();
	    		if(h > max){
	    			max = h;
	    		}
	    	}
	        return max;
	    }

	    public int getIconWidth() {
	    	int max = 0;
	    	int h;
	    	for(int i = 0; i < list.size(); i++){
	    		h = list.get(i).getIconWidth();
	    		if(h > max){
	    			max = h;
	    		}
	    	}
	        return max;
	    }
	    
	    @Override
	    public void paintIcon(Component c, Graphics g, int x, int y) {
	    	list.get(0).paintIcon(c,g,x,y);
	    	int inc = 0;
	    	for(int i = 1; i < list.size(); i++){
	    		list.get(i).paintIcon(c,g,inc,y);
	    		inc += 10;
	    	}
	    }	
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6020843624647630830L;

	
	/*
	 * Le piden cambiar y notificar
	 */
	public void changeToAndNotify(TipoCelda tipo) {
		try{
			ve.changeCelda(_x, _y, tipo);	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * @param tipo
	 * @param tipo_pasillo
	 */
	public void changeTo(TipoCelda tipo, int tipo_pasillo){
		bg = new ImageIcon(getIcono(tipo_pasillo));
		ci = new CombineIcon("background",bg);
		setIcon(ci);
		ci.rescale(this.getSize().width, this.getSize().height);
		switch(tipo){
		case ESCOMBRO:
			addElement("PIEDRA","Piedra");
			break;
		case ESCOMBRO_UNK:
			addElement("PIEDRA","NOPiedra");
			break;
		default:
			break;
		}
	}
	
	
}