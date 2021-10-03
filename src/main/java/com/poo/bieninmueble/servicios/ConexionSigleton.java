package com.poo.bieninmueble.servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * Clase para el control de la creación de la instacia de conexión a la base de datos.
 */
public class ConexionSigleton {

  private static Connection conexion;

  /**
   * El contrutor por defecto es priavdo porque la conexoón será instanciada dentrode de esta clase.
   * por el diseño de patrón de diseño Singleton
   */
  private ConexionSigleton() {
  }

  /**
   * Este método retrona la instacia de la conexión a la base de datos
   *
   * @return conexion la conexión a la base de datos
   */
  public static Connection getInstance() {
    if (conexion == null) {
      String nombreHost = "projectpoo-server.database.windows.net";
      String nombreDb = "projectpoo-database";
      String usuario = "administrador";
      String contrasenna = "Atipoo2021";
      String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;"
        + "hostNameInCertificate=*.database.windows.net;loginTimeout=30;", nombreHost, nombreDb, usuario, contrasenna);
      try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        conexion = DriverManager.getConnection(url);
      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
      }
    }
    return conexion;
  }
}
