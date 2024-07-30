/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simplesolutions.metrics.controllers;

import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import simplesolutions.conexion.Conexion;
import simplesolutions.cycletest.controllers.ControllerCycleTest;
import simplesolutions.metrics.models.ModelMetrics;


/**
 * @author JFS
 */
public class ControllerMetrics {
    public ArrayList<ModelMetrics> consultTotal() {
        ArrayList<ModelMetrics> listMetrics = new ArrayList();
        Conexion conectar = new Conexion();
        String sql = "SELECT m.id, m.ciclo_id, m.nombre_metrica, m.fecha_realizacion, "
                + "m.pruebas_aprobadas, m.pruebas_falladas, m.defectos_encontrados, "
                + "m.porcentaje_exito, m.tasa_defectos, cp.nombre_ciclo, v.version, a.nombre "
                + "FROM metricas m "
                + "JOIN ciclosdeprueba cp ON m.ciclo_id = cp.id "
                + "JOIN versiones v ON cp.version_id = v.id "
                + "JOIN aplicaciones a ON v.aplicacion_id = a.id "
                + "WHERE a.estado = 'A' "
                + "ORDER BY m.fecha_realizacion DESC";
        ResultSet rs;
        try {
            rs = conectar.consultar(sql);
            while(rs.next()){
                ModelMetrics metrics = new ModelMetrics();
                metrics.setId(rs.getInt("id"));
                ControllerCycleTest cycleTest = new ControllerCycleTest();
                metrics.setCycleTest(cycleTest.consultCycleTest(rs.getInt("ciclo_id")));
                metrics.setNameMetric(rs.getString("nombre_metrica"));
                metrics.setDate(rs.getString("fecha_realizacion"));
                metrics.setApprovedCases(rs.getInt("pruebas_aprobadas"));
                metrics.setFailedCases(rs.getInt("pruebas_falladas"));
                metrics.setDefectsFound(rs.getInt("defectos_encontrados"));
                metrics.setSuccessRate(rs.getDouble("porcentaje_exito"));
                metrics.setDefectRate(rs.getDouble("tasa_defectos"));
                
                listMetrics.add(metrics);
            }
        } catch (SQLException error) {
            System.out.println("Error en la consulta de metricas :"+error);
            JOptionPane.showMessageDialog(null,"Error en la consulta de metricas");
        }
        return listMetrics;
    }
        
    public ArrayList<ModelMetrics> consultByApplications(int id) {
        ArrayList<ModelMetrics> listMetrics = new ArrayList();
        Conexion conectar = new Conexion();        
        String sql = "SELECT m.id, m.ciclo_id, m.nombre_metrica, m.fecha_realizacion, "
                + "m.pruebas_aprobadas, m.pruebas_falladas, m.defectos_encontrados, "
                + "m.porcentaje_exito, m.tasa_defectos, cp.nombre_ciclo, v.version, a.nombre "
                + "FROM Aplicaciones a "
                + "JOIN versiones v ON a.id = v.aplicacion_id "
                + "JOIN ciclosdeprueba cp ON v.id = cp.version_id "
                + "JOIN metricas m ON cp.id = m.ciclo_id "                                
                + "WHERE a.id =" +id
                + " ORDER BY v.version";
        ResultSet rs;
        try {
            rs = conectar.consultar(sql);
            while(rs.next()){
                ModelMetrics metrics = new ModelMetrics();
                metrics.setId(rs.getInt("id"));
                ControllerCycleTest cycleTest = new ControllerCycleTest();
                metrics.setCycleTest(cycleTest.consultCycleTest(rs.getInt("ciclo_id")));
                metrics.setNameMetric(rs.getString("nombre_metrica"));
                metrics.setDate(rs.getString("fecha_realizacion"));
                metrics.setApprovedCases(rs.getInt("pruebas_aprobadas"));
                metrics.setFailedCases(rs.getInt("pruebas_falladas"));
                metrics.setDefectsFound(rs.getInt("defectos_encontrados"));
                metrics.setSuccessRate(rs.getDouble("porcentaje_exito"));
                metrics.setDefectRate(rs.getDouble("tasa_defectos"));
                
                listMetrics.add(metrics);
            }
        } catch (SQLException error) {
            System.out.println("Error en la consulta de metricas :"+error);
            JOptionPane.showMessageDialog(null,"Error en la consulta de metricas");
        }
        return listMetrics;
    }
    
