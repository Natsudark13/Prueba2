package com.poo.bieninmueble.controladores;

import com.poo.bieninmueble.logicaDeNegocios.Agente;
import com.poo.bieninmueble.logicaDeNegocios.IAgente;
import com.poo.bieninmueble.logicaDeNegocios.Cliente;
import com.poo.bieninmueble.logicaDeNegocios.ICliente;
import com.poo.bieninmueble.servicios.GeneradorCsv;
import com.poo.bieninmueble.vistas.VistaAdministrador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * La clase ControladorAdministrador se encarga de reaccionar a eventos de la vista administrador e 
 * invocar peticiones al modelo correspondiente para * completar satisfactoriamente la peticion del 
 * evento.
 */
public class ControladorAdministrador implements ActionListener {

  //atributos
  public VistaAdministrador vistaAdministrador;
  private ICliente cliente;
  private IAgente agente;

  /**
   * Constructor de la clase ControladorAdministrador.
   */
  public ControladorAdministrador() {
    this.vistaAdministrador = new VistaAdministrador();
    this.cliente = new Cliente();
    this.agente = new Agente();
    this.vistaAdministrador.btnDescargarClientes.addActionListener(this);
    this.vistaAdministrador.bntActualizarTablaAgentes.addActionListener(this);
    this.vistaAdministrador.btnRegistrarAgente.addActionListener(this);
    this.vistaAdministrador.btnActualizarClientes.addActionListener(this);
    try {
      ArrayList<Cliente> datos = cliente.getClientes();
      cargarDatosClientes(datos);
      ArrayList<Agente> datosAgente = agente.getAgentes();
      cargarDatosAgentes(datosAgente);
    } catch (SQLException ex) {
      Logger.getLogger(ControladorAdministrador.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Captura un evento dado en la vista del administrador e invoca el método del modelo 
   * correspondiente.
   *
   * @param e Nombre del evento
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Descargar .csv":
        descargarUsuariosCsv();
        break;
      case "Actualizar":
              try {
        ArrayList<Agente> datosAgentes = agente.getAgentes();
        cargarDatosAgentes(datosAgentes);
      } catch (SQLException ex) {
        Logger.getLogger(ControladorAdministrador.class.getName()).log(Level.SEVERE, null, ex);
      }
      break;
      case "Registrar":
        registrarAgente(this.vistaAdministrador.txtIdentificacion.getText(),
          this.vistaAdministrador.txtNombre.getText(),
          this.vistaAdministrador.txtApellido.getText(),
          this.vistaAdministrador.txtCorreo.getText(),
          this.vistaAdministrador.txtNumero.getText());
        break;
      case "Actualizar clientes":
              try {
        ArrayList<Cliente> datosClientes = cliente.getClientes();
        cargarDatosClientes(datosClientes);
      } catch (SQLException ex) {
        Logger.getLogger(ControladorAdministrador.class.getName()).log(Level.SEVERE, null, ex);
      }
      break;
    }
  }

  /**
   * Permite al usuario elegir el destino y descargar los clientes registrados
   * en un archivo formato CSV.
   */
  public void descargarUsuariosCsv() {
    try {
      GeneradorCsv registroCsv = new GeneradorCsv();
      JFileChooser f = new JFileChooser();
      f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      f.showSaveDialog(null);
      String ruta = f.getSelectedFile().toString();
      registroCsv.descargarUsuarios(ruta, cliente.getClientes());
      JOptionPane.showMessageDialog(null, "Los usuarios se descargaron de forma exitosa");
    } catch (SQLException ex) {
      Logger.getLogger(ControladorAdministrador.class.getName()).log(Level.SEVERE, null, ex);
      JOptionPane.showMessageDialog(null, "Error, los usuarios no se pudieron descargar");
    }
  }

  /**
   * Carga los datos recuperados de los clientes registrados en el sistema.
   *
   * @param pDatos lista con los datos utilizados para cargar la tabla de
   * clientes.
   */
  private void cargarDatosClientes(ArrayList<Cliente> pDatos) {
    if (pDatos.size() > 0) {
      DefaultTableModel modelo = (DefaultTableModel) vistaAdministrador.tablaClientes.getModel();
      modelo.setRowCount(0);
      vistaAdministrador.tablaClientes.getColumnModel().getColumn(4).setPreferredWidth(200);
      for (int i = 0; i < pDatos.size(); i++) {
        Vector v = new Vector();
        v.add(pDatos.get(i).getIdentificacion());
        v.add(pDatos.get(i).getNombre());
        v.add(pDatos.get(i).getApellido());
        v.add(pDatos.get(i).getNumeroTelefono());
        v.add(pDatos.get(i).getCorreo());
        modelo.addRow(v);
        vistaAdministrador.tablaClientes.setModel(modelo);
      }
    } else {
      JOptionPane.showMessageDialog(null, "No existen datos asociados");
    }

  }

  /**
   * Carga los datos recuperados de los agentes registrados en el sistema.
   *
   * @param pDatos lista con los datos utilizados para cargar la tabla de
   * agentes.
   */
  private void cargarDatosAgentes(ArrayList<Agente> pDatos) {
    if (pDatos.size() > 0) {
      DefaultTableModel modelo = (DefaultTableModel) vistaAdministrador.tablaAgentes.getModel();
      modelo.setRowCount(0);
      vistaAdministrador.tablaAgentes.getColumnModel().getColumn(4).setPreferredWidth(200);
      for (int i = 0; i < pDatos.size(); i++) {
        Vector v = new Vector();
        v.add(pDatos.get(i).getIdentificacion());
        v.add(pDatos.get(i).getNombre());
        v.add(pDatos.get(i).getApellido());
        v.add(pDatos.get(i).getNumeroTelefono());
        v.add(pDatos.get(i).getCorreo());
        v.add(pDatos.get(i).getCantPropiedades());
        modelo.addRow(v);
        vistaAdministrador.tablaAgentes.setModel(modelo);
      }
    } else {
      JOptionPane.showMessageDialog(null, "No existen datos asociados");
    }
  }

  /**
   * Registra un nuevo agente en el sistema.
   *
   * @param pIdentificacion Número de identificación del nuevo agente.
   * @param pNombre Nombre del nuevo agente.
   * @param pApellido Apellido del nuevo agente.
   * @param pCorreo Correo electronico del nuevo agente.
   * @param pNumeroTelefono Numero de telefono del nuevo agente.
   */
  public void registrarAgente(String pIdentificacion, String pNombre,
    String pApellido, String pCorreo, String pNumeroTelefono) {
    if (pIdentificacion.isBlank() || pNombre.isBlank() || pApellido.isBlank() || pCorreo.isBlank() 
      || pNumeroTelefono.isBlank()) {
      JOptionPane.showMessageDialog(null, "Complete todos los datos solicitados");
    } else {
      Agente agente = new Agente(pIdentificacion, pNombre, pApellido, pCorreo, pNumeroTelefono);
      try {
        System.out.println(agente.toString());
        agente.insertarAgente(agente);
        JOptionPane.showMessageDialog(null, "Agente registrado");
      } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error registrando el agente");
      }
    }
  }
}
