package com.poo.bieninmueble.dao;

import com.poo.bieninmueble.logicaDeNegocios.Propiedad;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * Declaración de la interface IDao.
 */
public interface IDao {
  public abstract void manipulationQuery(String query) throws SQLException;
  public abstract ArrayList<?> selectQuery(String query) throws SQLException;
  public abstract void insertarFotografias(Propiedad propiedad) throws SQLException;
  public abstract ArrayList<byte[]> getFotografias(String id) throws SQLException;
}
