package com.poo.bieninmueble.controladores;

import com.poo.bieninmueble.logicaDeNegocios.Agente;
import com.poo.bieninmueble.logicaDeNegocios.CentroComercial;
import com.poo.bieninmueble.logicaDeNegocios.IPropiedad;
import com.poo.bieninmueble.logicaDeNegocios.Propiedad;
import com.poo.bieninmueble.vistas.VistaCentroComercial;
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
 * La clase ControladorCentroComercial se encarga de reaccionar a eventos de la vista casa e invocar
 * peticiones al modelo correspondiente para completar satisfactoriamente la peticion del evento.
 */
public class ControladorCentroComercial implements ActionListener {
  //atributos

  public VistaCentroComercial vistaCentro;
  private IPropiedad propiedad;
  private Agente agente;

  /**
   * Constructor de la clase ControladorCentroComercial.
   *
   * @param pAgente Agente que está en sesión
   */
  public ControladorCentroComercial(Agente pAgente) {
    agente = pAgente;
    vistaCentro = new VistaCentroComercial();
    propiedad = new Propiedad();
    this.vistaCentro.btnActualizarCentro.addActionListener(this);
  }

  /**
   * Segundo constructor de la clase ControladorCentroComercial.
   */
  ControladorCentroComercial() {
    vistaCentro = new VistaCentroComercial();
    propiedad = new Propiedad();
    this.vistaCentro.btnActualizarCentro.addActionListener(this);
  }

  /**
   * Captura un evento dado en la vista del centro comercial e invoca el método del modelo 
   * correspondiente.
   * 
   * @param e Nombre del evento
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Actualizar":
        this.vistaCentro.txtIdCentro.setEnabled(false);
        actualizarCentroComercial();
        break;
      case "Crear":
        registrarNuevoCentroComercial();
        break;
    }
  }

  /**
   * Registra un nuevo centro comercial.
   */
  public void registrarNuevoCentroComercial() {
    if (this.vistaCentro.txtIdCentro.getText().isBlank() || this.vistaCentro.txtAreaCentro.getText().isBlank()
      || this.vistaCentro.txtMetroCentro.getText().isBlank()) {
      JOptionPane.showMessageDialog(null, "Por favor ingrese el número de finca, el área del terreno y el "
        + "valor del mentro cuadrado");
    } else {
      CentroComercial nuevoCentro = new CentroComercial();
      nuevoCentro.setNumFinca(Integer.parseInt(this.vistaCentro.txtIdCentro.getText()));
      nuevoCentro.setModalidad(this.vistaCentro.comboModalidadCentro.getSelectedItem().toString());
      nuevoCentro.setAreaTerreno(Double.parseDouble(this.vistaCentro.txtAreaCentro.getText()));
      nuevoCentro.setValorMetroCuadrado(Double.parseDouble(this.vistaCentro.txtMetroCentro.getText()));
      nuevoCentro.setValorFiscal(Double.parseDouble(this.vistaCentro.txtFiscalCentro.getText()));
      nuevoCentro.setProvincia(this.vistaCentro.txtProvinciaCentro.getSelectedItem().toString());
      nuevoCentro.setCanton(this.vistaCentro.txtCantonCentro.getText());
      nuevoCentro.setDistrito(this.vistaCentro.txtDistritoCentro.getText());
      nuevoCentro.setDirExacta(this.vistaCentro.txtDireccionCentro.getText());
      nuevoCentro.setEstado(this.vistaCentro.comboEstadoCentro.getSelectedItem().toString());
      nuevoCentro.setFotografias(getFotografias());
      double precio = Double.parseDouble(this.vistaCentro.txtMetroCentro.getText()) * Double.parseDouble(this.vistaCentro.txtAreaCentro.getText());
      nuevoCentro.setPrecio(precio);
      nuevoCentro.setAreaConstruccion(Double.parseDouble(this.vistaCentro.txtConstruccionCentro.getText()));
      nuevoCentro.setEstilo(this.vistaCentro.comboEstiloCentro.getSelectedItem().toString());
      nuevoCentro.setCantTiendas(Integer.parseInt(this.vistaCentro.txtTiendasCentro.getText()));
      nuevoCentro.setCantSalasCine((Integer.parseInt(this.vistaCentro.txtSalasCineCentro.getText())));
      nuevoCentro.setCadenaCine(this.vistaCentro.txtCadenaCentro.getText());
      nuevoCentro.setCantWifi((Integer.parseInt(this.vistaCentro.txtWifiCentro.getText())));
      nuevoCentro.setEscaleras((Integer.parseInt(this.vistaCentro.txtEscalerasCentro.getText())));
      nuevoCentro.setEspacios((Integer.parseInt(this.vistaCentro.txtEspaciosCentro.getText())));
      nuevoCentro.setTipo("Centro comercial");
      try {
        propiedad.registrarPropiedad(nuevoCentro, agente.getIdentificacion());
        nuevoCentro.registrarCentroComercial(nuevoCentro);
        JOptionPane.showMessageDialog(null, "El centro comercial se guardó de forma exitosa");
        this.vistaCentro.setVisible(false);
      } catch (SQLException ex) {
        Logger.getLogger(ControladorAgente.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "Ocurrió un error, el centro comercial no se pudo almacenar");
      }
    }
  }

