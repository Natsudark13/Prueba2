package com.poo.bieninmueble.servicios;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * Clase encargada de la gestion de archivos en formato XML
 */
public class GestorXML {

  /**
   * Cosntructor de la clase GestorXml
   */
  public GestorXML() {

  }

  /**
   * Abre el documento xml y verifica que exista el usaurio, si no existe entonces lo crea y si existe
   * entonces abre al ususario
   * 
   * @param pId Identificacion del usaurio
   * @param pNombre Nombre del usuario
   * @param pApellido Apellido del usuario
   * @param pClave Contrasenna del usuario
   * @param pTipo Tipo de usaurio
   */
  public void registrar(String pId, String pNombre, String pApellido, String pClave, String pTipo) {
    File archivoEntrada = new File("./credencialesUsuarios.xml");
    try {
      if (archivoEntrada.exists() == false) {
        DocumentBuilder constructorDocumento = construirDocumento();
        Document documento = constructorDocumento.newDocument();
        Element elementoRoot = documento.createElement("CredencialesUsuarios");
        documento.appendChild(elementoRoot);
        elementoRoot.appendChild(crearCredencial(documento, pId, pNombre, pApellido, pClave, pTipo));
        escribirArchivoXml(documento);
      } else {
        DocumentBuilder constructorDocumento = construirDocumento();
        Document documento = constructorDocumento.parse(archivoEntrada);
        Element elementoRoot = documento.getDocumentElement();
        elementoRoot.appendChild(crearCredencial(documento, pId, pNombre, pApellido, pClave, pTipo));
        DOMSource fuente = new DOMSource(documento);
        TransformerFactory transformadorFactory = TransformerFactory.newInstance();
        Transformer transformador = transformadorFactory.newTransformer();
        StreamResult resultado = new StreamResult(archivoEntrada);
        transformador.transform(fuente, resultado);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Valida las credenciales del usuario
   * 
   * @param pId Identificacion del usuario
   * @param pClave Constrasenna del usaurio
   * @param pTipo Tipo de usaurio
   * @return Verdadero si las credenciales son correctas y falso en el caso contrario
   */
  public boolean validarCredencial(String pId, String pClave, String pTipo) {
    String[] credenciales = leerCredenciales();
    if (pId.isBlank() == false && pClave.isBlank() == false) {
      for (String credencial : credenciales) {
        if (credencial.contains(pId) == true && credencial.contains(pClave) == true && credencial
          .contains(pTipo) == true) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Recorre y lee el archivo MXL
   * @return Una lista con con todas las filas de usaurios registrados
   */
  private String[] leerCredenciales() {
    String data = "";
    try {
      File inputFile = new File("./credencialesUsuarios.xml");
      DocumentBuilder constructorDocumento = construirDocumento();
      Document documento = constructorDocumento.parse(inputFile);
      documento.getDocumentElement().normalize();
      NodeList nLista = documento.getElementsByTagName("CredencialUsuario");
      for (int temp = 0; temp < nLista.getLength(); temp++) {
        Node nNodo = nLista.item(temp);

        if (nNodo.getNodeType() == Node.ELEMENT_NODE) {
          Element eElemento = (Element) nNodo;
          data += xmlParse(eElemento);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    String[] resultado = data.split("\n");
    return resultado;
  }

  /**
   * Aplica el formato Element a un registro en especifico en formato Document
   * 
   * @param pDocumento Documento del usaurio
   * @param pId Identificacion del usuario
   * @param pNombre Nombre del usuario
   * @param pApellido Apellido del usuario
   * @param pClave Contrasenna del usuario
   * @param pTipo Tipo del usaurio
   * @return Registro con formato Element
   */
  private Element crearCredencial(Document pDocumento, String pId, String pNombre, String pApellido, 
    String pClave, String pTipo) {
    Element credencialRoot = pDocumento.createElement("CredencialUsuario");
    Element idUsuario = pDocumento.createElement("IdUsuario");
    idUsuario.appendChild(pDocumento.createTextNode(pId));
    Element nombre = pDocumento.createElement("Nombre");
    nombre.appendChild(pDocumento.createTextNode(pNombre));
    Element apellido = pDocumento.createElement("Apellido");
    apellido.appendChild(pDocumento.createTextNode(pApellido));
    Element clave = pDocumento.createElement("Clave");
    clave.appendChild(pDocumento.createTextNode(pClave));
    Element tipo = pDocumento.createElement("Tipo");
    tipo.appendChild(pDocumento.createTextNode(pTipo));
    credencialRoot.appendChild(idUsuario);
    credencialRoot.appendChild(nombre);
    credencialRoot.appendChild(apellido);
    credencialRoot.appendChild(clave);
    credencialRoot.appendChild(tipo);
    return credencialRoot;
  }

  /**
   * Escribe en el archivo xml utilizado para validar los credenciales del usuario
   * 
   * @param pDocumento Nuevo usuario por registrar
   */
  private void escribirArchivoXml(Document pDocumento) {
    try {
      TransformerFactory transformadorFactory = TransformerFactory.newInstance();
      Transformer transformador = transformadorFactory.newTransformer();
      DOMSource fuente = new DOMSource(pDocumento);
      StreamResult resultado = new StreamResult(new File("./credencialesUsuarios.xml"));
      transformador.transform(fuente, resultado);
      StreamResult resultadoConsola = new StreamResult(System.out);
      transformador.transform(fuente, resultadoConsola);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Construye un documento XML
   * 
   * @return El documento
   */
  private DocumentBuilder construirDocumento() {
    DocumentBuilder constructorDocumentos = null;
    try {
      DocumentBuilderFactory bdFactory = DocumentBuilderFactory.newInstance();
      constructorDocumentos = bdFactory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
    return constructorDocumentos;
  }

  /**
   * Crea una cadena con la informacion del usuario en formato xml
   * 
   * @param eElemento Elemento que se va a modificar
   * @return Cadena con la informacion del usaurio en formato xml
   */
  private String xmlParse(Element eElemento) {
    String data = "";
    data += "< CredencialesUsuarios >" + "< IdUsuario >" + eElemento.getElementsByTagName("IdUsuario").item(0).getTextContent()
      + "< /IdUsuario >";
    data += "< Nombre >" + eElemento.getElementsByTagName("Nombre").item(0).getTextContent() + "< /Nombre >";
    data += "< Apellido >" + eElemento.getElementsByTagName("Apellido").item(0).getTextContent() + "< /Apellido >";
    data += "< Clave >" + eElemento.getElementsByTagName("Clave").item(0).getTextContent() + "< /Clave >";
    data += "< Tipo >" + eElemento.getElementsByTagName("Tipo").item(0).getTextContent() + "< /Tipo > < /CredencialesUsuarios > \n";
    return data;
  }
}
