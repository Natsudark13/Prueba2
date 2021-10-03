package com.poo.bieninmueble.dao;

import com.poo.bieninmueble.logicaDeNegocios.Comentario;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * La clase DaoComentario ejecuta las operaciones de la base de datos de la tabla Comentario
 */
public class DaoComentario extends Dao {

  /**
   * Constructor de la clase DaoComentario.
   */
  public DaoComentario() {
    super();
  }

  /**
   * Este método ejecuta la consulta SELECT que recupera información de los comentarios asociados a 
   * una propiedad desde la base de datos.
   *
   * @param query la consulta que va a ser ejecutada.
   * @return Lista con la información consultada de los comentarios
   * @throws SQLException En caso de no poder establecer la conexión.
   */
  @Override
  public ArrayList<Comentario> selectQuery(String query) throws SQLException {
    ArrayList<Comentario> result = new ArrayList<Comentario>();
    stmt = conexion.createStatement();
    rs = stmt.executeQuery(query);
    while (rs.next()) {
      Comentario nuevoComentario = new Comentario();
      nuevoComentario.setIdPropiedad(Integer.parseInt(rs.getString("ID_PROPIEDAD")));
      nuevoComentario.setComentario(rs.getString("COMENTARIO"));
      result.add(nuevoComentario);
    }
    rs.close();
    stmt.close();
    conexion.commit();
    return result;
  }
}
