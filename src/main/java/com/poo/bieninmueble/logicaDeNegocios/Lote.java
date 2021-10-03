package com.poo.bieninmueble.logicaDeNegocios;

import com.poo.bieninmueble.dao.DaoLote;
import com.poo.bieninmueble.dao.IDao;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * Clase que implementa todos los métodos referentes a Lote
 */
public class Lote extends Propiedad {

  //atributos
  private IDao bdLote;

  /**
   * Constructor de la clase Lote
   */
  public Lote() {
    bdLote = new DaoLote();
  }

  /**
   * Segundo constructor de la clase Lote
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
   */
  public Lote(int pNumFinca, String pModalidad, double pAreaTerreno, double pValorMetroCuadrado,
    double pValorFiscal, String pProvincia, String pCanton, String pDistrito, String pDirExacta,
    String pTipo, String pEstado, double pPrecio) {
    super(pNumFinca, pModalidad, pAreaTerreno, pValorMetroCuadrado, pValorFiscal, pProvincia,
      pCanton, pDistrito, pDirExacta, pTipo, pEstado, pPrecio);
    bdLote = new DaoLote();
  }

  /**
   * Registra un nuevo lote en el sistema
   *
   * @param nuevoLote Nuevo lote por registrar
   * @throws SQLException En caso de no poder establecer conexion con la base de datos
   */
  public void registrarLote(Lote nuevoLote) throws SQLException {
    int numFinca = nuevoLote.getNumFinca();
    bdLote.manipulationQuery("INSERT INTO LOTE (ID_LOTE) VALUES (" + numFinca + ")");
  }

  /**
   * Recupera la informacion de un lote en especifico
   *
   * @param idPropiedad Numero de finca de la propiedad
   * @return Informacion del lote en especifico
   * @throws SQLException En caso de no poder establecer conexion con la base de datos
   */
  public Lote getLote(String idPropiedad) throws SQLException {
    ArrayList<Lote> nuevoLote = (ArrayList<Lote>) bdLote.selectQuery("SELECT * FROM PROPIEDAD JOIN "
      + "LOTE ON ID_PROPIEDAD=ID_LOTE WHERE ID_LOTE = " + idPropiedad);
    if (nuevoLote.size() > 0) {
      Lote loteFinal = nuevoLote.get(0);
      ArrayList<byte[]> fotografias = loteFinal.getFotografias(idPropiedad);
      loteFinal.setFotografias(fotografias);
      return loteFinal;
    }
    return null;
  }
}
