/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simplesolutions.metrics.facade;


import java.util.ArrayList;
import simplesolutions.applications.controllers.ControllerApplications;
import simplesolutions.applications.models.ModelApplications;
import simplesolutions.cycletest.controllers.ControllerCycleTest;
import simplesolutions.cycletest.models.ModelCycleTest;
import simplesolutions.cycletest.views.ViewCycleTest;
import simplesolutions.metrics.controllers.ControllerMetrics;
import simplesolutions.metrics.models.ModelMetrics;
import simplesolutions.metrics.views.ViewMetrics;
import simplesolutions.versions.controllers.ControllerVersions;
import simplesolutions.versions.models.ModelVersions;

/**
 * @author JFS
 */
public class FacadeMetrics {
    
    private final ControllerApplications controllerApplication;
    private final ControllerVersions controllerVersion;
    private final ControllerCycleTest controllerCycleTest;
    private final ControllerMetrics controller;

    public FacadeMetrics() {
        this.controller = new ControllerMetrics();
        this.controllerApplication = new ControllerApplications();
        this.controllerVersion = new ControllerVersions();
        this.controllerCycleTest = new ControllerCycleTest();
    }
    
    public void populateComboBoxApplications(ViewMetrics view) {
        ArrayList<ModelApplications> data = controllerApplication.consultOnlyVersioned();
        view.fillComboBoxApplications(data);
    }
    
    public void populateComboBoxVersions(ViewMetrics view, int id) {
        ArrayList<ModelVersions> data = controllerVersion.consultForId(id);
        view.fillComboBoxVersions(data);
    }
    
    public void populateComboBoxCycleTests(ViewMetrics view, int id) {
//        ArrayList<ModelVersions> data = controllerCycleTest.consultForId(id);
//        view.fillComboBoxVersions(data);
    }

    public void populateTable(ViewMetrics view) {
        ArrayList<ModelCycleTest> data = controller.consult();
        view.fillTable(data);
    }
    
    public void save(ModelMetrics model) {
        controller.save(model);
    }
    
    public void update(ModelMetrics model) {
        controller.update(model);
    }
    
    public void delete(ModelMetrics model) {
        controller.delete(model);
    }
}
