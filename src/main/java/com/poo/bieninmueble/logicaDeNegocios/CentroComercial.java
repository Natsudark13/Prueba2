package com.poo.bieninmueble.logicaDeNegocios;

import com.poo.bieninmueble.dao.DaoCentroComercial;
import com.poo.bieninmueble.dao.IDao;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * Clase que implementa todos los métodos referentes a Centro Comercial
 */
public class CentroComercial extends Propiedad {

  //atributos
  private String estilo;
  private double areaConstruccion;
  private int cantTiendas;
  private int cantSalasCine;
  private String cadenaCine;
  private int cantWifi;
  private int escaleras;
  private int espacios;
  private IDao bdCentroComercial;

  /**
   * Construcctor de la clase CentroComercial
   */
  public CentroComercial() {
    bdCentroComercial = new DaoCentroComercial();
  }

  /**
   * Segundo constructor de la clase CentroComercial
   *
   * @param pNumFinca Numero de finca del nuevo centro comercial
   * @param pModalidad Modalidad del nuevo centro comercial
   * @param pAreaTerreno Area del terreno del nuevo centro comercial
   * @param pValorMetroCuadrado Valor del metro cuadrado del nuevo centro comercial
   * @param pValorFiscal Valor fiscal del nuevo centro comercial
   * @param pProvincia Provincia del nuevo centro comercial
   * @param pCanton Canton del nuevo centro comercial
   * @param pDistrito Distrito del nuevo centro comercial
   * @param pDirExacta Direccion exacta del nuevo centro comercial
   * @param pTipo Tipo del nuevo centro comercial
   * @param pEstado Estado del nuevo centro comercial
   * @param pPrecio Precio del nuevo centro comercial
   * @param pEstilo Estilo del nuevo centro comercial
   * @param pAreaConstruccion Area de construccion del nuevo centro comercial
   * @param pCantTiendas Cantidad de tiendas del nuevo centro comercial
   * @param pCantSalasCine Cantidad de salas de cine del nuevo centro comercial
   * @param pCadenaCine
   * @param pCantWifi Cantidad de wifi del nuevo centro comercial
   * @param pEscalera Cantidad de escaleras electricas del nuevo centro comercial
   * @param pEspacios Cantidad de espacios de parqueo para movilidad reducida del nuevo centro
   * comercial
   */
  public CentroComercial(int pNumFinca, String pModalidad, double pAreaTerreno, double pValorMetroCuadrado,
    double pValorFiscal, String pProvincia, String pCanton, String pDistrito, String pDirExacta,
    String pTipo, String pEstado, double pPrecio, String pEstilo, double pAreaConstruccion, int pCantTiendas,
    int pCantSalasCine, String pCadenaCine, int pCantWifi, int pEscalera, int pEspacios) {
    super(pNumFinca, pModalidad, pAreaTerreno, pValorMetroCuadrado, pValorFiscal, pProvincia,
      pCanton, pDistrito, pDirExacta, pTipo, pEstado, pPrecio);
    estilo = pEstilo;
    areaConstruccion = pAreaConstruccion;
    cantTiendas = pCantTiendas;
    cantSalasCine = pCantSalasCine;
    cadenaCine = pCadenaCine;
    cantWifi = pCantWifi;
    escaleras = pEscalera;
    espacios = pEspacios;
    bdCentroComercial = new DaoCentroComercial();
  }

  /**
   * Registra un nuevo centro comercial en el sistema.
   *
   * @param nuevoCentro Nuevo centro comercial por registrar
   * @throws SQLException En caso de no poder establecer conexion con la base de datos
   */
  public void registrarCentroComercial(CentroComercial nuevoCentro) throws SQLException {
    int numFinca = nuevoCentro.getNumFinca();
    int cantTiendas = nuevoCentro.getCantTiendas();
    int cantSalas = nuevoCentro.getCantSalasCine();
    String nombreCine = nuevoCentro.getCadenaCine();
    int cantWifi = nuevoCentro.getCantWifi();
    int cantEscaleras = nuevoCentro.getEscaleras();
    int cantEspacios = nuevoCentro.getEspacios();
    double areaContruccion = nuevoCentro.getAreaConstruccion();
    String estilo = nuevoCentro.getEstilo();
    bdCentroComercial.manipulationQuery("INSERT INTO CENTRO_COMERCIAL (ID_CENTRO_COMERCIAL,CANT_TIENDAS,"
      + "CANT_SALAS_CINE,NOM_CINE,CANT_WIFI,CANT_ESCALERAS,CANT_ESPACIOS_ESPECIALES,TIPO,AREA_CONSTRUCCION,"
      + "ESTILO) VALUES (" + numFinca + "," + cantTiendas + "," + cantSalas + ", '" + nombreCine
      + "'," + cantWifi + "," + cantEscaleras + "," + cantEspacios + ", 'Centro comercial'," + areaContruccion
      + ", '" + estilo + "')");
  }

  /**
   * Retorna la informacion de un centro comercial en especifico
   *
   * @param idPropiedad Numero de finca del centro comercial
   * @return Centro comercial especifico
   * @throws SQLException En caso de no poder establecer conexion con la base de datos
   */
  public CentroComercial getCentroComercial(String idPropiedad) throws SQLException {
    ArrayList<CentroComercial> nuevoCentro = (ArrayList<CentroComercial>) bdCentroComercial
      .selectQuery("SELECT * FROM PROPIEDAD JOIN CENTRO_COMERCIAL ON ID_PROPIEDAD=ID_CENTRO_COMERCIAL "
        + "WHERE ID_CENTRO_COMERCIAL = " + idPropiedad);
    if (nuevoCentro.size() > 0) {
      CentroComercial centroFinal = nuevoCentro.get(0);
      ArrayList<byte[]> fotografias = centroFinal.getFotografias(idPropiedad);
      centroFinal.setFotografias(fotografias);
      return centroFinal;
    }
    return null;
  }

