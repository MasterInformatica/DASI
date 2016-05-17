package icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import icaro.aplicaciones.MRS.informacion.InicioEstado;
import icaro.aplicaciones.MRS.informacion.TipoCelda;

public class MouseMenu extends MouseAdapter{
	private class MouseMenuItem extends JMenuItem{
	   private ComponenteBotonMapa btn;
	   public MouseMenuItem(ComponenteBotonMapa btn,AbstractAction abs){
		   super(abs);
		   this.btn = btn;
	   }
	   public ComponenteBotonMapa getbtn(){
		   return btn;
	   }
   }
	
	public JPopupMenu popup;
	public String st;
	private ComponenteBotonMapa btn;
	public MouseMenu(String st,ComponenteBotonMapa btn){
		this.st = st;
		this.btn = btn;
		popup = initPopUp();
		
	}
	public void mousePressed(MouseEvent e) {
		switch(st){
		case InicioEstado.ST_Inicio:
			break;
		case InicioEstado.ST_Fin:
            popup.show(e.getComponent(), e.getX(), e.getY());
            break;
		case InicioEstado.ST_NuevoEscenario:
			popup.show(e.getComponent(), e.getX(), e.getY());
            break;
		default:
			break;
		}
    }
	
	public void setEstado(String st){
		this.st = st;
	}
	
	private JPopupMenu initPopUp(){
		
		JPopupMenu ppp = new JPopupMenu();
		ppp.add(new MouseMenuItem(this.btn,new AbstractAction("Escombro Desconocido") {
			 @Override
		        public void actionPerformed(ActionEvent e) {
				 ((MouseMenuItem)e.getSource()).getbtn().changeToAndNotify(TipoCelda.ESCOMBRO_UNK);
		        }

		    }));
		   ppp.add(new MouseMenuItem(this.btn,new AbstractAction("Escombro Conocido") {
		        public void actionPerformed(ActionEvent e) {
		        	((MouseMenuItem)e.getSource()).getbtn().changeToAndNotify(TipoCelda.ESCOMBRO);
		        }
		    }));
		   ppp.add(new MouseMenuItem(this.btn,new AbstractAction("Pasillo") {
		        public void actionPerformed(ActionEvent e) {
		        	((MouseMenuItem)e.getSource()).getbtn().changeToAndNotify(TipoCelda.PASILLO);
		        }
		    }));
		   ppp.add(new MouseMenuItem(this.btn,new AbstractAction("Pared") {
		        public void actionPerformed(ActionEvent e) {
		        	((MouseMenuItem)e.getSource()).getbtn().changeToAndNotify(TipoCelda.PARED);
		        }
		    }));
		   return ppp;
	}
}