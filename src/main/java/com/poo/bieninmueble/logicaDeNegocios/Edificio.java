package com.poo.bieninmueble.logicaDeNegocios;

import com.poo.bieninmueble.dao.DaoEdificio;
import com.poo.bieninmueble.dao.DaoNivel;
import com.poo.bieninmueble.dao.IDao;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * Clase que implementa todos los métodos referentes a Edificio
 */
public class Edificio extends Propiedad {

  //atributos
  private double areaConstruccion;
  private String estilo;
  private double altura;
  private String piscina;
  private String rancho;
  private int cantParqueo;
  private ArrayList<Nivel> niveles;
  private IDao bdEdificio, bdNivel;
  private int cantNiveles;

  /**
   * Constructor de la clase Edificio
   */
  public Edificio() {
    bdEdificio = new DaoEdificio();
    bdNivel = new DaoNivel();
  }
  
  /**
   * Segundo constructor de la clase Edificio
   *
   * @param pNumFinca Numero de finca del nuevo edificio
   * @param pModalidad Modalidad del nuevo edificio
   * @param pAreaTerreno Area del terreno del nuevo edificio
   * @param pValorMetroCuadrado Valor del metro cuadrado del nuevo edificio
   * @param pValorFiscal Valor fiscal del nuevo edificio
   * @param pProvincia Provincia del nuevo edificio
   * @param pCanton Canton del nuevo edificio
   * @param pDistrito Distrito del nuevo edificio
   * @param pDirExacta Direccion exacta del nuevo edificio
   * @param pTipo Tipo del nuevo edificio
   * @param pEstado Estado del nuevo edificio
   * @param pPrecio Precio del nuevo edificio
   * @param pEstilo Estilo del nuevo edificio
   * @param pAreaConstruccion Area de construccion del nuevo edificio
   * @param pAltura Altura del nuevo edificio
   * @param pPiscina Verificacion de la existencia de la piscina
   * @param pRancho Verificacion de la existencia del rancho
   * @param pCantParqueo Cantidad de parqueo del nuevo edificio
   * @param pCantNiveles Cantidad de niveles del nuevo edificio.
   */
  public Edificio(int pNumFinca, String pModalidad, double pAreaTerreno, double pValorMetroCuadrado,
    double pValorFiscal, String pProvincia, String pCanton, String pDistrito, String pDirExacta,
    String pTipo, String pEstado, double pPrecio, String pEstilo,  double pAreaConstruccion,
    double pAltura, String pPiscina, String pRancho, int pCantParqueo, int pCantNiveles) {
    super(pNumFinca, pModalidad, pAreaTerreno, pValorMetroCuadrado, pValorFiscal, pProvincia,
      pCanton, pDistrito, pDirExacta, pTipo, pEstado, pPrecio);
    areaConstruccion = pAreaConstruccion;
    estilo = pEstilo;
    altura = pAltura;
    piscina = pPiscina;
    rancho = pRancho;
    cantParqueo = pCantParqueo;
    cantNiveles = pCantNiveles;
    bdEdificio = new DaoEdificio();
    bdNivel = new DaoNivel();
    niveles = new ArrayList<Nivel>();
  }

