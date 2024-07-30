/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package simplesolutions.views;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import simplesolutions.applications.models.ModelApplications;
import simplesolutions.cycletest.models.ModelCycleTest;
import simplesolutions.facade.FacadeReport;
import simplesolutions.metrics.models.ModelMetrics;
import simplesolutions.versions.models.ModelVersions;

/**
 * @author JFS
 */
public class ViewReport extends javax.swing.JDialog {

    private int selectedApplicationId = -1;
    private int selectedVersionId = -1;
    private int selectedCycleTestId = -1;
    private int idApplication = 0;
    private int idVersion = 0;
    
    FacadeReport facade = new FacadeReport();
    private ArrayList<ModelApplications> listApplications;
    private ArrayList<ModelVersions> listVersions;
    private ArrayList<ModelCycleTest> listCycleTests;
    private ArrayList<ModelMetrics> listMetrics;
    
    public ViewReport(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }
    
    private void init(){   
        cleanComboBoxVersion();
        cleanComboBoxCycleTest();
        facade.populateComboBoxApplications(this);     
        cleanFields();              
        
        btnClean.addActionListener(e -> clean());
        
        cbxApplication.addActionListener(e -> storeSelectedApplication());
        cbxVersions.addActionListener(e -> storeSelectedVersion());
        cbxCycleTests.addActionListener(e -> storeSelectedCycleTest());
        
        chbEnableVersion.addActionListener(e -> toggleComboBox(cbxVersions, chbEnableVersion.isSelected()));
        chbEnableCycleTest.addActionListener(e -> toggleComboBox(cbxCycleTests, chbEnableCycleTest.isSelected()));
        
        btnConsult.addActionListener(e -> executeQuery());
    }
    
    void clean(){
        cleanFields();
        cleanTable();
        cbxApplication.setSelectedIndex(0);
        cbxVersions.setSelectedIndex(0);
        cbxCycleTests.setSelectedIndex(0);
    }
    
    void cleanComboBoxVersion(){
        cbxVersions.removeAllItems();
        cbxVersions.addItem("Seleccione la versión");
    }
    
    void cleanComboBoxCycleTest(){
        cbxCycleTests.removeAllItems();
        cbxCycleTests.addItem("Seleccione el ciclo");
    }
    
    void cleanFields(){
        cbxApplication.setSelectedIndex(0);
        cleanComboBoxVersion();
        cleanComboBoxCycleTest();
        cbxVersions.setEnabled(false);
        cbxCycleTests.setEnabled(false);
        chbEnableVersion.setSelected(false);
        chbEnableCycleTest.setSelected(false);
    }
    
    void cleanTable(){
        for (int posicion = 0; posicion < 100; posicion++) {
            tblListMetrics.setValueAt("", posicion, 0);
            tblListMetrics.setValueAt("", posicion, 1);
            tblListMetrics.setValueAt("", posicion, 2);
            tblListMetrics.setValueAt("", posicion, 3);
            tblListMetrics.setValueAt("", posicion, 4);
            tblListMetrics.setValueAt("", posicion, 5);
            tblListMetrics.setValueAt("", posicion, 6);
            tblListMetrics.setValueAt("", posicion, 7);
            tblListMetrics.setValueAt("", posicion, 8);
            tblListMetrics.setValueAt("", posicion, 9);
            tblListMetrics.setValueAt("", posicion, 10);
        }
    }        
    
    private void toggleComboBox(javax.swing.JComboBox<String> comboBox, boolean enable) {
        comboBox.setEnabled(enable);
    }
    
    private void storeSelectedApplication() {
        int selectedIndex = cbxApplication.getSelectedIndex();
        if (selectedIndex > 0) {
            selectedApplicationId = listApplications.get(selectedIndex - 1).getId();
            facade.populateComboBoxVersions(this, selectedApplicationId);
        } else {
            selectedApplicationId = -1;
        }
    }
    
    private void executeQuery() {
        if (validSelection()) {
            if (selectedCycleTestId > 0) {
                facade.populateTableByCycleTest(this, selectedCycleTestId);
            } else if (selectedVersionId > 0) {
                facade.populateTableByVersion(this, selectedVersionId);
            } else if (selectedApplicationId > 0) {
                facade.populateTableByApplication(this, selectedApplicationId);
            }           
        }else {
            showErrorMessage();
        }
    }
    
