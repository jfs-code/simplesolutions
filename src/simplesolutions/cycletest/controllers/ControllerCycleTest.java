/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simplesolutions.cycletest.controllers;

import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import simplesolutions.conexion.Conexion;
import simplesolutions.cycletest.models.ModelCycleTest;
import simplesolutions.versions.controllers.ControllerVersions;

/**
 * @author JFS
 */
public class ControllerCycleTest {
    public ArrayList<ModelCycleTest> consult() {
        ArrayList<ModelCycleTest> listCyclesTest = new ArrayList();
        Conexion conectar = new Conexion();
        String sql = "SELECT v.id, v.version, v.aplicacion_id FROM versiones v INNER JOIN Aplicaciones a ON v.aplicacion_id = a.id WHERE a.estado = 'A'";
        ResultSet rs;
        try {
            rs = conectar.consultar(sql);
            while(rs.next()){
                ModelCycleTest cycleTest = new ModelCycleTest();
                cycleTest.setId(rs.getInt("id"));
                ControllerVersions version = new ControllerVersions();
                cycleTest.setVersions(version.consultVersion(rs.getInt("version_id")));
                cycleTest.setNameCycle(rs.getString("nombre_ciclo"));
                
                listCyclesTest.add(cycleTest);
            }
        } catch (SQLException error) {
            System.out.println("Error en la consulta de aplicaciones :"+error);
            JOptionPane.showMessageDialog(null,"Error en la consulta de aplicaciones");
        }
        return listCyclesTest;
    }
    
//    public void save(ModelCycleTest model){
//        Conexion conectar = new Conexion();
//        String sql = "INSERT INTO ciclosdeprueba(aplicacion_id, version) VALUES ('"+model.getApplications().getId()+"','"+model.getVersion()+"')";
//        try {
//            if(conectar.ejecutar(sql)){
//                JOptionPane.showMessageDialog(null, "Sus datos fueron registrados satisfatoriamente", "Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
//            }else{
//                JOptionPane.showMessageDialog(null, "Error en el proceso de guardar los datos en versiones, consultar con el administrador.", "Error", JOptionPane.ERROR_MESSAGE);
//            }        
//        } catch (HeadlessException error) {
//            System.out.println("Error en insertar datos en versiones : "+error);
//        }
//    }
//    
//    public void update(ModelCycleTest model){
//        Conexion conectar = new Conexion();
//        String sql = "UPDATE ciclosdeprueba SET version='"+model.getVersion()
//               +"', aplicacion_id ='"+model.getApplications().getId()
//               +"' WHERE id ="+model.getId();
//        try {            
//            if(conectar.ejecutar(sql)){
//                JOptionPane.showMessageDialog(null, "Su registro fue modificado satisfatoriamente", "Modificación exitosa", JOptionPane.INFORMATION_MESSAGE); 
//            }else{
//                JOptionPane.showMessageDialog(null, "Error en el proceso de modificación de datos en versiones, consultar con el administrador.", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        } catch (HeadlessException error) {
//            System.out.println("Error en modificar datos de versiones : "+error);
//        }       
//    }
    
    public void delete(ModelCycleTest model){
        Conexion conectar = new Conexion();
        String sql = "DELETE FROM ciclosdeprueba WHERE id =" + model.getId();
        try {            
            if(conectar.ejecutar(sql)){
                JOptionPane.showMessageDialog(null, "Su registro fue eliminado satisfatoriamente", "Eliminación exitosa", JOptionPane.INFORMATION_MESSAGE); 
            }else{
                JOptionPane.showMessageDialog(null, "Error en el proceso de eliminación de datos en ciclos de prueba, consultar con el administrador.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException error) {
            System.out.println("Error en eliminar datos en ciclos de prueba : "+error);
        }       
    }
}
