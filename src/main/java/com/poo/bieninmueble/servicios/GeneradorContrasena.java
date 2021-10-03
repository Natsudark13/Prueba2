package com.poo.bieninmueble.servicios;

import java.util.Random;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * Clase que permite la creacion aleatoria de las contrasennas de los usuarios, tanto para Agente
 * como para Cliente
 */
public class GeneradorContrasena {

  /**
   * Constructor de la clase GeneradorContrasena
   */
  public GeneradorContrasena() {
  }

  /**
   * Crea de manera aleatoria una contrasenna alfanumerica para los usuarios del sistema
   *
   * @param pCantidadCaracteres Cantidad de caracteres para la constrasenna
   * @return Una constrasenna aleatoria
   */
  public static String generar(int pCantidadCaracteres) {

    String minusculas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String mayusculas = "abcdefghijklmnopqrstuvwxyz";
    String numeros = "1234567890";
    String caracteres = mayusculas + minusculas + numeros;
    Random random = new Random();
    char[] contrasena = new char[pCantidadCaracteres];

    contrasena[0] = minusculas.charAt(random.nextInt(minusculas.length()));
    contrasena[1] = mayusculas.charAt(random.nextInt(mayusculas.length()));
    contrasena[2] = numeros.charAt(random.nextInt(numeros.length()));

    for (int i = 3; i < pCantidadCaracteres; i++) {
      contrasena[i] = caracteres.charAt(random.nextInt(caracteres.length()));
    }
    return new String(contrasena);
  }
}
