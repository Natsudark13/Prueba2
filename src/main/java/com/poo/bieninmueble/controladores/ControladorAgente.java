package com.poo.bieninmueble.controladores;

import com.poo.bieninmueble.logicaDeNegocios.Agente;
import com.poo.bieninmueble.logicaDeNegocios.Casa;
import com.poo.bieninmueble.logicaDeNegocios.CentroComercial;
import com.poo.bieninmueble.logicaDeNegocios.Cliente;
import com.poo.bieninmueble.logicaDeNegocios.Edificio;
import com.poo.bieninmueble.logicaDeNegocios.ICliente;
import com.poo.bieninmueble.logicaDeNegocios.Lote;
import com.poo.bieninmueble.logicaDeNegocios.Propiedad;
import com.poo.bieninmueble.vistas.VistaAgente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.poo.bieninmueble.logicaDeNegocios.IPropiedad;
import com.poo.bieninmueble.vistas.VistaProspectos;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * La clase ControladorAgente se encarga de reaccionar a eventos de la vista agente e invocar
 * peticiones al modelo correspondiente para completar satisfactoriamente la peticion del evento.
 */
public class ControladorAgente implements ActionListener, ListSelectionListener {

  //atributos
  public VistaAgente vistaAgente;
  public VistaProspectos vistaProspectos;
  protected ICliente cliente;
  protected Agente agente;
  protected IPropiedad propiedad;

