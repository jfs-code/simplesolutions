/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simplesolutions.applications.facade;

import java.util.ArrayList;
import simplesolutions.applications.controllers.ControllerApplications;
import simplesolutions.applications.models.ModelApplications;
import simplesolutions.applications.views.ViewApplications;

/**
 * @author JFS
 */
public class FacadeApplications {
    
    private final ControllerApplications controller;

    public FacadeApplications() {
        this.controller = new ControllerApplications();
    }

    public void populateTable(ViewApplications view) {
        ArrayList<ModelApplications> data = controller.consult();
        view.fillTable(data);
    }
    
    public void save(ModelApplications model) {
        controller.save(model);
    }
    
    public void update(ModelApplications model) {
        controller.update(model);
    }
    
    public void delete(ModelApplications model) {
        controller.delete(model);
    }
}