  /**
   * Registra un nuevo edificio en el sistema
   * 
   * @param nuevoEdificio Nuevo edificio por registrar
   * @throws SQLException En caso de no poder establecer conexion con la base de datos
   */
  public void registrarNuevoEdificio(Edificio nuevoEdificio) throws SQLException {
    int numFinca = nuevoEdificio.getNumFinca();
    double areaConstruccion = nuevoEdificio.getAreaConstruccion();
    String estilo = nuevoEdificio.getEstilo();
    double altura = nuevoEdificio.getAltura();
    int cantNiveles = nuevoEdificio.getNiveles().size();
    String piscina = nuevoEdificio.getPiscina();
    String rancho = nuevoEdificio.getRancho();
    int parqueo = nuevoEdificio.getCantParqueo();
    bdEdificio.manipulationQuery("INSERT INTO EDIFICIO (ID_EDIFICIO, AREA_CONTRUCCION, ESTILO, ALTURA, "
      + "CANT_NIVELES, PISCINA, RANCHO, PARQUEO_VIS) VALUES (" + numFinca + "," + areaConstruccion + 
      ", '" + estilo + "'," + altura + "," + cantNiveles + ",'" + piscina + "', '" + rancho + "'," + 
      parqueo + ")");
    ArrayList<Nivel> nivelesNuevos = nuevoEdificio.getNiveles();
    for (int i = 0; i < nivelesNuevos.size(); i++) {
      int residencias = nivelesNuevos.get(i).getCantResidencias();
      int zonas = nivelesNuevos.get(i).getCantZonas();
      String tipo = nivelesNuevos.get(i).getTipo();
      bdEdificio.manipulationQuery("INSERT INTO NIVEL (ID_EDIFICIO, CANT_RESIDENCIAS, TIPO, CANT_ZONA_COMUN) "
        + "VALUES (" + numFinca + "," + residencias + ", '" + tipo + "'" + "," + zonas + ")");
      niveles.add(new Nivel(residencias, zonas, tipo));
    }
  }

  /**
   * Recupera la informacion de un edificio en especifico
   * 
   * @param idPropiedad Numero de finca de la propiedad
   * @return Edificio especifico
   * @throws SQLException En caso de no poder establecer conexion con la base de datos
   */
  public Edificio getEdificio(String idPropiedad) throws SQLException {
    ArrayList<Edificio> nuevoEdificio = (ArrayList<Edificio>) bdEdificio.selectQuery("SELECT * FROM "
      + "PROPIEDAD JOIN EDIFICIO ON ID_PROPIEDAD=ID_EDIFICIO WHERE ID_EDIFICIO = "+ idPropiedad);
    if (nuevoEdificio.size() > 0) {
      Edificio edificioFinal = nuevoEdificio.get(0);
      ArrayList<byte[]> fotografias = edificioFinal.getFotografias(idPropiedad);
      ArrayList<Nivel> niveles = (ArrayList<Nivel>) bdNivel.selectQuery("SELECT * FROM NIVEL WHERE "
        + "ID_EDIFICIO = " + edificioFinal.getNumFinca());
      edificioFinal.setFotografias(fotografias);
      edificioFinal.setNiveles(niveles);
      return edificioFinal;
    }
    return null;
  }

  /**
   * Actualiza los datos de un edificio previamente registrado.
   * 
   * @param nuevoEdificio Edificio con la nueva informacion
   * @throws SQLException En caso de no poder establecer conexion con la base de datos
   */
  public void actualizarEdificio(Edificio nuevoEdificio) throws SQLException {
    int numFinca = nuevoEdificio.getNumFinca();
    double areaConstruccion = nuevoEdificio.getAreaConstruccion();
    String estilo = nuevoEdificio.getEstilo();
    double altura = nuevoEdificio.getAltura();
    int cantNiveles = nuevoEdificio.getNiveles().size();
    String piscina = nuevoEdificio.getPiscina();
    String rancho = nuevoEdificio.getRancho();
    int parqueo = nuevoEdificio.getCantParqueo();

    bdEdificio.manipulationQuery("UPDATE EDIFICIO SET AREA_CONTRUCCION = " + areaConstruccion + 
      ", ESTILO = '" + estilo + "', ALTURA = " + altura + ", CANT_NIVELES = "+ cantNiveles + ", "
        + "PISCINA = '" + piscina + "', RANCHO = '" + rancho + "', PARQUEO_VIS = " + parqueo + 
      " WHERE ID_EDIFICIO = " + numFinca);
    bdEdificio.manipulationQuery("DELETE FROM NIVEL WHERE ID_EDIFICIO = " + numFinca);
    ArrayList<Nivel> niveles = nuevoEdificio.getNiveles();
    for (int i = 0; i < niveles.size(); i++) {
      int residencias = niveles.get(i).getCantResidencias();
      int zonas = niveles.get(i).getCantZonas();
      String tipo = niveles.get(i).getTipo();
      bdEdificio.manipulationQuery("INSERT INTO NIVEL (ID_EDIFICIO, CANT_RESIDENCIAS, TIPO, CANT_ZONA_COMUN) "
        + "VALUES (" + numFinca + "," + residencias+ ", '" + tipo + "'" + "," + zonas + ")");
    }
  }

