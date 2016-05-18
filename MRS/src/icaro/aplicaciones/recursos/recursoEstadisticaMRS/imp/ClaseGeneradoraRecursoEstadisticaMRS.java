package icaro.aplicaciones.recursos.recursoEstadisticaMRS.imp;

import java.rmi.RemoteException;

import icaro.aplicaciones.recursos.recursoEstadisticaMRS.ItUsoRecursoEstadisticaMRS;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;

public class ClaseGeneradoraRecursoEstadisticaMRS extends ImplRecursoSimple
		implements ItUsoRecursoEstadisticaMRS {

	private static final long serialVersionUID = -3439155811399562462L;

	public ClaseGeneradoraRecursoEstadisticaMRS(String idRecurso) throws RemoteException {
		super(idRecurso);
		// TODO Auto-generated constructor stub
	}

}