  /**
   * Actualiza la informacion de un centro comercial previamente registrado
   *
   * @param nuevoCentro Centro comercial con la nueva informacion
   * @throws SQLException En caso de no poder establecer conexion con la base de datos
   */
  public void actualizarCentroComercial(CentroComercial nuevoCentro) throws SQLException {
    int numFinca = nuevoCentro.getNumFinca();
    int cantTiendas = nuevoCentro.getCantTiendas();
    int cantSalas = nuevoCentro.getCantSalasCine();
    String nombreCine = nuevoCentro.getCadenaCine();
    int cantWifi = nuevoCentro.getCantWifi();
    int cantEscaleras = nuevoCentro.getEscaleras();
    int cantEspacios = nuevoCentro.getEspacios();
    double areaContruccion = nuevoCentro.getAreaConstruccion();
    String estilo = nuevoCentro.getEstilo();
    bdCentroComercial.manipulationQuery("UPDATE CENTRO_COMERCIAL SET CANT_TIENDAS = " + cantTiendas
      + ", CANT_SALAS_CINE = " + cantSalas + ", NOM_CINE = '" + nombreCine + "', CANT_WIFI = "
      + cantWifi + ", CANT_ESCALERAS = " + cantEscaleras + ", CANT_ESPACIOS_ESPECIALES = " + cantEspacios
      + ", AREA_CONSTRUCCION = " + areaContruccion + ", ESTILO = '" + estilo + "' WHERE "
      + "ID_CENTRO_COMERCIAL = " + numFinca);
  }

  /**
   * Recupera el estilo del centro comercial.
   *
   * @return Estilo del centro comercial.
   */
  public String getEstilo() {
    return estilo;
  }

  /**
   * Asigna el estilo del centro comercial.
   *
   * @param estilo Estilo del centro comercial.
   */
  public void setEstilo(String estilo) {
    this.estilo = estilo;
  }

  /**
   * Recupera el area de construccion del centro comercial.
   *
   * @return Area de construccion del centro comercial.
   */
  public double getAreaConstruccion() {
    return areaConstruccion;
  }

  /**
   * Asigna el area de construccion del centro comercial.
   *
   * @param areaConstruccion Area de construccion del centro comercial.
   */
  public void setAreaConstruccion(double areaConstruccion) {
    this.areaConstruccion = areaConstruccion;
  }

  /**
   * Recupera la cantidad de tiendas del centro comercial.
   *
   * @return Cantidad de tiendas del centro comercial.
   */
  public int getCantTiendas() {
    return cantTiendas;
  }

  /**
   * Asigna la cantidad de tiendas del centro comercial
   *
   * @param cantTiendas Cantidad de tiendas del centro comercial.
   */
  public void setCantTiendas(int cantTiendas) {
    this.cantTiendas = cantTiendas;
  }

  /**
   * Recupera la cantidad de salas de cine del centro comercial.
   *
   * @return Cantidad de salas de cine del centro comercial.
   */
  public int getCantSalasCine() {
    return cantSalasCine;
  }

  /**
   * Asigna la cantidad de salas de cine del centro comercial
   *
   * @param cantSalasCine Cantidad de salas de cine del centro comercial.
   */
  public void setCantSalasCine(int cantSalasCine) {
    this.cantSalasCine = cantSalasCine;
  }

  /**
   * Recupera el nombre de la cadena de cine del centro comercial
   *
   * @return Nombre de la cadena de cine del centro comercial
   */
  public String getCadenaCine() {
    return cadenaCine;
  }

  /**
   * Asigna el nombre de la cadena de cine del centro comercial.
   *
   * @param cadenaCine Nombre de la cadena de cine del centro comercial.
   */
  public void setCadenaCine(String cadenaCine) {
    this.cadenaCine = cadenaCine;
  }

  /**
   * Recupera la cantidad de wifi del centro comercial.
   *
   * @return Cantidad de wifi del centro comercial.
   */
  public int getCantWifi() {
    return cantWifi;
  }

  /**
   * Asigna la cantidad de wifi del centro comercial
   *
   * @param cantWifi Cantidad de wifi del centro comercial.
   */
  public void setCantWifi(int cantWifi) {
    this.cantWifi = cantWifi;
  }

  /**
   * Recupera la cantidad de escaleras electricas del centro comercial.
   *
   * @return Cantidad de escaleras electricas.
   */
  public int getEscaleras() {
    return escaleras;
  }

  /**
   * Asigna la cantidad de escaleras electricas del centro comercial
   *
   * @param escaleras Cantidad de escaleras electricas del centro comercial.
   */
  public void setEscaleras(int escaleras) {
    this.escaleras = escaleras;
  }

  /**
   * Recupera la cantidad de espacios de parqueo para movilidad reducida del centro comercial.
   *
   * @return Cantidad de espacios de parqueo para movilidad reducida del centro comercial.
   */
  public int getEspacios() {
    return espacios;
  }

  /**
   * Asigna la cantidad de espacios de parqueo para movilidad reducida del centro comercial.
   *
   * @param espacios Cantidad de espacios de parqueo para movilidad reducida del centro comercial.
   */
  public void setEspacios(int espacios) {
    this.espacios = espacios;
  }
}
