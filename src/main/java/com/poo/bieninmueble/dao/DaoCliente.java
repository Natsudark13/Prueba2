package com.poo.bieninmueble.dao;

import com.poo.bieninmueble.logicaDeNegocios.Cliente;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * La clase DaoCliente ejecuta las operaciones de la base de datos de la tabla Usuario,
 * específicamente de los usuarios.
 */
public class DaoCliente extends Dao {

  /**
   * Constructor de la clase DaoAgente.
   */
  public DaoCliente() {
    super();
  }

  /**
   * Este método ejecuta la consulta SELECT que recupera información del cliente desde la base de
   * datos.
   *
   * @param query la consulta que va a ser ejecutada.
   * @return Lista con la información consultada de los clientes
   * @throws SQLException En caso de no poder establecer la conexión.
   */
  @Override
  public ArrayList<Cliente> selectQuery(String query) throws SQLException {
    ArrayList<Cliente> resultado = new ArrayList<Cliente>();
    stmt = conexion.createStatement();
    rs = stmt.executeQuery(query);
    while (rs.next()) {
      Cliente nuevoCliente = new Cliente();

      nuevoCliente.setIdentificacion(rs.getString(1));
      nuevoCliente.setNombre(rs.getString(3));
      nuevoCliente.setApellido(rs.getString(4));
      nuevoCliente.setCorreo(rs.getString(5));
      nuevoCliente.setContrasenna(rs.getString(6));
      nuevoCliente.setNumeroTelefono(rs.getString(2));
      resultado.add(nuevoCliente);
    }
    rs.close();
    stmt.close();
    conexion.commit();
    return resultado;
  }
}
