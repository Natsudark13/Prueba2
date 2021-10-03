package com.poo.bieninmueble.controladores;

import com.poo.bieninmueble.logicaDeNegocios.Agente;
import com.poo.bieninmueble.logicaDeNegocios.Cliente;
import com.poo.bieninmueble.servicios.GestorXML;
import com.poo.bieninmueble.servicios.Email;
import com.poo.bieninmueble.vistas.VistaCredenciales;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Marco Gómez, Mauricio Loría, Anjelica Tristani.
 *
 * La clase ControladorPrincipal se encarga de reaccionar a eventos de la vista principal e invocar 
 * peticiones al modelo correspondiente para completar satisfactoriamente la peticion del evento.
 */
public class ControladorPrincipal implements ActionListener{
  
  //atributos
  public VistaCredenciales vistaPrincipal;
  private GestorXML validadorCredenciales;
  private Email correo;

  /**
   * Constructor de la clase ControladorPrincipal.
   * @param vista Vista principal de inicio de sesión
   */
  public ControladorPrincipal(VistaCredenciales vista){
      vistaPrincipal = vista;
      this.vistaPrincipal.btnSalirPrincipal.addActionListener(this);
      this.vistaPrincipal.btnIniciar.addActionListener(this);
      this.validadorCredenciales = new GestorXML();
      this.correo = Email.getInstance();
  }

  /**
   * Captura un evento dado en la vista principal e invoca el método del modelo correspondiente.
   *
   * @param e Nombre del evento
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Iniciar sesión":
      {
        try {
          iniciarSesion();
        } catch (SQLException ex) {
          Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
        break;

      case "Salir":
        cerrarSesion();
        break;
      default:
      break;
    }
  }

  /**
   * Verifica las credenciales del usuario.
   */
  public void iniciarSesion() throws SQLException{
    String usuario = this.vistaPrincipal.txtUsuario.getText();
    System.out.println(usuario);
    String contrasenna = this.vistaPrincipal.txtContrasenna.getText();
    System.out.println(contrasenna);
    String rol = this.vistaPrincipal.comboRol.getSelectedItem().toString();
    if (validadorCredenciales.validarCredencial(usuario, contrasenna, rol) == true){
      System.out.println(rol);
      switch (rol) {
        case "Agente":
          Agente agente = new Agente(usuario);
          ControladorAgente controladorAgente = new ControladorAgente(agente);
          controladorAgente.vistaAgente.setVisible(true);
          this.vistaPrincipal.setVisible(false);
          break;
        case "Administrador":
          ControladorAdministrador controladorAdmin = new ControladorAdministrador();
          controladorAdmin.vistaAdministrador.setVisible(true);
          this.vistaPrincipal.setVisible(false);
          break;
        case "Cliente":
          Cliente cliente = new Cliente();
          cliente = cliente.getCliente(usuario);
          ControladorCliente controladorCliente = new ControladorCliente(cliente);
          controladorCliente.vistaCliente.setVisible(true);
          this.vistaPrincipal.setVisible(false);
          break;
        default:
          JOptionPane.showMessageDialog(null, "Ocurrió un problema");
          break;
      }
    } else {
      JOptionPane.showMessageDialog(null, "Error al validar credenciales");
    }
  }

  /**
   * Cierra la ventana principal.
   */
  public void cerrarSesion(){
    this.vistaPrincipal.setVisible(false);
    System.exit(0);

  }

  /**
   * Método principal para correr la aplicación
   * @param args Argumento 
   */
  public static void main(String[] args) {
    VistaCredenciales vistaPrincipal = new VistaCredenciales();
    ControladorPrincipal controladorPrincipal = new ControladorPrincipal(vistaPrincipal);
    controladorPrincipal.vistaPrincipal.setVisible(true);
  }
}
