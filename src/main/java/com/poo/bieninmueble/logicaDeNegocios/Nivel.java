/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poo.bieninmueble.logicaDeNegocios;

/**
 *
 * @author anjel
 */
public class Nivel {

  private int cantResidencias;
  private int cantZonas;
  private String tipo;

  /**
   * Constructor de Nivel
   */
  public Nivel() {
  }

  /**
   * Segundo constructor de Nivel
   *
   * @param pCantidadResidencias Cantidad de residencias del nivel
   * @param pCantZonas Cantidad de zonas en comun del nivel
   * @param pTipo Tipo de nivel
   */
  public Nivel(int pCantidadResidencias, int pCantZonas, String pTipo) {
    cantResidencias = pCantidadResidencias;
    cantZonas = pCantZonas;
    tipo = pTipo;
  }

  /**
   * Retorna la cantidad de residencias del nivel
   *
   * @return Cantidad de residencias del nivel
   */
  public int getCantResidencias() {
    return cantResidencias;
  }

  /**
   * Asigna la cantidad de residencias del nivel
   *
   * @param cantResidencias Cantidad de residencias del nivel
   */
  public void setCantResidencias(int cantResidencias) {
    this.cantResidencias = cantResidencias;
  }

  /**
   * Retorna la cantidad de zonas en comun del nivel
   *
   * @return Cantidad de zonas en comun del nivel
   */
  public int getCantZonas() {
    return cantZonas;
  }

  /**
   * Asigna la cantidad de zonas en comun del nivel
   *
   * @param cantZonas Cantidad de zonas en comun del nivel
   */
  public void setCantZonas(int cantZonas) {
    this.cantZonas = cantZonas;
  }

  /**
   * Retorna el tipo de nivel
   *
   * @return Tipo de nivel
   */
  public String getTipo() {
    return tipo;
  }

  /**
   * Asigna el tipo de nivel
   *
   * @param tipo Tipo de nivel
   */
  public void setTipo(String tipo) {
    this.tipo = tipo;
  }
}
