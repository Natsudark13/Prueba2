package com.poo.bieninmueble.controladores;

import com.poo.bieninmueble.logicaDeNegocios.Agente;
import com.poo.bieninmueble.logicaDeNegocios.IPropiedad;
import com.poo.bieninmueble.logicaDeNegocios.Lote;
import com.poo.bieninmueble.logicaDeNegocios.Propiedad;
import com.poo.bieninmueble.vistas.VistaLote;
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
 * La clase ControladorLote se encarga de reaccionar a eventos de la vista lote e invocar peticiones
 * al modelo correspondiente para completar satisfactoriamente la peticion del evento.
 */
public class ControladorLote implements ActionListener {

  //atributos
  public VistaLote vistaLote;
  private IPropiedad propiedad;
  private Agente agente;

  /**
   * Constructor de la clase ControladorLote.
   *
   * @param pAgente Agente que está en la sesión
   */
  public ControladorLote(Agente pAgente) {
    agente = pAgente;
    vistaLote = new VistaLote();
    propiedad = new Propiedad();
    this.vistaLote.btnActualizarLote.addActionListener(this);
  }

  /**
   * Segundo constructor de la clase ControladorLote.
   */
  public ControladorLote() {
    vistaLote = new VistaLote();
    propiedad = new Propiedad();
    this.vistaLote.btnActualizarLote.addActionListener(this);
  }

  /**
   * Captura un evento dado en la vista del lote e invoca el método del modelo correspondiente.
   *
   * @param e Nombre del evento
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Actualizar":
        this.vistaLote.txtIdLote.setEnabled(false);
        actualizarLote();
        break;
      case "Crear":
        registrarNuevoLote();
        break;
    }
  }

  /**
   * Registra un nuevo lote.
   */
  public void registrarNuevoLote() {
    if (this.vistaLote.txtIdLote.getText().isBlank() || this.vistaLote.txtAreaLote.getText().isBlank()
      || this.vistaLote.txtMetroLote.getText().isBlank()) {
      JOptionPane.showMessageDialog(null, "Por favor ingrese el número de finca, el área del terreno y el "
        + "valor del mentro cuadrado");
    } else {
      Lote nuevoLote = new Lote();
      nuevoLote.setNumFinca(Integer.parseInt(this.vistaLote.txtIdLote.getText()));
      nuevoLote.setModalidad(this.vistaLote.comboModalidadLote.getSelectedItem().toString());
      nuevoLote.setAreaTerreno(Double.parseDouble(this.vistaLote.txtAreaLote.getText()));
      nuevoLote.setValorMetroCuadrado(Double.parseDouble(this.vistaLote.txtMetroLote.getText()));
      nuevoLote.setValorFiscal(Double.parseDouble(this.vistaLote.txtFiscalLote.getText()));
      nuevoLote.setProvincia(this.vistaLote.txtProvinciaLote.getSelectedItem().toString());
      nuevoLote.setCanton(this.vistaLote.txtCantonLote.getText());
      nuevoLote.setDistrito(this.vistaLote.txtDistritoLote.getText());
      nuevoLote.setDirExacta(this.vistaLote.txtDireccionLote.getText());
      nuevoLote.setTipo("Lote");
      nuevoLote.setEstado(this.vistaLote.comboEstadoLote.getSelectedItem().toString());
      nuevoLote.setFotografias((getFotografias()));
      double precio = Double.parseDouble(this.vistaLote.txtMetroLote.getText()) * Double.parseDouble(this.vistaLote.txtAreaLote.getText());
      nuevoLote.setPrecio(precio);
      try {
        propiedad.registrarPropiedad(nuevoLote, agente.getIdentificacion());
        nuevoLote.registrarLote(nuevoLote);
        JOptionPane.showMessageDialog(null, "El lote se guardó de forma exitosa");
        this.vistaLote.setVisible(false);
      } catch (SQLException ex) {
        Logger.getLogger(ControladorAgente.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "Ocurrió un error, el lote no se pudo almacenar");
      }
    }
  }

