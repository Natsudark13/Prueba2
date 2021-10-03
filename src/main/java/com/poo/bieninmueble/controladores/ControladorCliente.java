package com.poo.bieninmueble.controladores;

import com.google.zxing.WriterException;
import com.poo.bieninmueble.logicaDeNegocios.Agente;
import com.poo.bieninmueble.logicaDeNegocios.Casa;
import com.poo.bieninmueble.logicaDeNegocios.CentroComercial;
import com.poo.bieninmueble.logicaDeNegocios.Edificio;
import com.poo.bieninmueble.vistas.VistaCliente;
import com.poo.bieninmueble.logicaDeNegocios.IPropiedad;
import com.poo.bieninmueble.logicaDeNegocios.Lote;
import com.poo.bieninmueble.logicaDeNegocios.Cliente;
import com.poo.bieninmueble.logicaDeNegocios.Comentario;
import com.poo.bieninmueble.logicaDeNegocios.Propiedad;
import com.poo.bieninmueble.servicios.Email;
import com.poo.bieninmueble.servicios.GeneradorQR;
import com.poo.bieninmueble.vistas.VistaComentario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * La clase ControladorCliente se encarga de reaccionar a eventos de la vista cliente e invocar
 * peticiones al modelo correspondiente para completar satisfactoriamente la peticion del evento.
 */
public class ControladorCliente implements ActionListener {

  //atributos
  public VistaCliente vistaCliente;
  public VistaComentario vistaComentario;
  private IPropiedad propiedad;
  private Cliente cliente;

