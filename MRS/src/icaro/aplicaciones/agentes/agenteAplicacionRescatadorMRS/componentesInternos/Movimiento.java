package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.componentesInternos;

import java.util.ArrayList;

import icaro.aplicaciones.MRS.informacion.Coordenada;
import icaro.aplicaciones.MRS.informacion.Rescatador;
import icaro.aplicaciones.recursos.recursoPersistenciaMRS.ItfUsoRecursoPersistenciaMRS;
import icaro.aplicaciones.recursos.recursoPlanificadorMRS.ItfUsoRecursoPlanificadorMRS;
import icaro.aplicaciones.recursos.recursoVisualizadorMRS.ItfUsoRecursoVisualizadorMRS;

public class Movimiento extends Thread{
	public Coordenada destino;
	public Rescatador yo;
	
	public ItfUsoRecursoPlanificadorMRS itfusoRecPlanRuta;
	public ItfUsoRecursoVisualizadorMRS itfusoRecVisualizador;
	public ItfUsoRecursoPersistenciaMRS itfusoRecPersistencia;
	
	public Movimiento(	Rescatador yo,
						ItfUsoRecursoPlanificadorMRS itfusoRecPlanRuta,
						ItfUsoRecursoVisualizadorMRS itfusoRecVisualizador,
						ItfUsoRecursoPersistenciaMRS itfusoRecPersistencia
	){
		this. yo = yo;
		this.itfusoRecPlanRuta = itfusoRecPlanRuta;
		this.itfusoRecVisualizador = itfusoRecVisualizador;
		this.itfusoRecPersistencia = itfusoRecPersistencia;
	}
	
	@Override
	public synchronized void run() {
		while(true){
			try{ Thread.sleep((long) (0.2*1e3));
			}catch (InterruptedException e) {e.printStackTrace();}
			
			if (destino != null){
				if ((destino.getX() != yo.getCoordenadasActuales().getX()) ||
						(destino.getY() != yo.getCoordenadasActuales().getY())){
					
					ArrayList <Coordenada> ruta = null;
					try{
						ruta = itfusoRecPlanRuta.getRuta(yo.getCoordenadasActuales(), destino);
					}catch (Exception e){ e.printStackTrace();}
					
					Coordenada siguientePaso = null;
					
					try{
						siguientePaso = ruta.get(1);
					}catch(Exception e){e.printStackTrace();}
					
					if(siguientePaso != null){
						yo.move(siguientePaso);
						try {
							itfusoRecVisualizador.mueveAgente(yo.getName(), yo.getCoordenadasActuales());
						}catch(Exception e){e.printStackTrace();}
					}
					
					this.checkBloqueos();
					
					if ((destino.getX() == yo.getCoordenadasActuales().getX()) &&
							(destino.getY() == yo.getCoordenadasActuales().getY()))
						alcanzarDestino();
				}else{
					alcanzarDestino();
				}
			}
		}
	}
	
	public void setDestino(Coordenada destino){
		this.destino = destino;
	}
	
	private void alcanzarDestino(){
		this.destino = null;
	}
	
	private void checkBloqueos(){
		Coordenada c = new Coordenada(yo.getCoordenadasActuales());
		ArrayList<Coordenada> bloqueos = null;
		try {
			bloqueos = this.itfusoRecPersistencia.getEscenario().getMapa().getObstaculos(c);
		}catch(Exception e1){e1.printStackTrace();}
		

		if(bloqueos != null && bloqueos.size()>0){
			for(Coordenada cc : bloqueos){
				try {
					this.itfusoRecPlanRuta.informarBloqueo(cc);
				}catch(Exception e){e.printStackTrace();}
				try {
					this.itfusoRecVisualizador.informarBloqueo(cc);
				}catch(Exception e){e.printStackTrace();}
			}
		}
	}

}
