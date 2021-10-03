package com.poo.bieninmueble.logicaDeNegocios;

import com.poo.bieninmueble.dao.DaoComentario;
import com.poo.bieninmueble.dao.DaoPropiedad;
import com.poo.bieninmueble.dao.IDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * Clase que implementa todos los métodos referentes a Propiedad
 */
public class Propiedad implements IPropiedad {

  //atributos
  protected int numFinca;
  protected String modalidad;
  protected double areaTerreno;
  protected double valorMetroCuadrado;
  protected double valorFiscal;
  protected String provincia;
  protected String canton;
  protected String distrito;
  protected String dirExacta;
  protected String tipo;
  protected String estado;
  protected double precio;
  private IDao bdPropiedad, bdComentario;
  protected ArrayList<byte[]> fotografias;
  protected ArrayList<Comentario> comentarios;

  /**
   * Constructor de la clase Propiedad.
   */
  public Propiedad() {
    bdPropiedad = new DaoPropiedad();
    bdComentario = new DaoComentario();
  }

  /**
   * Segundo constructor de la clase Propiedad
   *
   * @param pNumFinca Numero de finca de la nueva propiedad
   * @param pModalidad Modalidad de la nueva propiedad
   * @param pAreaTerreno Area del terreno de la nueva propiedad
   * @param pValorMetroCuadrado Valor del metro cuadrado de la nueva propiedad
   * @param pValorFiscal Valor fiscal de la nueva propiedad
   * @param pProvincia Provincia de la nueva propiedad
   * @param pCanton Canton de la nueva propiedad
   * @param pDistrito Distrito de la nueva propiedad
   * @param pDirExacta Direccion exacta de la nueva propiedad
   * @param pTipo Tipo de la nueva propiedad
   * @param pEstado Estado de la nueva propiedad
   * @param pPrecio Precio de la nueva propiedad
   */
  public Propiedad(int pNumFinca, String pModalidad, double pAreaTerreno, double pValorMetroCuadrado,
    double pValorFiscal, String pProvincia, String pCanton, String pDistrito, String pDirExacta,
    String pTipo, String pEstado, double pPrecio) {
    this();
    numFinca = pNumFinca;
    areaTerreno = pAreaTerreno;
    valorMetroCuadrado = pValorMetroCuadrado;
    valorFiscal = pValorFiscal;
    modalidad = pModalidad;
    provincia = pProvincia;
    canton = pCanton;
    distrito = pDistrito;
    dirExacta = pDirExacta;
    tipo = pTipo;
    estado = pEstado;
    precio = pPrecio;
  }

  /**
   * Retorna la informacion de una propiedad en especifico
   *
   * @param pNumFinca Numero de finca de la propiedad que se desea consultar
   * @return Informacion de la propiedad en especifico
   * @throws SQLException En caso de no poder establecer conexion con la base de datos
   */
  @Override
  public Propiedad getPropiedadId(String pNumFinca) throws SQLException {
    ArrayList<Propiedad> resultado = new ArrayList<Propiedad>();
    resultado = (ArrayList<Propiedad>) bdPropiedad.selectQuery("SELECT * FROM PROPIEDAD WHERE "
      + "ID_PROPIEDAD = '" + pNumFinca + "'");
    Propiedad propiedadSeleccionada = resultado.get(0);
    return propiedadSeleccionada;
  }

  /**
   * Retorna las propiedades asociadas a un agente en especifico
   *
   * @param pId Identificador del agente
   * @return Una lista con las propiedades asociadas a un agente
   * @throws SQLException En caso de no poder establecer conexion con la base de datos
   */
  @Override
  public ArrayList<Propiedad> getPropiedadesAgente(String pId) throws SQLException {
    ArrayList<Propiedad> resultado = new ArrayList<Propiedad>();
    resultado = (ArrayList<Propiedad>) bdPropiedad.selectQuery("SELECT * FROM PROPIEDAD WHERE ID_AGENTE "
      + "= '" + pId + "'");
    return resultado;
  }

  /**
   * Retorna todas las propiedades registradas en el sistema que se encuentren activas
   *
   * @return Lista con todas las propiedades activas
   * @throws SQLException En caso de no poder establecer conexion con la base de datos
   */
  @Override
  public ArrayList<Propiedad> getPropiedadesCliente() throws SQLException {
    ArrayList<Propiedad> resultado = new ArrayList<Propiedad>();
    resultado = (ArrayList<Propiedad>) bdPropiedad.selectQuery("SELECT * FROM PROPIEDAD WHERE ESTADO "
      + "= 'Activo'");
    return resultado;
  }

