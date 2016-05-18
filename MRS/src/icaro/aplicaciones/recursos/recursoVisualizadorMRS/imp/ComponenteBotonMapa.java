package icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import icaro.aplicaciones.MRS.informacion.InicioEstado;
import icaro.aplicaciones.MRS.informacion.TipoCelda;
import icaro.aplicaciones.MRS.informacion.VocabularioMRS;

/**
 * Clase del Boton que se incorpora a la interfaz, 
 * permite tener diferente cantidad de iconos en el mismo boton
 * @author Jesus Domenech
 */
public class ComponenteBotonMapa extends JButton {
	
	/**
	 * ruta del las imagenes genericas del visualizador
	 */
	private static String rutaArteEscenario = VocabularioMRS.RutaArte+"/";
	
	/**
	 * ruta de las imagenes del fondo, galerias en diferentes direcciones
	 */
	private static String rutaArteBG = VocabularioMRS.RutaArteBG+"/";
	
	/**
	 * nombre del fichero del minero
	 */
	private static String rutaMin ="miner.png";
	
	/**
	 * nombre del fichero del robot
	 */
	private static String rutaRob ="robot.png";
	
	/**
	 * nombre del fichero de un obstaculo
	 */
	private static String rutaPie ="piedra.png";
	
	/**
	 * Icono Background
	 */
	private ImageIcon bg;
	
	/**
	 * Icono Combinado
	 */
	private CombineIcon ci;
	
	/**
	 * Desplegable edicion mapa
	 */
    private MouseMenu mm;
    
    /**
     * Referencia al contenedor de botones
     */
	private VisorEscenario ve;
	
	/**
	 * Posicion del boton
	 */
    private int _x,_y;
    
    /**
     * Constructora del boton
     * @param x posicion columnas
     * @param y posicion filas
     * @param type tipo de casilla
     */
	public ComponenteBotonMapa( int x, int y,int type) {
		super();
		_x = x;
		_y = y;
		bg = new ImageIcon(getIcono(type));
		ci = new CombineIcon("background",bg);
		setIcon(ci);
		
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
	
	/**
	 * Recibe notificaciones de cambio de estado de la simulacion 
	 * para reaccionar acorde a el
	 * @param n_st estado definido en <code>IncioEstado</code>
	 */
	public void cambioEstado(String n_st){
		mm.setEstado(n_st);
	}
	
	/**
	 * Cambia el boton y notifica el cambio.
	 * @param tipo Nuevo tipo de casillas
	 */
	public void changeToAndNotify(TipoCelda tipo) {
		try{
			ve.changeCelda(_x, _y, tipo);	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Cambia el tipo de boton
	 * @param tipo nuevo tipo
	 * @param tipo_pasillo tipo de pasillo en su caso
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
	
	/**
	 * Setea el contenedor de botones
	 * @param ve Ventana principal que contiene el boton
	 */
	public void setOutPoint(VisorEscenario ve){
		this.ve = ve;
	}
	
	/**
	 * devuelve la ruta del icono background segun su codificacion
	 * @param type codificacion de la casilla
	 * @return devuelve la ruta del icono
	 */
	private String getIcono(int type){
		if(type >=0 && type <=16)
			return rutaArteBG+type+".png";
		else
			return rutaArteEscenario+"error.png";
	}
	
	/**
	 * elimina un componente del conjunto de iconos
	 * @param id nombre a eliminar del conjunto de iconos
	 */
	public void removeElement(String id){
		ci.removeIcon(id);
		repaint();
	}
	
	/**
	 * Incorpora un nuevo icono
	 * @param id nombre del nuevo icono a incorporar
	 * @param ic Icono a incorporar
	 * @param dimension size actual del boton
	 */
	public void addIcon(String id,ImageIcon ic, Dimension dimension){
		Image img = ic.getImage().getScaledInstance(dimension.width, dimension.height, Image.SCALE_FAST);
		ci.addIcon(id, new ImageIcon(img));
		repaint();
	}
	
	/**
	 * incorpora un nuevo componente (agente u obstaculo)
	 * @param id nombre del componente
	 * @param Type tipo del componente puede ser <code> Miner |Robot | Escombro | Piedra | NOPiedra </code>
	 */
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
	
	/**
	 * reescala las imagenes del boton
	 * @param w Ancho del boton
	 * @param h Altura del boton
	 */
	public void rescale(int w, int h){
		ci.rescale(w,h);
		repaint();
	}
	
	/**
	 * Dibuja un agente
	 * @param idAgente nombre del agente
	 * @param tipo tipo del agente
	 */
	public void dibujaAgente(String idAgente,String tipo) {
		addElement(idAgente,tipo);
	}
	/*public void dibujaAgente2(String idAgente) {
		addElement(idAgente,"Miner");
	}*/
	public void eliminaAgente(String idAgente) {
		removeElement(idAgente);
	}
	
	/**
	 * Clase Multiples Iconos
	 * @author Jesus Domenech
	 */
	public class CombineIcon implements Icon {
		/**
		 * Lista de Iconos 
		 */
	    private Vector<ImageIcon> list;
	    
	    /**
	     * Lista de IDs de los Iconos
	     */
	    private Vector<String> listId;
	    
	    /**
	     * Ultimo size de las imagenes
	     */
	    private Dimension lastSize;

	    /**
	     * Constructora del Icono Combinado
	     */
	    public CombineIcon(){
	    	list = new Vector<ImageIcon>();
	    	listId = new Vector<String>();
	    	lastSize = new Dimension(50,50);
	    }
	    
	    /**
	     * Constructora del Icono Combinado
	     * @param id nombre del id, "background" en general.
	     * @param ic Icono inicial
	     */
	    public CombineIcon(String id, ImageIcon ic) {
	    	this();
	    	addIcon(id,ic);
	    }
	    
	    /**
	     * Reescala todas las imagenes del Icono actual
	     * @param wd Anchura de la imagen
	     * @param hg Altura de la imagen
	     */
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
	    
	    /**
	     * Incorpora un nuevo Icono
	     * @param id nombre del Icono
	     * @param ic Icono a incorporar
	     */
	    public void addIcon(String id,ImageIcon ic){
	    	Image im = ic.getImage().getScaledInstance(lastSize.width,lastSize.height,Image.SCALE_FAST);
	    	list.addElement(new ImageIcon(im));
	    	listId.addElement(id);
	    }
	    
	    /**
	     * Elimina un icono segun su posicion
	     * @param idx posicion del icono a eliminar
	     */
	    public void removeIcon(int idx){
	    	try{
	    		list.remove(idx);
	    		listId.remove(idx);
	    	}catch(Exception e){
	    		
	    	}
	    }
	    
	    /**
	     * Elimina un icono segun su nombre
	     * @param id nombre del icono
	     */
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
	    
	    @Override
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

	    @Override
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
	 * serial Identificador del componente GUI
	 */
	private static final long serialVersionUID = -6020843624647630830L;	
}