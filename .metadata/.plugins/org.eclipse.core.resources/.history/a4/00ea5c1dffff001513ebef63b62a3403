/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.informacion.InfoParaDecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author FGarijo
 * 
 * Corresponde a un decisión de que otro agente se ha hecho cargo de la tarea
 */
public class EliminarObjetivoyDecisionInfoActualizarFoco1 extends TareaSincrona{
    @Override
   public void ejecutar(Object... params) {
	   try {
             MisObjetivos misObjs = (MisObjetivos) params[0];
             Objetivo ayudarVictima = (Objetivo)params[1]; // el estado es pending
             InfoParaDecidirQuienVa infoDecision = (InfoParaDecidirQuienVa)params[2];
             Focus focoActual = (Focus)params[3]; // el foco actual es decidir quien va 
//                Objetivo objetivoMasPrioritario = misObjs.getobjetivoMasPrioritario();
//                if (objetivoMasPrioritario != null)
//                if(objetivoMasPrioritario.getState()== Objetivo.SOLVED){
//                    objetivoMasPrioritario.setPriority(-1);
//                    misObjs.addObjetivo(objetivoMasPrioritario);      
//                }else if(objetivoMasPrioritario.getPriority()<0)objetivoMasPrioritario=null;
////                ayudarVictima.setSolving();
////                misObjs.addObjetivo(ayudarVictima);
////                objetivoMasPrioritario = misObjs.getobjetivoMasPrioritario();
                this.getEnvioHechos().eliminarHechoWithoutFireRules(ayudarVictima);
                this.getEnvioHechos().eliminarHechoWithoutFireRules(infoDecision);
//                focoActual.setFocusToObjetivoMasPrioritario(misObjs);
                focoActual.setFoco(null);
                this.getEnvioHechos().actualizarHecho(focoActual);
            trazas.aceptaNuevaTrazaEjecReglas(this.identAgente, "Se ejecuta la tarea " + this.identTarea+
                    "Foco anterior : "+focoActual.getFocoAnterior()+
                                    " Se actualiza el  foco a :  "+ focoActual +"\n" );
            System.out.println("\n"+this.identAgente +"Se ejecuta la tarea " + this.getIdentTarea()+ " Se actualiza el  objetivo:  "+ ayudarVictima+"\n\n" );
                          
             
       } catch (Exception e) {
			 e.printStackTrace();
       }
   }

}

