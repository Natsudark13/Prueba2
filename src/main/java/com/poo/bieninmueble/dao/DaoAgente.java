package com.poo.bieninmueble.dao;

import com.poo.bieninmueble.logicaDeNegocios.Agente;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * La clase DaoAgente ejecuta las operaciones de la base de datos de la tabla Usuarios,
 * específicamente para agentes.
 */
public class DaoAgente extends Dao {

  /**
   * Constructor de la clase DaoAgente.
   */
  public DaoAgente() {
    super();
  }

  /**
   * Este método ejecuta la consulta SELECT que recupera información del agente desde la base de
   * datos.
   *
   * @param query La consulta que va a ser ejecutada.
   * @return Lista con la información consultada de los agentes
   * @throws SQLException En caso de no poder establecer la conexión.
   */
  @Override
  public ArrayList<Agente> selectQuery(String query) throws SQLException {
    ArrayList<Agente> resultado = new ArrayList<Agente>();
    stmt = conexion.createStatement();
    rs = stmt.executeQuery(query);
    while (rs.next()) {
      Agente nuevoAgente = new Agente();
      nuevoAgente.setIdentificacion(rs.getString("ID_USUARIO"));
      nuevoAgente.setNombre(rs.getString("NOMBRE"));
      nuevoAgente.setApellido(rs.getString("APELLIDO"));
      nuevoAgente.setContrasenna(rs.getString("CONTRASENNA"));
      nuevoAgente.setNumeroTelefono(rs.getString("NUMTELEFONO"));
      nuevoAgente.setCorreo(rs.getString("CORREO"));
      try {
        nuevoAgente.setCantPropiedades(Integer.parseInt(rs.getString(7)));
      } catch (Exception e) {
        System.out.println("No es posible calcular cantidad de propiedades de los agentes");
      };
      resultado.add(nuevoAgente);
    }
    rs.close();
    stmt.close();
    conexion.commit();
    return resultado;
  }
}
