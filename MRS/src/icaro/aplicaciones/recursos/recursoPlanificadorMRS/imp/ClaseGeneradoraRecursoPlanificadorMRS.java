package icaro.aplicaciones.recursos.recursoPlanificadorMRS.imp;

import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.aplicaciones.MRS.informacion.Mapa;
import icaro.aplicaciones.MRS.informacion.Coordenada;
import icaro.aplicaciones.MRS.informacion.InicioEstado;
import icaro.aplicaciones.MRS.informacion.TipoCelda;
import icaro.aplicaciones.recursos.recursoPlanificadorMRS.ItfUsoRecursoPlanificadorMRS;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;


/**
 * 
 * @author Hristo
 *
 */

public class ClaseGeneradoraRecursoPlanificadorMRS extends ImplRecursoSimple
		implements ItfUsoRecursoPlanificadorMRS {

	private static final long serialVersionUID = -7272037961162939091L;
	private String recursoId;
	private Mapa mapaConocimiento;
	
	public ClaseGeneradoraRecursoPlanificadorMRS(String idRecurso) throws Exception {
		super(idRecurso);
		recursoId = idRecurso;
		try {
			trazas.aceptaNuevaTraza(new InfoTraza(idRecurso, "El constructor de la clase generadora del recurso "
					+ idRecurso + " ha completado su ejecucion ....", InfoTraza.NivelTraza.debug));
		} catch (Exception e) {
			this.trazas.trazar(recursoId, "-----> Se ha producido un error en la creacion del recurso : " + e.getMessage(),
					InfoTraza.NivelTraza.error);
			this.itfAutomata.transita("error");
			throw e;
		}
	}
	
	@Override
	public void setMapa(Mapa mapa) throws Exception{
		this.mapaConocimiento = new Mapa(mapa);
	}
	
	@Override
	public ArrayList<Coordenada> getRuta(Coordenada start, Coordenada finish) throws Exception{
		TipoCelda[][] map = this.mapaConocimiento.getMapa();
		if (map[finish.x][finish.y] != TipoCelda.PASILLO)
			return new ArrayList<Coordenada>();
		int sizeX = map.length;
		int sizeY = map[0].length;
		boolean[][] visited = new boolean[sizeX][sizeY];
		for (int i=0; i<sizeX; i++)
			Arrays.fill(visited[i], false);
		return this._getRuta(map, start, finish, visited, 0, new int[]{-1});
	}

	private ArrayList<Coordenada> _getRuta(TipoCelda[][] map, Coordenada start, Coordenada finish, boolean[][] visited, int count, int[] minRuta){
		if(minRuta[0] != -1)
			if(minRuta[0] < count)
				return new ArrayList<Coordenada>();

		if(start.x == finish.x && start.y == finish.y){
			minRuta[0] = count;
			ArrayList<Coordenada> to_return = new ArrayList<Coordenada>();
			to_return.add(finish);
			return to_return;}

		visited[start.x][start.y] = true;

		ArrayList<ArrayList<Coordenada>> aux = new ArrayList<ArrayList<Coordenada>>();
		if(start.x-1 >= 0)
			if(!visited[start.x-1][start.y])
				if(map[start.x-1][start.y] == TipoCelda.PASILLO)
					aux.add(_getRuta(map, new Coordenada(start.x-1, start.y), finish, visited, count+1, minRuta));

		if(start.x+1 < map.length)
			if(!visited[start.x+1][start.y])
				if(map[start.x+1][start.y] == TipoCelda.PASILLO)
					aux.add(_getRuta(map, new Coordenada(start.x+1, start.y), finish, visited, count+1, minRuta));

		if(start.y-1 >= 0)
			if(!visited[start.x][start.y-1])
				if(map[start.x][start.y-1] == TipoCelda.PASILLO)
					aux.add(_getRuta(map, new Coordenada(start.x, start.y-1), finish, visited, count+1, minRuta));

		if(start.y+1 < map[0].length)
			if(!visited[start.x][start.y+1])
				if(map[start.x][start.y+1] == TipoCelda.PASILLO)
					aux.add(_getRuta(map, new Coordenada(start.x, start.y+1), finish, visited, count+1, minRuta));

		visited[start.x][start.y] = false;

		Iterator<ArrayList<Coordenada>> y = aux.iterator();
		while (y.hasNext()) {
			ArrayList<Coordenada> z = y.next();
			if(z.size() != 0){
				Coordenada last = z.get(z.size()-1);
				if(last.x == finish.x && last.y == finish.y)
					continue;
			}
			y.remove();
		}

		if(aux.size() == 0)
			return new ArrayList<Coordenada>();

		int theChosenOne = 0;
		int min = aux.get(0).size();
		for(int i=1; i<aux.size(); i++)
			if(aux.get(i).size() < min){
				theChosenOne = i;
				min = aux.get(i).size();}

		ArrayList<Coordenada> to_return = new ArrayList<Coordenada>();
		to_return.add(start);
		to_return.addAll(aux.get(theChosenOne));
		return to_return;
	}

	@Override
	//Devuelve true si no se conocia el obstaculo
	public boolean informarBloqueo(Coordenada c) {
		boolean ret = (this.mapaConocimiento.getMapa()[c.getX()][c.getY()] == TipoCelda.PASILLO);
		this.mapaConocimiento.getMapa()[c.getX()][c.getY()] = TipoCelda.ESCOMBRO;
		
		return ret;
	}
	
	@Override
	public void cambioEstado(String st) {
		switch(st){
		case InicioEstado.ST_NuevoEscenario:
			break;
		case InicioEstado.ST_Inicio:
			break;
		case InicioEstado.ST_Fin:
			break;
		default: 
			break;	
		}
	}

	@Override
	public void changeMap(int x, int y, TipoCelda t)  {
		if(t == TipoCelda.ESCOMBRO_UNK)
			mapaConocimiento.getMapa()[x][y] = TipoCelda.PASILLO;
		else
			mapaConocimiento.getMapa()[x][y] = t;
	}
}
