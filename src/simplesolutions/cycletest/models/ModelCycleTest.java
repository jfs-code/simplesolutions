/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simplesolutions.cycletest.models;

import simplesolutions.versions.models.ModelVersions;

/**
 * @author JFS
 */
public class ModelCycleTest {
    private int id;
    private ModelVersions versions;
    private String nameCycle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ModelVersions getVersions() {
        return versions;
    }

    public void setVersions(ModelVersions versions) {
        this.versions = versions;
    }

    public String getNameCycle() {
        return nameCycle;
    }

    public void setNameCycle(String nameCycle) {
        this.nameCycle = nameCycle;
    }

    public ModelCycleTest() {
    }

    public ModelCycleTest(int id, ModelVersions versions, String nameCycle) {
        this.id = id;
        this.versions = versions;
        this.nameCycle = nameCycle;
    }
    
}
