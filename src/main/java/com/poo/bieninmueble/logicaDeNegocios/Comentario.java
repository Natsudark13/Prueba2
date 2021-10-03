package com.poo.bieninmueble.logicaDeNegocios;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * Clase que implementa todos los métodos referentes a los Comentario
 */
public class Comentario {

  //atributos
  private String comentario;
  private int idPropiedad;

  /**
   * Constructor de la clase Comentario
   */
  public Comentario() {
  }

  /**
   * Segundo constructor de la clase Comentario
   *
   * @param pComentario Texto del comentario por agregar
   * @param pIdPropiedad Numero de finca de la propiedad referente al comentario
   */
  public Comentario(String pComentario, int pIdPropiedad) {
    comentario = pComentario;
    idPropiedad = pIdPropiedad;
  }

  /**
   * Crea una cadena con la información del comentario
   *
   * @return Cadena con la información del comentario
   */
  @Override
  public String toString() {
    String resultado = new String();
    resultado = "Comentario: " + this.comentario + "\n" + "Id: " + this.idPropiedad;
    return resultado;
  }

  /**
   * Retorna el texto del comentario
   *
   * @return Texto del comentario
   */
  public String getComentario() {
    return comentario;
  }

  /**
   * Asigna el texto del comentario.
   *
   * @param comentario Texto del comentario
   */
  public void setComentario(String comentario) {
    this.comentario = comentario;
  }

  /**
   * Retorna el numero de finca de la propiedad
   *
   * @return Numero de finca
   */
  public int getIdPropiedad() {
    return idPropiedad;
  }

  /**
   * Asigna el numero de finca de la propiedad
   *
   * @param idPropiedad Numero de finca
   */
  public void setIdPropiedad(int idPropiedad) {
    this.idPropiedad = idPropiedad;
  }
}
