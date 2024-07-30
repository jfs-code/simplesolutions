/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simplesolutions.facade;

import java.util.ArrayList;
import simplesolutions.applications.controllers.ControllerApplications;
import simplesolutions.applications.models.ModelApplications;
import simplesolutions.cycletest.controllers.ControllerCycleTest;
import simplesolutions.cycletest.models.ModelCycleTest;
import simplesolutions.metrics.controllers.ControllerMetrics;
import simplesolutions.metrics.models.ModelMetrics;
import simplesolutions.versions.controllers.ControllerVersions;
import simplesolutions.versions.models.ModelVersions;
import simplesolutions.views.ViewReport;

/**
 * @author JFS
 */
public class FacadeReport {
    
    private final ControllerApplications controllerApplication;
    private final ControllerVersions controllerVersion;
    private final ControllerCycleTest controllerCycleTest;
    private final ControllerMetrics controller;

    public FacadeReport() {
        this.controller = new ControllerMetrics();
        this.controllerApplication = new ControllerApplications();
        this.controllerVersion = new ControllerVersions();
        this.controllerCycleTest = new ControllerCycleTest();
    }
    
    public void populateComboBoxApplications(ViewReport view) {
        ArrayList<ModelApplications> data = controllerApplication.consultOnlyVersioned();
        view.fillComboBoxApplications(data);
    }
    
    public void populateComboBoxVersions(ViewReport view, int id) {
        ArrayList<ModelVersions> data = controllerVersion.consultForId(id);
        view.fillComboBoxVersions(data);
    }
    
    public void populateComboBoxCycleTests(ViewReport view, int id) {
        ArrayList<ModelCycleTest> data = controllerCycleTest.consultForId(id);
        view.fillComboBoxCycleTest(data);
    }
    
    public void populateTableByApplication(ViewReport view, int id) {
        ArrayList<ModelMetrics> data = controller.consultByApplications(id);
        view.fillTable(data);
    }
    
    public void populateTableByCycleTest(ViewReport view, int id) {
        ArrayList<ModelMetrics> data = controller.consultByCycleTests(id);
        view.fillTable(data);
    }
    
    public void populateTableByVersion(ViewReport view, int id) {
        ArrayList<ModelMetrics> data = controller.consultByVersions(id);
        view.fillTable(data);
    }

}