  /**
   * Retorna las propiedades asociadas a un agente cuando se aplica un filtro de tipo, modalidad o
   * provincia
   *
   * @param pCriterio Criterio de busqueda
   * @param pDato Dato de busqueda
   * @param pId Identificador del agente
   * @return Lista con las propiedades asociadas a un agente cuando se aplica un filtro de tipo,
   * modalidad o provincia
   * @throws SQLException En caso de no poder establecer conexion con la base de datos
   */
  @Override
  public ArrayList<Propiedad> getPropiedadesFiltroAgente(String pCriterio, String pDato, String pId)
    throws SQLException {
    ArrayList<Propiedad> resultado = new ArrayList<Propiedad>();
    resultado = (ArrayList<Propiedad>) bdPropiedad.selectQuery("SELECT * FROM PROPIEDAD WHERE ID_AGENTE "
      + "= '" + pId + "' AND " + pCriterio + " = '" + pDato + "'");
    return resultado;
  }

  /**
   * Retorna las propiedades activas cuando se aplica un filtro de tipo, modalidad o provincia
   *
   * @param pCriterio Criterio de busqueda
   * @param pDato Dato de busqueda
   * @return Lista con las propiedades activas cuando se aplica un filtro de tipo, modalidad o
   * provincia
   * @throws SQLException En caso de no poder establecer conexion con la base de datos
   */
  @Override
  public ArrayList<Propiedad> getPropiedadesFiltroCliente(String pCriterio, String pDato) throws
    SQLException {
    ArrayList<Propiedad> resultado = new ArrayList<Propiedad>();
    resultado = (ArrayList<Propiedad>) bdPropiedad.selectQuery("SELECT * FROM PROPIEDAD WHERE ESTADO "
      + "= 'ACTIVO' AND " + pCriterio + " = '" + pDato + "'");
    return resultado;
  }

  /**
   * Retorna las propiedades activas que se encuentren en un rango especifico de precio
   *
   * @param pMenor Numero menor del rango de precio
   * @param pMayor Numero mayor del rango de precio
   * @return Lista con las propiedades activas que se encuentren en un rango especifico de precio
   * @throws SQLException En caso de no poder establecer conexion con la base de datos
   */
  @Override
  public ArrayList<Propiedad> getPrecioCliente(double pMenor, double pMayor) throws SQLException {
    ArrayList<Propiedad> resultado = new ArrayList<Propiedad>();
    resultado = (ArrayList<Propiedad>) bdPropiedad.selectQuery("SELECT * FROM PROPIEDAD WHERE ESTADO = 'ACTIVO' AND "
      + "PRECIO >= " + pMenor + " AND PRECIO <= " + pMayor);
    return resultado;
  }

  /**
   * Retorna las propiedades asociadas a un agente que se encuentren en un rango especifico de
   * precio
   *
   * @param pMenor Numero menor del rango de precio
   * @param pMayor Numero mayor del rango de precio
   * @param pId Idnetificador del agente
   * @return Lista con las propiedades asociadas a un agente que se encuentren en un rango
   * especifico de precio
   * @throws SQLException En caso de no poder establecer conexion con la base de datos
   */
  @Override
  public ArrayList<Propiedad> getPrecioAgente(double pMenor, double pMayor, String pId) throws SQLException {
    ArrayList<Propiedad> resultado = new ArrayList<Propiedad>();
    resultado = (ArrayList<Propiedad>) bdPropiedad.selectQuery("SELECT * FROM PROPIEDAD WHERE ID_AGENTE = '" + pId + "' AND "
      + "PRECIO >= " + pMenor + " AND PRECIO <= " + pMayor);
    return resultado;
  }

  /**
   * Retorna las fotogracias asociadas a una propiedad
   * 
   * @param id Numero de finca de la propiedad
   * @return Lista con las fotografias asociadas a una propiedad
   * @throws SQLException En caso de no poder establecer conexion con la base de datos
   */
  @Override
  public ArrayList<byte[]> getFotografias(String id) throws SQLException {
    ArrayList<byte[]> resultado = new ArrayList<byte[]>();
    resultado = (ArrayList<byte[]>) bdPropiedad.getFotografias(id);
    return resultado;
  }