    private boolean validSelection(){
        return selectedApplicationId != -1;
    }
    
    private void showErrorMessage(){
        String mensajeError = "El campo aplicativos debe estar seleccionado. No puede decir seleccione un aplicativo.";
        String tituloError = "Error";
        JOptionPane.showMessageDialog(this, mensajeError, tituloError, JOptionPane.ERROR_MESSAGE);
    }

    private void storeSelectedVersion() {
        int selectedIndex = cbxVersions.getSelectedIndex();
        if (selectedIndex > 0) {
            selectedVersionId = listVersions.get(selectedIndex - 1).getId();
            facade.populateComboBoxCycleTests(this, selectedVersionId);
        } else {
            selectedVersionId = -1;
        }
    }

    private void storeSelectedCycleTest() {
        int selectedIndex = cbxCycleTests.getSelectedIndex();
        if (selectedIndex > 0) {
            selectedCycleTestId = listCycleTests.get(selectedIndex - 1).getId();
        } else {
            selectedCycleTestId = -1;
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
    
    public void fillComboBoxCycleTest(ArrayList<ModelCycleTest> listCycleTests){        
        this.listCycleTests = listCycleTests;
        cbxCycleTests.removeAllItems();
        cbxCycleTests.addItem("Seleccione el ciclo");
        for (ModelCycleTest cicleTest : listCycleTests) {
            cbxCycleTests.addItem(cicleTest.getNameCycle());
        }    
    }
    
    public void fillTable(ArrayList<ModelMetrics> listMetrics){
        cleanTable();
        this.listMetrics = listMetrics;
        for (int posicion = 0; posicion < listMetrics.size(); posicion++) {
            tblListMetrics.setValueAt(posicion+1, posicion,0);
            tblListMetrics.setValueAt(listMetrics.get(posicion).getNameMetric(), posicion,1);
            tblListMetrics.setValueAt(listMetrics.get(posicion).getCycleTest().getVersions().getApplications().getName(), posicion,2);
            tblListMetrics.setValueAt(listMetrics.get(posicion).getCycleTest().getVersions().getVersion(), posicion,3);
            tblListMetrics.setValueAt(listMetrics.get(posicion).getCycleTest().getNameCycle(), posicion,4);
            tblListMetrics.setValueAt(listMetrics.get(posicion).getDate(), posicion,5);
            tblListMetrics.setValueAt(listMetrics.get(posicion).getApprovedCases(), posicion,6);
            tblListMetrics.setValueAt(listMetrics.get(posicion).getFailedCases(), posicion,7);
            tblListMetrics.setValueAt(listMetrics.get(posicion).getDefectsFound(), posicion,8);
            tblListMetrics.setValueAt(listMetrics.get(posicion).getSuccessRate(), posicion,9);
            tblListMetrics.setValueAt(listMetrics.get(posicion).getDefectRate(), posicion,10);
        }
    }    
    
    void getSelectedComboBoxApplications(){
        int selectedRow = cbxApplication.getSelectedIndex();
        if (selectedRow > 0) {
            idApplication = listApplications.get(selectedRow -1).getId();    
            facade.populateComboBoxVersions(this, idApplication);
            facade.populateTableByApplication(this, idApplication);
            
        }
    }
    
    void getSelectedComboBoxVersions(){
        int selectedRow = cbxVersions.getSelectedIndex();
        if (selectedRow > 0) {
            idVersion = listVersions.get(selectedRow -1).getId();    
            facade.populateComboBoxCycleTests(this, idVersion);
            facade.populateTableByVersion(this, idVersion);
        }
    }
    
    void getSelectedComboBoxCycleTest() {
    int selectedRow = cbxCycleTests.getSelectedIndex();
    if (selectedRow > 0) {
        int idCycleTest = listCycleTests.get(selectedRow - 1).getId();    
        facade.populateTableByCycleTest(this, idCycleTest);
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
        tblListMetrics = new javax.swing.JTable();
        pnlData = new javax.swing.JPanel();
        lblVersion = new javax.swing.JLabel();
        cbxVersions = new javax.swing.JComboBox<>();
        lblApplication = new javax.swing.JLabel();
        cbxApplication = new javax.swing.JComboBox<>();
        lblCycleTests = new javax.swing.JLabel();
        cbxCycleTests = new javax.swing.JComboBox<>();
        btnConsult = new javax.swing.JButton();
        btnClean = new javax.swing.JButton();
        chbEnableVersion = new javax.swing.JCheckBox();
        chbEnableCycleTest = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión Reportes");

        pnlTable.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Méticas realizadas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tblListMetrics.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "#", "Métrica", "Aplicativo", "Versión", "Ciclo de pruebas", "Fecha", "Aprobadas", "Falladas", "Defectos", "Porcentaje éxito", "Tasa defectos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblListMetrics);
        if (tblListMetrics.getColumnModel().getColumnCount() > 0) {
            tblListMetrics.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblListMetrics.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblListMetrics.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblListMetrics.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblListMetrics.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblListMetrics.getColumnModel().getColumn(5).setPreferredWidth(100);
            tblListMetrics.getColumnModel().getColumn(6).setPreferredWidth(80);
            tblListMetrics.getColumnModel().getColumn(7).setPreferredWidth(70);
            tblListMetrics.getColumnModel().getColumn(8).setPreferredWidth(80);
            tblListMetrics.getColumnModel().getColumn(9).setPreferredWidth(130);
            tblListMetrics.getColumnModel().getColumn(10).setPreferredWidth(100);
        }

        javax.swing.GroupLayout pnlTableLayout = new javax.swing.GroupLayout(pnlTable);
        pnlTable.setLayout(pnlTableLayout);
        pnlTableLayout.setHorizontalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlTableLayout.setVerticalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTableLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(288, 288, 288))
        );

        pnlData.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tipos de consultas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        lblVersion.setText("Versiones");

        cbxVersions.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblApplication.setText("Aplicativos");

        cbxApplication.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblCycleTests.setText("Ciclos de pruebas");

        cbxCycleTests.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnConsult.setText("Consultar");

        btnClean.setText("Limpiar");

        chbEnableVersion.setText("Habilita");

        chbEnableCycleTest.setText("Habilita");

        javax.swing.GroupLayout pnlDataLayout = new javax.swing.GroupLayout(pnlData);
        pnlData.setLayout(pnlDataLayout);
        pnlDataLayout.setHorizontalGroup(
            pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlDataLayout.createSequentialGroup()
                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblApplication)
                            .addComponent(cbxApplication, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(64, 64, 64)
                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlDataLayout.createSequentialGroup()
                                .addComponent(lblVersion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(chbEnableVersion))
                            .addComponent(cbxVersions, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlDataLayout.createSequentialGroup()
                                .addComponent(lblCycleTests)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(chbEnableCycleTest))
                            .addComponent(cbxCycleTests, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26))
                    .addGroup(pnlDataLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnConsult, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(87, 87, 87)
                        .addComponent(btnClean, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(281, 281, 281))))
        );
        pnlDataLayout.setVerticalGroup(
            pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDataLayout.createSequentialGroup()
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlDataLayout.createSequentialGroup()
                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCycleTests)
                            .addComponent(chbEnableCycleTest))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxCycleTests, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlDataLayout.createSequentialGroup()
                        .addComponent(lblApplication)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxApplication, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlDataLayout.createSequentialGroup()
                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblVersion)
                            .addComponent(chbEnableVersion))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxVersions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConsult)
                    .addComponent(btnClean))
                .addContainerGap(11, Short.MAX_VALUE))
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
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlTable, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

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
            java.util.logging.Logger.getLogger(ViewReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ViewReport dialog = new ViewReport(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnConsult;
    private javax.swing.JComboBox<String> cbxApplication;
    private javax.swing.JComboBox<String> cbxCycleTests;
    private javax.swing.JComboBox<String> cbxVersions;
    private javax.swing.JCheckBox chbEnableCycleTest;
    private javax.swing.JCheckBox chbEnableVersion;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblApplication;
    private javax.swing.JLabel lblCycleTests;
    private javax.swing.JLabel lblVersion;
    private javax.swing.JPanel pnlData;
    private javax.swing.JPanel pnlTable;
    private javax.swing.JTable tblListMetrics;
    // End of variables declaration//GEN-END:variables
}
