//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.07.04 at 04:19:28 PM CEST 
//

package icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Instancia complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Instancia">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="listaPropiedades" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}ListaPropiedades" minOccurs="0"/>
 *         &lt;element name="nodoEspecifico" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}Nodo" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="refDescripcion" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Instancia", propOrder = { "listaPropiedades", "nodoEspecifico" })
@XmlSeeAlso({ InstanciaGestor.class })
public class Instancia {

	protected ListaPropiedades listaPropiedades;
	protected Nodo nodoEspecifico;
	@XmlAttribute(name = "id", required = true)
	protected String id;
	@XmlAttribute(name = "refDescripcion", required = true)
	protected String refDescripcion;

	/**
	 * Gets the value of the listaPropiedades property.
	 * 
	 * @return possible object is {@link ListaPropiedades }
	 * 
	 */
	public ListaPropiedades getListaPropiedades() {
		return listaPropiedades;
	}

	/**
	 * Sets the value of the listaPropiedades property.
	 * 
	 * @param value
	 *            allowed object is {@link ListaPropiedades }
	 * 
	 */
	public void setListaPropiedades(ListaPropiedades value) {
		this.listaPropiedades = value;
	}

	/**
	 * Gets the value of the nodoEspecifico property.
	 * 
	 * @return possible object is {@link Nodo }
	 * 
	 */
	public Nodo getNodoEspecifico() {
		return nodoEspecifico;
	}

	/**
	 * Sets the value of the nodoEspecifico property.
	 * 
	 * @param value
	 *            allowed object is {@link Nodo }
	 * 
	 */
	public void setNodoEspecifico(Nodo value) {
		this.nodoEspecifico = value;
	}

	/**
	 * Gets the value of the id property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setId(String value) {
		this.id = value;
	}

	/**
	 * Gets the value of the refDescripcion property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRefDescripcion() {
		return refDescripcion;
	}

	/**
	 * Sets the value of the refDescripcion property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRefDescripcion(String value) {
		this.refDescripcion = value;
	}

}