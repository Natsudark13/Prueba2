package com.poo.bieninmueble.dao;

import com.poo.bieninmueble.logicaDeNegocios.CentroComercial;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * La clase DaoCentroComercial ejecuta las operaciones de la base de datos de la tabla
 * Centro_Comercial.
 */
public class DaoCentroComercial extends Dao {

  /**
   * Constructor de la clase DaoCentroComercial
   */
  public DaoCentroComercial() {
    super();
  }

  /**
   * Este método ejecuta la consulta SELECT que recupera información del centro comercial desde la
   * base de datos.
   *
   * @param query La consulta que va a ser ejecutada.
   * @return Lista con la información consultada de los centros comerciales
   * @throws SQLException En caso de no poder establecer la conexión.
   */
  @Override
  public ArrayList<CentroComercial> selectQuery(String query) throws SQLException {
    ArrayList<CentroComercial> result = new ArrayList<CentroComercial>();
    stmt = conexion.createStatement();
    rs = stmt.executeQuery(query);
    while (rs.next()) {
      CentroComercial nuevoCentro = new CentroComercial();
      nuevoCentro.setNumFinca((rs.getInt("ID_PROPIEDAD")));
      nuevoCentro.setModalidad(rs.getString("MODALIDAD"));
      nuevoCentro.setAreaTerreno(rs.getFloat("AREA_TERRENO"));
      nuevoCentro.setValorMetroCuadrado(rs.getFloat("VALOR_METRO"));
      nuevoCentro.setValorFiscal(rs.getFloat("VALOR_FISCAL"));
      nuevoCentro.setProvincia(rs.getString("PROVINCIA"));
      nuevoCentro.setCanton(rs.getString("CANTON"));
      nuevoCentro.setDistrito(rs.getString("DISTRITO"));
      nuevoCentro.setDirExacta(rs.getString("DIREXACTA"));
      nuevoCentro.setEstado(rs.getString("ESTADO"));
      nuevoCentro.setAreaConstruccion(rs.getDouble("AREA_CONSTRUCCION"));
      nuevoCentro.setEstilo(rs.getString("ESTILO"));
      nuevoCentro.setCantTiendas(rs.getInt("CANT_TIENDAS"));
      nuevoCentro.setCantSalasCine(rs.getInt("CANT_SALAS_CINE"));
      nuevoCentro.setCadenaCine(rs.getString("NOM_CINE"));
      nuevoCentro.setCantWifi(rs.getInt("CANT_WIFI"));
      nuevoCentro.setEscaleras(rs.getInt("CANT_ESCALERAS"));
      nuevoCentro.setEspacios(rs.getInt("CANT_ESPACIOS_eSPECIALES"));
      result.add(nuevoCentro);
    }
    rs.close();
    stmt.close();
    conexion.commit();
    return result;
  }
}