    public ArrayList<ModelMetrics> consultByVersions(int id) {
        ArrayList<ModelMetrics> listMetrics = new ArrayList();
        Conexion conectar = new Conexion();        
        String sql = "SELECT m.id, m.ciclo_id, m.nombre_metrica, m.fecha_realizacion, "
                + "m.pruebas_aprobadas, m.pruebas_falladas, m.defectos_encontrados, "
                + "m.porcentaje_exito, m.tasa_defectos, c.nombre_ciclo, v.version "
                + "FROM metricas m "
                + "JOIN ciclosdeprueba c ON m.ciclo_id = c.id "
                + "JOIN versiones v ON c.version_id = v.id "                              
                + "WHERE v.id =" +id
                + " ORDER BY m.fecha_realizacion DESC";
        ResultSet rs;
        try {
            rs = conectar.consultar(sql);
            while(rs.next()){
                ModelMetrics metrics = new ModelMetrics();
                metrics.setId(rs.getInt("id"));
                ControllerCycleTest cycleTest = new ControllerCycleTest();
                metrics.setCycleTest(cycleTest.consultCycleTest(rs.getInt("ciclo_id")));
                metrics.setNameMetric(rs.getString("nombre_metrica"));
                metrics.setDate(rs.getString("fecha_realizacion"));
                metrics.setApprovedCases(rs.getInt("pruebas_aprobadas"));
                metrics.setFailedCases(rs.getInt("pruebas_falladas"));
                metrics.setDefectsFound(rs.getInt("defectos_encontrados"));
                metrics.setSuccessRate(rs.getDouble("porcentaje_exito"));
                metrics.setDefectRate(rs.getDouble("tasa_defectos"));
                
                listMetrics.add(metrics);
            }
        } catch (SQLException error) {
            System.out.println("Error en la consulta de metricas :"+error);
            JOptionPane.showMessageDialog(null,"Error en la consulta de metricas");
        }
        return listMetrics;
    }
    
    public ArrayList<ModelMetrics> consultByCycleTests(int id) {
        ArrayList<ModelMetrics> listMetrics = new ArrayList();
        Conexion conectar = new Conexion();        
        String sql = "SELECT m.id, m.ciclo_id, m.nombre_metrica, m.fecha_realizacion, "
                + "m.pruebas_aprobadas, m.pruebas_falladas, m.defectos_encontrados, "
                + "m.porcentaje_exito, m.tasa_defectos "
                + "FROM metricas m "
                + "JOIN ciclosdeprueba c ON m.ciclo_id = c.id "                           
                + "WHERE c.id =" +id
                + " ORDER BY m.fecha_realizacion DESC";
        ResultSet rs;
        try {
            rs = conectar.consultar(sql);
            while(rs.next()){
                ModelMetrics metrics = new ModelMetrics();
                metrics.setId(rs.getInt("id"));
                ControllerCycleTest cycleTest = new ControllerCycleTest();
                metrics.setCycleTest(cycleTest.consultCycleTest(rs.getInt("ciclo_id")));
                metrics.setNameMetric(rs.getString("nombre_metrica"));
                metrics.setDate(rs.getString("fecha_realizacion"));
                metrics.setApprovedCases(rs.getInt("pruebas_aprobadas"));
                metrics.setFailedCases(rs.getInt("pruebas_falladas"));
                metrics.setDefectsFound(rs.getInt("defectos_encontrados"));
                metrics.setSuccessRate(rs.getDouble("porcentaje_exito"));
                metrics.setDefectRate(rs.getDouble("tasa_defectos"));
                
                listMetrics.add(metrics);
            }
        } catch (SQLException error) {
            System.out.println("Error en la consulta de metricas :"+error);
            JOptionPane.showMessageDialog(null,"Error en la consulta de metricas");
        }
        return listMetrics;
    }
    
