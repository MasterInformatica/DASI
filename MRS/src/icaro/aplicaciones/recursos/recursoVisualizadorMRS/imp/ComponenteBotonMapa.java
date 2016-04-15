package icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ComponenteBotonMapa extends JButton {
	private ImageIcon bg;
	private ImageIcon min,rob,pie;
	private CombineIcon ci;
	private int prof;
	public ComponenteBotonMapa(String file) throws IOException {
		super();
		prof = 1;
		bg = new ImageIcon(file);
		min = new ImageIcon("images/miner.png");
		rob = new ImageIcon("images/miner.png");
		pie = new ImageIcon("images/miner.png");
		//ci = new CombineIcon("background",bg);
		
		setMargin(new Insets(0, 0, 0, 0));
		setBorder(new EmptyBorder(0,0,0,0));
		addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
		    ci.addIcon("min"+prof, min);
		    prof++;
		  }
		});
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				Random rnd = new Random();
				JButton btn = (JButton) e.getComponent();
				Dimension size = btn.getSize();
				Insets insets = btn.getInsets();
				size.width -= insets.left + insets.right;
				size.height -= insets.top + insets.bottom;
				Image img = bg.getImage().getScaledInstance(size.width,size.height,Image.SCALE_FAST);
				Image img2 = min.getImage().getScaledInstance(size.width,size.height,Image.SCALE_FAST);
				//ci.rescale(size.width,size.height);
				if(rnd.nextBoolean())
					btn.setIcon(new CombineIcon(new ImageIcon(img),new ImageIcon(img2)));
				else
					btn.setIcon(new ImageIcon(img));
			}

		});
	}
	public void removeElement(String id){
		ci.removeIcon(id);
	}
	
	public void addElement(String id,String Type){
		switch(Type){
		case "Miner":
			ci.addIcon(id, min);
			break;
		case "Robot":
			ci.addIcon(id, rob);
			break;
		case "Piedra":
			ci.addIcon(id, pie);
			break;
		}
	}
	
	
	public class CombineIcon implements Icon {
	    private Vector<ImageIcon> list;
	    private Vector<String> listId;

	    public CombineIcon(){
	    	list = new Vector<ImageIcon>();
	    	listId = new Vector<String>();
	    }
	    public CombineIcon(ImageIcon id, ImageIcon ic) {
	    	this();
	    	addIcon("a",id);
	    	addIcon("b",ic);
	    }
	    public CombineIcon(String id, ImageIcon ic) {
	    	this();
	    	addIcon(id,ic);
	    }
	    
	    public void rescale(int wd, int hg){
	    	System.out.println(hg+" rescale "+wd);
	    	Image im;
	    	int w=wd;
	    	if ( list.size()>1){
	    		//h = hg/list.size()-1;
	    		w = wd/list.size()-1;
	    	}
	    	im = list.get(0).getImage();
    		im.getScaledInstance(hg,wd,Image.SCALE_SMOOTH);
    		list.set(0, new ImageIcon(im));

	    	for(int i = 1; i < list.size(); i++){
	    		im = list.get(i).getImage();
	    		im.getScaledInstance(hg,w,Image.SCALE_SMOOTH);
	    		list.set(i, new ImageIcon(im));
	    	}
	    	
	    }
	    
	    public void addIcon(String id,ImageIcon ic){
	    	list.addElement(ic);
	    	listId.addElement(id);
	    }
	    
	    public void addIcon(int idx, String id,ImageIcon ic){
	    	list.add(idx,ic);
	    	listId.add(idx,id);
	    }
	    
	    public void removeIcon(int idx){
	    	list.remove(idx);
	    	listId.remove(idx);
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
	    	int inc = x;
	    	for(int i = 1; i < list.size(); i++){
	    		list.get(i).paintIcon(c,g,inc,y);
	    		inc += 10;
	    	}
	    }

		
	}
}