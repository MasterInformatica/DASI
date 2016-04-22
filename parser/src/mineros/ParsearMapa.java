package mineros;

import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import mineros.Rescatador;

public class ParsearMapa {

	public Escenario parseEscenario(File file) throws Exception  {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(file);

		
		Escenario e = new Escenario();
		
		
		
		doc.getDocumentElement().normalize();
		NodeList listMapa = doc.getElementsByTagName("Mapa");
		/*
		<Mapa key="Mapa" rows="5" cols="10" >
      		<Celda tipo="PASILLO" x="2" y="3" />
   		</Mapa>
		 */
			Mapa myMap;
			Node mapNode = listMapa.item(0);
			if (mapNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) mapNode;
				int cols =Integer.parseInt(eElement.getAttribute("cols"));
				int rows =Integer.parseInt(eElement.getAttribute("rows"));
				
				myMap = new Mapa(cols, rows);
				int size = eElement.getElementsByTagName("Celda").getLength();
				for (int i = 0; i < size; i++) {
					String tipo= eElement.getElementsByTagName("Celda").item(i).getAttributes().getNamedItem("tipo").getNodeValue();
					int x= Integer.parseInt(eElement.getElementsByTagName("Celda").item(i).getAttributes().getNamedItem("x").getNodeValue());
					int y= Integer.parseInt(eElement.getElementsByTagName("Celda").item(i).getAttributes().getNamedItem("y").getNodeValue());
					myMap.setCoord(x,y,str2tipocelda(tipo));
				}
			}else{
				throw new Error("No hay mapa");
			}
			e.setMapa(myMap);
		
		NodeList listRescatador = doc.getElementsByTagName("Rescatadores");
		for (int tempR = 0; tempR < listRescatador.getLength(); tempR++) {
			Node nNode = listRescatador.item(tempR);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				int size = eElement.getElementsByTagName("Rescatador").getLength();
				for (int i = 0; i < size; i++) {
					String tipo= eElement.getElementsByTagName("Rescatador").item(i).getAttributes().getNamedItem("tipo").getNodeValue();
					int startX= Integer.parseInt(eElement.getElementsByTagName("Rescatador").item(i).getAttributes().getNamedItem("startX").getNodeValue());
					int startY= Integer.parseInt(eElement.getElementsByTagName("Rescatador").item(i).getAttributes().getNamedItem("startY").getNodeValue());
					Coordenada coordenadasIniciales= new Coordenada(startX, startY);
					Rescatador myResc=new Rescatador(tipo,coordenadasIniciales);
					e.addRobot(myResc);
				}
			}
			
		}
		NodeList listMinero = doc.getElementsByTagName("Mineros");
		for (int tempM = 0; tempM < listMinero.getLength(); tempM++) {
			Node mNode = listMinero.item(tempM);
			if (mNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) mNode;
				int size = eElement.getElementsByTagName("Minero").getLength();
				for (int i = 0; i < size; i++) {
					String tipo= eElement.getElementsByTagName("Minero").item(i).getAttributes().getNamedItem("tipo").getNodeValue();
					int startX= Integer.parseInt(eElement.getElementsByTagName("Minero").item(i).getAttributes().getNamedItem("startX").getNodeValue());
					int startY= Integer.parseInt(eElement.getElementsByTagName("Minero").item(i).getAttributes().getNamedItem("startY").getNodeValue());
					Coordenada coordenadasIniciales= new Coordenada(startX, startY);
					Minero myMin=new Minero(tipo,coordenadasIniciales);
					e.addVictima(myMin);
				}

			}
		}
		return e;
	}

	private TipoCelda str2tipocelda(String str){
		if(str.toUpperCase().equals("PASILLO"))
			return TipoCelda.PASILLO;
		if(str.toUpperCase().equals("ESCOMBRO"))
			return TipoCelda.ESCOMBRO;
		return TipoCelda.PARED;	
	}

	
	public static void main(String[] args) throws Exception {
		ParsearMapa parser = new ParsearMapa();
		File file = new File("Escenario1.xml");
		Escenario e = parser.parseEscenario(file);
		System.out.println(e);
	
	}
	
}
