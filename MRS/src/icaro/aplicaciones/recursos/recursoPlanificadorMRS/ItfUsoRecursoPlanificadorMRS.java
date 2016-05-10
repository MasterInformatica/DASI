package icaro.aplicaciones.recursos.recursoPlanificadorMRS;

import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;
import icaro.aplicaciones.MRS.informacion.Mapa;

import java.util.ArrayList;

import icaro.aplicaciones.MRS.informacion.Coordenada;

public interface ItfUsoRecursoPlanificadorMRS extends ItfUsoRecursoSimple{
	public void setMapa(Mapa mapa) throws Exception;
	public ArrayList<Coordenada> getRuta(Coordenada start, Coordenada finish) throws Exception;
	public void informarBloqueo(Coordenada c) throws Exception;
}
