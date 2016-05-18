package icaro.aplicaciones.recursos.recursoEstadisticaMRS.imp;

import java.awt.FlowLayout;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Ventana de Estadisticas
 * @author Hristo Ivanov
 */
public class VisorEstadistica extends JFrame{

	private static final long serialVersionUID = 4843580013703665703L;

	/**
	 * Constructor de la ventana de Estadisticas
	 * @param inicioDeRescate
	 * @param finDeRescate
	 * @param numeroDeRescatadores
	 * @param numeroDeVictimas
	 * @param numeroDeVictimasRescatadas
	 * @param movimientosPorRescatador
	 * @param tiempoMaximoDeRecate
	 * @param tiempoMinimoDeRecate
	 * @param timepoMedioDeRecate
	 * @param numeroDeObstaculosEncontrados
	 * @throws Exception
	 */
	public VisorEstadistica(
			long inicioDeRescate,
			long finDeRescate,
			int numeroDeRescatadores,
			int numeroDeVictimas,
			int numeroDeVictimasRescatadas,
			Map<String, Integer>movimientosPorRescatador,
			long tiempoMaximoDeRecate,
			long tiempoMinimoDeRecate,
			float timepoMedioDeRecate,
			int numeroDeObstaculosEncontrados
	) throws Exception{
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setTitle("MRS - Estadisticas");
		
		int cont = 0;
		
		Date dateInicioDeRescate = new Date();
		Date dateFinDeRescate = new Date();
		try {
			dateInicioDeRescate 		= new Date(inicioDeRescate);
			dateFinDeRescate 			= new Date(finDeRescate);
		}catch(Exception e){e.printStackTrace();}
		JLabel labelInicioDeRescate		= new JLabel("Inicio de la simulaci�n: " + dateInicioDeRescate.toString());
		JLabel labelFinDeRescate		= new JLabel("Fin de la simulaci�n: " + dateFinDeRescate.toString());
		labelInicioDeRescate.setLocation(0, cont * labelInicioDeRescate.getHeight());
		cont += 1;
		labelFinDeRescate.setLocation(0, cont * labelInicioDeRescate.getHeight());
		cont += 1;
		this.add(labelInicioDeRescate);
		this.add(labelFinDeRescate);
		
		JLabel labelNumeroDeRescatadores = new JLabel("Numero de rescatadores: "+ numeroDeRescatadores + "                                              ");
		labelNumeroDeRescatadores.setLocation(0, cont * labelInicioDeRescate.getHeight());
		this.add(labelNumeroDeRescatadores);
		cont += 1;
		
		JLabel labelNumeroDeVictimas = new JLabel("Numero de visctimas: "+ numeroDeVictimas+ "                                                          ");
		labelNumeroDeVictimas.setLocation(0, cont * labelInicioDeRescate.getHeight());
		this.add(labelNumeroDeVictimas);
		cont += 1;
		
		
		JLabel labelMovimientosPorRescatador = new JLabel("Movimientos por rescatador:                                                                  ");
		labelMovimientosPorRescatador.setLocation(0, cont * labelInicioDeRescate.getHeight());
		this.add(labelMovimientosPorRescatador);
		cont += 1;
		
		for(Entry<String, Integer> entry : movimientosPorRescatador.entrySet()) {
			JLabel labelAuxMovimientosPorRescatador = new JLabel("      "+entry.getKey()+": "+entry.getValue()+"                                         ");
			labelMovimientosPorRescatador.setLocation(0, cont * labelInicioDeRescate.getHeight());
			this.add(labelAuxMovimientosPorRescatador);
			cont += 1;
		}
		
		JLabel labelTiempoMaximoDeRecate = new JLabel("Tiempo m�ximo de rescate: " + tiempoMaximoDeRecate + "                                            ");
		labelTiempoMaximoDeRecate.setLocation(0, cont * labelInicioDeRescate.getHeight());
		this.add(labelTiempoMaximoDeRecate);
		cont += 1;
		
		JLabel labelTiempoMinimoDeRecate = new JLabel("Tiempo minimo de rescate: " + tiempoMinimoDeRecate + "                                            ");
		labelTiempoMinimoDeRecate.setLocation(0, cont * labelInicioDeRescate.getHeight());
		this.add(labelTiempoMinimoDeRecate);
		cont += 1;
		
		JLabel labelTimepoMedioDeRecate = new JLabel("Tiempo medio de rescate: " + timepoMedioDeRecate + "                                               ");
		labelTimepoMedioDeRecate.setLocation(0, cont * labelInicioDeRescate.getHeight());
		this.add(labelTimepoMedioDeRecate);
		cont += 1;
		
		JLabel labelNumeroDeObstaculosEncontrados = new JLabel("Numero de obstaculos encontrados: " + numeroDeObstaculosEncontrados + "                  ");
		labelNumeroDeObstaculosEncontrados.setLocation(0, cont * labelInicioDeRescate.getHeight());
		this.add(labelNumeroDeObstaculosEncontrados);
		cont += 1;

		
		this.setSize(400, 300);
		this.setVisible(true);
		this.setResizable(false);
	}
}
