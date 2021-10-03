package com.poo.bieninmueble.logicaDeNegocios;

import com.poo.bieninmueble.dao.DaoCliente;
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
 * Clase que implementa todos los métodos referentes al Cliente
 */
public class Cliente implements ICliente {

  //atributos
  private String nombre;
  private String apellido;
  private String correo;
  private String contrasenna;
  private String numeroTelefono;
  private String identificacion;
  private IDao bdCliente;

  /**
   * Constructor de la clase Cliente
   */
  public Cliente() {
    bdCliente = new DaoCliente();
  }

  /**
   * Segundo constructor de la clase Cliente
   *
   * @param pNombre Nombre del nuevo cliente
   * @param pApellido Apellido del nuevo cliente
   * @param pNumTelefono Numero de telefono del nuevo cliente
   * @param pCorreo Correo electronico del nuevo cliente
   * @param pIdentificacion Identificacion del nuevo cliente
   */
  public Cliente(String pNombre, String pApellido, String pNumTelefono, String pCorreo,
    String pIdentificacion) {
    this();
    nombre = pNombre;
    apellido = pApellido;
    correo = pCorreo;
    numeroTelefono = pNumTelefono;
    identificacion = pIdentificacion;
  }

  /**
   * Tercer constructor de la Cliente
   *
   * @param pIdentificacion Identificacion del cliente
   */
  public Cliente(String pIdentificacion) {
    bdCliente = new DaoCliente();
    identificacion = pIdentificacion;
  }

