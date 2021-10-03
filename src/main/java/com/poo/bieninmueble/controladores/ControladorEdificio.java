package com.poo.bieninmueble.controladores;

import com.poo.bieninmueble.logicaDeNegocios.Agente;
import com.poo.bieninmueble.logicaDeNegocios.Edificio;
import com.poo.bieninmueble.logicaDeNegocios.IPropiedad;
import com.poo.bieninmueble.logicaDeNegocios.Nivel;
import com.poo.bieninmueble.logicaDeNegocios.Propiedad;
import com.poo.bieninmueble.vistas.VistaEdificio;
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
 * La clase ControladorEdificio se encarga de reaccionar a eventos de la vista edificio e invocar
 * peticiones al modelo correspondiente para completar satisfactoriamente la peticion del evento.
 */
public class ControladorEdificio implements ActionListener {

  //atributos
  public VistaEdificio vistaEdificio;
  private IPropiedad propiedad;
  private Agente agente;

  /**
   * Constructor de la clase ControladorEdificio.
   *
   * @param pAgente Agente que está en la sesión.
   */
  public ControladorEdificio(Agente pAgente) {
    agente = pAgente;
    vistaEdificio = new VistaEdificio();
    propiedad = new Propiedad();
    this.vistaEdificio.btnActualizarEdificio.addActionListener(this);
  }

  /**
   * Segundo constructor de la clase ControladorEdificio.
   */
  ControladorEdificio() {
    vistaEdificio = new VistaEdificio();
    propiedad = new Propiedad();
    this.vistaEdificio.btnActualizarEdificio.addActionListener(this);
  }

  /**
   * Captura un evento dado en la vista del edificio e invoca el método del modelo correspondiente.
   *
   * @param e Nombre del evento
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Actualizar":
        this.vistaEdificio.txtIdEdificio.setEnabled(false);
        actualizarEdificio();
        break;
      case "Crear":
        registrarNuevoEdificio();
        break;
    }
  }

  /**
   * Registra un nuevo edificio.
   */
  public void registrarNuevoEdificio() {
    if (this.vistaEdificio.txtIdEdificio.getText().isBlank() || this.vistaEdificio.txtAreaEdificio.getText().isBlank()
      || this.vistaEdificio.txtMetroEdificio.getText().isBlank()) {
      JOptionPane.showMessageDialog(null, "Por favor ingrese el número de finca, el área del terreno y el "
        + "valor del mentro cuadrado");
    } else {
      Edificio nuevoEdificio = new Edificio();
      nuevoEdificio.setNumFinca(Integer.parseInt(this.vistaEdificio.txtIdEdificio.getText()));
      nuevoEdificio.setModalidad(this.vistaEdificio.comboModalidadEdificio.getSelectedItem().toString());
      nuevoEdificio.setAreaTerreno(Double.parseDouble(this.vistaEdificio.txtAreaEdificio.getText()));
      nuevoEdificio.setValorMetroCuadrado(Double.parseDouble(this.vistaEdificio.txtMetroEdificio.getText()));
      nuevoEdificio.setValorFiscal(Double.parseDouble(this.vistaEdificio.txtFiscalEdificio.getText()));
      nuevoEdificio.setProvincia(this.vistaEdificio.txtProvinciaEdificio.getSelectedItem().toString());
      nuevoEdificio.setCanton(this.vistaEdificio.txtCantonEdificio.getText());
      nuevoEdificio.setDistrito(this.vistaEdificio.txtDistritoEdificio.getText());
      nuevoEdificio.setDirExacta(this.vistaEdificio.txtDireccionEdificio.getText());
      nuevoEdificio.setEstado(this.vistaEdificio.comboEstadoEdificio.getSelectedItem().toString());
      nuevoEdificio.setFotografias(getFotografias());
      double precio = Double.parseDouble(this.vistaEdificio.txtMetroEdificio.getText()) * Double.parseDouble(this.vistaEdificio.txtAreaEdificio.getText());
      nuevoEdificio.setPrecio(precio);
      nuevoEdificio.setTipo("Edificio");
      nuevoEdificio.setAltura(Double.parseDouble(this.vistaEdificio.txtAlturaEdificio.getText()));
      nuevoEdificio.setPiscina(this.vistaEdificio.comboPiscinaEdificio.getSelectedItem().toString());
      nuevoEdificio.setRancho(this.vistaEdificio.comboRanchoEdificio.getSelectedItem().toString());
      nuevoEdificio.setCantParqueo(Integer.parseInt(this.vistaEdificio.txtVisitasEdificio.getText()));
      nuevoEdificio.setAreaConstruccion(Double.parseDouble(this.vistaEdificio.txtConstruccionEdificio.getText()));
      nuevoEdificio.setEstilo(this.vistaEdificio.comboEstiloEdificio.getSelectedItem().toString());
      nuevoEdificio.setNiveles(getNiveles());
      try {
        propiedad.registrarPropiedad(nuevoEdificio, agente.getIdentificacion());
        nuevoEdificio.registrarNuevoEdificio(nuevoEdificio);
        JOptionPane.showMessageDialog(null, "El edificio se guardó de forma exitosa");
        this.vistaEdificio.setVisible(false);
      } catch (Exception ex) {
        Logger.getLogger(ControladorAgente.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "Ocurrió un error, el edificio comercial no se pudo almacenar");
      }
    }
  }

