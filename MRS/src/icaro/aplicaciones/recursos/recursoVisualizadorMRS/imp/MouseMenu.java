package icaro.aplicaciones.recursos.recursoVisualizadorMRS.imp;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import icaro.aplicaciones.MRS.informacion.InicioEstado;
import icaro.aplicaciones.MRS.informacion.TipoCelda;

/**
 * Clase que contiene el menu de edicion del mapa
 * @author Jesus Domenech
 */
public class MouseMenu extends MouseAdapter {

	/**
	 * Clase que contiene un elemento del menu de edicion del mapa
	 * @author Jesus Domenech
	 */
	private class MouseMenuItem extends JMenuItem {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6327543559125815303L;
		/**
		 * Referencia al boton del mapa
		 */
		private ComponenteBotonMapa btn;

		/**
		 * Constructora del elemento del menu
		 * @param btn Boton que lo contiene
		 * @param abs Accion a realizar
		 */
		public MouseMenuItem(ComponenteBotonMapa btn, AbstractAction abs) {
			super(abs);
			this.btn = btn;
		}

		/**
		 * Devuelve el Boton
		 * @return Devuelve el Boton
		 */
		public ComponenteBotonMapa getbtn() {
			return btn;
		}
	}
	
	/**
	 * Elemento emergente
	 */
	public JPopupMenu popup;
	
	/**
	 * Estado Actual del menu
	 */
	public String st;
	
	/**
	 * Referencia al boton
	 */
	private ComponenteBotonMapa btn;

	/**
	 * Constructora del Menu
	 * @param st Estado actual de la simulacion
	 * @param btn Boton
	 */
	public MouseMenu(String st, ComponenteBotonMapa btn) {
		this.st = st;
		this.btn = btn;
		popup = initPopUp();

	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		switch (st) {
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
	/**
	 * Cambia el estado actual
	 * @param st nuevo estado <code>InicioEstado</code>
	 */
	public void setEstado(String st) {
		this.st = st;
	}

	/**
	 * Inicializa el desplegable del menu
	 * @return devuelve el popup
	 */
	private JPopupMenu initPopUp() {
		JPopupMenu ppp = new JPopupMenu();
		ppp.add(new MouseMenuItem(this.btn, new AbstractAction("Escombro Desconocido") {
			private static final long serialVersionUID = -3721545538385414042L;

			@Override
			public void actionPerformed(ActionEvent e) {
				((MouseMenuItem) e.getSource()).getbtn().changeToAndNotify(TipoCelda.ESCOMBRO_UNK);
			}
		}));
		ppp.add(new MouseMenuItem(this.btn, new AbstractAction("Escombro Conocido") {
			private static final long serialVersionUID = 5538682552677449102L;

			public void actionPerformed(ActionEvent e) {
				((MouseMenuItem) e.getSource()).getbtn().changeToAndNotify(TipoCelda.ESCOMBRO);
			}
		}));
		ppp.add(new MouseMenuItem(this.btn, new AbstractAction("Pasillo") {
			private static final long serialVersionUID = -6613422859961131401L;

			public void actionPerformed(ActionEvent e) {
				((MouseMenuItem) e.getSource()).getbtn().changeToAndNotify(TipoCelda.PASILLO);
			}
		}));
		ppp.add(new MouseMenuItem(this.btn, new AbstractAction("Pared") {
			private static final long serialVersionUID = 810956241502426513L;

			public void actionPerformed(ActionEvent e) {
				((MouseMenuItem) e.getSource()).getbtn().changeToAndNotify(TipoCelda.PARED);
			}
		}));
		return ppp;
	}
}