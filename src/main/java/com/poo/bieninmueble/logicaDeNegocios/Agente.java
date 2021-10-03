package com.poo.bieninmueble.logicaDeNegocios;

import com.poo.bieninmueble.dao.DaoAgente;
import com.poo.bieninmueble.dao.IDao;
import com.poo.bieninmueble.servicios.Email;
import com.poo.bieninmueble.servicios.GeneradorContrasena;
import com.poo.bieninmueble.servicios.GestorXML;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * Clase que implementa todos los métodos referentes al Agente
 */
public class Agente implements IAgente {

  //atributos
  private String identificacion;
  private String nombre;
  private String apellido;
  private String correo;
  private String contrasenna;
  private String numeroTelefono;
  private int cantPropiedades;
  private IDao bdAgente;

  /**
   * Constructor de la clase Agente
   */
  public Agente() {
    bdAgente = new DaoAgente();
  }

  /**
   * Segundo constructor de la clase Agente, en este caso solo se necesita crear al agente con la
   * identificación
   *
   * @param pIdentificacion Identificacion del agente
   */
  public Agente(String pIdentificacion) {
    this();
    this.identificacion = pIdentificacion;
  }

  /**
   * Tercer constructor de la clase Agente.
   *
   * @param pIdentifiacion Identificación del nuevo agente
   * @param pNombre Nombre del nuevo agente
   * @param pApellido Apellido del nuevo agente
   * @param pCorreo Correo electronico del nuevo agente
   * @param pNumeroTelefono Numero de telefono del nuevo agente
   */
  public Agente(String pIdentifiacion, String pNombre, String pApellido, String pCorreo,
    String pNumeroTelefono) {
    identificacion = pIdentifiacion;
    nombre = pNombre;
    apellido = pApellido;
    correo = pCorreo;
    numeroTelefono = pNumeroTelefono;
    bdAgente = new DaoAgente();
  }