  /**
   * Muestra en la vista centro coemrcial la información de un centro comercial solicitado
   * 
   * @param centroComercial Centro comercial que se desea visuazliar
   */
  public void verCentroComercial(CentroComercial centroComercial) {
    this.vistaCentro.txtIdCentro.setText(String.valueOf(centroComercial.getNumFinca()));
    this.vistaCentro.txtIdCentro.setEnabled(false);
    this.vistaCentro.txtAreaCentro.setText(String.valueOf(centroComercial.getAreaTerreno()));
    this.vistaCentro.txtMetroCentro.setText(String.valueOf(centroComercial.getValorMetroCuadrado()));
    this.vistaCentro.txtFiscalCentro.setText(String.valueOf(centroComercial.getValorFiscal()));
    this.vistaCentro.comboModalidadCentro.setSelectedItem(centroComercial.getModalidad());
    this.vistaCentro.txtProvinciaCentro.setSelectedItem(centroComercial.getProvincia());
    this.vistaCentro.txtCantonCentro.setText(centroComercial.getCanton());
    this.vistaCentro.txtDistritoCentro.setText(centroComercial.getDistrito());
    this.vistaCentro.txtDireccionCentro.setText(centroComercial.getDirExacta());
    this.vistaCentro.comboEstadoCentro.setSelectedItem(centroComercial.getEstado());
    this.vistaCentro.txtConstruccionCentro.setText(String.valueOf(centroComercial.getAreaConstruccion()));
    this.vistaCentro.comboEstiloCentro.setSelectedItem(centroComercial.getEstilo());
    this.vistaCentro.txtTiendasCentro.setText(String.valueOf(centroComercial.getCantTiendas()));
    this.vistaCentro.txtSalasCineCentro.setText(String.valueOf(centroComercial.getCantSalasCine()));
    this.vistaCentro.txtCadenaCentro.setText(centroComercial.getCadenaCine());
    this.vistaCentro.txtWifiCentro.setText(String.valueOf(centroComercial.getCantWifi()));
    this.vistaCentro.txtEscalerasCentro.setText(String.valueOf(centroComercial.getEscaleras()));
    this.vistaCentro.txtEspaciosCentro.setText(String.valueOf(centroComercial.getEspacios()));
    DefaultTableModel modelo = (DefaultTableModel) this.vistaCentro.tablaImagenesCentro.getModel();
    for (int i = 0; i < centroComercial.getFotografias().size(); i++) {
      modelo.addRow(new Object[]{centroComercial.getFotografias().get(i)});
    }
  }

  /**
   * Actualiza la información de una casa previamente registrado.
   */
  public void actualizarCentroComercial() {
    CentroComercial nuevoCentro = new CentroComercial();
    nuevoCentro.setNumFinca(Integer.parseInt(this.vistaCentro.txtIdCentro.getText()));
    nuevoCentro.setModalidad(this.vistaCentro.comboModalidadCentro.getSelectedItem().toString());
    nuevoCentro.setAreaTerreno(Double.parseDouble(this.vistaCentro.txtAreaCentro.getText()));
    nuevoCentro.setValorMetroCuadrado(Double.parseDouble(this.vistaCentro.txtMetroCentro.getText()));
    nuevoCentro.setValorFiscal(Double.parseDouble(this.vistaCentro.txtFiscalCentro.getText()));
    nuevoCentro.setProvincia(this.vistaCentro.txtProvinciaCentro.getSelectedItem().toString());
    nuevoCentro.setCanton(this.vistaCentro.txtCantonCentro.getText());
    nuevoCentro.setDistrito(this.vistaCentro.txtDistritoCentro.getText());
    nuevoCentro.setDirExacta(this.vistaCentro.txtDireccionCentro.getText());
    nuevoCentro.setEstado(this.vistaCentro.comboEstadoCentro.getSelectedItem().toString());
    nuevoCentro.setFotografias(getFotografias());
    double precio = Double.parseDouble(this.vistaCentro.txtMetroCentro.getText()) * Double.parseDouble(this.vistaCentro.txtAreaCentro.getText());
    nuevoCentro.setPrecio(precio);
    nuevoCentro.setAreaConstruccion(Double.parseDouble(this.vistaCentro.txtConstruccionCentro.getText()));
    nuevoCentro.setEstilo(this.vistaCentro.comboEstiloCentro.getSelectedItem().toString());
    nuevoCentro.setCantTiendas(Integer.parseInt(this.vistaCentro.txtTiendasCentro.getText()));
    nuevoCentro.setCantSalasCine((Integer.parseInt(this.vistaCentro.txtSalasCineCentro.getText())));
    nuevoCentro.setCadenaCine(this.vistaCentro.txtCadenaCentro.getText());
    nuevoCentro.setCantWifi((Integer.parseInt(this.vistaCentro.txtWifiCentro.getText())));
    nuevoCentro.setEscaleras((Integer.parseInt(this.vistaCentro.txtEscalerasCentro.getText())));
    nuevoCentro.setEspacios((Integer.parseInt(this.vistaCentro.txtEspaciosCentro.getText())));
    nuevoCentro.setTipo("Centro comercial");
    propiedad.actualizarPropiedad(nuevoCentro);
    try {
      nuevoCentro.actualizarCentroComercial(nuevoCentro);
    } catch (SQLException ex) {
      Logger.getLogger(ControladorCasa.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
/**
 * Recupera las imagenes asociadas al centro coercial.
 * 
 * @return Lista con todas las imagenes asociadas al centro comercial.
 */
  public ArrayList<byte[]> getFotografias() {
    ArrayList<byte[]> resultado = new ArrayList<byte[]>();
    if ((this.vistaCentro.tablaImagenesCentro.getRowCount() - 1) == -1) {
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
      for (int i = 0; i <= (this.vistaCentro.tablaImagenesCentro.getRowCount() - 1); i++) {
        resultado.add((byte[]) this.vistaCentro.tablaImagenesCentro.getValueAt(i, 0));
      }
    }
    return resultado;
  }
}