  /**
   * Constructor de la clase ControladorAgente
   *
   * @param pAgente Agente que está en sesión
   */
  public ControladorAgente(Agente pAgente) {
    this.agente = pAgente;
    this.propiedad = new Propiedad();
    this.vistaAgente = new VistaAgente();
    this.cliente = new Cliente();
    this.vistaProspectos = new VistaProspectos();
    this.vistaAgente.btnFiltrar.addActionListener(this);
    this.vistaAgente.btnBuscarRango.addActionListener(this);
    this.vistaAgente.btnVerPropiedad.addActionListener(this);
    this.vistaAgente.btnEditarPropiedad.addActionListener(this);
    this.vistaAgente.btnEliminarPropiedad.addActionListener(this);
    this.vistaAgente.btnLote.addActionListener(this);
    this.vistaAgente.btnCasa.addActionListener(this);
    this.vistaAgente.btnCentro.addActionListener(this);
    this.vistaAgente.btnEdificio.addActionListener(this);
    this.vistaAgente.tablaPropiedades.getSelectionModel().addListSelectionListener(this);
    this.vistaAgente.btnConsultarProspectos.addActionListener(this);
    try {
      actualizarDatos();
    } catch (SQLException ex) {
      Logger.getLogger(ControladorAgente.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Captura un evento dado en la vista del agente e invoca el método del modelo correspondiente.
   * 
   * @param e Nombre del evento
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Buscar": {
        try {
          String criterio = this.vistaAgente.comboFiltroPropiedad.getSelectedItem().toString();
          String dato = this.vistaAgente.txtCriterioFiltro.getText();
          filtrarDatos(criterio, dato);
        } catch (SQLException ex) {
          Logger.getLogger(ControladorAgente.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      break;
      case "Crear Lote":
        ControladorLote controladorLote = new ControladorLote(agente);
        controladorLote.vistaLote.btnActualizarLote.setText("Crear");
        controladorLote.vistaLote.setVisible(true);
        break;
      case "Crear Casa":
        ControladorCasa controladorCasa = new ControladorCasa(agente);
        controladorCasa.vistaCasa.btnActualizarCasa.setText("Crear");
        controladorCasa.vistaCasa.setVisible(true);
        break;
      case "Crear Centro Comercial":
        ControladorCentroComercial controladorCentro = new ControladorCentroComercial(agente);
        controladorCentro.vistaCentro.btnActualizarCentro.setText("Crear");
        controladorCentro.vistaCentro.setVisible(true);
        break;
      case "Crear Edificio":
        ControladorEdificio controladorEdificio = new ControladorEdificio(agente);
        controladorEdificio.vistaEdificio.btnActualizarEdificio.setText("Crear");
        controladorEdificio.vistaEdificio.setVisible(true);
        break;
      case "Eliminar":
        int reply = JOptionPane.showConfirmDialog(null, "Seguro/a que desea eliminar la propiedad",
          "Eliminar propiedad", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
          try {
            String idPropiedad = this.vistaAgente.lblIdPropiedad.getText();
            Propiedad propiedadSeleccionada = propiedad.getPropiedadId(idPropiedad);
            propiedadSeleccionada.eliminarPropiedad(idPropiedad);
            actualizarDatos();
          } catch (IndexOutOfBoundsException exa) {
            JOptionPane.showMessageDialog(null, "Seleccione una propiedad");
          } catch (SQLException ex) {
            Logger.getLogger(ControladorAgente.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
        break;
      case "Buscar precio":
        try {
        double menor = Double.parseDouble(this.vistaAgente.txtMenor.getText());
        double mayor = Double.parseDouble(this.vistaAgente.txtMayor.getText());
        filtrarPrecio(menor, mayor);
      } catch (NumberFormatException nfe) {
        JOptionPane.showMessageDialog(null, "El rango no es adecuado, seleccione otros números");
      }
      break;
      case "Consultar Prospectos":
        try {
        String idPropiedadProspecto = this.vistaAgente.lblIdPropiedad.getText();
        this.vistaProspectos.lblIdPropiedadProspectos.setText(idPropiedadProspecto);
        try {
          ArrayList<Cliente> resultadoProspectos = cliente.getProspectos(idPropiedadProspecto);
          ArrayList<Cliente> resultadoProspectosUnicos = new ArrayList<Cliente>();
          for (Cliente elemento : resultadoProspectos) {
            boolean repetido = false;
            for (Cliente elemento1 : resultadoProspectosUnicos) {
              if (elemento.getIdentificacion().equals(elemento1.getIdentificacion())) {
                repetido = true;
              }
            }
            if (false == repetido) {
              resultadoProspectosUnicos.add(elemento);
            }
          }
          cargarDatosProspectos(resultadoProspectosUnicos);
          if (false == resultadoProspectosUnicos.isEmpty()) {
            this.vistaProspectos.setVisible(true);
          }
        } catch (SQLException ex) {
          Logger.getLogger(ControladorAgente.class.getName()).log(Level.SEVERE, null, ex);
        }
      } catch (ArrayIndexOutOfBoundsException ex) {
        JOptionPane.showMessageDialog(null, "Seleccione una propiedad");
      }
      break;
      case "Ver": 
        try {
        String idPropiedad = this.vistaAgente.lblIdPropiedad.getText();
        getPropiedad(idPropiedad);
      } catch (ArrayIndexOutOfBoundsException ex) {
        JOptionPane.showMessageDialog(null, "Seleccione una propiedad");
      } catch (SQLException ex) {
        Logger.getLogger(ControladorAgente.class.getName()).log(Level.SEVERE, null, ex);
      }
      break;
      case "Editar": 
        try {
        String idPropiedadEditar = this.vistaAgente.lblIdPropiedad.getText();
        editarPropiedad(idPropiedadEditar);
      } catch (ArrayIndexOutOfBoundsException ex) {
        JOptionPane.showMessageDialog(null, "Seleccione una propiedad");
      } catch (SQLException ex) {
        Logger.getLogger(ControladorAgente.class.getName()).log(Level.SEVERE, null, ex);
      }
      break;
    }
  }

  /**
   * Captura un evento dado en la tabla de las propiedades e invoca el método del modelo 
   * correspondiente.
   * @param e 
   */
  @Override
  public void valueChanged(ListSelectionEvent e) {
    try {
      int index = this.vistaAgente.tablaPropiedades.getSelectedRow();
      if (index >= 0) {
        TableModel modelo = this.vistaAgente.tablaPropiedades.getModel();
        String numFinca = modelo.getValueAt(index, 0).toString();
        ArrayList<byte[]> resultado = propiedad.getFotografias(numFinca);
        byte[] imagen = resultado.get(0);
        ImageIcon imagenIcon = new ImageIcon(new ImageIcon(imagen).getImage().
          getScaledInstance(this.vistaAgente.lblImagenPropiedad.getWidth(),
            this.vistaAgente.lblImagenPropiedad.getHeight(), Image.SCALE_SMOOTH));
        this.vistaAgente.lblImagenPropiedad.setIcon(imagenIcon);
      }
    } catch (SQLException ex) {
      Logger.getLogger(ControladorAgente.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  /**
   * Filtra la búsqueda de las propiedades registradas por tipo, provincia o modalidad.
   * 
   * @param criterio Criterio de búsqueda
   * @param dato Valor del criterio.
   * @throws SQLException En caso que no se encuentren los datos correspondientes.
   */
  public void filtrarDatos(String criterio, String dato) throws SQLException {
    if (criterio.equals("Todas")) {
      actualizarDatos();
    } else {
      ArrayList<Propiedad> resultado = propiedad.getPropiedadesFiltroAgente(criterio, dato,
        agente.getIdentificacion());
      cargarDatosPropiedadesAgente(resultado);
    }
  }

  /**
   * Filtra la búsqueda de las propiedades registradas por un rango de precio.
   * 
   * @param menor Precio menor del rango.
   * @param mayor Precio mayor del rango.
   */
  public void filtrarPrecio(double menor, double mayor) {
    ArrayList<Propiedad> resultado;
    try {
      resultado = propiedad.getPrecioCliente(menor, mayor);
      cargarDatosPropiedadesAgente(resultado);
    } catch (SQLException ex) {
      Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Recupera los datos de las propiedades asociadas al agente.
   * 
   * @throws SQLException En caso que no se encuentren los datos correspondientes.
   */
  private void actualizarDatos() throws SQLException {
    ArrayList<Propiedad> resultado = propiedad.getPropiedadesAgente(agente.getIdentificacion());
    cargarDatosPropiedadesAgente(resultado);
  }

  /**
   * Actualiza los datos de la tabla de propiedades.
   * 
   * @param pDatos Información de las propiedades registradas.
   * @throws SQLException En caso que no se encuentren los datos correspondientes.
   */
  private void cargarDatosPropiedadesAgente(ArrayList<Propiedad> pDatos) throws SQLException {
    if (pDatos.size() > 0) {
      DefaultTableModel modelo = (DefaultTableModel) this.vistaAgente.tablaPropiedades.getModel();
      modelo.setRowCount(0);
      for (int i = 0; i < pDatos.size(); i++) {
        Vector v = new Vector();
        v.add(pDatos.get(i).getNumFinca());
        v.add(pDatos.get(i).getTipo());
        v.add(pDatos.get(i).getModalidad());
        v.add(pDatos.get(i).getPrecio());
        v.add(pDatos.get(i).getProvincia());
        modelo.addRow(v);
        vistaAgente.tablaPropiedades.setModel(modelo);
      }
    } else {
      JOptionPane.showMessageDialog(null, "No existen datos asociados");
    }
  }
  
  /**
   * Actualiza los datos de la tabla de prospectos.
   * 
   * @param pDatos Información de los prospectos asociados a una propiedad
   * @throws SQLException En caso que no se encuentren los datos correspondientes.
   */
  private void cargarDatosProspectos(ArrayList<Cliente> pDatos) throws SQLException {
    if (pDatos.size() > 0) {
      DefaultTableModel modelo = (DefaultTableModel) vistaProspectos.tablaProspectos.getModel();
      modelo.setRowCount(0);
      for (int i = 0; i < pDatos.size(); i++) {
        Vector v = new Vector();
        v.add(pDatos.get(i).getIdentificacion());
        v.add(pDatos.get(i).getNombre());
        v.add(pDatos.get(i).getApellido());
        v.add(pDatos.get(i).getNumeroTelefono());
        modelo.addRow(v);
        vistaProspectos.tablaProspectos.setModel(modelo);
      }
    } else {
      JOptionPane.showMessageDialog(null, "No existen datos asociados");
    }
  }

  /**
   * Solicita la información de una propiedad depentiendo del tipo de bien inmueble al que
   * corresponde.
   * 
   * @param idPropiedad Numero de finca de la propiedad que se desea visualizar
   * @throws SQLException En caso que no se encuentren los datos correspondientes.
   */
  public void getPropiedad(String idPropiedad) throws SQLException {
    int index = this.vistaAgente.tablaPropiedades.getSelectedRow();
    TableModel modelo = this.vistaAgente.tablaPropiedades.getModel();
    String tipo = modelo.getValueAt(index, 1).toString();
    switch (tipo) {
      case "Lote":
        Lote nuevoLote = new Lote();
        nuevoLote = nuevoLote.getLote(idPropiedad);
        ControladorLote verLote = new ControladorLote(agente);
        verLote.verLote(nuevoLote);
        verLote.vistaLote.btnBuscarImagenLote.setVisible(false);
        verLote.vistaLote.btnEliminarImagenLote.setVisible(false);
        verLote.vistaLote.btnActualizarLote.setVisible(false);
        verLote.vistaLote.setVisible(true);
        break;
      case "Casa":
        Casa nuevaCasa = new Casa();
        nuevaCasa = nuevaCasa.getCasa(idPropiedad);
        ControladorCasa verCasa = new ControladorCasa(agente);
        verCasa.verCasa(nuevaCasa);
        verCasa.vistaCasa.btnBuscarImagenCasa.setVisible(false);
        verCasa.vistaCasa.btnEliminarImagenCasa.setVisible(false);
        verCasa.vistaCasa.btnActualizarCasa.setVisible(false);
        verCasa.vistaCasa.setVisible(true);
        break;
      case "Centro comercial":
        CentroComercial nuevoCentro = new CentroComercial();
        nuevoCentro = nuevoCentro.getCentroComercial(idPropiedad);
        ControladorCentroComercial verCentro = new ControladorCentroComercial(agente);
        verCentro.verCentroComercial(nuevoCentro);
        verCentro.vistaCentro.btnBuscarImagenCentro.setVisible(false);
        verCentro.vistaCentro.btnEliminarImagenCentro.setVisible(false);
        verCentro.vistaCentro.btnActualizarCentro.setVisible(false);
        verCentro.vistaCentro.setVisible(true);
        break;
      case "Edificio":
        Edificio nuevoEdificio = new Edificio();
        nuevoEdificio = nuevoEdificio.getEdificio(idPropiedad);
        ControladorEdificio verEdificio = new ControladorEdificio(agente);
        verEdificio.verEdificio(nuevoEdificio);
        verEdificio.vistaEdificio.btnBuscarImagenEdificio.setVisible(false);
        verEdificio.vistaEdificio.btnEliminarImagenEdificio.setVisible(false);
        verEdificio.vistaEdificio.btnActualizarEdificio.setVisible(false);
        verEdificio.vistaEdificio.setVisible(true);
        break;
    }
  }

    /**
   * Edita la información de una propiedad depentiendo del tipo de bien inmueble al que
   * corresponde.
   * 
   * @param idPropiedadEditar Numero de finca de la propiedad que se desea editar
   * @throws SQLException En caso que no se encuentren los datos correspondientes.
   */
  public void editarPropiedad(String idPropiedadEditar) throws SQLException {
    int index = this.vistaAgente.tablaPropiedades.getSelectedRow();
    TableModel modelo = this.vistaAgente.tablaPropiedades.getModel();
    String tipo = modelo.getValueAt(index, 1).toString();
    switch (tipo) {
      case "Lote":
        Lote nuevoLote = new Lote();
        nuevoLote = nuevoLote.getLote(idPropiedadEditar);
        ControladorLote verLote = new ControladorLote(agente);
        verLote.verLote(nuevoLote);
        verLote.vistaLote.setVisible(true);
        break;
      case "Casa":
        Casa nuevaCasa = new Casa();
        nuevaCasa = nuevaCasa.getCasa(idPropiedadEditar);
        ControladorCasa verCasa = new ControladorCasa(agente);
        verCasa.verCasa(nuevaCasa);
        verCasa.vistaCasa.setVisible(true);
        break;
      case "Centro comercial":
        CentroComercial nuevoCentro = new CentroComercial();
        nuevoCentro = nuevoCentro.getCentroComercial(idPropiedadEditar);
        ControladorCentroComercial verCentro = new ControladorCentroComercial(agente);
        verCentro.verCentroComercial(nuevoCentro);
        verCentro.vistaCentro.setVisible(true);
        break;
      case "Edificio":
        Edificio nuevoEdificio = new Edificio();
        nuevoEdificio = nuevoEdificio.getEdificio(idPropiedadEditar);
        ControladorEdificio verEdificio = new ControladorEdificio(agente);
        verEdificio.verEdificio(nuevoEdificio);
        verEdificio.vistaEdificio.setVisible(true);
        break;
    }
  }
}
