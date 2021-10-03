package com.poo.bieninmueble.dao;

import com.poo.bieninmueble.logicaDeNegocios.Edificio;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * La clase DaoCentroComercial ejecuta las operaciones de la base de datos de la tabla Edificio.
 */
public class DaoEdificio extends Dao {

  /**
   * Constructor de la clase DaoEdificio
   */
  public DaoEdificio() {
    super();
  }

  /**
   * Este método ejecuta la consulta SELECT que recupera información del edificio desde la
   * base de datos.
   *
   * @param query La consulta que va a ser ejecutada.
   * @return Lista con la información consultada de los edificios
   * @throws SQLException En caso de no poder establecer la conexión.
   */
  @Override
  public ArrayList<Edificio> selectQuery(String query) throws SQLException {
    ArrayList<Edificio> result = new ArrayList<Edificio>();
    stmt = conexion.createStatement();
    rs = stmt.executeQuery(query);
    while (rs.next()) {
      Edificio nuevoEdificio = new Edificio();
      nuevoEdificio.setNumFinca((rs.getInt("ID_PROPIEDAD")));
      nuevoEdificio.setModalidad(rs.getString("MODALIDAD"));
      nuevoEdificio.setAreaTerreno(rs.getFloat("AREA_TERRENO"));
      nuevoEdificio.setValorMetroCuadrado(rs.getFloat("VALOR_METRO"));
      nuevoEdificio.setValorFiscal(rs.getFloat("VALOR_FISCAL"));
      nuevoEdificio.setProvincia(rs.getString("PROVINCIA"));
      nuevoEdificio.setCanton(rs.getString("CANTON"));
      nuevoEdificio.setDistrito(rs.getString("DISTRITO"));
      nuevoEdificio.setDirExacta(rs.getString("DIREXACTA"));
      nuevoEdificio.setEstado(rs.getString("ESTADO"));
      nuevoEdificio.setAreaConstruccion(rs.getDouble("AREA_CONTRUCCION"));
      nuevoEdificio.setAltura(rs.getDouble("ALTURA"));
      nuevoEdificio.setEstilo(rs.getString("ESTILO"));
      nuevoEdificio.setCantNiveles(rs.getInt("CANT_NIVELES"));
      nuevoEdificio.setPiscina(rs.getString("PISCINA"));
      nuevoEdificio.setRancho(rs.getString("RANCHO"));
      nuevoEdificio.setCantParqueo(rs.getInt("PARQUEO_VIS"));
      result.add(nuevoEdificio);
    }
    rs.close();
    stmt.close();
    conexion.commit();
    return result;
  }
}
