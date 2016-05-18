package icaro.aplicaciones.recursos.recursoPersistenciaMRS.imp;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import icaro.aplicaciones.MRS.informacion.Coordenada;
import icaro.aplicaciones.MRS.informacion.Escenario;
import icaro.aplicaciones.MRS.informacion.InicioEstado;
import icaro.aplicaciones.MRS.informacion.Mapa;
import icaro.aplicaciones.MRS.informacion.Minero;
import icaro.aplicaciones.MRS.informacion.Rescatador;
import icaro.aplicaciones.MRS.informacion.Robot;
import icaro.aplicaciones.MRS.informacion.TipoCelda;
import icaro.aplicaciones.MRS.informacion.Victima;
import icaro.aplicaciones.MRS.informacion.VocabularioMRS;
import icaro.aplicaciones.recursos.recursoPersistenciaMRS.ItfUsoRecursoPersistenciaMRS;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 * Clase Generadora del Recurso PersistenciaMRS
 * @author Jesus Domenech
 * @author Veronica Chamorro
 */
public class ClaseGeneradoraRecursoPersistenciaMRS extends ImplRecursoSimple
implements ItfUsoRecursoPersistenciaMRS{
	
	private static final long serialVersionUID = -2900178575013666003L;
	/**
	 * Id del recurso PersistenciaMRS
	 */
	private String recursoId;
	
	/**
	 * Ultimo escenario Parseado
	 */
	private Escenario escenario;
	
	/**
	 * Constructora del Recurso
	 * @param idRecurso id del recurso
	 * @throws Exception
	 */
	public ClaseGeneradoraRecursoPersistenciaMRS(String idRecurso) throws Exception {
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
	public Escenario getEscenario(){
		return this.escenario;
	}
	
	@Override
	public Escenario parseEscenario(File file) throws Exception{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(file);

		
		Escenario e = new Escenario();
		
		doc.getDocumentElement().normalize();
		NodeList listMapa = doc.getElementsByTagName(VocabularioMRS.TAG_MAPA);

			Mapa myMap;
			Node mapNode = listMapa.item(0);
			if (mapNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) mapNode;
				int cols =Integer.parseInt(eElement.getAttribute(VocabularioMRS.ATTR_COLS));
				int rows =Integer.parseInt(eElement.getAttribute(VocabularioMRS.ATTR_ROWS));
				
				myMap = new Mapa(cols, rows);
				int size = eElement.getElementsByTagName(VocabularioMRS.TAG_CELDA).getLength();
				for (int i = 0; i < size; i++) {
					String tipo= eElement.getElementsByTagName(VocabularioMRS.TAG_CELDA).item(i).getAttributes().getNamedItem(VocabularioMRS.ATTR_tipoCelda).getNodeValue();
					int x= Integer.parseInt(eElement.getElementsByTagName(VocabularioMRS.TAG_CELDA).item(i).getAttributes().getNamedItem("x").getNodeValue());
					int y= Integer.parseInt(eElement.getElementsByTagName(VocabularioMRS.TAG_CELDA).item(i).getAttributes().getNamedItem("y").getNodeValue());
					myMap.setCoord(x,y,str2tipocelda(tipo));
				}
			}else{
				throw new Error("No hay mapa");
			}
			e.setMapa(myMap);
		
		NodeList listRescatador = doc.getElementsByTagName(VocabularioMRS.TAG_listRobot);
		for (int tempR = 0; tempR < listRescatador.getLength(); tempR++) {
			Node nNode = listRescatador.item(tempR);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				int size = eElement.getElementsByTagName(VocabularioMRS.TAG_Robot).getLength();
				for (int i = 0; i < size; i++) {
					String tipo= eElement.getElementsByTagName(VocabularioMRS.TAG_Robot).item(i).getAttributes().getNamedItem(VocabularioMRS.ATTR_AgTipo).getNodeValue();
					String nombre= eElement.getElementsByTagName(VocabularioMRS.TAG_Robot).item(i).getAttributes().getNamedItem(VocabularioMRS.ATTR_AgName).getNodeValue();
					int startX= Integer.parseInt(eElement.getElementsByTagName(VocabularioMRS.TAG_Robot).item(i).getAttributes().getNamedItem(VocabularioMRS.ATTR_AgStartX).getNodeValue());
					int startY= Integer.parseInt(eElement.getElementsByTagName(VocabularioMRS.TAG_Robot).item(i).getAttributes().getNamedItem(VocabularioMRS.ATTR_AgStartY).getNodeValue());
					Coordenada coordenadasIniciales= new Coordenada(startX, startY);
					Rescatador myResc=new Rescatador(tipo,coordenadasIniciales, nombre);
					e.addRobot(myResc);
				}
			}
		}
		NodeList listMinero = doc.getElementsByTagName(VocabularioMRS.TAG_listVictim);
		for (int tempM = 0; tempM < listMinero.getLength(); tempM++) {
			Node mNode = listMinero.item(tempM);
			if (mNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) mNode;
				int size = eElement.getElementsByTagName(VocabularioMRS.TAG_Victim).getLength();
				for (int i = 0; i < size; i++) {
					String tipo= eElement.getElementsByTagName(VocabularioMRS.TAG_Victim).item(i).getAttributes().getNamedItem(VocabularioMRS.ATTR_AgTipo).getNodeValue();
					String nombre= eElement.getElementsByTagName(VocabularioMRS.TAG_Victim).item(i).getAttributes().getNamedItem(VocabularioMRS.ATTR_AgName).getNodeValue();
					int startX= Integer.parseInt(eElement.getElementsByTagName(VocabularioMRS.TAG_Victim).item(i).getAttributes().getNamedItem(VocabularioMRS.ATTR_AgStartX).getNodeValue());
					int startY= Integer.parseInt(eElement.getElementsByTagName(VocabularioMRS.TAG_Victim).item(i).getAttributes().getNamedItem(VocabularioMRS.ATTR_AgStartY).getNodeValue());
					Coordenada coordenadasIniciales= new Coordenada(startX, startY);
					Minero myMin=new Minero(tipo,coordenadasIniciales, nombre);
					e.addVictima(myMin);
				}

			}
		}
		this.escenario = e;
		return e;
	}
	
	
	/**
	 * Dado un string lo convierte al correspondiente enumerado
	 * @param str string a convertir
	 * @return elemento de <code>TipoCelda</code> correspondiente al string
	 */
	private TipoCelda str2tipocelda(String str){
		if(str.toUpperCase().equals("PASILLO"))
			return TipoCelda.PASILLO;
		if(str.toUpperCase().equals("ESCOMBRO_UNK"))
			return TipoCelda.ESCOMBRO_UNK;
		if(str.toUpperCase().equals("ESCOMBRO"))
			return TipoCelda.ESCOMBRO;
		return TipoCelda.PARED;	
	}

	@Override
	public void escenarioToXML(Escenario escenario, File file) throws Exception {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Mapa m = escenario.getMapa();
        List<Robot> lR = escenario.getListaRobots();
        List<Victima> lV = escenario.getListaVictimas();
        Date d = new Date();
        Document doc1 = dBuilder.newDocument();
        Element persi = doc1.createElement(VocabularioMRS.TAG_PERSISTENCIA);
        persi.setAttribute("id", ""+lR.size()+"R"+lV.size()+"_time"+d);
        // MAPA
        Element esce = doc1.createElement(VocabularioMRS.TAG_MAPA);
        esce.setAttribute(VocabularioMRS.ATTR_COLS, String.valueOf(m.getNumCols()));
        esce.setAttribute(VocabularioMRS.ATTR_ROWS, String.valueOf(m.getNumRows()));
        // --- Celdas del mapa
        for(int i = 0; i < m.getNumRows(); i++){
        	for(int j = 0; j < m.getNumCols(); j++){
        		Element celda = doc1.createElement(VocabularioMRS.TAG_CELDA);
                celda.setAttribute(VocabularioMRS.ATTR_tipoCelda, m.getCoord(i, j).toString());
                celda.setAttribute("x", ""+i);
                celda.setAttribute("y", ""+j);
                esce.appendChild(celda);
        	}
        }
        persi.appendChild(esce);
        
        // ROBOTS
        Element listR = doc1.createElement(VocabularioMRS.TAG_listRobot);
        listR.setAttribute("numRes", ""+lR.size());
        for(int i = 0; i < lR.size(); i++){
        	Element rb = doc1.createElement(VocabularioMRS.TAG_Robot);
        	rb.setAttribute(VocabularioMRS.ATTR_AgName, lR.get(i).getName());
        	rb.setAttribute(VocabularioMRS.ATTR_AgTipo, lR.get(i).getTipo());
        	rb.setAttribute(VocabularioMRS.ATTR_AgStartX, ""+lR.get(i).getCoordenadasIniciales().x);
        	rb.setAttribute(VocabularioMRS.ATTR_AgStartY, ""+lR.get(i).getCoordenadasIniciales().y);
        	listR.appendChild(rb);
        }
        persi.appendChild(listR);
       
        // VICTIMAS
        Element listV = doc1.createElement(VocabularioMRS.TAG_listVictim);
        listV.setAttribute("numMin", ""+lR.size());
        for(int i = 0; i < lV.size(); i++){
        	Element rb = doc1.createElement(VocabularioMRS.TAG_Victim);
        	rb.setAttribute(VocabularioMRS.ATTR_AgName, lV.get(i).getName());
        	rb.setAttribute(VocabularioMRS.ATTR_AgTipo, lV.get(i).getTipo());
        	rb.setAttribute(VocabularioMRS.ATTR_AgStartX, ""+lV.get(i).getCoordenadasIniciales().x);
        	rb.setAttribute(VocabularioMRS.ATTR_AgStartY, ""+lV.get(i).getCoordenadasIniciales().y);
        	listV.appendChild(rb);
        }
        persi.appendChild(listV);
        
        doc1.appendChild(persi);
        DOMSource source = new DOMSource(doc1);
        
        StreamResult result = new StreamResult(file);
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
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
}