  /**
   * Muestra en la vista lote la información de un edificio solicitado.
   *
   * @param lote Lote que se desea visuazliar
   */
  public void verLote(Lote lote) {
    this.vistaLote.txtIdLote.setText(String.valueOf(lote.getNumFinca()));
    this.vistaLote.txtIdLote.setEnabled(false);
    this.vistaLote.txtAreaLote.setText(String.valueOf(lote.getAreaTerreno()));
    this.vistaLote.txtMetroLote.setText(String.valueOf(lote.getValorMetroCuadrado()));
    this.vistaLote.txtFiscalLote.setText(String.valueOf(lote.getValorFiscal()));
    this.vistaLote.comboModalidadLote.setSelectedItem(lote.getModalidad());
    this.vistaLote.txtProvinciaLote.setSelectedItem(lote.getProvincia());
    this.vistaLote.txtCantonLote.setText(lote.getCanton());
    this.vistaLote.txtDistritoLote.setText(lote.getDistrito());
    this.vistaLote.txtDireccionLote.setText(lote.getDirExacta());
    this.vistaLote.comboEstadoLote.setSelectedItem(lote.getEstado());
    DefaultTableModel modelo = (DefaultTableModel) this.vistaLote.tablaImagenesLote.getModel();

    for (int i = 0; i < lote.getFotografias().size(); i++) {
      modelo.addRow(new Object[]{lote.getFotografias().get(i)});
    }
  }

  /**
   * Actualiza la información de un edificio previamente registrado.
   */
  public void actualizarLote() {
    Propiedad nuevoLote = new Lote();
    nuevoLote.setNumFinca(Integer.parseInt(this.vistaLote.txtIdLote.getText()));
    nuevoLote.setModalidad(this.vistaLote.comboModalidadLote.getSelectedItem().toString());
    nuevoLote.setAreaTerreno(Double.parseDouble(this.vistaLote.txtAreaLote.getText()));
    nuevoLote.setValorMetroCuadrado(Double.parseDouble(this.vistaLote.txtMetroLote.getText()));
    nuevoLote.setValorFiscal(Double.parseDouble(this.vistaLote.txtFiscalLote.getText()));
    nuevoLote.setProvincia(this.vistaLote.txtProvinciaLote.getSelectedItem().toString());
    nuevoLote.setCanton(this.vistaLote.txtCantonLote.getText());
    nuevoLote.setDistrito(this.vistaLote.txtDistritoLote.getText());
    nuevoLote.setDirExacta(this.vistaLote.txtDireccionLote.getText());
    nuevoLote.setTipo("Lote");
    nuevoLote.setEstado(this.vistaLote.comboEstadoLote.getSelectedItem().toString());
    nuevoLote.setFotografias(getFotografias());
    double precio = Double.parseDouble(this.vistaLote.txtMetroLote.getText()) * Double.parseDouble(this.vistaLote.txtAreaLote.getText());
    nuevoLote.setPrecio(precio);
    propiedad.actualizarPropiedad(nuevoLote);
    JOptionPane.showMessageDialog(null, "El lote se guardó de forma exitosa");
  }

  /**
   * Recupera las imagenes asociadas al edificio.
   * 
   * @return Una lista con todas las imagenes asociadas al lote.
   */
  public ArrayList<byte[]> getFotografias() {
    ArrayList<byte[]> resultado = new ArrayList<byte[]>();
    if ((this.vistaLote.tablaImagenesLote.getRowCount() - 1) == -1) {
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
      for (int i = 0; i <= (this.vistaLote.tablaImagenesLote.getRowCount() - 1); i++) {
        resultado.add((byte[]) this.vistaLote.tablaImagenesLote.getValueAt(i, 0));
      }
    }
    return resultado;
  }
}
