/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simplesolutions.facade;

import javax.swing.JFrame;
import simplesolutions.applications.views.ViewApplications;
import simplesolutions.cycletest.views.ViewCycleTest;
import simplesolutions.metrics.views.ViewMetrics;
import simplesolutions.versions.views.ViewVersions;
import simplesolutions.views.ViewReport;

/**
 * @author JFS
 */
public class FacadeMenu {
    
    private final JFrame parentFrame;

    public FacadeMenu(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }
    
    public void executeApplications(){
        ViewApplications va=new ViewApplications(parentFrame, true);
        va.setLocationRelativeTo(null);
        va.setVisible(true);
    }
    
    public void executeVersions(){
        ViewVersions vv=new ViewVersions(parentFrame, true);
        vv.setLocationRelativeTo(null);
        vv.setVisible(true);
    }
    
    public void executeMetrics(){
        ViewMetrics vm=new ViewMetrics(parentFrame, true);
        vm.setLocationRelativeTo(null);
        vm.setVisible(true);
    }
    
    public void executeCycleTest(){
        ViewCycleTest vct=new ViewCycleTest(parentFrame, true);
        vct.setLocationRelativeTo(null);
        vct.setVisible(true);
    }
    
    public void executeReport(){
        ViewReport vr=new ViewReport(parentFrame, true);
        vr.setLocationRelativeTo(null);
        vr.setVisible(true);
    }

}
