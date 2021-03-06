/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poo.bieninmueble.vistas;

/**
 *
 * @author anjel
 */
public class VistaCredenciales extends javax.swing.JFrame {

    /**
     * Creates new form VistaCredenciales
     */
    public VistaCredenciales() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    btnIniciar = new javax.swing.JButton();
    txtUsuario = new javax.swing.JTextField();
    jLabel3 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();
    btnSalir = new javax.swing.JLabel();
    btnSalirPrincipal = new javax.swing.JButton();
    jLabel5 = new javax.swing.JLabel();
    comboRol = new javax.swing.JComboBox<>();
    txtContrasenna = new javax.swing.JPasswordField();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel1.setText("Usuario:");
    getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, -1, 20));

    jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel2.setText("Contraseña:");
    getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, -1, -1));

    btnIniciar.setText("Iniciar sesión");
    getContentPane().add(btnIniciar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 190, -1, -1));
    getContentPane().add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, 150, -1));

    jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel3.setText("Bienvenido/a");
    getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 400, -1));
    getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 0, 10, 240));
    getContentPane().add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 390, 10));

    btnSalirPrincipal.setText("Salir");
    getContentPane().add(btnSalirPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, 90, -1));

    jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel5.setText("Rol:");
    getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, -1, -1));

    comboRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Agente", "Cliente" }));
    getContentPane().add(comboRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, 150, -1));
    getContentPane().add(txtContrasenna, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 150, -1));

    pack();
  }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaCredenciales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaCredenciales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaCredenciales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaCredenciales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaCredenciales().setVisible(true);
            }
        });
    }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  public javax.swing.JButton btnIniciar;
  public javax.swing.JLabel btnSalir;
  public javax.swing.JButton btnSalirPrincipal;
  public javax.swing.JComboBox<String> comboRol;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  public javax.swing.JPasswordField txtContrasenna;
  public javax.swing.JTextField txtUsuario;
  // End of variables declaration//GEN-END:variables
}
