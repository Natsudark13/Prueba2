package com.poo.bieninmueble.dao;

import com.poo.bieninmueble.logicaDeNegocios.Propiedad;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * La clase DaoPropiedad ejecuta las operaciones de la base de datos de la propiedad. 
 */
public class DaoPropiedad extends Dao {

  /**
   * Constructor de la clase DaoPropiedad.
   */
  public DaoPropiedad() {
    super();
  }

  /**
   * Este método ejecuta la consulta SELECT que recupera información de la propiedad desde la base
   * de datos.
   *
   * @param query La consulta que va a ser ejecutada.
   * @return Lista con la información consultada de las propiedades
   * @throws SQLException En caso de no poder establecer la conexión.
   */
  @Override
  public ArrayList<Propiedad> selectQuery(String query) throws SQLException {
    ArrayList<Propiedad> result = new ArrayList<Propiedad>();
    stmt = conexion.createStatement();
    rs = stmt.executeQuery(query);
    while (rs.next()) {
      Propiedad nuevaPropiedad = new Propiedad();
      nuevaPropiedad.setNumFinca(Integer.parseInt(rs.getString(1)));
      nuevaPropiedad.setModalidad(rs.getString(3));
      nuevaPropiedad.setProvincia(rs.getString(7));
      nuevaPropiedad.setTipo(rs.getString(11));
      nuevaPropiedad.setPrecio(Double.parseDouble(rs.getString(13)));
      result.add(nuevaPropiedad);
    }
    rs.close();
    stmt.close();
    conexion.commit();
    return result;
  }

}
