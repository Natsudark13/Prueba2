package com.poo.bieninmueble.controladores;

import com.poo.bieninmueble.logicaDeNegocios.Agente;
import com.poo.bieninmueble.logicaDeNegocios.Casa;
import com.poo.bieninmueble.logicaDeNegocios.IPropiedad;
import com.poo.bieninmueble.logicaDeNegocios.Propiedad;
import com.poo.bieninmueble.vistas.VistaCasa;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * La clase ControladorCasa se encarga de reaccionar a eventos de la vista casa e invocar peticiones
 * al modelo correspondiente para completar satisfactoriamente la peticion del evento.
 */
public class ControladorCasa implements ActionListener {

  //atributos
  public VistaCasa vistaCasa;
  private IPropiedad propiedad;
  private Agente agente;

  /**
   * Constructor de la clase ControladorCasa
   *
   * @param pAgente Agente que está en sesión
   */
  public ControladorCasa(Agente pAgente) {
    agente = pAgente;
    vistaCasa = new VistaCasa();
    propiedad = new Propiedad();
    this.vistaCasa.btnActualizarCasa.addActionListener(this);
  }
  
  /**
   * Segundo constructor de la clase ControladorCasa.
   */
  ControladorCasa() {
    vistaCasa = new VistaCasa();
    propiedad = new Propiedad();
    this.vistaCasa.btnActualizarCasa.addActionListener(this);
  }

  /**
   * Captura un evento dado en la vista del casa e invoca el método del modelo correspondiente.
   * 
   * @param e Nombre del evento
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Actualizar":
        this.vistaCasa.txtIdCasa.setEnabled(false);
        actualizarCasa();
        break;
      case "Crear":
        registrarNuevaCasa();
        break;
    }
  }

  /**
   * Registra una nueva casa.
   */
  public void registrarNuevaCasa() {
    if (this.vistaCasa.txtIdCasa.getText().isBlank() || this.vistaCasa.txtAreaCasa.getText().isBlank()
      || this.vistaCasa.txtMetroCasa.getText().isBlank()) {
      JOptionPane.showMessageDialog(null, "Por favor ingrese el número de finca, el área del terreno y el "
        + "valor del mentro cuadrado");
    } else {
      Casa nuevaCasa = new Casa();
      nuevaCasa.setNumFinca(Integer.parseInt(this.vistaCasa.txtIdCasa.getText()));
      nuevaCasa.setModalidad(this.vistaCasa.comboModalidadCasa.getSelectedItem().toString());
      nuevaCasa.setAreaTerreno(Double.parseDouble(this.vistaCasa.txtAreaCasa.getText()));
      nuevaCasa.setValorMetroCuadrado(Double.parseDouble(this.vistaCasa.txtMetroCasa.getText()));
      nuevaCasa.setValorFiscal(Double.parseDouble(this.vistaCasa.txtFiscalCasa.getText()));
      nuevaCasa.setProvincia(this.vistaCasa.txtProvinciaCasa.getSelectedItem().toString());
      nuevaCasa.setCanton(this.vistaCasa.txtCantonCasa.getText());
      nuevaCasa.setDistrito(this.vistaCasa.txtDistritoCasa.getText());
      nuevaCasa.setDirExacta(this.vistaCasa.txtDireccionCasa.getText());
      nuevaCasa.setTipo("Casa");
      nuevaCasa.setEstado(this.vistaCasa.comboEstadoCasa.getSelectedItem().toString());
      nuevaCasa.setFotografias((getFotografias()));
      double precio = Double.parseDouble(this.vistaCasa.txtMetroCasa.getText()) * 
        Double.parseDouble(this.vistaCasa.txtAreaCasa.getText());
      nuevaCasa.setPrecio(precio);
      nuevaCasa.setAnnoConstruccion(this.vistaCasa.txtAnnoCasa.getText());
      nuevaCasa.setAreaConstruccion(Double.parseDouble(this.vistaCasa.txtConstruccionCasa.getText()));
      nuevaCasa.setColor(this.vistaCasa.txtColorCasa.getText());
      nuevaCasa.setEstilo(this.vistaCasa.comboEstiloCasa.getSelectedItem().toString());
      nuevaCasa.setNiveles(Integer.parseInt(this.vistaCasa.txtNivelesCasa.getText()));
      try {
        propiedad.registrarPropiedad(nuevaCasa, agente.getIdentificacion());
        nuevaCasa.registrarCasa(nuevaCasa);
        JOptionPane.showMessageDialog(null, "La casa se guardó de forma exitosa");
        this.vistaCasa.setVisible(false);
      } catch (SQLException ex) {
        Logger.getLogger(ControladorAgente.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "Ocurrió un error, la casa no se pudo almacenar");
      }
    }
  }

