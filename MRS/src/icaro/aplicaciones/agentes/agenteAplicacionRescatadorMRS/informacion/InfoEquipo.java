package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class InfoEquipo {
	private Lock lock = null;
	private Map<String, Integer> mapaInfoEquipo = null;


	public InfoEquipo(){
		this.lock = new ReentrantLock();
		this.mapaInfoEquipo = new Hashtable<String, Integer>(); 
	}
	
	public void addRobot(String str){
		{
			this.lock.lock();
			
			int s = this.mapaInfoEquipo.size();
			this.mapaInfoEquipo.put(str, s);
		
			this.lock.unlock();
		}
	}
	
	public int getIdRobot(String str){
		return this.mapaInfoEquipo.get(str);
	}
	
	public int getSize(){
		int s;
		{
			this.lock.lock();
			s = this.mapaInfoEquipo.size();
			this.lock.unlock();
		}
		
		return s;
	}
}