  /**
   * Método que registra un nuevo agente en la base de datos, XML y envia correo electrónico para
   * indicar la contraseña del nuevo registro
   *
   * @param pAgente Nuevo agente que se va a insertar en la base de datos
   * @throws SQLException
   */
  @Override
  public void insertarAgente(Agente pAgente) throws SQLException {
    String contrasena = String.valueOf(GeneradorContrasena.generar(6));
    contrasena = "" + contrasena.trim();
    pAgente.setContrasenna(contrasena);
    bdAgente.manipulationQuery("INSERT INTO USUARIO(ID_USUARIO,NUMTELEFONO,NOMBRE,APELLIDO, CORREO, "
      + "CONTRASENNA, ROL) VALUES " + "('" + pAgente.getIdentificacion() + "','" + pAgente.getNumeroTelefono()
      + "','" + pAgente.getNombre() + "','" + pAgente.getApellido() + "','" + pAgente.getCorreo()
      + "','" + contrasena + "','" + "Agente" + "')");
    GestorXML registradorCredencialXML = new GestorXML();
    Email correo = Email.getInstance();
    registradorCredencialXML.registrar(pAgente.getIdentificacion(),
      pAgente.getNombre(), pAgente.getApellido(), contrasena, "Agente");
    try {
      correo.enviar(pAgente.getCorreo(), "Registro Completado", "<h1>Agente registrado</h1> "
        + "<h3>Su contraseña es: " + contrasena + "</h3>", new ArrayList<byte[]>(),false);
    } catch (IOException ex) {
      Logger.getLogger(Agente.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Recupera la información de los agentes registrados en el sistema.
   *
   * @return Lista con la información de los agentes registrados
   * @throws SQLException En caso de no poder establecer la conexión con la base de datos
   */
  @Override
  public ArrayList<Agente> getAgentes() throws SQLException {
    ArrayList<Agente> listaAgentes = new ArrayList<Agente>();
    listaAgentes = (ArrayList<Agente>) bdAgente.selectQuery("SELECT ID_USUARIO, NOMBRE, APELLIDO, "
      + "CONTRASENNA, NUMTELEFONO, CORREO, CASE WHEN TOTAL IS NULL THEN 0 ELSE TOTAL END AS TOTAL "
      + "FROM USUARIO LEFT JOIN (SELECT ID_AGENTE,COUNT(ID_PROPIEDAD) AS TOTAL FROM PROPIEDAD GROUP "
      + "BY ID_AGENTE) AS PROPIEDADES_AGENTE ON USUARIO.ID_USUARIO = PROPIEDADES_AGENTE.ID_AGENTE "
      + "WHERE USUARIO.ROL = 'AGENTE' ORDER BY APELLIDO");
    return listaAgentes;
  }

  /**
   * Recupera un agente en específico asociado a una propiedad.
   *
   * @param idPropiedad Numero de finca de la propiedad
   * @return Agente que creó la propiedad específica
   * @throws SQLException En caso de no poder establecer la conexión con la base de datos
   */
  @Override
  public Agente getAgente(String idPropiedad) throws SQLException {
    ArrayList<Agente> agente = new ArrayList<Agente>();
    agente = (ArrayList<Agente>) bdAgente.selectQuery("SELECT ID_USUARIO, NOMBRE, APELLIDO, CONTRASENNA, "
      + "NUMTELEFONO, CORREO, CASE WHEN TOTAL IS NULL THEN 0 ELSE TOTAL END AS TOTAL FROM USUARIO LEFT "
      + "JOIN (SELECT ID_AGENTE,COUNT(ID_PROPIEDAD) AS TOTAL FROM PROPIEDAD GROUP BY ID_AGENTE) AS "
      + "PROPIEDADES_AGENTE ON USUARIO.ID_USUARIO = PROPIEDADES_AGENTE.ID_AGENTE JOIN PROPIEDAD ON "
      + "USUARIO.ID_USUARIO=PROPIEDAD.ID_AGENTE WHERE ID_PROPIEDAD = " + idPropiedad);
    if (agente.size() > 0) {
      return agente.get(0);
    }
    return null;
  }

  /**
   * Retorna la identificación del agente
   *
   * @return Identificación del agente
   */
  public String getIdentificacion() {
    return identificacion;
  }

  /**
   * Asigna la identificación del agente
   *
   * @param pIdentificacion Identificación del agente
   */
  public void setIdentificacion(String pIdentificacion) {
    identificacion = pIdentificacion;
  }

  /**
   * Retorna el nombre del agente
   *
   * @return Nombre del agente
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Asigna el nombre del agente
   *
   * @param pNombre Nombre del agente
   */
  public void setNombre(String pNombre) {
    nombre = pNombre;
  }

  /**
   * Retorna el apellido del agente
   *
   * @return Apellido del agente
   */
  public String getApellido() {
    return apellido;
  }

  /**
   * Asigna el apellido del agente
   *
   * @param pApellido Apellido del agente
   */
  public void setApellido(String pApellido) {
    apellido = pApellido;
  }

  /**
   * Retorna el correo electrónico del agente
   *
   * @return Correo electronico del agente
   */
  public String getCorreo() {
    return correo;
  }

  /**
   * Asigna el correo electronico del agente
   *
   * @param pCorreo Correo electronico del agente
   */
  public void setCorreo(String pCorreo) {
    correo = pCorreo;
  }

  /**
   * Retorna la contrasenna del agente
   *
   * @return Contrasenna del agente
   */
  public String getContrasenna() {
    return contrasenna;
  }

  /**
   * Asigna la contrasenna del agente
   *
   * @param pContrasenna Contrasenna del agente
   */
  public void setContrasenna(String pContrasenna) {
    contrasenna = pContrasenna;
  }

  /**
   * Retorna el numero de telefono del agente
   *
   * @return Numero de telefono del agente
   */
  public String getNumeroTelefono() {
    return numeroTelefono;
  }

  /**
   * Asigna el numero de telefono del agente
   *
   * @param pNumeroTelefono Numero de telefono del agente
   */
  public void setNumeroTelefono(String pNumeroTelefono) {
    numeroTelefono = pNumeroTelefono;
  }

  /**
   * Retorna la cantidad de propiedades del agente
   *
   * @return Cantidad de propiedades del agente
   */
  public int getCantPropiedades() {
    return cantPropiedades;
  }

  /**
   * Asigna la cantidad de propiedades del agente
   *
   * @param pCantPropiedades La cantidad de propiedades del agente
   */
  public void setCantPropiedades(int pCantPropiedades) {
    cantPropiedades = pCantPropiedades;
  }

  /**
   * Crea una cadena con la información personal del agente
   *
   * @return Cadena con la información personal del agente
   */
  @Override
  public String toString() {
    String resultado;
    resultado = "Nombre: " + this.getNombre() + "\n"
      + "Apellido: " + this.getApellido() + "\n"
      + "Identificacion: " + this.getIdentificacion() + "\n"
      + "Correo: " + this.getCorreo() + "\n"
      + "Numero de telefono: " + this.getNumeroTelefono() + "\n"
      + "Cantidad de propiedades: " + this.getCantPropiedades();
    return resultado;
  }
}
