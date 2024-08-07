/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package simplesolutions.cycletest.views;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import simplesolutions.applications.models.ModelApplications;
import simplesolutions.cycletest.facade.FacadeCycleTest;
import simplesolutions.cycletest.models.ModelCycleTest;
import simplesolutions.versions.models.ModelVersions;

/**
 * @author JFS
 */
public class ViewCycleTest extends javax.swing.JDialog {

    private boolean isEditing = false;
    private int editingRow = -1;
    private int editingId = -1;
    private int idApplication = 0;
    
    FacadeCycleTest facade = new FacadeCycleTest();
    private ArrayList<ModelApplications> listApplications;
    private ArrayList<ModelVersions> listVersions;
    private ArrayList<ModelCycleTest> listCycleTests;
    
    public ViewCycleTest(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }
    
    private void init(){   
        cleanComboBoxVersion();
        facade.populateComboBoxApplications(this);        
        facade.populateTable(this);
        inactivateButtond();
        cleanFields();
        
        btnSave.addActionListener(e -> save());
        btnDelete.addActionListener(e -> delete());
        btnClean.addActionListener(e -> clean());
        
        cbxApplication.addActionListener(e -> getSelectedComboBoxApplications());
        
        tblListCycles.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    getSelectedTable();
                }
            }
        });
    }
    
    void activateButtonSave(){
        btnSave.setEnabled(true);
    }
    
    void inactivateButtond(){
        btnDelete.setEnabled(false);
    }
    
    void activateButtond(){
        btnDelete.setEnabled(true);
    }
    
    void clean(){
        cleanFields();
        inactivateButtond();
        btnSave.setText("Guardar");
        cbxApplication.setSelectedIndex(0);
        cbxVersions.setSelectedIndex(0);
    }
    
    void cleanComboBoxVersion(){
        cbxVersions.removeAllItems();
        cbxVersions.addItem("Seleccione la versión");
    }
    
    void cleanFields(){
        txtCycle.setText("");
        cbxApplication.setSelectedIndex(0);
        cleanComboBoxVersion();
        inactivateButtond();
    }
    
    void cleanTable(){
        for (int posicion = 0; posicion < 100; posicion++) {
            tblListCycles.setValueAt("", posicion, 0);
            tblListCycles.setValueAt("", posicion, 1);
            tblListCycles.setValueAt("", posicion, 2);
            tblListCycles.setValueAt("", posicion, 3);
        }
    }        
    
    public void fillComboBoxApplications(ArrayList<ModelApplications> listApplications){
        this.listApplications = listApplications;
        cbxApplication.removeAllItems();
        cbxApplication.addItem("Seleccione el aplicativo");
        for (ModelApplications app : listApplications) {
            cbxApplication.addItem(app.getName());
        }  
    }
    
    public void fillComboBoxVersions(ArrayList<ModelVersions> listVersions){        
        this.listVersions = listVersions;
        cbxVersions.removeAllItems();
        cbxVersions.addItem("Seleccione la versión");
        for (ModelVersions version : listVersions) {
            cbxVersions.addItem(version.getVersion());
        }    
    }
    
    public void fillTable(ArrayList<ModelCycleTest> listCycleTests){
        cleanTable();
        this.listCycleTests = listCycleTests;
        for (int posicion = 0; posicion < listCycleTests.size(); posicion++) {
            tblListCycles.setValueAt(posicion+1, posicion,0);
            tblListCycles.setValueAt(listCycleTests.get(posicion).getNameCycle(), posicion,1);
            tblListCycles.setValueAt(listCycleTests.get(posicion).getVersions().getApplications().getName(), posicion,2);
            tblListCycles.setValueAt(listCycleTests.get(posicion).getVersions().getVersion(), posicion,3);
        }
    }
    
    void save() {
        String application = cbxApplication.getSelectedItem().toString();
        int version = cbxVersions.getSelectedIndex();
        String cycleTest = txtCycle.getText();
                        
        if (!cycleTest.isEmpty() && version != 0) {
            ModelCycleTest model = new ModelCycleTest();            
            model.setNameCycle(cycleTest);
            ModelVersions selectedVersion = listVersions.get(version - 1);
            model.setVersions(selectedVersion);

            if (isEditing && editingRow != -1) {
                model.setId(editingId);
                facade.update(model);
                tblListCycles.setValueAt(cycleTest, editingRow, 1); 
                tblListCycles.setValueAt(application, editingRow, 2);              
            } else {
                facade.save(model);
                facade.populateTable(this);
            }

            cleanFields();
            isEditing = false;
            editingRow = -1;
            editingId = -1;
            btnSave.setText("Guardar");
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un aplicativo ó la versión no puede estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    int returnIndexApplication(String name) {
        int numero = 0;
        for (int posicion = 0; posicion < listApplications.size(); posicion++) {
            if(name.equalsIgnoreCase(listApplications.get(posicion).getName())){
                numero = posicion+1;
            }
        }
        return numero;
    }
    
    int returnId(int row) {
        int id = 0;
        for (int posicion = 0; posicion < listCycleTests.size(); posicion++) {
            if(posicion == row){
                id = listCycleTests.get(posicion).getId();
            }
        }
        return id;
    }
    
    int returnIndexVersion(String name) {
        int numero = 0;
        for (int posicion = 0; posicion < listVersions.size(); posicion++) {
            if(name.equalsIgnoreCase(listVersions.get(posicion).getVersion())){
                numero = posicion+1;
            }
        }
        return numero;
    }
    
    void getSelectedTable(){
        int selectedRow = tblListCycles.getSelectedRow();
        if (selectedRow >= 0) {
            String cycletest = (String) tblListCycles.getValueAt(selectedRow, 1);
            String application = (String) tblListCycles.getValueAt(selectedRow, 2);
            String version = (String) tblListCycles.getValueAt(selectedRow, 3);
            txtCycle.setText(cycletest);
            cbxApplication.setSelectedIndex(returnIndexApplication(application));
            cbxVersions.setSelectedIndex(returnIndexVersion(version));
            editingId = returnId(selectedRow);

            isEditing = true;
            editingRow = selectedRow;
            btnSave.setText("Modificar");
            activateButtond();
        }
    }
    
    void getSelectedComboBoxApplications(){
        int selectedRow = cbxApplication.getSelectedIndex();
        if (selectedRow > 0) {
            idApplication = listApplications.get(selectedRow -1).getId();    
            facade.populateComboBoxVersions(this, idApplication);
        }
    }
    
    void delete(){
        String name = txtCycle.getText();
        if (!name.isEmpty()) {
            ModelCycleTest model = new ModelCycleTest();

            if (editingRow != -1) {
                model.setId(editingId);
                facade.delete(model);                
                facade.populateTable(this);              
            } 

            cleanFields();
            editingRow = -1;
            editingId = -1;
            btnSave.setText("Guardar");
            inactivateButtond();
        } else {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblListCycles = new javax.swing.JTable();
        pnlData = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        txtCycle = new javax.swing.JTextField();
        lblVersion = new javax.swing.JLabel();
        cbxVersions = new javax.swing.JComboBox<>();
        lblApplication = new javax.swing.JLabel();
        cbxApplication = new javax.swing.JComboBox<>();
        pnlButtons = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClean = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión ciclos de prueba ");

        pnlTable.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ciclos de prueba", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tblListCycles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "#", "Ciclo de Prueba", "Aplicativo", "Versión"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblListCycles);
        if (tblListCycles.getColumnModel().getColumnCount() > 0) {
            tblListCycles.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblListCycles.getColumnModel().getColumn(1).setPreferredWidth(255);
            tblListCycles.getColumnModel().getColumn(2).setPreferredWidth(255);
            tblListCycles.getColumnModel().getColumn(3).setPreferredWidth(72);
        }

        javax.swing.GroupLayout pnlTableLayout = new javax.swing.GroupLayout(pnlTable);
        pnlTable.setLayout(pnlTableLayout);
        pnlTableLayout.setHorizontalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        pnlTableLayout.setVerticalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTableLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(288, 288, 288))
        );

        pnlData.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nuevo ciclo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        lblName.setText("Nombre del ciclo de pruebas");

        txtCycle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCycleMouseClicked(evt);
            }
        });

        lblVersion.setText("Versiones");

        cbxVersions.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblApplication.setText("Aplicativos");

        cbxApplication.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnlDataLayout = new javax.swing.GroupLayout(pnlData);
        pnlData.setLayout(pnlDataLayout);
        pnlDataLayout.setHorizontalGroup(
            pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCycle, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblName)
                    .addComponent(lblApplication)
                    .addComponent(cbxApplication, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblVersion)
                    .addComponent(cbxVersions, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        pnlDataLayout.setVerticalGroup(
            pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDataLayout.createSequentialGroup()
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblVersion)
                    .addGroup(pnlDataLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblApplication)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxVersions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxApplication, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCycle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnSave.setText("Guardar");

        btnDelete.setText("Eliminar");

        btnClean.setText("Limpiar");

        javax.swing.GroupLayout pnlButtonsLayout = new javax.swing.GroupLayout(pnlButtons);
        pnlButtons.setLayout(pnlButtonsLayout);
        pnlButtonsLayout.setHorizontalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlButtonsLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSave, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                    .addComponent(btnClean, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        pnlButtonsLayout.setVerticalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlButtonsLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(btnSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClean)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                        .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlTable, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCycleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCycleMouseClicked
        // TODO add your handling code here:
        activateButtonSave();
    }//GEN-LAST:event_txtCycleMouseClicked

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
            java.util.logging.Logger.getLogger(ViewCycleTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewCycleTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewCycleTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewCycleTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ViewCycleTest dialog = new ViewCycleTest(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClean;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cbxApplication;
    private javax.swing.JComboBox<String> cbxVersions;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblApplication;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblVersion;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlData;
    private javax.swing.JPanel pnlTable;
    private javax.swing.JTable tblListCycles;
    private javax.swing.JTextField txtCycle;
    // End of variables declaration//GEN-END:variables
}
