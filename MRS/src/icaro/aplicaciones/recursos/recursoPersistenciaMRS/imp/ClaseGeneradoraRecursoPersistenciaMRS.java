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
import icaro.aplicaciones.MRS.informacion.Mapa;
import icaro.aplicaciones.MRS.informacion.Minero;
import icaro.aplicaciones.MRS.informacion.Rescatador;
import icaro.aplicaciones.MRS.informacion.Robot;
import icaro.aplicaciones.MRS.informacion.TipoCelda;
import icaro.aplicaciones.MRS.informacion.Victima;
import icaro.aplicaciones.recursos.recursoPersistenciaMRS.ItfUsoRecursoPersistenciaMRS;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

public class ClaseGeneradoraRecursoPersistenciaMRS extends ImplRecursoSimple
implements ItfUsoRecursoPersistenciaMRS{
	
	private static final long serialVersionUID = -2900178575013666003L;
	private String recursoId;
	private Escenario escenario;
	
	private final String TAG_PERSISTENCIA = "PersistenciaMRS";
	private final String TAG_MAPA = "Mapa";
	private final String ATTR_COLS = "cols";
	private final String ATTR_ROWS = "rows";
	private final String TAG_CELDA = "Celda";
	private final String ATTR_tipoCelda = "tipo";
	private final String TAG_listRobot = "Rescatadores";
	private final String TAG_Robot = "Rescatador";
	private final String TAG_listVictim = "Mineros";
	private final String TAG_Victim = "Minero";
	private final String ATTR_AgTipo = "tipo";
	private final String ATTR_AgName = "name";
	private final String ATTR_AgStartX = "startX";
	private final String ATTR_AgStartY = "startY";
	
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
		NodeList listMapa = doc.getElementsByTagName(TAG_MAPA);
		/*
		<Mapa key="Mapa" rows="5" cols="10" >
      		<Celda tipo="PASILLO" x="2" y="3" />
   		</Mapa>
		 */
			Mapa myMap;
			Node mapNode = listMapa.item(0);
			if (mapNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) mapNode;
				int cols =Integer.parseInt(eElement.getAttribute(ATTR_COLS));
				int rows =Integer.parseInt(eElement.getAttribute(ATTR_ROWS));
				
				myMap = new Mapa(cols, rows);
				int size = eElement.getElementsByTagName(TAG_CELDA).getLength();
				for (int i = 0; i < size; i++) {
					String tipo= eElement.getElementsByTagName(TAG_CELDA).item(i).getAttributes().getNamedItem(ATTR_tipoCelda).getNodeValue();
					int x= Integer.parseInt(eElement.getElementsByTagName(TAG_CELDA).item(i).getAttributes().getNamedItem("x").getNodeValue());
					int y= Integer.parseInt(eElement.getElementsByTagName(TAG_CELDA).item(i).getAttributes().getNamedItem("y").getNodeValue());
					myMap.setCoord(x,y,str2tipocelda(tipo));
				}
			}else{
				throw new Error("No hay mapa");
			}
			e.setMapa(myMap);
		
		NodeList listRescatador = doc.getElementsByTagName(TAG_listRobot);
		for (int tempR = 0; tempR < listRescatador.getLength(); tempR++) {
			Node nNode = listRescatador.item(tempR);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				int size = eElement.getElementsByTagName(TAG_Robot).getLength();
				for (int i = 0; i < size; i++) {
					String tipo= eElement.getElementsByTagName(TAG_Robot).item(i).getAttributes().getNamedItem(ATTR_AgTipo).getNodeValue();
					String nombre= eElement.getElementsByTagName(TAG_Robot).item(i).getAttributes().getNamedItem(ATTR_AgName).getNodeValue();
					int startX= Integer.parseInt(eElement.getElementsByTagName(TAG_Robot).item(i).getAttributes().getNamedItem(ATTR_AgStartX).getNodeValue());
					int startY= Integer.parseInt(eElement.getElementsByTagName(TAG_Robot).item(i).getAttributes().getNamedItem(ATTR_AgStartY).getNodeValue());
					Coordenada coordenadasIniciales= new Coordenada(startX, startY);
					Rescatador myResc=new Rescatador(tipo,coordenadasIniciales, nombre);
					e.addRobot(myResc);
				}
			}
			
		}
		NodeList listMinero = doc.getElementsByTagName(TAG_listVictim);
		for (int tempM = 0; tempM < listMinero.getLength(); tempM++) {
			Node mNode = listMinero.item(tempM);
			if (mNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) mNode;
				int size = eElement.getElementsByTagName(TAG_Victim).getLength();
				for (int i = 0; i < size; i++) {
					String tipo= eElement.getElementsByTagName(TAG_Victim).item(i).getAttributes().getNamedItem(ATTR_AgTipo).getNodeValue();
					String nombre= eElement.getElementsByTagName(TAG_Victim).item(i).getAttributes().getNamedItem(ATTR_AgName).getNodeValue();
					int startX= Integer.parseInt(eElement.getElementsByTagName(TAG_Victim).item(i).getAttributes().getNamedItem(ATTR_AgStartX).getNodeValue());
					int startY= Integer.parseInt(eElement.getElementsByTagName(TAG_Victim).item(i).getAttributes().getNamedItem(ATTR_AgStartY).getNodeValue());
					Coordenada coordenadasIniciales= new Coordenada(startX, startY);
					Minero myMin=new Minero(tipo,coordenadasIniciales, nombre);
					e.addVictima(myMin);
				}

			}
		}
		this.escenario = e;
		return e;
	}
	
	

	private TipoCelda str2tipocelda(String str){
		if(str.toUpperCase().equals("PASILLO"))
			return TipoCelda.PASILLO;
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
        Element persi = doc1.createElement(TAG_PERSISTENCIA);
        persi.setAttribute("id", ""+lR.size()+"R"+lV.size()+"_time"+d);
        // MAPA
        Element esce = doc1.createElement(TAG_MAPA);
        esce.setAttribute(ATTR_COLS, String.valueOf(m.getNumCols()));
        esce.setAttribute(ATTR_ROWS, String.valueOf(m.getNumRows()));
        // --- Celdas del mapa
        for(int i = 0; i < m.getNumRows(); i++){
        	for(int j = 0; j < m.getNumCols(); j++){
        		Element celda = doc1.createElement(TAG_CELDA);
                celda.setAttribute(ATTR_tipoCelda, m.getCoord(i, j).name());
                celda.setAttribute("x", ""+i);
                celda.setAttribute("y", ""+j);
                esce.appendChild(celda);
        	}
        }
        persi.appendChild(esce);
        
        // ROBOTS
        Element listR = doc1.createElement(TAG_listRobot);
        listR.setAttribute("numRes", ""+lR.size());
        for(int i = 0; i < lR.size(); i++){
        	Element rb = doc1.createElement(TAG_Robot);
        	rb.setAttribute(ATTR_AgName, lR.get(i).getName());
        	rb.setAttribute(ATTR_AgTipo, lR.get(i).getTipo());
        	rb.setAttribute(ATTR_AgStartX, ""+lR.get(i).getCoordenadasIniciales().x);
        	rb.setAttribute(ATTR_AgStartY, ""+lR.get(i).getCoordenadasIniciales().y);
        	listR.appendChild(rb);
        }
        persi.appendChild(listR);
       
        // VICTIMAS
        Element listV = doc1.createElement(TAG_listVictim);
        listV.setAttribute("numMin", ""+lR.size());
        for(int i = 0; i < lV.size(); i++){
        	Element rb = doc1.createElement(TAG_Victim);
        	rb.setAttribute(ATTR_AgName, lV.get(i).getName());
        	rb.setAttribute(ATTR_AgTipo, lV.get(i).getTipo());
        	rb.setAttribute(ATTR_AgStartX, ""+lV.get(i).getCoordenadasIniciales().x);
        	rb.setAttribute(ATTR_AgStartY, ""+lV.get(i).getCoordenadasIniciales().y);
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
	
	// TEST
	public static void main(String[] args) {
		try {
			ClaseGeneradoraRecursoPersistenciaMRS c = new ClaseGeneradoraRecursoPersistenciaMRS("yo");
			Escenario ec;

			ec = c.parseEscenario(new File("MRS/escenarios/Escenario1.xml"));
		
			c.escenarioToXML(ec, new File("MRS/escenarios/Escenario1_copy.xml"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
