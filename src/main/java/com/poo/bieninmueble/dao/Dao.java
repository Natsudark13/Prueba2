package com.poo.bieninmueble.dao;

import com.poo.bieninmueble.logicaDeNegocios.Propiedad;
import com.poo.bieninmueble.servicios.ConexionSigleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * La clase Dao ejecuta las operaciones a la base de datos. Esta clase implementa la interface IDao.
 */
public abstract class Dao implements IDao {

  //atributos
  protected Statement stmt;
  protected ResultSet rs;
  protected Connection conexion;

  /**
   * Constructor de la clase Dao que establece la conexión a la base de datos.
   */
  public Dao() {
    conexion = ConexionSigleton.getInstance();
  }

  /**
   * Este método ejecuta una operación en la base de datos, puede ser: insertar, borrar o
   * actualizar.
   *
   * @param query La consulta que va a ser ejecutada.
   * @throws SQLException En caso de no poder establecer la conexión.
   */
  @Override
  public void manipulationQuery(String query) throws SQLException {
    stmt = conexion.createStatement();
    stmt.executeUpdate(query);
    stmt.close();
    conexion.commit();
  }

  /**
   * Inserta las fotografias asociadas a una propiedad en la tabla de fotografias
   *
   * @param propiedad Propiedad asociada a las fotografias
   * @throws SQLException En caso de no poder insertar los datos
   */
  @Override
  public void insertarFotografias(Propiedad propiedad) throws SQLException {
    String query = "INSERT INTO FOTOGRAFIA_PROPIEDAD (FOTOGRAFIA, ID_PROPIEDAD) VALUES (?,?)";
    PreparedStatement pst = conexion.prepareStatement(query);
    System.out.println(propiedad.getFotografias().size());
    for (int i = 0; i < propiedad.getFotografias().size(); i++) {
      pst.setBytes(1, propiedad.getFotografias().get(i));
      pst.setInt(2, propiedad.getNumFinca());
      pst.executeUpdate();
    }
    pst.close();
  }

  /**
   * Recupera las fotografias de una propiedad en específico.
   *
   * @param idPropiedad Numero de finca de la propiedad
   * @return Lista con todas las fotografias de la propiedad
   * @throws SQLException En caso de no poder recuperar los datos solicitados
   */
  public ArrayList<byte[]> getFotografias(String idPropiedad) throws SQLException {
    ArrayList<byte[]> result = new ArrayList<byte[]>();
    stmt = conexion.createStatement();
    rs = stmt.executeQuery("SELECT * FROM FOTOGRAFIA_PROPIEDAD WHERE ID_PROPIEDAD = " + idPropiedad);
    while (rs.next()) {
      result.add(rs.getBytes(2));
    }
    rs.close();
    stmt.close();
    conexion.commit();
    return result;
  }
}
