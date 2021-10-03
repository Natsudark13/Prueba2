package com.poo.bieninmueble.logicaDeNegocios;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 * 
 * Declaración de la interface IAgente
 */
public interface IAgente {
  public ArrayList<Agente> getAgentes() throws SQLException;
  public void insertarAgente(Agente pAgente) throws SQLException;
  public Agente getAgente(String idPropiedad) throws SQLException;
}
