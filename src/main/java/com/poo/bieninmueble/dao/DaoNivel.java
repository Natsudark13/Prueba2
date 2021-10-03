package com.poo.bieninmueble.dao;

import com.poo.bieninmueble.logicaDeNegocios.Nivel;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * La clase DaoLote ejecuta las operaciones de la base de datos de la tabla Nivel.
 */
public class DaoNivel extends Dao {

  /**
   * Constructor de la clase DaoNivel
   */
  public DaoNivel() {
    super();
  }

  /**
   * Este método ejecuta la consulta SELECT que recupera información del nivel desde la base de
   * datos.
   *
   * @param query La consulta que va a ser ejecutada.
   * @return Lista con la información consultada de los niveles
   * @throws SQLException En caso de no poder establecer la conexión.
   */
  @Override
  public ArrayList<Nivel> selectQuery(String query) throws SQLException {
    ArrayList<Nivel> result = new ArrayList<Nivel>();
    stmt = conexion.createStatement();
    rs = stmt.executeQuery(query);
    while (rs.next()) {
      Nivel nuevoNivel = new Nivel();
      nuevoNivel.setCantResidencias(rs.getInt("CANT_RESIDENCIAS"));
      nuevoNivel.setCantZonas(rs.getInt("CANT_ZONA_COMUN"));
      nuevoNivel.setTipo(rs.getString("TIPO"));
      result.add(nuevoNivel);
    }
    rs.close();
    stmt.close();
    conexion.commit();
    return result;
  }
}