  /**
   * Recupera la información de los niveles asociados a un edificio.
   *
   * @return Lista con todos los niveles asociados a un edificio.
   */
  private ArrayList<Nivel> getNiveles() {
    ArrayList<Nivel> niveles = new ArrayList<Nivel>();
    for (int i = 0; i <= (this.vistaEdificio.tablaNivelesEdificio.getRowCount() - 1); i++) {
      int residencias = Integer.parseInt(this.vistaEdificio.tablaNivelesEdificio.getValueAt(i, 0).toString());
      int zonas = Integer.parseInt(this.vistaEdificio.tablaNivelesEdificio.getValueAt(i, 1).toString());
      String tipo = this.vistaEdificio.tablaNivelesEdificio.getValueAt(i, 2).toString();
      Nivel nuevoNivel = new Nivel(residencias, zonas, tipo);
      niveles.add(nuevoNivel);
    }
    return niveles;
  }

  /**
   * Muestra en la vista edificio la información de un edificio solicitado.
   *
   * @param edificio Edificio que se desea visuazliar
   */
  public void verEdificio(Edificio edificio) {
    this.vistaEdificio.txtIdEdificio.setText(String.valueOf(edificio.getNumFinca()));
    this.vistaEdificio.txtIdEdificio.setEnabled(false);
    this.vistaEdificio.txtAreaEdificio.setText(String.valueOf(edificio.getAreaTerreno()));
    this.vistaEdificio.txtMetroEdificio.setText(String.valueOf(edificio.getValorMetroCuadrado()));
    this.vistaEdificio.txtFiscalEdificio.setText(String.valueOf(edificio.getValorFiscal()));
    this.vistaEdificio.comboModalidadEdificio.setSelectedItem(edificio.getModalidad());
    this.vistaEdificio.txtProvinciaEdificio.setSelectedItem(edificio.getProvincia());
    this.vistaEdificio.txtCantonEdificio.setText(edificio.getCanton());
    this.vistaEdificio.txtDistritoEdificio.setText(edificio.getDistrito());
    this.vistaEdificio.txtDireccionEdificio.setText(edificio.getDirExacta());
    this.vistaEdificio.comboEstadoEdificio.setSelectedItem(edificio.getEstado());
    this.vistaEdificio.txtConstruccionEdificio.setText(String.valueOf(edificio.getAreaConstruccion()));
    this.vistaEdificio.comboEstiloEdificio.setSelectedItem(edificio.getEstilo());
    this.vistaEdificio.txtAlturaEdificio.setText(String.valueOf(edificio.getAltura()));
    this.vistaEdificio.comboPiscinaEdificio.setSelectedItem(edificio.getPiscina());
    this.vistaEdificio.comboRanchoEdificio.setSelectedItem(edificio.getRancho());
    this.vistaEdificio.txtVisitasEdificio.setText(String.valueOf(edificio.getCantParqueo()));
    DefaultTableModel modeloImagenes = (DefaultTableModel) this.vistaEdificio.tablaImagenesEdificio.getModel();
    for (int i = 0; i < edificio.getFotografias().size(); i++) {
      modeloImagenes.addRow(new Object[]{edificio.getFotografias().get(i)});
    }
    DefaultTableModel modeloNiveles = (DefaultTableModel) this.vistaEdificio.tablaNivelesEdificio.getModel();
    for (int i = 0; i < edificio.getNiveles().size(); i++) {
      String registro[] = new String[3];
      registro[0] = String.valueOf(edificio.getNiveles().get(i).getCantResidencias());
      registro[1] = String.valueOf(edificio.getNiveles().get(i).getCantZonas());
      registro[2] = edificio.getNiveles().get(i).getTipo();
      modeloNiveles.addRow(registro);
    }
  }

