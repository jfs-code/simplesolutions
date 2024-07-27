/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simplesolutions.versions.models;

import simplesolutions.applications.models.ModelApplications;

/**
 * @author JFS
 */
public class ModelVersions {
    private int id;
    private ModelApplications applications;
    private String version;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ModelApplications getApplications() {
        return applications;
    }

    public void setApplications(ModelApplications applications) {
        this.applications = applications;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ModelVersions() {
    }

    public ModelVersions(int id, ModelApplications applications, String version) {
        this.id = id;
        this.applications = applications;
        this.version = version;
    }
        
}
