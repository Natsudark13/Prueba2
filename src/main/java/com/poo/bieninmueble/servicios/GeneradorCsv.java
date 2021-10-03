package com.poo.bieninmueble.servicios;

import com.poo.bieninmueble.logicaDeNegocios.Cliente;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * Clase que permite la creacion de archivos en formato CSV
 */
public class GeneradorCsv {

  /**
   * Constructor de la clase GeneradosCsv
   */
  public GeneradorCsv() {}

  /**
   * Escribe y almacena un archivo CSV con los datos de los clientes registrados
   * 
   * @param ruta Ruta donde se almacena el archivo en el sistema
   * @param pDatos Lista de clientes registrados en el sistema
   */
  public void descargarUsuarios(String ruta, ArrayList<Cliente> pDatos) {
    String commaDelimiter = ",";
    String newLineSeparator = "\n";
    try {
      String separador = System.getProperty("file.separator");
      String rutaFinal = ruta + separador + "usuarios.csv";
      FileWriter fileWriter = new FileWriter(rutaFinal, false);
      fileWriter.append("identificacion,nombre,apellido,correo electronico,numero telefonico \n");
      for (int i = 0; i < pDatos.size(); i++) {
        fileWriter.append(pDatos.get(i).getIdentificacion());
        fileWriter.append(commaDelimiter);
        fileWriter.append(pDatos.get(i).getNombre());
        fileWriter.append(commaDelimiter);
        fileWriter.append(pDatos.get(i).getApellido());
        fileWriter.append(commaDelimiter);
        fileWriter.append(pDatos.get(i).getCorreo());
        fileWriter.append(commaDelimiter);
        fileWriter.append(pDatos.get(i).getNumeroTelefono());
        fileWriter.append(newLineSeparator);
      }
      fileWriter.flush();
      fileWriter.close();
    } catch (IOException e) {
      System.out.println("Error en la escritura del archivo csv");
    }
  }
}