  /**
   * Actualiza la información de un edificio previamente registrado.
   */
  public void actualizarEdificio() {
    Edificio nuevoEdificio = new Edificio();
    nuevoEdificio.setNumFinca(Integer.parseInt(this.vistaEdificio.txtIdEdificio.getText()));
    nuevoEdificio.setModalidad(this.vistaEdificio.comboModalidadEdificio.getSelectedItem().toString());
    nuevoEdificio.setAreaTerreno(Double.parseDouble(this.vistaEdificio.txtAreaEdificio.getText()));
    nuevoEdificio.setValorMetroCuadrado(Double.parseDouble(this.vistaEdificio.txtMetroEdificio.getText()));
    nuevoEdificio.setValorFiscal(Double.parseDouble(this.vistaEdificio.txtFiscalEdificio.getText()));
    nuevoEdificio.setProvincia(this.vistaEdificio.txtProvinciaEdificio.getSelectedItem().toString());
    nuevoEdificio.setCanton(this.vistaEdificio.txtCantonEdificio.getText());
    nuevoEdificio.setDistrito(this.vistaEdificio.txtDistritoEdificio.getText());
    nuevoEdificio.setDirExacta(this.vistaEdificio.txtDireccionEdificio.getText());
    nuevoEdificio.setEstado(this.vistaEdificio.comboEstadoEdificio.getSelectedItem().toString());
    nuevoEdificio.setFotografias(getFotografias());
    double precio = Double.parseDouble(this.vistaEdificio.txtMetroEdificio.getText()) * Double.parseDouble(this.vistaEdificio.txtAreaEdificio.getText());
    nuevoEdificio.setPrecio(precio);
    nuevoEdificio.setTipo("Edificio");
    nuevoEdificio.setAltura(Double.parseDouble(this.vistaEdificio.txtAlturaEdificio.getText()));
    nuevoEdificio.setPiscina(this.vistaEdificio.comboPiscinaEdificio.getSelectedItem().toString());
    nuevoEdificio.setRancho(this.vistaEdificio.comboRanchoEdificio.getSelectedItem().toString());
    nuevoEdificio.setCantParqueo(Integer.parseInt(this.vistaEdificio.txtVisitasEdificio.getText()));
    nuevoEdificio.setAreaConstruccion(Double.parseDouble(this.vistaEdificio.txtConstruccionEdificio.getText()));
    nuevoEdificio.setEstilo(this.vistaEdificio.comboEstiloEdificio.getSelectedItem().toString());
    nuevoEdificio.setNiveles(getNiveles());
    propiedad.actualizarPropiedad(nuevoEdificio);
    try {
      nuevoEdificio.actualizarEdificio(nuevoEdificio);
    } catch (SQLException ex) {
      Logger.getLogger(ControladorCasa.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Recupera las imagenes asociadas al edificio.
   * 
   * @return Una lista con todas las imagenes asociadas al edificio.
   */
  public ArrayList<byte[]> getFotografias() {
    ArrayList<byte[]> resultado = new ArrayList<byte[]>();
    if ((this.vistaEdificio.tablaImagenesEdificio.getRowCount() - 1) == -1) {
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
      for (int i = 0; i <= (this.vistaEdificio.tablaImagenesEdificio.getRowCount() - 1); i++) {
        resultado.add((byte[]) this.vistaEdificio.tablaImagenesEdificio.getValueAt(i, 0));
      }
    }
    return resultado;
  }
}
