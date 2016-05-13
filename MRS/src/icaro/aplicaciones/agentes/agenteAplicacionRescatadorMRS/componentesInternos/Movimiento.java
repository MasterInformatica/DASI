package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.componentesInternos;

import java.util.ArrayList;

import icaro.aplicaciones.MRS.informacion.Coordenada;
import icaro.aplicaciones.MRS.informacion.Rescatador;
import icaro.aplicaciones.MRS.informacion.RobotStatus;
import icaro.aplicaciones.recursos.recursoPersistenciaMRS.ItfUsoRecursoPersistenciaMRS;
import icaro.aplicaciones.recursos.recursoPlanificadorMRS.ItfUsoRecursoPlanificadorMRS;
import icaro.aplicaciones.recursos.recursoVisualizadorMRS.ItfUsoRecursoVisualizadorMRS;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ItfProcesadorObjetivos;

public class Movimiento extends Thread{
	public Coordenada destino;
	public Rescatador yo;
	public boolean alcanzado;
	public String mineroMvto = null;
	
	public ItfUsoRecursoPlanificadorMRS itfusoRecPlanRuta;
	public ItfUsoRecursoVisualizadorMRS itfusoRecVisualizador;
	public ItfUsoRecursoPersistenciaMRS itfusoRecPersistencia;
	
	public ItfProcesadorObjetivos itfHechos;
	
	public Movimiento(	Rescatador yo,
						ItfUsoRecursoPlanificadorMRS itfusoRecPlanRuta,
						ItfUsoRecursoVisualizadorMRS itfusoRecVisualizador,
						ItfUsoRecursoPersistenciaMRS itfusoRecPersistencia
	){
		this. yo = yo;
		this.itfusoRecPlanRuta = itfusoRecPlanRuta;
		this.itfusoRecVisualizador = itfusoRecVisualizador;
		this.itfusoRecPersistencia = itfusoRecPersistencia;
		
		this.alcanzado = true;
	}
	
	@Override
	public synchronized void run() {
		while(true){
			try{ Thread.sleep((long) (0.2*1e3));
			}catch (InterruptedException e) {e.printStackTrace();}
			
			if (!alcanzado){//destino != null){
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
							itfusoRecVisualizador.mueveRobot(yo.getName(), yo.getCoordenadasActuales());
						
							if(mineroMvto != null)
								itfusoRecVisualizador.mueveVictima(mineroMvto, yo.getCoordenadasActuales());
							
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
		int st = this.yo.getStatus();
		if(st == RobotStatus.PARADO){
			this.yo.SetStatus(RobotStatus.HACIA_MINERO);
			mineroMvto = null;
		}else if(st==RobotStatus.CON_MINERO)
			this.yo.SetStatus(RobotStatus.HACIA_SALIDA);
			
		this.destino = destino;
		this.alcanzado = false;
	}
	
	public void setMinero(String m){
		this.mineroMvto = m;
/*		try {
			this.itfusoRecVisualizador.mostrarVictimaRescatada(m);1
		} catch (Exception e) {
			e.printStackTrace();
		} */
	}
	
	
	private void alcanzarDestino(){
		this.alcanzado = true;
		
		
		int st = this.yo.getStatus();
		if(st == RobotStatus.HACIA_MINERO){
			this.yo.SetStatus(RobotStatus.CON_MINERO);
		}
		else if(st==RobotStatus.HACIA_SALIDA){
			mineroMvto = null;
			this.yo.SetStatus(RobotStatus.PARADO);
		}
		
		this.itfHechos.actualizarHecho(this.yo);
		
		
		//this.destino = null;
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

	public void setItHechos(ItfProcesadorObjetivos envioHechos) {
		this.itfHechos = envioHechos;
	}

}
