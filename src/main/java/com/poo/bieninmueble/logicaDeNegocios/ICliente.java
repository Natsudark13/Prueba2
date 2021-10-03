package com.poo.bieninmueble.logicaDeNegocios;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 * 
 * Declaración de la interface ICliente
 */
public interface ICliente {
  public ArrayList<Cliente> getClientes() throws SQLException;
  public ArrayList<Cliente> getProspectos(String pIdPropiedad) throws SQLException;
  public void setProspecto(String pCliente, int pPropiedad, String pFecha) throws SQLException;
  public void insertarCliente(Cliente pCliente) throws SQLException;
  public Cliente getCliente(String idUsuario) throws SQLException;
}