  /**
   * Muestra en la vista casa la información de una casa solicitada
   * 
   * @param casa Casa que se desea visualizar
   */
  public void verCasa(Casa casa) {
    this.vistaCasa.txtIdCasa.setText(String.valueOf(casa.getNumFinca()));
    this.vistaCasa.txtIdCasa.setEnabled(false);
    this.vistaCasa.txtAreaCasa.setText(String.valueOf(casa.getAreaTerreno()));
    this.vistaCasa.txtMetroCasa.setText(String.valueOf(casa.getValorMetroCuadrado()));
    this.vistaCasa.txtFiscalCasa.setText(String.valueOf(casa.getValorFiscal()));
    this.vistaCasa.comboModalidadCasa.setSelectedItem(casa.getModalidad());
    this.vistaCasa.txtProvinciaCasa.setSelectedItem(casa.getProvincia());
    this.vistaCasa.txtCantonCasa.setText(casa.getCanton());
    this.vistaCasa.txtDistritoCasa.setText(casa.getDistrito());
    this.vistaCasa.txtDireccionCasa.setText(casa.getDirExacta());
    this.vistaCasa.comboEstadoCasa.setSelectedItem(casa.getEstado());
    this.vistaCasa.txtConstruccionCasa.setText(String.valueOf(casa.getAreaConstruccion()));
    this.vistaCasa.comboEstiloCasa.setSelectedItem(casa.getEstilo());
    this.vistaCasa.txtNivelesCasa.setText(String.valueOf(casa.getNiveles()));
    this.vistaCasa.txtColorCasa.setText(casa.getColor());
    this.vistaCasa.txtAnnoCasa.setText(casa.getAnnoConstruccion());
    DefaultTableModel modelo = (DefaultTableModel) this.vistaCasa.tablaImagenesCasa.getModel();
    for (int i = 0; i < casa.getFotografias().size(); i++) {
      modelo.addRow(new Object[]{casa.getFotografias().get(i)});
    }
  }

  /**
   * Actualiza la información de una casa previamente registrada.
   */
  public void actualizarCasa() {
    Casa nuevaCasa = new Casa();
    nuevaCasa.setNumFinca(Integer.parseInt(this.vistaCasa.txtIdCasa.getText()));
    nuevaCasa.setModalidad(this.vistaCasa.comboModalidadCasa.getSelectedItem().toString());
    nuevaCasa.setAreaTerreno(Double.parseDouble(this.vistaCasa.txtAreaCasa.getText()));
    nuevaCasa.setValorMetroCuadrado(Double.parseDouble(this.vistaCasa.txtMetroCasa.getText()));
    nuevaCasa.setValorFiscal(Double.parseDouble(this.vistaCasa.txtFiscalCasa.getText()));
    nuevaCasa.setProvincia(this.vistaCasa.txtProvinciaCasa.getSelectedItem().toString());
    nuevaCasa.setCanton(this.vistaCasa.txtCantonCasa.getText());
    nuevaCasa.setDistrito(this.vistaCasa.txtDistritoCasa.getText());
    nuevaCasa.setDirExacta(this.vistaCasa.txtDireccionCasa.getText());
    nuevaCasa.setTipo("Casa");
    nuevaCasa.setEstado(this.vistaCasa.comboEstadoCasa.getSelectedItem().toString());
    nuevaCasa.setFotografias((getFotografias()));
    double precio = Double.parseDouble(this.vistaCasa.txtMetroCasa.getText()) * 
      Double.parseDouble(this.vistaCasa.txtAreaCasa.getText());
    nuevaCasa.setPrecio(precio);
    nuevaCasa.setAnnoConstruccion(this.vistaCasa.txtAnnoCasa.getText());
    nuevaCasa.setAreaConstruccion(Double.parseDouble(this.vistaCasa.txtConstruccionCasa.getText()));
    nuevaCasa.setColor(this.vistaCasa.txtColorCasa.getText());
    nuevaCasa.setEstilo(this.vistaCasa.comboEstiloCasa.getSelectedItem().toString());
    nuevaCasa.setNiveles(Integer.parseInt(this.vistaCasa.txtNivelesCasa.getText()));
    propiedad.actualizarPropiedad(nuevaCasa);
    try {
      nuevaCasa.actualizarCasa(nuevaCasa);
    } catch (SQLException ex) {
      Logger.getLogger(ControladorCasa.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Recupera las imagenes asociadas a la casa.
   * 
   * @return Una lista con todas las imagenes asociadas a la casa.
   */
  public ArrayList<byte[]> getFotografias() {
    ArrayList<byte[]> resultado = new ArrayList<byte[]>();
    if ((this.vistaCasa.tablaImagenesCasa.getRowCount() - 1) == -1) {
      byte[] imagenLote;
      File imagen = new File("./propiedadGenerica.jpg");
      FileInputStream fis;
      try {
        fis = new FileInputStream(imagen);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int j; (j = fis.read(buffer)) != -1;) {
          bos.write(buffer, 0, j);
        }
        imagenLote = bos.toByteArray();
        resultado.add(imagenLote);
      } catch (FileNotFoundException ex) {
        Logger.getLogger(ControladorLote.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException ex) {
        Logger.getLogger(ControladorLote.class.getName()).log(Level.SEVERE, null, ex);
      }
    } else {
      for (int i = 0; i <= (this.vistaCasa.tablaImagenesCasa.getRowCount() - 1); i++) {
        resultado.add((byte[]) this.vistaCasa.tablaImagenesCasa.getValueAt(i, 0));
      }
    }
    return resultado;
  }
}