    public ArrayList<ModelMetrics> consult() {
        ArrayList<ModelMetrics> listMetrics = new ArrayList();
        Conexion conectar = new Conexion();
        String sql = "SELECT * FROM metricas m INNER JOIN ciclosdeprueba c ON m.ciclo_id = c.id ";
        ResultSet rs;
        try {
            rs = conectar.consultar(sql);
            while(rs.next()){
                ModelMetrics metrics = new ModelMetrics();
                metrics.setId(rs.getInt("id"));
                ControllerCycleTest cycleTest = new ControllerCycleTest();
                metrics.setCycleTest(cycleTest.consultCycleTest(rs.getInt("ciclo_id")));
                metrics.setNameMetric(rs.getString("nombre_metrica"));
                metrics.setDate(rs.getString("fecha_realizacion"));
                metrics.setApprovedCases(rs.getInt("pruebas_aprobadas"));
                metrics.setFailedCases(rs.getInt("pruebas_falladas"));
                metrics.setDefectsFound(rs.getInt("defectos_encontrados"));
                metrics.setSuccessRate(rs.getDouble("porcentaje_exito"));
                metrics.setDefectRate(rs.getDouble("tasa_defectos"));
                
                listMetrics.add(metrics);
            }
        } catch (SQLException error) {
            System.out.println("Error en la consulta de metricas :"+error);
            JOptionPane.showMessageDialog(null,"Error en la consulta de metricas");
        }
        return listMetrics;
    }
    
    public void save(ModelMetrics model){
        Conexion conectar = new Conexion();
        String sql = "INSERT INTO metricas(ciclo_id, nombre_metrica, fecha_realizacion, "
                + "pruebas_aprobadas, pruebas_falladas, defectos_encontrados) "
                + "VALUES ('"+model.getCycleTest().getId()+"','"+model.getNameMetric()
                + "','"+model.getDate()+"','"+model.getApprovedCases()
                + "','"+model.getFailedCases()+"','"+model.getDefectsFound()+"')";
        try {
            if(conectar.ejecutar(sql)){
                JOptionPane.showMessageDialog(null, "Sus datos fueron registrados satisfatoriamente", "Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "Error en el proceso de guardar los datos en metricas, consultar con el administrador.", "Error", JOptionPane.ERROR_MESSAGE);
            }        
        } catch (HeadlessException error) {
            System.out.println("Error en insertar datos en metricas : "+error);
        }
    }
    
    public void update(ModelMetrics model){
        Conexion conectar = new Conexion();
        String sql = "UPDATE metricas SET ciclo_id='"+model.getCycleTest().getId()
               +"', nombre_metrica='"+model.getNameMetric()
               +"', pruebas_aprobadas ='"+model.getApprovedCases()
               +"', pruebas_falladas ='"+model.getFailedCases()
               +"', defectos_encontrados ='"+model.getDefectsFound()
               +"' WHERE id ="+model.getId();
        try {            
            if(conectar.ejecutar(sql)){
                JOptionPane.showMessageDialog(null, "Su registro fue modificado satisfatoriamente", "Modificación exitosa", JOptionPane.INFORMATION_MESSAGE); 
            }else{
                JOptionPane.showMessageDialog(null, "Error en el proceso de modificación de datos en metricas, consultar con el administrador.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException error) {
            System.out.println("Error en modificar datos de metricas : "+error);
        }       
    }
    
    public void delete(ModelMetrics model){
        Conexion conectar = new Conexion();
        String sql = "DELETE FROM metricas WHERE id =" + model.getId();
        try {            
            if(conectar.ejecutar(sql)){
                JOptionPane.showMessageDialog(null, "Su registro fue eliminado satisfatoriamente", "Eliminación exitosa", JOptionPane.INFORMATION_MESSAGE); 
            }else{
                JOptionPane.showMessageDialog(null, "Error en el proceso de eliminación de datos en metricas, consultar con el administrador.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException error) {
            System.out.println("Error en eliminar datos en metricas : "+error);
        }       
    }
}
