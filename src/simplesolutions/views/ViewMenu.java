package simplesolutions.views;

import simplesolutions.facade.FacadeMenu;

/**
 * @author JFS
 */
public final class ViewMenu extends javax.swing.JFrame {

    /**
     * Creates new form VwMenu
     */
    public ViewMenu() {
        initComponents();
        init();
    }
    
    FacadeMenu facadeMenu = new FacadeMenu(this);
    
    void init(){
        this.setExtendedState(MAXIMIZED_BOTH);
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblimage = new javax.swing.JLabel();
        mnbmenu = new javax.swing.JMenuBar();
        mngestionfacturacion = new javax.swing.JMenu();
        mntApplications = new javax.swing.JMenuItem();
        mntVersions = new javax.swing.JMenuItem();
        mntMetrics = new javax.swing.JMenuItem();
        mntCycleTest = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("QA Insight");

        lblimage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/simplesolutions/images/logo-simplesolutions-1030x376.png"))); // NOI18N

        mngestionfacturacion.setText("QA Management");
        mngestionfacturacion.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        mntApplications.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mntApplications.setText("Aplicaciones");
        mntApplications.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mntApplicationsActionPerformed(evt);
            }
        });
        mngestionfacturacion.add(mntApplications);

        mntVersions.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mntVersions.setText("Versiones");
        mntVersions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mntVersionsActionPerformed(evt);
            }
        });
        mngestionfacturacion.add(mntVersions);

        mntMetrics.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mntMetrics.setText("Métricas");
        mntMetrics.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mntMetricsActionPerformed(evt);
            }
        });
        mngestionfacturacion.add(mntMetrics);

        mntCycleTest.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mntCycleTest.setText("Ciclo de Pruebas");
        mntCycleTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mntCycleTestActionPerformed(evt);
            }
        });
        mngestionfacturacion.add(mntCycleTest);

        mnbmenu.add(mngestionfacturacion);

        setJMenuBar(mnbmenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblimage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblimage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mntApplicationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mntApplicationsActionPerformed
        // TODO add your handling code here:
        facadeMenu.executeApplications();
    }//GEN-LAST:event_mntApplicationsActionPerformed

    private void mntVersionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mntVersionsActionPerformed
        // TODO add your handling code here:
        facadeMenu.executeVersions();
    }//GEN-LAST:event_mntVersionsActionPerformed

    private void mntMetricsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mntMetricsActionPerformed
        // TODO add your handling code here:
        facadeMenu.executeMetrics();
    }//GEN-LAST:event_mntMetricsActionPerformed

    private void mntCycleTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mntCycleTestActionPerformed
        // TODO add your handling code here:
        facadeMenu.executeCycleTest();
    }//GEN-LAST:event_mntCycleTestActionPerformed

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
            java.util.logging.Logger.getLogger(ViewMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblimage;
    private javax.swing.JMenuBar mnbmenu;
    private javax.swing.JMenu mngestionfacturacion;
    private javax.swing.JMenuItem mntApplications;
    private javax.swing.JMenuItem mntCycleTest;
    private javax.swing.JMenuItem mntMetrics;
    private javax.swing.JMenuItem mntVersions;
    // End of variables declaration//GEN-END:variables
}