/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poo.bieninmueble.vistas;

import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anjel
 */
public class VistaLote extends javax.swing.JFrame {

  /**
   * Creates new form VistaLote
   */
  public VistaLote() {
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

    jLabel9 = new javax.swing.JLabel();
    txtIdLote = new javax.swing.JTextField();
    jLabel10 = new javax.swing.JLabel();
    comboModalidadLote = new javax.swing.JComboBox<>();
    jLabel11 = new javax.swing.JLabel();
    txtAreaLote = new javax.swing.JTextField();
    jLabel13 = new javax.swing.JLabel();
    txtMetroLote = new javax.swing.JTextField();
    jLabel14 = new javax.swing.JLabel();
    txtFiscalLote = new javax.swing.JTextField();
    jLabel15 = new javax.swing.JLabel();
    txtProvinciaLote = new javax.swing.JComboBox<>();
    jLabel16 = new javax.swing.JLabel();
    txtCantonLote = new javax.swing.JTextField();
    jLabel17 = new javax.swing.JLabel();
    txtDistritoLote = new javax.swing.JTextField();
    jLabel12 = new javax.swing.JLabel();
    txtDireccionLote = new javax.swing.JTextField();
    jLabel5 = new javax.swing.JLabel();
    comboEstadoLote = new javax.swing.JComboBox<>();
    jLabel78 = new javax.swing.JLabel();
    jScrollPane2 = new javax.swing.JScrollPane();
    tablaImagenesLote = new javax.swing.JTable();
    labelImagenLote = new javax.swing.JLabel();
    btnBuscarImagenLote = new javax.swing.JButton();
    btnEliminarImagenLote = new javax.swing.JButton();
    btnActualizarLote = new javax.swing.JButton();
    jButton1 = new javax.swing.JButton();
    jLabel1 = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jLabel9.setBackground(new java.awt.Color(204, 204, 204));
    jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel9.setText("N??mero de finca:");
    jLabel9.setOpaque(true);
    getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 130, 20));

    txtIdLote.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        txtIdLoteActionPerformed(evt);
      }
    });
    getContentPane().add(txtIdLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 200, -1));

    jLabel10.setBackground(new java.awt.Color(204, 204, 204));
    jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel10.setText("Modalidad:");
    jLabel10.setOpaque(true);
    getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 130, 20));

    comboModalidadLote.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Venta", "Alquiler" }));
    getContentPane().add(comboModalidadLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 200, -1));

    jLabel11.setBackground(new java.awt.Color(204, 204, 204));
    jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel11.setText("??rea del terreno:");
    jLabel11.setOpaque(true);
    getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 130, 20));
    getContentPane().add(txtAreaLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 200, -1));

    jLabel13.setBackground(new java.awt.Color(204, 204, 204));
    jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel13.setText("Valor metro cuadrado:");
    jLabel13.setOpaque(true);
    getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 130, 20));
    getContentPane().add(txtMetroLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, 200, -1));

    jLabel14.setBackground(new java.awt.Color(204, 204, 204));
    jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel14.setText("Valor fiscal:");
    jLabel14.setOpaque(true);
    getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 130, 20));
    getContentPane().add(txtFiscalLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 200, -1));

    jLabel15.setBackground(new java.awt.Color(204, 204, 204));
    jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel15.setText("Provincia:");
    jLabel15.setOpaque(true);
    getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 130, 20));

    txtProvinciaLote.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "San Jos??", "Alajuela", "Cartago", "Heredia", "Guanacaste", "Lim??n", "Puntarenas" }));
    getContentPane().add(txtProvinciaLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, 200, -1));

    jLabel16.setBackground(new java.awt.Color(204, 204, 204));
    jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel16.setText("Cant??n:");
    jLabel16.setOpaque(true);
    getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 130, 20));
    getContentPane().add(txtCantonLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 270, 200, -1));

    jLabel17.setBackground(new java.awt.Color(204, 204, 204));
    jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel17.setText("Distrito:");
    jLabel17.setOpaque(true);
    getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 130, 20));
    getContentPane().add(txtDistritoLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 320, 200, -1));

    jLabel12.setBackground(new java.awt.Color(204, 204, 204));
    jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel12.setText("Direcci??n exacta:");
    jLabel12.setOpaque(true);
    getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 130, 20));
    getContentPane().add(txtDireccionLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 360, 200, -1));

    jLabel5.setBackground(new java.awt.Color(204, 204, 204));
    jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel5.setText("Estado:");
    jLabel5.setOpaque(true);
    getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 130, 20));

    comboEstadoLote.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));
    getContentPane().add(comboEstadoLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 400, 200, -1));

    jLabel78.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jLabel78.setText("Im??genes:");
    getContentPane().add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 30, 320, 30));

    tablaImagenesLote.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {

      },
      new String [] {
        "Imagenes"
      }
    ));
    tablaImagenesLote.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        tablaImagenesLoteMouseClicked(evt);
      }
    });
    jScrollPane2.setViewportView(tablaImagenesLote);

    getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, 270, 100));

    labelImagenLote.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    getContentPane().add(labelImagenLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 60, 230, 130));

    btnBuscarImagenLote.setText("Buscar imagen");
    btnBuscarImagenLote.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnBuscarImagenLoteActionPerformed(evt);
      }
    });
    getContentPane().add(btnBuscarImagenLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 170, 120, -1));

    btnEliminarImagenLote.setText("Eliminar Imagen");
    btnEliminarImagenLote.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnEliminarImagenLoteActionPerformed(evt);
      }
    });
    getContentPane().add(btnEliminarImagenLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 170, 140, -1));

    btnActualizarLote.setText("Actualizar");
    btnActualizarLote.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnActualizarLoteActionPerformed(evt);
      }
    });
    getContentPane().add(btnActualizarLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 390, 120, 40));

    jButton1.setForeground(new java.awt.Color(255, 0, 51));
    jButton1.setText("X");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });
    getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 0, 50, -1));
    getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 450));

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void txtIdLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdLoteActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_txtIdLoteActionPerformed

  private void tablaImagenesLoteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaImagenesLoteMouseClicked
    int fila = tablaImagenesLote.getSelectedRow();
    TableModel modelo = tablaImagenesLote.getModel();
    byte[] archivoImagen = (byte[]) modelo.getValueAt(fila, 0);

    ImageIcon imagenIcon = new ImageIcon(new ImageIcon(archivoImagen).getImage().getScaledInstance(labelImagenLote.getWidth(), labelImagenLote.getHeight()
      , Image.SCALE_SMOOTH));
  labelImagenLote.setIcon(imagenIcon);
  }//GEN-LAST:event_tablaImagenesLoteMouseClicked

  private void btnBuscarImagenLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarImagenLoteActionPerformed
    byte[] imagenLote;
    String archivoImagen;
    JFileChooser chooser = new JFileChooser();
    chooser.showOpenDialog(null);

    File archivo = chooser.getSelectedFile();
    archivoImagen = archivo.getAbsolutePath();

    File imagen = new File(archivoImagen);
    FileInputStream fis;
    
    try {
      fis = new FileInputStream(imagen);

      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      byte[] buffer = new byte[1024];

      for (int j; (j = fis.read(buffer)) != -1;) {
        bos.write(buffer, 0, j);
      }
      imagenLote = bos.toByteArray();
      DefaultTableModel modelo = (DefaultTableModel) tablaImagenesLote.getModel();
      modelo.addRow(new Object[]{imagenLote});
    } catch (IOException ex) {
      Logger.getLogger(VistaLote.class.getName()).log(Level.SEVERE, null, ex);
    }


  }//GEN-LAST:event_btnBuscarImagenLoteActionPerformed

  private void btnEliminarImagenLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarImagenLoteActionPerformed
    DefaultTableModel modelo = (DefaultTableModel) tablaImagenesLote.getModel();
    int fila = tablaImagenesLote.getSelectedRow();
    modelo.removeRow(fila);
    labelImagenLote.setIcon(null);
    labelImagenLote.revalidate();
  }//GEN-LAST:event_btnEliminarImagenLoteActionPerformed

  private void btnActualizarLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarLoteActionPerformed

  }//GEN-LAST:event_btnActualizarLoteActionPerformed

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    this.setVisible(false);
  }//GEN-LAST:event_jButton1ActionPerformed

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
      java.util.logging.Logger.getLogger(VistaLote.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(VistaLote.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(VistaLote.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(VistaLote.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new VistaLote().setVisible(true);
      }
    });
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  public javax.swing.JButton btnActualizarLote;
  public javax.swing.JButton btnBuscarImagenLote;
  public javax.swing.JButton btnEliminarImagenLote;
  public javax.swing.JComboBox<String> comboEstadoLote;
  public javax.swing.JComboBox<String> comboModalidadLote;
  private javax.swing.JButton jButton1;
  private javax.swing.JLabel jLabel1;
  public javax.swing.JLabel jLabel10;
  public javax.swing.JLabel jLabel11;
  public javax.swing.JLabel jLabel12;
  public javax.swing.JLabel jLabel13;
  public javax.swing.JLabel jLabel14;
  public javax.swing.JLabel jLabel15;
  public javax.swing.JLabel jLabel16;
  public javax.swing.JLabel jLabel17;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel78;
  public javax.swing.JLabel jLabel9;
  public javax.swing.JScrollPane jScrollPane2;
  public javax.swing.JLabel labelImagenLote;
  public javax.swing.JTable tablaImagenesLote;
  public javax.swing.JTextField txtAreaLote;
  public javax.swing.JTextField txtCantonLote;
  public javax.swing.JTextField txtDireccionLote;
  public javax.swing.JTextField txtDistritoLote;
  public javax.swing.JTextField txtFiscalLote;
  public javax.swing.JTextField txtIdLote;
  public javax.swing.JTextField txtMetroLote;
  public javax.swing.JComboBox<String> txtProvinciaLote;
  // End of variables declaration//GEN-END:variables
}