  /**
   * Inserta un nuevo cliente en el sistema
   *
   * @param pCliente Nuevo cliente
   * @throws SQLException En caso de no establecer conexion con la base de datos
   */
  @Override
  public void insertarCliente(Cliente pCliente) throws SQLException {
    String contrasena = String.valueOf(GeneradorContrasena.generar(6));
    contrasena = "" + contrasena.trim();
    pCliente.setContrasenna(contrasena);
    bdCliente.manipulationQuery("INSERT INTO USUARIO(ID_USUARIO,NUMTELEFONO,NOMBRE,APELLIDO, CORREO, "
      + "CONTRASENNA, ROL) VALUES ('" + pCliente.getIdentificacion() + "','" + pCliente.getNumeroTelefono()
      + "','" + pCliente.getNombre() + "','" + pCliente.getApellido() + "','" + pCliente.getCorreo() + "','"
      + contrasena + "','" + "Cliente" + "')");
    GestorXML registradorCredencialXML = new GestorXML();
    Email correo = Email.getInstance();
    registradorCredencialXML.registrar(pCliente.getIdentificacion(),
      pCliente.getNombre(), pCliente.getApellido(), contrasena, "Cliente");
    try {
      correo.enviar(pCliente.getCorreo(), "Registro Completado", "<h1>Cliente registrado</h1> "
        + "<h3>Su contraseña es: " + contrasena + "</h3>", new ArrayList<byte[]>(),false);
    } catch (IOException ex) {
      Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Busca y retorna todos los clientes registrados en el sistema.
   *
   * @return Lista con todos los clientes registrados en el sistema
   * @throws SQLException En caso de no establecer conexion con la base de datos
   */
  @Override
  public ArrayList<Cliente> getClientes() throws SQLException {
    ArrayList<Cliente> resultado = new ArrayList<Cliente>();
    resultado = (ArrayList<Cliente>) bdCliente.selectQuery("SELECT * FROM USUARIO WHERE ROL = 'CLIENTE'");
    return resultado;
  }

  /**
   * Retorna la informacion de un cliente en especifico
   * 
   * @param idUsuario Identificador del cliente
   * @return Informacion del cliente
   * @throws SQLException En caso de no establecer conexion con la base de datos
   */
  @Override
  public Cliente getCliente(String idUsuario) throws SQLException {
    ArrayList<Cliente> resultado = new ArrayList<Cliente>();
    resultado = (ArrayList<Cliente>) bdCliente.selectQuery("SELECT * FROM USUARIO WHERE ID_USUARIO = "
      + "'"+idUsuario+"'");
    if(resultado.size()>0){
      return resultado.get(0);
    }
    return null;
  }
  /**
   * Busca y retorna todos los prospectos de una propiedad
   *
   * @param pIdPropiedad Numero de finca de la propiedad solicitada
   * @return Lista con todos los prospectos de una propiedad
   * @throws SQLException En caso de no establecer conexion con la base de datos
   */
  @Override
  public ArrayList<Cliente> getProspectos(String pIdPropiedad) throws SQLException {
    ArrayList<Cliente> resultado = new ArrayList<Cliente>();
    resultado = (ArrayList<Cliente>) bdCliente.selectQuery("SELECT USUARIO.ID_USUARIO, NUMTELEFONO, NOMBRE, APELLIDO, CORREO, CONTRASENNA  FROM USUARIO RIGHT JOIN PROSPECTO ON PROSPECTO.ID_USUARIO = USUARIO.ID_USUARIO WHERE ID_PROPIEDAD ='" + pIdPropiedad + "'" + "ORDER BY PROSPECTO.FECHA_CONSULTA");
    return resultado;
  }

  /**
   * Asigna un prospecto a una propiedad especifica
   *
   * @param pCliente Identificacion del cliente
   * @param pPropiedad Numero de finca de la propiedad
   * @param pFecha Fecha en que se asigna el prospecto
   * @throws SQLException En caso de no establecer conexion con la base de datos
   */
  @Override
  public void setProspecto(String pCliente, int pPropiedad, String pFecha) throws SQLException {
    bdCliente.manipulationQuery("INSERT INTO PROSPECTO (ID_PROPIEDAD, ID_USUARIO, FECHA_CONSULTA) "
      + "VALUES (" + pPropiedad + ", '" + pCliente + "', '" + pFecha + "')");
  }

  /**
   * Retorna la identificación del cliente
   *
   * @return Identificación del cliente
   */
  public String getIdentificacion() {
    return identificacion;
  }

  /**
   * Asigna la identificación del cliente
   *
   * @param pIdentificacion Identificación del cliente
   */
  public void setIdentificacion(String pIdentificacion) {
    identificacion = pIdentificacion;
  }

  /**
   * Retorna el nombre del cliente
   *
   * @return Nombre del cliente
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Asigna el nombre del cliente
   *
   * @param pNombre Nombre del cliente
   */
  public void setNombre(String pNombre) {
    nombre = pNombre;
  }

  /**
   * Retorna el apellido del cliente
   *
   * @return Apellido del cliente
   */
  public String getApellido() {
    return apellido;
  }

  /**
   * Asigna el apellido del cliente
   *
   * @param pApellido Apellido del cliente
   */
  public void setApellido(String pApellido) {
    apellido = pApellido;
  }

  /**
   * Retorna el correo electrónico del cliente
   *
   * @return Correo electronico del cliente
   */
  public String getCorreo() {
    return correo;
  }

  /**
   * Asigna el correo electronico del cliente
   *
   * @param pCorreo Correo electronico del cliente
   */
  public void setCorreo(String pCorreo) {
    correo = pCorreo;
  }

  /**
   * Retorna la contrasenna del cliente
   *
   * @return Contrasenna del cliente
   */
  public String getContrasenna() {
    return contrasenna;
  }

  /**
   * Asigna la contrasenna del cliente
   *
   * @param pContrasenna Contrasenna del cliente
   */
  public void setContrasenna(String pContrasenna) {
    contrasenna = pContrasenna;
  }

  /**
   * Retorna el numero de telefono del cliente
   *
   * @return Numero de telefono del cliente
   */
  public String getNumeroTelefono() {
    return numeroTelefono;
  }

  /**
   * Asigna el numero de telefono del cliente
   *
   * @param pNumeroTelefono Numero de telefono del cliente
   */
  public void setNumeroTelefono(String pNumeroTelefono) {
    numeroTelefono = pNumeroTelefono;
  }
  
  /**
   * Crea una cadena con la información personal del cliente
   *
   * @return Cadena con la información personal del cliente
   */
  @Override
  public String toString() {
    String resultado;
    resultado = "Nombre: " + this.getNombre() + "\n"
      + "Apellido: " + this.getApellido() + "\n"
      + "Identificación: " + this.getIdentificacion() + "\n"
      + "Correo: " + this.getCorreo() + "\n"
      + "Numero de teléfono: " + this.getNumeroTelefono();
    return resultado;
  }

}