  /**
   * Constructor de la clase ControladorCliente.
   *
   * @param pCliente Cliente que está en la sesión.
   */
  public ControladorCliente(Cliente pCliente) {
    this.propiedad = new Propiedad();
    this.vistaCliente = new VistaCliente();
    this.vistaComentario = new VistaComentario();
    this.vistaCliente.btnBuscarFiltro.addActionListener(this);
    this.vistaCliente.btnBuscarRango.addActionListener(this);
    this.vistaCliente.btnVer.addActionListener(this);
    this.vistaCliente.btnComentario.addActionListener(this);
    this.vistaComentario.btnAgregarComentario.addActionListener(this);
    this.vistaCliente.btnFicha.addActionListener(this);
    cliente = pCliente;
    try {
      ArrayList<Propiedad> resultado = propiedad.getPropiedadesCliente();
      cargarDatosPropiedadesCliente(resultado);
    } catch (SQLException ex) {
      Logger.getLogger(ControladorAgente.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Captura un evento dado en la vista cliente e invoca el método del modelo correspondiente.
   *
   * @param e Nombre del evento
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Buscar":
        String criterio = this.vistaCliente.comboFiltroCliente.getSelectedItem().toString();
        String dato = this.vistaCliente.txtCriterioCliente.getText();
        System.out.println(criterio + "   " + dato);
        filtrarDatos(criterio, dato);
        this.vistaCliente.txtCriterioCliente.setText("Escriba el criterio de búsqueda...");
        break;
      case "Buscar precio":
        try {
        double menor = Double.parseDouble(this.vistaCliente.txtMenor.getText());
        double mayor = Double.parseDouble(this.vistaCliente.txtMayor.getText());
        this.vistaCliente.txtMenor.setText("Menor");
        this.vistaCliente.txtMayor.setText("Mayor");
        this.vistaCliente.txtCriterioCliente.setText("Escriba el criterio de búsqueda...");
        filtrarPrecio(menor, mayor);
      } catch (NumberFormatException nfe) {
        JOptionPane.showMessageDialog(null, "El rango no es adecuado, seleccione otros números");
      }
      break;
      case "Ver propiedad":
        try {
        int fila = this.vistaCliente.tablaPropiedadesCliente.getSelectedRow();
        TableModel modelo = this.vistaCliente.tablaPropiedadesCliente.getModel();
        String idPropiedad = modelo.getValueAt(fila, 0).toString();
        try {
          getPropiedad(idPropiedad);
        } catch (SQLException ex) {
          Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
      } catch (ArrayIndexOutOfBoundsException ex) {
        JOptionPane.showMessageDialog(null, "Seleccione una propiedad");
      }
      break;
      case "Escribir comentario":
        try {
        int index = this.vistaCliente.tablaPropiedadesCliente.getSelectedRow();
        TableModel modeloComentario = this.vistaCliente.tablaPropiedadesCliente.getModel();
        String numFinca = modeloComentario.getValueAt(index, 0).toString();
        this.vistaComentario.lblPropiedad.setText(numFinca);
        cargarDatosComentarios(numFinca);
        this.vistaComentario.setVisible(true);
      } catch (ArrayIndexOutOfBoundsException aiobe) {
        JOptionPane.showMessageDialog(null, "Seleccione una propiedad");
      } catch (SQLException ex) {
        Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
      }
      break;
      case "Agregar":
        generarComentario();
        break;
      case "Solicitar ficha":
        try {
        int index = this.vistaCliente.tablaPropiedadesCliente.getSelectedRow();
        TableModel modeloComentario = this.vistaCliente.tablaPropiedadesCliente.getModel();
        String numFinca = modeloComentario.getValueAt(index, 0).toString();
        solicitarFicha(numFinca);
        JOptionPane.showMessageDialog(null, "La ficha se envió a su correo electrónico");
      } catch (ArrayIndexOutOfBoundsException aiobe) {
        JOptionPane.showMessageDialog(null, "Seleccione una propiedad");
      } catch (SQLException ex) {
        Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
      }
        break;
    }
  }

  /**
   * Filtra la búsqueda de las propiedades registradas por tipo, provincia o modalidad.
   * 
   * @param pCriterio Criterio de búsqueda
   * @param pDato Valor del criterio.
   */
  private void filtrarDatos(String pCriterio, String pDato) {
    try {
      if (pCriterio.equals("Todas")) {
        ArrayList<Propiedad> resultado = propiedad.getPropiedadesCliente();
        cargarDatosPropiedadesCliente(resultado);
      } else {
        ArrayList<Propiedad> resultado = propiedad.getPropiedadesFiltroCliente(pCriterio, pDato);
        cargarDatosPropiedadesCliente(resultado);
      }
    } catch (SQLException ex) {
      Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Actualiza la tabla de comentarios dependiendo de la propiedad solicitada.
   * 
   * @param pId Numero de finca de la propiedad solicitada.
   * @throws SQLException En caso que no se encuentren los datos correspondientes.
   */
  private void cargarDatosComentarios(String pId) throws SQLException {
    ArrayList<Comentario> pDatos = propiedad.getComentarios(pId);
    if (pDatos.size() > 0) {
      DefaultTableModel modelo = (DefaultTableModel) this.vistaComentario.tablaComentarios.getModel();
      modelo.setRowCount(0);
      for (int i = 0; i < pDatos.size(); i++) {
        Vector v = new Vector();
        v.add(pDatos.get(i).getComentario());
        modelo.addRow(v);
        vistaComentario.tablaComentarios.setModel(modelo);
      }
    } else {
      JOptionPane.showMessageDialog(null, "No existen datos asociados");
    }
  }

  /**
   * Actualiza los datos de la tabla de propiedades.
   * 
   * @param pDatos Información de las propiedades registradas.
   * @throws SQLException En caso que no se encuentren los datos correspondientes.
   */
  private void cargarDatosPropiedadesCliente(ArrayList<Propiedad> pDatos) throws SQLException {
    if (pDatos.size() > 0) {
      DefaultTableModel modelo = (DefaultTableModel) vistaCliente.tablaPropiedadesCliente.getModel();
      modelo.setRowCount(0);
      for (int i = 0; i < pDatos.size(); i++) {
        Vector v = new Vector();
        v.add(pDatos.get(i).getNumFinca());
        v.add(pDatos.get(i).getProvincia());
        v.add(pDatos.get(i).getTipo());
        v.add(pDatos.get(i).getModalidad());
        v.add(pDatos.get(i).getPrecio());
        modelo.addRow(v);
        vistaCliente.tablaPropiedadesCliente.setModel(modelo);
      }
    } else {
      JOptionPane.showMessageDialog(null, "No existen datos asociados");
    }
  }

  /**
   * Filtra la búsqueda de las propiedades registradas por un rango de precio.
   * 
   * @param menor Precio menor del rango.
   * @param mayor Precio mayor del rango.
   */
  private void filtrarPrecio(double menor, double mayor) {
    ArrayList<Propiedad> resultado;
    try {
      resultado = propiedad.getPrecioCliente(menor, mayor);
      cargarDatosPropiedadesCliente(resultado);
    } catch (SQLException ex) {
      Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Solicita la información de una propiedad depentiendo del tipo de bien inmueble al que
   * corresponde.
   *
   * @param idPropiedad Numero de finca de la propiedad que se desea visualizar
   * @throws SQLException En caso que no se encuentren los datos correspondientes.
   */
  private void getPropiedad(String idPropiedad) throws SQLException {
    int index = this.vistaCliente.tablaPropiedadesCliente.getSelectedRow();
    TableModel modelo = this.vistaCliente.tablaPropiedadesCliente.getModel();
    String tipo = modelo.getValueAt(index, 2).toString();
    switch (tipo) {
      case "Lote":
        Lote nuevoLote = new Lote();
        nuevoLote = nuevoLote.getLote(idPropiedad);
        ControladorLote verLote = new ControladorLote();
        verLote.verLote(nuevoLote);
        verLote.vistaLote.btnBuscarImagenLote.setVisible(false);
        verLote.vistaLote.btnEliminarImagenLote.setVisible(false);
        verLote.vistaLote.btnActualizarLote.setVisible(false);
        verLote.vistaLote.setVisible(true);
        registrarProspecto(cliente.getIdentificacion(), nuevoLote.getNumFinca());
        break;
      case "Casa":
        Casa nuevaCasa = new Casa();
        nuevaCasa = nuevaCasa.getCasa(idPropiedad);
        ControladorCasa verCasa = new ControladorCasa();
        verCasa.verCasa(nuevaCasa);
        verCasa.vistaCasa.btnBuscarImagenCasa.setVisible(false);
        verCasa.vistaCasa.btnEliminarImagenCasa.setVisible(false);
        verCasa.vistaCasa.btnActualizarCasa.setVisible(false);
        verCasa.vistaCasa.setVisible(true);
        registrarProspecto(cliente.getIdentificacion(), nuevaCasa.getNumFinca());
        break;
      case "Centro comercial":
        CentroComercial nuevoCentro = new CentroComercial();
        nuevoCentro = nuevoCentro.getCentroComercial(idPropiedad);
        ControladorCentroComercial verCentro = new ControladorCentroComercial();
        verCentro.verCentroComercial(nuevoCentro);
        verCentro.vistaCentro.btnBuscarImagenCentro.setVisible(false);
        verCentro.vistaCentro.btnEliminarImagenCentro.setVisible(false);
        verCentro.vistaCentro.btnActualizarCentro.setVisible(false);
        verCentro.vistaCentro.setVisible(true);
        registrarProspecto(cliente.getIdentificacion(), nuevoCentro.getNumFinca());
        break;
      case "Edificio":
        Edificio nuevoEdificio = new Edificio();
        nuevoEdificio = nuevoEdificio.getEdificio(idPropiedad);
        ControladorEdificio verEdificio = new ControladorEdificio();
        verEdificio.verEdificio(nuevoEdificio);
        verEdificio.vistaEdificio.btnBuscarImagenEdificio.setVisible(false);
        verEdificio.vistaEdificio.btnEliminarImagenEdificio.setVisible(false);
        verEdificio.vistaEdificio.btnActualizarEdificio.setVisible(false);
        verEdificio.vistaEdificio.setVisible(true);
        registrarProspecto(cliente.getIdentificacion(), nuevoEdificio.getNumFinca());
        break;
    }
  }

  /**
   * Registra un prospecto de una propiedad en específico.
   * 
   * @param idCliente Identifación del cliente.
   * @param idPropiedad Numero de finca de la propiedad
   */
  public void registrarProspecto(String idCliente, int idPropiedad) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String fecha = dtf.format(now);
    try {
      this.cliente.setProspecto(idCliente, idPropiedad, fecha);
    } catch (SQLException ex) {
      Logger.getLogger(ControladorAgente.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Registra un comentario a una propiedad en específico.
   */
  public void generarComentario() {
    String numFinca = this.vistaComentario.lblPropiedad.getText();
    String comentario = this.vistaComentario.txtComentario.getText();
    try {
      propiedad.agregarComentario(numFinca, comentario);
    } catch (SQLException ex) {
      Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  /**
   * Envia la ficha de una propiedad seleccionada al correo electronico del usuario
   * 
   * @param idPropiedad Numero de finca de la propiedad
   * @throws SQLException En caso de no establecer conexión con la base de datos
   */
   private void solicitarFicha(String idPropiedad) throws SQLException{
      try {
          Agente agente = new Agente();
          Email correo = Email.getInstance();
          GeneradorQR qr = new GeneradorQR();
          String informacion = "";
          ArrayList<byte[]> fotografias = new ArrayList<>();
          int index = this.vistaCliente.tablaPropiedadesCliente.getSelectedRow();
          TableModel modelo = this.vistaCliente.tablaPropiedadesCliente.getModel();
          String tipo = modelo.getValueAt(index, 2).toString();
          switch (tipo) {
              case "Lote":
                  Lote nuevoLote = new Lote();
                  nuevoLote = nuevoLote.getLote(idPropiedad);
                  informacion = nuevoLote.toString();
                  fotografias = nuevoLote.getFotografias();
                  break;
              case "Casa":
                  Casa nuevaCasa = new Casa();
                  nuevaCasa = nuevaCasa.getCasa(idPropiedad);
                  informacion = nuevaCasa.toString();
                  fotografias = nuevaCasa.getFotografias();
                  break;
              case "Centro comercial":
                  CentroComercial nuevoCentro = new CentroComercial();
                  nuevoCentro = nuevoCentro.getCentroComercial(idPropiedad);
                  informacion = nuevoCentro.toString();
                  fotografias = nuevoCentro.getFotografias();
                  break;
              case "Edificio":
                  Edificio nuevoEdificio = new Edificio();
                  nuevoEdificio = nuevoEdificio.getEdificio(idPropiedad);
                  informacion = nuevoEdificio.toString();
                  fotografias = nuevoEdificio.getFotografias();
          }
          agente = agente.getAgente(idPropiedad);
          qr.createQRImage(agente.toString());
          correo.enviar(cliente.getCorreo(), "Ficha Solicitada",
                  "<h1>Información solicitada</h1>"+
                          "<p>"+informacion+"</p>", fotografias,true);
      } catch (WriterException ex) {
          Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
          JOptionPane.showMessageDialog(null, "Hubo un problema generado el código QR");
      } catch (IOException ex) {
          Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
          JOptionPane.showMessageDialog(null, "Hubo un problema enviando la información de la propiedad");
      }
  }
}
