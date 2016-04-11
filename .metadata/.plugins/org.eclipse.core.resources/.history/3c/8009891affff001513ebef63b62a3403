/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.Rosace.tareasComunes;
import icaro.aplicaciones.Rosace.informacion.EvaluacionAgente;
import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VictimsToRescue;
import icaro.aplicaciones.agentes.agenteAplicacionSubordinadoConCambioRolCognitivo.tareas.GeneraryEncolarObjetivoActualizarFocoNC1;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.informacion.InfoParaDecidirQuienVa;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.InfoCompMovimiento;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.InfoCompMovimiento.EstadoMovimientoRobot;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.ItfUsoMovimientoCtrl;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Informe;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author Francisco J Garijo
 */
public class ProcesarInformeLlegadaDestino extends TareaSincrona{
  int velocidadCruceroPordefecto = 1;// metros por segundo 
  private ItfUsoMovimientoCtrl itfcompMov;
  private Victim victimaRescatar;
  @Override
  public void ejecutar(Object... params) {
		try {
//Suponemos que cuando llega al destino se salva la victima
 // habría que actualizar las victimas, los objetivos, el estado del movimiento  y cambiar el foco                   
             MisObjetivos misObjs = (MisObjetivos)params[0];
             VictimsToRescue victims = (VictimsToRescue) params[1];
             Focus focoActual = (Focus) params[2];
             InfoCompMovimiento infoCompMov = (InfoCompMovimiento) params[3];
             Informe informeRecibido = (Informe) params[4];
//             trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente, "Se Ejecuta la Tarea :"+ this.identTarea , InfoTraza.NivelTraza.info));
             trazas.aceptaNuevaTrazaEjecReglas(this.identAgente, "Se Procesa el informe   recibido por el agente :"+ informeRecibido.referenciaContexto +" Cuyo contenido:"+informeRecibido.contenidoInforme + "\n");
            // se actualiza el coste de la  vicitima salvada
             itfcompMov = (ItfUsoMovimientoCtrl)infoCompMov.getitfAccesoComponente();
             String victimaRescatadaId = informeRecibido.getReferenciaContexto();
             victims.addEstimatedCostVictim2Rescue(victimaRescatadaId, 0);
             this.getEnvioHechos().eliminarHecho(informeRecibido);
             // Se actualizan los objetivos, se da por conseguido el objetivo salvar a la victima
             // se supone que este objetivo era el mas prioritario, si no lo era hay un problema
              Objetivo objetivoConseguido = misObjs.getobjetivoMasPrioritario();
              Thread accesoCompMovimiento = new Thread(){
				public void run(){
					itfcompMov.moverAdestino(victimaRescatar.getName(), victimaRescatar.getCoordinateVictim(), velocidadCruceroPordefecto);
				}
			};
              if (victimaRescatadaId.equals(objetivoConseguido.getobjectReferenceId())){
                  objetivoConseguido.setSolved();
                  objetivoConseguido.setPriority(-1);
                  misObjs.cambiarPrioridad(objetivoConseguido);
                  // Se actualiza el componente movimiento
             // Verificamos el foco si tiene un objetivo solved cambiamos el foco al objetivo actual
             // si tiene uno solving lo dejamos
                  Objetivo nuevoObjetivo = misObjs.getobjetivoMasPrioritario();
                  if( nuevoObjetivo.getState()!= Objetivo.SOLVED){
             // tiene  objetivos pendientes , se da la orden de que vaya a salvar a la victima
                      Victim victimaRescatada = victims.getVictimToRescue(victimaRescatadaId);
                      String idVictimaRescatar=nuevoObjetivo.getobjectReferenceId();
                      victimaRescatar = victims.getVictimToRescue(idVictimaRescatar);
                     infoCompMov.setidentDestino(idVictimaRescatar);
//                     infoCompMov.itfAccesoComponente.moverAdestino(nuevoObjetivo.getobjectReferenceId(), victimaRescatada.getCoordinateVictim(), velocidadCruceroPordefecto);
                  accesoCompMovimiento.start();
                  }
                  String estadoComponente=EstadoMovimientoRobot.RobotEnMovimiento.name();
//                  infoCompMov.setidentEstadoRobot(estadoComponente);
//                  if(focoActual.getFoco().getState()==Objetivo.SOLVED) focoActual.setFoco(objetivoConseguido);
                  focoActual.setFoco(nuevoObjetivo);
//                   focoActual.refocusUltimoObjetivoSolving();
              // Se envian los cambios al motor   
                  
                  this.getEnvioHechos().actualizarHechoWithoutFireRules(victims);
                  this.getEnvioHechos().actualizarHechoWithoutFireRules(misObjs);
//                  this.getEnvioHechos().actualizarHechoWithoutFireRules(infoCompMov);
                  this.getEnvioHechos().actualizarHechoWithoutFireRules(objetivoConseguido);
                  this.getEnvioHechos().actualizarHecho(focoActual);
                  trazas.aceptaNuevaTrazaEjecReglas(this.identAgente, "Se Ejecuta la Tarea :"+ this.identTarea +
                           "El identificador de la victima  :"+ victimaRescatadaId + " y el del ultimo objetivo : "+objetivoConseguido.getobjectReferenceId()+" coinciden " +
                           "EstadoComponente : "+estadoComponente+ "Objetivo mas prioritario en curso "+misObjs.getobjetivoMasPrioritario().toString()+ "\n"+ "  El foco esta en el ojetivo :  "+focoActual + "\n");
              }else
                   trazas.aceptaNuevaTrazaEjecReglas(this.identAgente, "Se Ejecuta la Tarea :"+ this.identTarea +
                           "El identificador de la victima  :"+ victimaRescatadaId + " y el del ultimo objetivo : "+objetivoConseguido.getobjectReferenceId()+" NO coinciden " + "\n");
        } catch (Exception e) {
			   e.printStackTrace();
        }
                
}


}
