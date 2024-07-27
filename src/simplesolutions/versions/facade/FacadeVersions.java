/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simplesolutions.versions.facade;

import java.util.ArrayList;
import simplesolutions.applications.controllers.ControllerApplications;
import simplesolutions.applications.models.ModelApplications;
import simplesolutions.versions.controllers.ControllerVersions;
import simplesolutions.versions.models.ModelVersions;
import simplesolutions.versions.views.ViewVersions;

/**
 * @author JFS
 */
public class FacadeVersions {
    
    private final ControllerVersions controller;
    private final ControllerApplications controllerApplication;

    public FacadeVersions() {
        this.controller = new ControllerVersions();
        this.controllerApplication = new ControllerApplications();
    }
    
    public void populateComboBox(ViewVersions view) {
        ArrayList<ModelApplications> data = controllerApplication.consult();
        view.fillComboBox(data);
    }

    public void populateTable(ViewVersions view) {
        ArrayList<ModelVersions> data = controller.consult();
        view.fillTable(data);
    }
    
    public void save(ModelVersions model) {
        controller.save(model);
    }
    
    public void update(ModelVersions model) {
        controller.update(model);
    }
    
    public void delete(ModelVersions model) {
        controller.delete(model);
    }
}
