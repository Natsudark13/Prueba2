package com.poo.bieninmueble.logicaDeNegocios;

import com.poo.bieninmueble.dao.DaoCasa;
import com.poo.bieninmueble.dao.IDao;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * Clase que implementa todos los métodos referentes a la Casa
 */
public class Casa extends Propiedad {

  //atributos
  private String color;
  private String estilo;
  private double areaConstruccion;
  private int niveles;
  private String annoConstruccion;
  private IDao bdCasa;

  /**
   * Constructor de la clase Casa
   */
  public Casa() {
    bdCasa = new DaoCasa();
  }

  /**
   * Segundo Constructor de la clase Casa
   *
   * @param pNumFinca Numero de finca de la nueva casa
   * @param pModalidad Modalidad de la nueva casa
   * @param pAreaTerreno Area del terreno de la nueva casa
   * @param pValorMetroCuadrado Valor del metro cuadrado de la nueva casa
   * @param pValorFiscal Valor fiscal de la nueva casa
   * @param pProvincia Provincia de la nueva casa
   * @param pCanton Canton de la nueva casa
   * @param pDistrito Distrito de la nueva casa
   * @param pDirExacta Direccion exacta de la nueva casa
   * @param pTipo Tipo de propiedad
   * @param pEstado Estado de la nueva casa
   * @param pPrecio Precio de la nueva casa
   * @param pColor Color de la nueva casa
   * @param pEstilo Estilo de la nueva casa
   * @param pAreaConstruccion Area de construccion de la nueva casa
   * @param pNiveles Cantidad de niveles de la nueva casa
   * @param pAnnoConstruccion Anno de construccion de la nueva casa
   */
  public Casa(int pNumFinca, String pModalidad, double pAreaTerreno, double pValorMetroCuadrado,
    double pValorFiscal, String pProvincia, String pCanton, String pDistrito, String pDirExacta,
    String pTipo, String pEstado, double pPrecio, String pColor, String pEstilo, double pAreaConstruccion,
    int pNiveles, String pAnnoConstruccion) {
    super(pNumFinca, pModalidad, pAreaTerreno, pValorMetroCuadrado, pValorFiscal, pProvincia,
      pCanton, pDistrito, pDirExacta, pTipo, pEstado, pPrecio);
    color = pColor;
    estilo = pEstilo;
    areaConstruccion = pAreaConstruccion;
    niveles = pNiveles;
    annoConstruccion = pAnnoConstruccion;
  }

  /**
   * Registra una nueva casa
   *
   * @param nuevaCasa Casa que se va a registrar en el sistema
   * @throws SQLException En caso de no poder establecer la conexion con la base de datos
   */
  public void registrarCasa(Casa nuevaCasa) throws SQLException {
    int numFinca = nuevaCasa.getNumFinca();
    int cantNiveles = nuevaCasa.getNiveles();
    String color = nuevaCasa.getColor();
    String estilo = nuevaCasa.getEstilo();
    double areaConstruccion = nuevaCasa.getAreaConstruccion();
    String annoConstruccion = nuevaCasa.getAnnoConstruccion();
    bdCasa.manipulationQuery("INSERT INTO CASA (ID_CASA, CANT_NIVELES, COLOR, ANNO_CONSTRUCCION, "
      + "TIPO, AREA_CONSTRUCCION, ESTILO) VALUES (" + numFinca + "," + cantNiveles + ", '" + color
      + "', " + annoConstruccion + ", 'Casa', " + areaConstruccion + ",'" + estilo + "')");
  }

  /**
   * Recupera la informacion de una casa en especifico
   *
   * @param idPropiedad Numero de finca de la casa
   * @return Casa solicitada
   * @throws SQLException En caso de no poder establecer la conexion con la base de datos
   */
  public Casa getCasa(String idPropiedad) throws SQLException {
    ArrayList<Casa> nuevaCasa = (ArrayList<Casa>) bdCasa.selectQuery("SELECT * FROM PROPIEDAD JOIN "
      + "CASA ON ID_PROPIEDAD=ID_CASA WHERE ID_CASA = " + idPropiedad);
    if (nuevaCasa.size() > 0) {
      Casa casaFinal = nuevaCasa.get(0);
      ArrayList<byte[]> fotografias = casaFinal.getFotografias(idPropiedad);
      casaFinal.setFotografias(fotografias);
      return casaFinal;
    }
    return null;
  }

  /**
   * Actualiza la informacion de una casa previamente registrada
   *
   * @param nuevaCasa Casa con la nueva informacion
   * @throws SQLException En caso de no poder establecer la conexion con la base de datos
   */
  public void actualizarCasa(Casa nuevaCasa) throws SQLException {
    int numFinca = nuevaCasa.getNumFinca();
    int cantNiveles = nuevaCasa.getNiveles();
    String color = nuevaCasa.getColor();
    String estilo = nuevaCasa.getEstilo();
    double areaConstruccion = nuevaCasa.getAreaConstruccion();
    String annoConstruccion = nuevaCasa.getAnnoConstruccion();
    bdCasa.manipulationQuery("UPDATE CASA SET CANT_NIVELES = " + cantNiveles + ", COLOR = '" + color
      + "', ANNO_CONSTRUCCION = " + annoConstruccion + ", AREA_CONSTRUCCION = " + areaConstruccion
      + ", ESTILO = '" + estilo + "' WHERE ID_CASA = " + numFinca);
  }

  /**
   * Recupera el color de la casa
   *
   * @return Color de la casa
   */
  public String getColor() {
    return color;
  }

  /**
   * Asigna el color de la casa
   *
   * @param color Color de la casa
   */
  public void setColor(String color) {
    this.color = color;
  }

  /**
   * Recupera el estilo de la casa
   *
   * @return Estilo de la casa
   */
  public String getEstilo() {
    return estilo;
  }

  /**
   * Asigna el estilo de la casa
   *
   * @param estilo Estilo de la casa
   */
  public void setEstilo(String estilo) {
    this.estilo = estilo;
  }

  /**
   * Recupera el area de construccion de la casa
   *
   * @return Area de construccion de la casa
   */
  public double getAreaConstruccion() {
    return areaConstruccion;
  }

  /**
   * Asigna el area de construccion de la casa
   *
   * @param areaConstruccion Area de construccion de la casa
   */
  public void setAreaConstruccion(double areaConstruccion) {
    this.areaConstruccion = areaConstruccion;
  }

  /**
   * Recupera la cantidad de niveles de la casa
   *
   * @return Cantidad de niveles de la casa
   */
  public int getNiveles() {
    return niveles;
  }

  /**
   * Asigna la cantidad de niveles de la casa
   *
   * @param niveles Cantidad de niveles de la casa
   */
  public void setNiveles(int niveles) {
    this.niveles = niveles;
  }

  /**
   * Recupera el anno de construccion de la casa
   *
   * @return Anno de construccion de la casa
   */
  public String getAnnoConstruccion() {
    return annoConstruccion;
  }

  /**
   * Asigna el anno de construccion de la casa
   *
   * @param annoConstruccion Anno de construccion de la casa
   */
  public void setAnnoConstruccion(String annoConstruccion) {
    this.annoConstruccion = annoConstruccion;
  }
}