  /**
   * Registra una nueva propiedad en el sistema
   * 
   * @param nuevaPropiedad Nueva propiedad para registrar en el sistema
   * @param pIdAgente Identificador del agente que esta registrando la propiedad
   */
  @Override
  public void registrarPropiedad(Propiedad nuevaPropiedad, String pIdAgente) {
    int numFinca = nuevaPropiedad.getNumFinca();
    String modalidad = nuevaPropiedad.getModalidad();
    double area = nuevaPropiedad.getAreaTerreno();
    double metro = nuevaPropiedad.getValorMetroCuadrado();
    double fiscal = nuevaPropiedad.getValorFiscal();
    String provincia = nuevaPropiedad.getProvincia();
    String canton = nuevaPropiedad.getCanton();
    String distrito = nuevaPropiedad.getDistrito();
    String dirExacta = nuevaPropiedad.getDirExacta();
    String estado = nuevaPropiedad.getEstado();
    String tipo = nuevaPropiedad.getTipo();
    System.out.println(nuevaPropiedad.getFotografias().size());
    double precio = nuevaPropiedad.getPrecio();
    try {
      bdPropiedad.manipulationQuery("INSERT INTO PROPIEDAD (ID_PROPIEDAD, ID_AGENTE, MODALIDAD, "
        + "AREA_TERRENO, VALOR_METRO, VALOR_FISCAL, PROVINCIA, CANTON, DISTRITO, DIREXACTA, TIPO, "
        + "ESTADO, PRECIO) VALUES (" + numFinca + ", '" + pIdAgente + "' , '" + modalidad + "'," + 
        area + "," + metro + "," + fiscal + ", '" + provincia + "' , '" + canton + "' , '" + distrito 
        + "' , '" + dirExacta + "' , '" + tipo + "' , '" + estado + "', " + precio + ")");
      bdPropiedad.insertarFotografias(nuevaPropiedad);
    } catch (SQLException ex) {
      Logger.getLogger(Propiedad.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Elimina una propiedad del sistema
   * 
   * @param pIdPropiedad Numero de finca de la propiedad que se va a eliminar
   */
  @Override
  public void eliminarPropiedad(String pIdPropiedad) {
    try {
      bdPropiedad.manipulationQuery("DELETE FROM PROPIEDAD WHERE ID_PROPIEDAD = '" + pIdPropiedad + "'");
    } catch (SQLException ex) {
      Logger.getLogger(Propiedad.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Actualiza la informacion de una propiedad previamente registrada
   * 
   * @param nuevaPropiedad Propiedad con la nueva informacion por actualizar
   */
  @Override
  public void actualizarPropiedad(Propiedad nuevaPropiedad) {
    int numFinca = nuevaPropiedad.getNumFinca();
    String modalidad = nuevaPropiedad.getModalidad();
    double area = nuevaPropiedad.getAreaTerreno();
    double metro = nuevaPropiedad.getValorMetroCuadrado();
    double fiscal = nuevaPropiedad.getValorFiscal();
    String provincia = nuevaPropiedad.getProvincia();
    String canton = nuevaPropiedad.getCanton();
    String distrito = nuevaPropiedad.getDistrito();
    String dirExacta = nuevaPropiedad.getDirExacta();
    String estado = nuevaPropiedad.getEstado();
    String tipo = nuevaPropiedad.getTipo();
    System.out.println(nuevaPropiedad.getFotografias().size());
    double precio = nuevaPropiedad.getPrecio();
    try {
      bdPropiedad.manipulationQuery("UPDATE PROPIEDAD SET MODALIDAD = '" + modalidad + "', AREA_TERRENO "
        + "= " + area + ", VALOR_METRO = " + metro + ", VALOR_FISCAL = "+ fiscal + ", PROVINCIA = '" 
        + provincia + "', CANTON = '" + canton + "' , DISTRITO = '" + distrito + "', DIREXACTA = '" 
        + dirExacta + "', ESTADO = '" + estado + "', PRECIO = " + precio + " WHERE ID_PROPIEDAD = " 
        + numFinca);
      bdPropiedad.manipulationQuery("DELETE FROM FOTOGRAFIA_PROPIEDAD WHERE ID_PROPIEDAD = " + numFinca);
      bdPropiedad.insertarFotografias(nuevaPropiedad);
    } catch (SQLException ex) {
      Logger.getLogger(Propiedad.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Agrega un comentario a una propiedad
   * 
   * @param pNumFinca Numero de finca de la propiedad
   * @param pComentario Texto del comentario
   * @throws SQLException En caso de no poder establecer conexion con la base de datos
   */
  @Override
  public void agregarComentario(String pNumFinca, String pComentario) throws SQLException {
    Comentario comentario = new Comentario(pComentario, Integer.parseInt(pNumFinca));
    bdPropiedad.manipulationQuery("INSERT INTO COMENTARIO (ID_PROPIEDAD, COMENTARIO) VALUES (" + 
      comentario.getIdPropiedad() + ", '" + comentario.getComentario() + "')");
    comentarios.add(comentario);
  }

  /**
   * Retorna todos los comentarios asociados a una propiedad
   * 
   * @param pId Numero de finca de la propiedad
   * @return Lista con todos los comentarios asociados a una propiedad
   * @throws SQLException En caso de no poder establecer conexion con la base de datos
   */
  @Override
  public ArrayList<Comentario> getComentarios(String pId) throws SQLException {
    ArrayList<Comentario> comentarios = new ArrayList<Comentario>();
    comentarios = (ArrayList<Comentario>) bdComentario.selectQuery("SELECT * FROM COMENTARIO WHERE "
      + "ID_PROPIEDAD = " + pId);
    return comentarios;
  }
  /**
   * Retorna la informacion de la propiedad
   * @return String con toda la informacion de la propiedad
   */
  @Override
  public String toString(){
    String resultado;
    resultado = "Número Finca: " + this.getNumFinca() + "\n"
      + "Modalidad: " + this.getModalidad() + "\n"
      + "Área terreno: " + this.getAreaTerreno() + "\n"
      + "Valor metro cuadrado: " + this.getValorMetroCuadrado() + "\n"
      + "Precio: " + this.getPrecio() + "\n"
      + "Provincia: " + this.getProvincia() + "\n"
      + "Cantón: " + this.getCanton()+ "\n"
      + "Distrito: " + this.getDistrito()+ "\n"
      + "Direccion exacta: " + this.getDirExacta()+ "\n"
      + "Tipo: " + this.getTipo() + "\n";
    return resultado;
  }

  public int getNumFinca() {
    return numFinca;
  }

  public void setNumFinca(int pNumFinca) {
    numFinca = pNumFinca;
  }

  public double getAreaTerreno() {
    return areaTerreno;
  }

  public void setAreaTerreno(double pAreaTerreno) {
    areaTerreno = pAreaTerreno;
  }

  public double getValorMetroCuadrado() {
    return valorMetroCuadrado;
  }

  public void setValorMetroCuadrado(double pValorMetroCuadrado) {
    valorMetroCuadrado = pValorMetroCuadrado;
  }

  public double getValorFiscal() {
    return valorFiscal;
  }

  public void setValorFiscal(double pValorFiscal) {
    valorFiscal = pValorFiscal;
  }

  public String getModalidad() {
    return modalidad;
  }

  public void setModalidad(String modalidad) {
    this.modalidad = modalidad;
  }

  public String getProvincia() {
    return provincia;
  }

  public void setProvincia(String provincia) {
    this.provincia = provincia;
  }

  public String getCanton() {
    return canton;
  }

  public void setCanton(String canton) {
    this.canton = canton;
  }

  public String getDistrito() {
    return distrito;
  }

  public void setDistrito(String distrito) {
    this.distrito = distrito;
  }

  public String getDirExacta() {
    return dirExacta;
  }

  public void setDirExacta(String dirExacta) {
    this.dirExacta = dirExacta;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public ArrayList<byte[]> getFotografias() {
    return fotografias;
  }

  public void setFotografias(ArrayList<byte[]> fotografias) {
    this.fotografias = fotografias;
  }

  public double getPrecio() {
    return precio;
  }

  public void setPrecio(double precio) {
    this.precio = precio;
  }

  public ArrayList<Comentario> getComentarios() {
    return comentarios;
  }

  public void setComentarios(ArrayList<Comentario> comentarios) {
    this.comentarios = comentarios;
  }
}
