/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poo.bieninmueble.logicaDeNegocios;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 * 
 * Declaración de la interface IPropiedad
 */
public interface IPropiedad {
      public abstract void registrarPropiedad(Propiedad nuevaPropiedad, String pIdAgente);
      public abstract void eliminarPropiedad(String pIdPropiedad);
      public abstract ArrayList<Propiedad> getPropiedadesAgente(String pId) throws SQLException;
      public abstract ArrayList<Propiedad> getPropiedadesFiltroAgente(String pCriterio, String pDato, String pId) throws SQLException;
      public abstract Propiedad getPropiedadId(String pNumFinca) throws SQLException;
      public abstract ArrayList<Propiedad> getPropiedadesCliente() throws SQLException;
      public abstract ArrayList<Propiedad> getPropiedadesFiltroCliente(String pCriterio, String pDato) throws SQLException;
      public abstract ArrayList<Propiedad> getPrecioCliente(double pMenor, double pMayor) throws SQLException;
      public abstract ArrayList<Propiedad> getPrecioAgente(double pMenor, double pMayor, String pId) throws SQLException;
      public abstract ArrayList<byte[]> getFotografias(String id) throws SQLException;
      public abstract void actualizarPropiedad(Propiedad nuevaPropiedad);
      public abstract void agregarComentario(String pNumFinca, String pComentario) throws SQLException;
      public abstract ArrayList<Comentario> getComentarios(String pId) throws SQLException;
}
