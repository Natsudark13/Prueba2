package com.poo.bieninmueble.dao;

import com.poo.bieninmueble.logicaDeNegocios.Lote;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * La clase DaoLote ejecuta las operaciones de la base de datos de la tabla Lote.
 */
public class DaoLote extends Dao {

  /**
   * Constructor de la clase Lote
   */
  public DaoLote() {
    super();
  }

  /**
   * Este método ejecuta la consulta SELECT que recupera información del lote desde la base de
   * datos.
   *
   * @param query La consulta que va a ser ejecutada.
   * @return Lista con la información consultada de los lotes
   * @throws SQLException En caso de no poder establecer la conexión.
   */
  @Override
  public ArrayList<Lote> selectQuery(String query) throws SQLException {
    ArrayList<Lote> result = new ArrayList<Lote>();
    stmt = conexion.createStatement();
    rs = stmt.executeQuery(query);
    while (rs.next()) {
      Lote nuevoLote = new Lote();
      nuevoLote.setNumFinca((rs.getInt("ID_PROPIEDAD")));
      nuevoLote.setModalidad(rs.getString("MODALIDAD"));
      nuevoLote.setAreaTerreno(rs.getFloat("AREA_TERRENO"));
      nuevoLote.setValorMetroCuadrado(rs.getFloat("VALOR_METRO"));
      nuevoLote.setValorFiscal(rs.getFloat("VALOR_FISCAL"));
      nuevoLote.setProvincia(rs.getString("PROVINCIA"));
      nuevoLote.setCanton(rs.getString("CANTON"));
      nuevoLote.setDistrito(rs.getString("DISTRITO"));
      nuevoLote.setDirExacta(rs.getString("DIREXACTA"));
      nuevoLote.setEstado(rs.getString("ESTADO"));
      result.add(nuevoLote);
    }
    rs.close();
    stmt.close();
    conexion.commit();
    return result;
  }
}
