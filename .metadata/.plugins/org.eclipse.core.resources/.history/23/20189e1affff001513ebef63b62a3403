/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.imp;

import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.ItfUsoMovimientoCtrl;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.ItfUsoRecursoVisualizadorEntornosSimulacion;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Informe;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Temporizador;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ItfProcesadorObjetivos;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.util.Exceptions;


/**
 *
 * @author FGarijo
 */
public class MaquinaEstadoMovimientoCtrl   {
    private static EstadoAbstractoMovRobot instance;
    private String identEstadoActual;
    private String identComponente;
    private String identDestino;
  public static  enum EstadoMovimientoRobot {Indefinido,RobotParado, RobotEnMovimiento, RobotBloqueado,RobotavanceImposible,enDestino,  error}
    //Nombres de las clases que implementan estados del recurso interno
    public static  enum EvalEnergiaRobot {sinEnergia,energiaSuficiente,EnergiaJusta, EnergiaInsuficiente }
    public EstadoAbstractoMovRobot estadoActual;
    public RobotParado estadoRobotParado;
    public String identAgente;
//    public RobotEnMovimiento estadoMovimiento;
    public volatile double velocidadRobot;
    public ItfUsoRecursoTrazas trazas;
    private  Map<EstadoMovimientoRobot, EstadoAbstractoMovRobot> estadosCreados;
    public volatile Coordinate robotposicionActual;
    public volatile Coordinate destinoCoord;
    public double distanciaDestino ;
    protected Integer velocidadCrucero;
    public ItfProcesadorObjetivos itfProcObjetivos;
    protected HebraMonitorizacionLlegada monitorizacionLlegadaDestino;
    ItfUsoRecursoVisualizadorEntornosSimulacion itfUsoRecVisEntornosSimul;
    private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass().getSimpleName());
    
    public void bloquear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    public boolean estamosEnDestino(String identDestino) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    return estadoActual.estamosEnDestino(identDestino);
    }

    void setVelocidadInicial(float velocidadInicial) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    this.velocidadRobot = velocidadInicial;
    }

    public synchronized void inicializar(ItfProcesadorObjetivos itfProcObj, ItfUsoRecursoVisualizadorEntornosSimulacion itfVisSimul) {
       identAgente = itfProcObj.getAgentId();
        if (identComponente ==null) identComponente = identAgente+"."+this.getClass().getSimpleName();
       itfProcObjetivos =itfProcObj;
       itfUsoRecVisEntornosSimul=itfVisSimul;
    }
   
    public  MaquinaEstadoMovimientoCtrl (){
        estadosCreados = new EnumMap<EstadoMovimientoRobot, EstadoAbstractoMovRobot>(EstadoMovimientoRobot.class) ;
        
    }
    
    public EstadoAbstractoMovRobot getEstadoActual (){
        return  estadoActual ;
    }
    public void SetComponentId (String idComp){
        identComponente = idComp;
    }
     public void SetItfUsoRecursoVisualizadorEntornosSimulacion (ItfUsoRecursoVisualizadorEntornosSimulacion itfVisualEntSim){
        itfUsoRecVisEntornosSimul = itfVisualEntSim;
    }
    public void SetInfoContexto (ItfProcesadorObjetivos itfProcObj){
        identAgente = itfProcObj.getAgentId();
        itfProcObjetivos = itfProcObj;
   //     identComponente = identAgente+"."+this.getClass().getSimpleName();
        if (identComponente ==null) identComponente = identAgente+"."+this.getClass().getSimpleName();
 //       estadoRobotParado =new RobotParado(agenteId);
 //      estadoActual= estadoRobotParado;
        trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
    }
     public synchronized EstadoAbstractoMovRobot  cambiarEstado (EstadoMovimientoRobot nuevoEstadoId){
      if (!nuevoEstadoId.name().equals(identEstadoActual)) {
        trazas.trazar(identComponente, " se cambia el estado: "+ identEstadoActual+  " al estado : " + nuevoEstadoId , NivelTraza.debug);
        estadoActual = estadosCreados.get(nuevoEstadoId);
        
        if ( estadoActual != null  ) {}
         else estadoActual =  crearInstanciaEstado2(nuevoEstadoId);       
    }
      identEstadoActual = estadoActual.identEstadoActual;
      return estadoActual; 
    }
     		               	
    private  EstadoAbstractoMovRobot crearInstanciaEstado1(EstadoMovimientoRobot estadoId) {
        EstadoAbstractoMovRobot objRobotEstado;
        try {
            String identClase = this.getClass().getSimpleName();
            String rutaClase = this.getClass().getName().replace(identClase,estadoId.name() );
            Class claseRobotEstado = Class.forName(rutaClase);
            try {
                objRobotEstado = (EstadoAbstractoMovRobot) claseRobotEstado.newInstance();
            } catch (Exception ex) {
                Logger.getLogger(MaquinaEstadoMovimientoCtrl.class.getName()).log(Level.SEVERE, null, ex);
                 return null;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MaquinaEstadoMovimientoCtrl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
              
        return objRobotEstado;
    }
     private EstadoAbstractoMovRobot crearInstanciaEstado2(EstadoMovimientoRobot estadoId) {
         
         if(estadoId.equals(EstadoMovimientoRobot.RobotBloqueado))estadoActual = new RobotBloqueado(this);
         else if(estadoId.equals(EstadoMovimientoRobot.RobotParado))estadoActual = new RobotParado(this);
            else estadoActual = new RobotEnMovimiento(this);
         estadoActual.inicializar(itfProcObjetivos, itfUsoRecVisEntornosSimul);
         identEstadoActual = estadoId.name();
         estadoActual.identComponente=identComponente;
         estadosCreados.put(estadoId,estadoActual);
         trazas.trazar(identComponente, " se crea el estado: "+ identEstadoActual+  " y se pone la maquina de estados  en este estado  " , NivelTraza.debug);
         
         return estadoActual;
         
     }

    public  void inicializarInfoMovimiento(icaro.aplicaciones.Rosace.informacion.Coordinate coordInicial, float velocidadInicial){
        robotposicionActual =coordInicial;
        velocidadRobot = velocidadInicial;
//        this.estadoActual.inicializarInfoMovimiento(coordInicial,velocidadInicial);
//        this.robotposicionActual = coordInicial;
    } 
 
public synchronized void moverAdestino(String identdest,Coordinate coordDestino, float velocidadCrucero) {   
    // suponemos que la orden de moverse se da desde un estado parado
    // cuando se da desde un estado en movimiento se activa la orden cambiar destino
        if (velocidadCrucero<= 0)trazas.trazar(identComponente, "La velocidad debe ser mayor que cero. Se ignora la operacion", InfoTraza.NivelTraza.error);
              else this.velocidadRobot = velocidadCrucero;
        if ( this.identEstadoActual.equals(EstadoMovimientoRobot.RobotEnMovimiento.name()))
                if ( identdest.equals(identDestino))
                        this.trazas.trazar (this.identComponente, " Se esta avanzando hacia el destino ", InfoTraza.NivelTraza.debug);
                else { 
                    // cambiar destino
                    if (monitorizacionLlegadaDestino != null)this.monitorizacionLlegadaDestino.finalizar();
                    robotposicionActual = monitorizacionLlegadaDestino.getCoordRobot();
                    this.velocidadRobot = velocidadCrucero;
                    this.destinoCoord = coordDestino;
                    this.identDestino = identdest;
               this.monitorizacionLlegadaDestino = new HebraMonitorizacionLlegada (this.identAgente,this,this.itfUsoRecVisEntornosSimul);
//               monitorizacionLlegadaDestino.setValoresIniciales(identDestino,robotposicionActual,destinoCoord);
//               this.robotposicionActual = this.maquinaEstados.getCoordenadasActuales();
//               this.monitorizacionLlegadaDestino = new HebraMonitorizacionLlegada (this.identAgente,this,this.itfUsoRecVisEntornosSimul);
                    monitorizacionLlegadaDestino.inicializarDestino(identdest,this.robotposicionActual,coordDestino,velocidadCrucero);
                    monitorizacionLlegadaDestino.start();
                  }
        if ( this.identEstadoActual.equals(EstadoMovimientoRobot.RobotParado.name()))
            if ( identdest.equals(identDestino))
                        this.trazas.trazar (this.identComponente, " Estamos parados en destino ", InfoTraza.NivelTraza.error);
            else{// se da la orden de avanzar al destino
               this.velocidadRobot = velocidadCrucero;
               this.destinoCoord = coordDestino;
               this.identDestino = identdest;
               if (monitorizacionLlegadaDestino == null) {
                   log.debug("Se crea la hebra de  monitorizacion para destino " + identdest + "  posicion actual -> ("+this.robotposicionActual.getX() + " , " + this.robotposicionActual.getY() + ")"); 
                    monitorizacionLlegadaDestino= new HebraMonitorizacionLlegada (this.identAgente,this,this.itfUsoRecVisEntornosSimul);
                    monitorizacionLlegadaDestino.inicializarDestino(identdest,this.robotposicionActual,coordDestino,velocidadCrucero);
                     monitorizacionLlegadaDestino.start();
               }else {
//               robotposicionActual = monitorizacionLlegadaDestino.getCoordRobot();
              
//               this.monitorizacionLlegadaDestino = new HebraMonitorizacionLlegada (this.identAgente,this,this.itfUsoRecVisEntornosSimul);
//               monitorizacionLlegadaDestino.setValoresIniciales(identDestino,robotposicionActual,destinoCoord);
//               this.robotposicionActual = this.maquinaEstados.getCoordenadasActuales();
//               this.monitorizacionLlegadaDestino = new HebraMonitorizacionLlegada (this.identAgente,this,this.itfUsoRecVisEntornosSimul);       
               log.debug("Se da orden de  monitorizacion para destino " + identdest + "  posicion actual -> ("+this.robotposicionActual.getX() + " , " + this.robotposicionActual.getY() + ")"); 
               monitorizacionLlegadaDestino.inicializarDestino(identdest,this.robotposicionActual,coordDestino,velocidadCrucero);
               monitorizacionLlegadaDestino.start();
               }
                   }
         }
  
        public void cambiaVelocidad( float nuevaVelocidadCrucero) {
            velocidadRobot =nuevaVelocidadCrucero;
        }
  
//        public synchronized void cambiaDestino(String identDest,icaro.aplicaciones.Rosace.informacion.Coordinate coordDestino) {
////            estadoActual.cambiaDestino(identDest,coordDestino);
//            this.destinoCoord = coordDestino;
//            trazas.trazar(identAgente, "Se recibe una  orden de cambiar  a destino. El robot esta en el estado :"+ identEstadoActual
//                + " CoordActuales =  "+this.robotposicionActual.toString() + " CoordDestino =  " +this.destinoCoord.toString(), InfoTraza.NivelTraza.debug);
////            this.estadoActual.identDestino = identDest;
//         //   identDestino = identDest;
//        }   
        public synchronized void cambiaDestino(String identdest,Coordinate coordDestino) {
        // Habria que obtener la posicion actual y recalcular la distancia y el tiempo
        // lo dejamos con mover a destino desde la posicion inicial 
            if ( identdest.equals(identDestino))
                        this.trazas.trazar (this.identComponente, " El nuevo destino coincide con el  destino actual ", InfoTraza.NivelTraza.debug);
            else {
                if ( this.identEstadoActual.equals(EstadoMovimientoRobot.RobotEnMovimiento)){
                this.destinoCoord = coordDestino;
                this.identDestino = identdest;
                if (monitorizacionLlegadaDestino != null)this.monitorizacionLlegadaDestino.finalizar();
                robotposicionActual = monitorizacionLlegadaDestino.getCoordRobot();
//            this.monitorizacionLlegadaDestino.finalizar();
                trazas.trazar(identAgente, "Se recibe una  orden de cambiar  a destino. El robot esta en el estado :"+ identEstadoActual
                + " CoordActuales =  "+this.robotposicionActual.toString() + " CoordDestino =  " +this.destinoCoord.toString(), InfoTraza.NivelTraza.debug);
                }
                moverAdestino(identDestino,destinoCoord,this.velocidadCrucero);
            }
        }

        public synchronized void parar(){
         if (monitorizacionLlegadaDestino != null)this.monitorizacionLlegadaDestino.finalizar();
                robotposicionActual = monitorizacionLlegadaDestino.getCoordRobot();
                trazas.trazar(identAgente, "Se recibe una  orden de parada. El robot esta en el estado :"+ identEstadoActual
                + " CoordActuales =  "+this.robotposicionActual.toString() + " CoordDestino =  " +this.destinoCoord.toString(), InfoTraza.NivelTraza.debug);
        }
//
//        public void continuar(){
//            estadoActual.continuar();
//        }

    public synchronized void estamosEnDestino(String identDest, Coordinate destinoCoord){
        // se informa al control de que estamos en el destino. Se cambia el estado a parar
//        estadoActual = this.cambiarEstado(MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot.RobotParado);
//        this.estadoActual.identDestino = identDest;
//        Informe informeLlegada = new Informe (identComponente,identDest, VocabularioRosace.MsgeLlegadaDestino);
//        Temporizador informeTemp = new Temporizador (500,itfProcObjetivos,informeLlegada);
//        robotposicionActual = monitorizacionLlegadaDestino.getCoordDestino();
        estadoActual = this.cambiarEstado(EstadoMovimientoRobot.RobotParado);
//        this.itfProcObjetivos.insertarHecho(informeLlegada);   
        this.robotposicionActual = destinoCoord;
        this.estadoActual.identDestino = identDest;
//        this.estadoActual.setCoordenadasActuales(destinoCoord);
//         trazas.trazar(identAgente, "Se informa de llegada al  destino: " +informeLlegada + " El robot esta en el estado :"+ identEstadoActual
//                + " CoordActuales =  "+destinoCoord.toString() , InfoTraza.NivelTraza.error);
        
  
    }

    public synchronized void imposibleAvanzarADestino(){
       estadoActual = this.cambiarEstado(EstadoMovimientoRobot.RobotBloqueado);
    }
    

    public synchronized Coordinate getCoordenadasActuales() {
     return this.robotposicionActual;
    }

    public synchronized void setCoordenadasActuales(Coordinate nuevasCoordenadas) {
        if (nuevasCoordenadas != null){
            this.robotposicionActual = nuevasCoordenadas;
//       this.estadoActual.  robotposicionActual.x = nuevasCoordenadas.x;
//        this.estadoActual. robotposicionActual.y = nuevasCoordenadas.y;
//        this.estadoActual. robotposicionActual.z = nuevasCoordenadas.z;
        }
    }
//    public  EstadoAbstractoMovRobot getEstadoActual (){
//        return this.estadoActual;
//    }
//     public void  setEstadoActual (EstadoAbstractoMovRobot estadoRobot){
//         this.estadoActual = estadoRobot;
//    }
 
     public  String getIdentEstadoMovRobot(){
         return identEstadoActual;
     }
     
}
