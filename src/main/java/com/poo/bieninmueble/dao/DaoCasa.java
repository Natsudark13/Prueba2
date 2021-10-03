package com.poo.bieninmueble.dao;

import com.poo.bieninmueble.logicaDeNegocios.Casa;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * La clase DaoCasa ejecuta las operaciones de la base de datos de la tabla Casa.
 */
public class DaoCasa extends Dao {

  /**
   * Constructor de la clase DaoCasa.
   */
  public DaoCasa() {
    super();
  }

  /**
   * Este método ejecuta la consulta SELECT que recupera información de la casa desde la base de
   * datos.
   *
   * @param query La consulta que va a ser ejecutada.
   * @return Lista con la información consultada de las casa
   * @throws SQLException En caso de no poder establecer la conexión.
   */
  @Override
  public ArrayList<Casa> selectQuery(String query) throws SQLException {
    ArrayList<Casa> result = new ArrayList<Casa>();
    stmt = conexion.createStatement();
    rs = stmt.executeQuery(query);
    while (rs.next()) {
      Casa nuevaCasa = new Casa();
      nuevaCasa.setNumFinca((rs.getInt("ID_PROPIEDAD")));
      nuevaCasa.setModalidad(rs.getString("MODALIDAD"));
      nuevaCasa.setAreaTerreno(rs.getFloat("AREA_TERRENO"));
      nuevaCasa.setValorMetroCuadrado(rs.getFloat("VALOR_METRO"));
      nuevaCasa.setValorFiscal(rs.getFloat("VALOR_FISCAL"));
      nuevaCasa.setProvincia(rs.getString("PROVINCIA"));
      nuevaCasa.setCanton(rs.getString("CANTON"));
      nuevaCasa.setDistrito(rs.getString("DISTRITO"));
      nuevaCasa.setDirExacta(rs.getString("DIREXACTA"));
      nuevaCasa.setEstado(rs.getString("ESTADO"));
      nuevaCasa.setAreaConstruccion(rs.getDouble("AREA_CONSTRUCCION"));
      nuevaCasa.setNiveles(rs.getInt("CANT_NIVELES"));
      nuevaCasa.setColor(rs.getString("COLOR"));
      nuevaCasa.setEstilo(rs.getString("ESTILO"));
      nuevaCasa.setAnnoConstruccion(rs.getString("ANNO_CONSTRUCCION"));
      result.add(nuevaCasa);
    }
    rs.close();
    stmt.close();
    conexion.commit();
    return result;
  }
}
