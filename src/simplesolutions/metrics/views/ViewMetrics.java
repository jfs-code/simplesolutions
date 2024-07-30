/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package simplesolutions.metrics.views;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import simplesolutions.applications.models.ModelApplications;
import simplesolutions.controllers.ControllerUtilitario;
import simplesolutions.controllers.ControllerValidaCaracteres;
import simplesolutions.cycletest.models.ModelCycleTest;
import simplesolutions.metrics.facade.FacadeMetrics;
import simplesolutions.metrics.models.ModelMetrics;
import simplesolutions.versions.models.ModelVersions;

/**
 *
 * @author Sc0rp10n
 */
public class ViewMetrics extends javax.swing.JDialog {

    private boolean isEditing = false;
    private int editingRow = -1;
    private int editingId = -1;
    private int idApplication = 0;
    private int idVersion = 0;
    
    FacadeMetrics facade = new FacadeMetrics();
    private ArrayList<ModelApplications> listApplications;
    private ArrayList<ModelVersions> listVersions;
    private ArrayList<ModelCycleTest> listCycleTests;
    private ArrayList<ModelMetrics> listMetrics;
    
    public ViewMetrics(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }
    
    private void init(){   
        cleanComboBoxVersion();
        cleanComboBoxCycleTest();
        facade.populateComboBoxApplications(this);        
        facade.populateTable(this);
        inactivateButtond();
        cleanFields();
        
        btnSave.addActionListener(e -> save());
        btnDelete.addActionListener(e -> delete());
        btnClean.addActionListener(e -> clean());
        
        cbxApplication.addActionListener(e -> getSelectedComboBoxApplications());
        cbxVersions.addActionListener(e -> getSelectedComboBoxVersions());
        
        tblListMetrics.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
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
        txtNameMetric.setText("");
        txtApprovedCases.setText("");
        txtDefectsFound.setText("");
        txtFailedCases.setText("");
        cbxApplication.setSelectedIndex(0);
        cleanComboBoxVersion();
        cleanComboBoxCycleTest();
        inactivateButtond();
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
        }
    }
    
    private boolean areValidFields(String metric, String approvedCases, String failedCases, String defectsFound){
        return !metric.isEmpty() && !approvedCases.isEmpty() && !failedCases.isEmpty() && !defectsFound.isEmpty();
    }
    
    private void showErrorMessage(){
        String mensajeError = "Los campos nombre de la métrica, casos aprobados, casos fallados, defectos encontrados. No pueden estar vacíos.";
        String tituloError = "Error";
        JOptionPane.showMessageDialog(this, mensajeError, tituloError, JOptionPane.ERROR_MESSAGE);
    }
    
    ModelMetrics dataForSave() {        
        ModelMetrics model = new ModelMetrics();        
        int cycleTest = cbxCycleTests.getSelectedIndex();
        String metric = txtNameMetric.getText();
        String approvedCases = txtApprovedCases.getText();
        String failedCases = txtFailedCases.getText();
        String defectsFound = txtDefectsFound.getText();
        if (areValidFields(metric, approvedCases, failedCases, defectsFound)) {
            model.setNameMetric(metric);
            ModelCycleTest selectedCycleTest = listCycleTests.get(cycleTest - 1);            
            model.setCycleTest(selectedCycleTest);
            model.setApprovedCases(Integer.parseInt(approvedCases));
            model.setFailedCases(Integer.parseInt(failedCases));                        
            model.setDefectsFound(Integer.parseInt(defectsFound));
            ControllerUtilitario utilitarioDate = new ControllerUtilitario();
            model.setDate(utilitarioDate.fechaHoy());            
        }else {
            showErrorMessage();
        }
        return model;
    }
    
    void save() {
        int application = cbxApplication.getSelectedIndex();
        int version = cbxVersions.getSelectedIndex();
        int cycleTest = cbxCycleTests.getSelectedIndex();
                        
        if (application != 0 && version != 0 && cycleTest != 0) {
            ModelMetrics model = dataForSave();   

            if (isEditing && editingRow != -1) {
                model.setId(editingId);
                facade.update(model);   
                facade.populateTable(this);
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
            JOptionPane.showMessageDialog(this, "Debe seleccionar un aplicativo, la versión ó el ciclo de pruebas. Debe tener un elemento seleccionado cada uno.", "Error", JOptionPane.ERROR_MESSAGE);
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
        for (int posicion = 0; posicion < listMetrics.size(); posicion++) {
            if(posicion == row){
                id = listMetrics.get(posicion).getId();
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
    
    int returnIndexCycleTest(String name) {
        int numero = 0;
        for (int posicion = 0; posicion < listCycleTests.size(); posicion++) {
            if(name.equalsIgnoreCase(listCycleTests.get(posicion).getNameCycle())){
                numero = posicion+1;
            }
        }
        return numero;
    }
    
    void getSelectedTable(){
        int selectedRow = tblListMetrics.getSelectedRow();
        if (selectedRow >= 0) {
            cbxApplication.setSelectedIndex(returnIndexApplication((String) tblListMetrics.getValueAt(selectedRow, 2)));
            cbxVersions.setSelectedIndex(returnIndexVersion((String) tblListMetrics.getValueAt(selectedRow, 3)));
            cbxCycleTests.setSelectedIndex(returnIndexCycleTest((String) tblListMetrics.getValueAt(selectedRow, 4)));
                        
            txtNameMetric.setText(tblListMetrics.getValueAt(selectedRow, 1).toString());
            txtApprovedCases.setText(tblListMetrics.getValueAt(selectedRow, 6).toString());
            txtFailedCases.setText(tblListMetrics.getValueAt(selectedRow, 7).toString());
            txtDefectsFound.setText(tblListMetrics.getValueAt(selectedRow, 8).toString());
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
    
    void getSelectedComboBoxVersions(){
        int selectedRow = cbxVersions.getSelectedIndex();
        if (selectedRow > 0) {
            idVersion = listVersions.get(selectedRow -1).getId();    
            facade.populateComboBoxCycleTests(this, idVersion);
        }
    }
    
    void delete(){
        String name = txtNameMetric.getText();
        if (!name.isEmpty()) {
            ModelMetrics model = new ModelMetrics();

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
    
    void validatesNumbers(KeyEvent evt) {
        ControllerValidaCaracteres vc = new ControllerValidaCaracteres();
        vc.validaNumeros(evt);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlButtons = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClean = new javax.swing.JButton();
        pnlTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblListMetrics = new javax.swing.JTable();
        pnlData = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        txtNameMetric = new javax.swing.JTextField();
        lblVersion = new javax.swing.JLabel();
        cbxVersions = new javax.swing.JComboBox<>();
        lblApplication = new javax.swing.JLabel();
        cbxApplication = new javax.swing.JComboBox<>();
        lblCycleTests = new javax.swing.JLabel();
        cbxCycleTests = new javax.swing.JComboBox<>();
        txtApprovedCases = new javax.swing.JTextField();
        txtFailedCases = new javax.swing.JTextField();
        txtDefectsFound = new javax.swing.JTextField();
        lblApprovedCases = new javax.swing.JLabel();
        lblFailedCases = new javax.swing.JLabel();
        lblDefectsFound = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Getión de métricas");

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

        pnlTable.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Méticas realizadas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tblListMetrics.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "#", "Métrica", "Aplicativo", "Versión", "Ciclo de pruebas", "Fecha", "Aprobadas", "Falladas", "Defectos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
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
            tblListMetrics.getColumnModel().getColumn(5).setPreferredWidth(120);
            tblListMetrics.getColumnModel().getColumn(6).setPreferredWidth(80);
            tblListMetrics.getColumnModel().getColumn(7).setPreferredWidth(80);
            tblListMetrics.getColumnModel().getColumn(8).setPreferredWidth(80);
        }

        javax.swing.GroupLayout pnlTableLayout = new javax.swing.GroupLayout(pnlTable);
        pnlTable.setLayout(pnlTableLayout);
        pnlTableLayout.setHorizontalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1010, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlTableLayout.setVerticalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTableLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(288, 288, 288))
        );

        pnlData.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nuevo métrica", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        lblName.setText("Nombre de la métrica");

        txtNameMetric.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNameMetricMouseClicked(evt);
            }
        });

        lblVersion.setText("Versiones");

        cbxVersions.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblApplication.setText("Aplicativos");

        cbxApplication.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblCycleTests.setText("Ciclos de pruebas");

        cbxCycleTests.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtApprovedCases.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApprovedCasesKeyTyped(evt);
            }
        });

        txtFailedCases.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFailedCasesKeyTyped(evt);
            }
        });

        txtDefectsFound.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDefectsFoundKeyTyped(evt);
            }
        });

        lblApprovedCases.setText("Casos aprobados");

        lblFailedCases.setText("Casos fallados");

        lblDefectsFound.setText("Defectos encontrados");

        javax.swing.GroupLayout pnlDataLayout = new javax.swing.GroupLayout(pnlData);
        pnlData.setLayout(pnlDataLayout);
        pnlDataLayout.setHorizontalGroup(
            pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDataLayout.createSequentialGroup()
                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblApplication)
                            .addComponent(cbxApplication, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(64, 64, 64)
                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblVersion)
                            .addComponent(cbxVersions, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCycleTests)
                            .addComponent(cbxCycleTests, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26))
                    .addGroup(pnlDataLayout.createSequentialGroup()
                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNameMetric, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblName))
                        .addGap(18, 18, 18)
                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblApprovedCases)
                            .addComponent(txtApprovedCases, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFailedCases)
                            .addComponent(txtFailedCases, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDefectsFound)
                            .addComponent(txtDefectsFound, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        pnlDataLayout.setVerticalGroup(
            pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDataLayout.createSequentialGroup()
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlDataLayout.createSequentialGroup()
                        .addComponent(lblCycleTests)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxCycleTests, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlDataLayout.createSequentialGroup()
                        .addComponent(lblApplication)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxApplication, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlDataLayout.createSequentialGroup()
                        .addComponent(lblVersion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxVersions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(lblApprovedCases)
                    .addComponent(lblFailedCases)
                    .addComponent(lblDefectsFound))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNameMetric, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApprovedCases, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFailedCases, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDefectsFound, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void txtNameMetricMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNameMetricMouseClicked
        // TODO add your handling code here:
        activateButtonSave();
    }//GEN-LAST:event_txtNameMetricMouseClicked

    private void txtApprovedCasesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApprovedCasesKeyTyped
        // TODO add your handling code here:
        validatesNumbers(evt);
    }//GEN-LAST:event_txtApprovedCasesKeyTyped

    private void txtFailedCasesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFailedCasesKeyTyped
        // TODO add your handling code here:
        validatesNumbers(evt);
    }//GEN-LAST:event_txtFailedCasesKeyTyped

    private void txtDefectsFoundKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDefectsFoundKeyTyped
        // TODO add your handling code here:
        validatesNumbers(evt);
    }//GEN-LAST:event_txtDefectsFoundKeyTyped

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
            java.util.logging.Logger.getLogger(ViewMetrics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewMetrics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewMetrics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewMetrics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ViewMetrics dialog = new ViewMetrics(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> cbxCycleTests;
    private javax.swing.JComboBox<String> cbxVersions;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblApplication;
    private javax.swing.JLabel lblApprovedCases;
    private javax.swing.JLabel lblCycleTests;
    private javax.swing.JLabel lblDefectsFound;
    private javax.swing.JLabel lblFailedCases;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblVersion;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlData;
    private javax.swing.JPanel pnlTable;
    private javax.swing.JTable tblListMetrics;
    private javax.swing.JTextField txtApprovedCases;
    private javax.swing.JTextField txtDefectsFound;
    private javax.swing.JTextField txtFailedCases;
    private javax.swing.JTextField txtNameMetric;
    // End of variables declaration//GEN-END:variables
}
