/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simplesolutions.cycletest.facade;

import java.util.ArrayList;
import simplesolutions.cycletest.controllers.ControllerCycleTest;
import simplesolutions.cycletest.models.ModelCycleTest;
import simplesolutions.cycletest.views.ViewCycleTest;
import simplesolutions.versions.controllers.ControllerVersions;
import simplesolutions.versions.models.ModelVersions;

/**
 * @author JFS
 */
public class FacadeCycleTest {
    
    private final ControllerVersions controllerVersion;
    private final ControllerCycleTest controller;

    public FacadeCycleTest() {
        this.controller = new ControllerCycleTest();
        this.controllerVersion = new ControllerVersions();
    }
    
    public void populateComboBox(ViewCycleTest view) {
        ArrayList<ModelVersions> data = controllerVersion.consult();
//        view.fillComboBox(data);
    }

    public void populateTable(ViewCycleTest view) {
        ArrayList<ModelCycleTest> data = controller.consult();
//        view.fillTable(data);
    }
    
    public void save(ModelCycleTest model) {
//        controller.save(model);
    }
    
    public void update(ModelCycleTest model) {
//        controller.update(model);
    }
    
    public void delete(ModelCycleTest model) {
        controller.delete(model);
    }
}
