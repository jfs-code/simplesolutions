/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simplesolutions.versions.controllers;

import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import simplesolutions.applications.controllers.ControllerApplications;
import simplesolutions.conexion.Conexion;
import simplesolutions.versions.models.ModelVersions;

/**
 * @author JFS
 */
public class ControllerVersions {
    public ArrayList<ModelVersions> consult() {
        ArrayList<ModelVersions> listversions = new ArrayList();
        Conexion conectar = new Conexion();
        String sql = "SELECT v.id, v.version, v.aplicacion_id FROM versiones v INNER JOIN Aplicaciones a ON v.aplicacion_id = a.id WHERE a.estado = 'A'";
        ResultSet rs;
        try {
            rs = conectar.consultar(sql);
            while(rs.next()){
                ModelVersions version = new ModelVersions();
                version.setId(rs.getInt("id"));
                ControllerApplications application = new ControllerApplications();
                version.setApplications(application.consultApplication(rs.getInt("aplicacion_id")));
                version.setVersion(rs.getString("version"));
                
                listversions.add(version);
            }
        } catch (SQLException error) {
            System.out.println("Error en la consulta de aplicaciones :"+error);
            JOptionPane.showMessageDialog(null,"Error en la consulta de aplicaciones");
        }
        return listversions;
    }
    
    public void save(ModelVersions model){
        Conexion conectar = new Conexion();
        String sql = "INSERT INTO versiones(aplicacion_id, version) VALUES ('"+model.getApplications().getId()+"','"+model.getVersion()+"')";
        try {
            if(conectar.ejecutar(sql)){
                JOptionPane.showMessageDialog(null, "Sus datos fueron registrados satisfatoriamente", "Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "Error en el proceso de guardar los datos en versiones, consultar con el administrador.", "Error", JOptionPane.ERROR_MESSAGE);
            }        
        } catch (HeadlessException error) {
            System.out.println("Error en insertar datos en versiones : "+error);
        }
    }
    
    public void update(ModelVersions model){
        Conexion conectar = new Conexion();
        String sql = "UPDATE versiones SET version='"+model.getVersion()
               +"', aplicacion_id ='"+model.getApplications().getId()
               +"' WHERE id ="+model.getId();
        try {            
            if(conectar.ejecutar(sql)){
                JOptionPane.showMessageDialog(null, "Su registro fue modificado satisfatoriamente", "Modificación exitosa", JOptionPane.INFORMATION_MESSAGE); 
            }else{
                JOptionPane.showMessageDialog(null, "Error en el proceso de modificación de datos en versiones, consultar con el administrador.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException error) {
            System.out.println("Error en modificar datos de versiones : "+error);
        }       
    }
    
    public void delete(ModelVersions model){
        Conexion conectar = new Conexion();
        String sql = "DELETE FROM WHERE id =" + model.getId();
        try {            
            if(conectar.ejecutar(sql)){
                JOptionPane.showMessageDialog(null, "Su registro fue eliminado satisfatoriamente", "Modificación exitosa", JOptionPane.INFORMATION_MESSAGE); 
            }else{
                JOptionPane.showMessageDialog(null, "Error en el proceso de eliminación de datos en versiones, consultar con el administrador.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException error) {
            System.out.println("Error en eliminar datos en versiones : "+error);
        }       
    }
}