  /**
   * Retorna el area de construccion del edificio
   * 
   * @return Area de construccion del edificio
   */
  public double getAreaConstruccion() {
    return areaConstruccion;
  }

  /**
   * Asigna el area de construccion del edificio
   * @param areaConstruccion Area de construccion del edificio
   */
  public void setAreaConstruccion(double areaConstruccion) {
    this.areaConstruccion = areaConstruccion;
  }

  /**
   * Retorna el estilo del edificio
   * 
   * @return Estilo del edificio
   */
  public String getEstilo() {
    return estilo;
  }

  /**
   * Asigna el estilo del edificio
   * 
   * @param estilo Estilo del edificio
   */
  public void setEstilo(String estilo) {
    this.estilo = estilo;
  }

  /**
   * Retorna la altura del edificio
   * 
   * @return Altura del edificio
   */
  public double getAltura() {
    return altura;
  }

  /**
   * Asigna la altura del edificio
   * 
   * @param altura Altura del edificio
   */
  public void setAltura(double altura) {
    this.altura = altura;
  }

  /**
   * Retorna si existe pisicina en el edificio o no
   * 
   * @return Si existe pisicina retorna un Si, de lo contrario un No
   */
  public String getPiscina() {
    return piscina;
  }

  /**
   * Asigna si existe pisicina en el edificio o no
   * 
   * @param piscina Si existe pisicina retorna un Si, de lo contrario un No
   */
  public void setPiscina(String piscina) {
    this.piscina = piscina;
  }

  /**
   * Retorna si existe rancho en el edificio o no
   * 
   * @return Si existe rancho retorna un Si, de lo contrario un No
   */
  public String getRancho() {
    return rancho;
  }

  /**
   * Asigna si existe rancho en el edificio o no
   * 
   * @param rancho Si existe rancho retorna un Si, de lo contrario un No
   */
  public void setRancho(String rancho) {
    this.rancho = rancho;
  }

  /**
   * Retorna la cantidad de parqueo
   * 
   * @return Cantidad de parqueo
   */
  public int getCantParqueo() {
    return cantParqueo;
  }

  /**
   * Asigna la cantidad de parqueo
   * 
   * @param cantParqueo Cantidad de parqueo
   */
  public void setCantParqueo(int cantParqueo) {
    this.cantParqueo = cantParqueo;
  }

  /**
   * Retorna una lista con la informacion de los niveles asociados al edificio
   * 
   * @return Lista con la informacion de los niveles asociados al edificio
   */
  public ArrayList<Nivel> getNiveles() {
    return niveles;
  }

  /**
   * Asigna una lista con la informacion de los niveles asociados al edificio
   * 
   * @param niveles Lista con la informacion de los niveles asociados al edificio
   */
  public void setNiveles(ArrayList<Nivel> niveles) {
    this.niveles = niveles;
  }

  /**
   * Retorna la cantidad de niveles
   * 
   * @return Cantidad de niveles
   */
  public int getCantNiveles() {
    return cantNiveles;
  }

  /**
   * Asigna la cantidad de niveles
   * 
   * @param cantNiveles Cantidad de niveles
   */
  public void setCantNiveles(int cantNiveles) {
    this.cantNiveles = cantNiveles;
  }

}
