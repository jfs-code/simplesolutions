/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simplesolutions.metrics.models;

import java.util.Date;
import simplesolutions.cycletest.models.ModelCycleTest;

/**
 * @author JFS
 */
public class ModelMetrics {
    private int id;
    private ModelCycleTest cycleTest;
    private String nameMetric;
    private String date;
    private int approvedCases;
    private int failedCases;
    private int defectsFound;
    private double successRate;
    private double defectRate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ModelCycleTest getCycleTest() {
        return cycleTest;
    }

    public void setCycleTest(ModelCycleTest cycleTest) {
        this.cycleTest = cycleTest;
    }

    public String getNameMetric() {
        return nameMetric;
    }

    public void setNameMetric(String nameMetric) {
        this.nameMetric = nameMetric;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getApprovedCases() {
        return approvedCases;
    }

    public void setApprovedCases(int approvedCases) {
        this.approvedCases = approvedCases;
    }

    public int getFailedCases() {
        return failedCases;
    }

    public void setFailedCases(int failedCases) {
        this.failedCases = failedCases;
    }

    public int getDefectsFound() {
        return defectsFound;
    }

    public void setDefectsFound(int defectsFound) {
        this.defectsFound = defectsFound;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }

    public double getDefectRate() {
        return defectRate;
    }

    public void setDefectRate(double defectRate) {
        this.defectRate = defectRate;
    }

    public ModelMetrics() {
    }

    public ModelMetrics(int id, ModelCycleTest cycleTest, String nameMetric, String date, int approvedCases, int failedCases, int defectsFound, double successRate, double defectRate) {
        this.id = id;
        this.cycleTest = cycleTest;
        this.nameMetric = nameMetric;
        this.date = date;
        this.approvedCases = approvedCases;
        this.failedCases = failedCases;
        this.defectsFound = defectsFound;
        this.successRate = successRate;
        this.defectRate = defectRate;
    }
    
}
