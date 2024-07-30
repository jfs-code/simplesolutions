/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simplesolutions.applications.controllers;

import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import simplesolutions.applications.models.ModelApplications;
import simplesolutions.conexion.Conexion;

/**
 * @author JFS
 */
public class ControllerApplications {
    public ArrayList<ModelApplications> consult() {
        ArrayList<ModelApplications> listapplications = new ArrayList();
        Conexion conectar = new Conexion();
        String sql = "SELECT * FROM aplicaciones WHERE estado = 'A'";
        ResultSet rs;
        try {
            rs = conectar.consultar(sql);
            while(rs.next()){
                ModelApplications application = new ModelApplications();
                application.setId(rs.getInt("id"));
                application.setName(rs.getString("nombre"));
                application.setStatus(rs.getString("estado"));
                
                listapplications.add(application);
            }
        } catch (SQLException error) {
            System.out.println("Error en la consulta de aplicaciones :"+error);
            JOptionPane.showMessageDialog(null,"Error en la consulta de aplicaciones");
        }
        return listapplications;
    }
    
    public ArrayList<ModelApplications> consultOnlyVersioned() {
        ArrayList<ModelApplications> listapplications = new ArrayList();
        Conexion conectar = new Conexion();
        String sql = "SELECT a.id, a.nombre, a.estado FROM aplicaciones a INNER JOIN Versiones v ON a.id = v.aplicacion_id WHERE a.estado = 'A' GROUP BY a.id, a.nombre, a.estado";
        ResultSet rs;
        try {
            rs = conectar.consultar(sql);
            while(rs.next()){
                ModelApplications application = new ModelApplications();
                application.setId(rs.getInt("id"));
                application.setName(rs.getString("nombre"));
                application.setStatus(rs.getString("estado"));
                
                listapplications.add(application);
            }
        } catch (SQLException error) {
            System.out.println("Error en la consulta de aplicaciones :"+error);
            JOptionPane.showMessageDialog(null,"Error en la consulta de aplicaciones");
        }
        return listapplications;
    }
    
    public ModelApplications consultApplication(int id) {
        ModelApplications application = new ModelApplications();
        Conexion conectar = new Conexion();
        String sql = "SELECT * FROM aplicaciones WHERE id ="+id;
        ResultSet rs;
        try {
            rs = conectar.consultar(sql);
            if(rs.next()){
                application.setId(rs.getInt("id"));
                application.setName(rs.getString("nombre"));
                application.setStatus(rs.getString("estado"));
            }
        } catch (SQLException error) {
            System.out.println("Error en la consulta de aplicaciones :"+error);
            JOptionPane.showMessageDialog(null,"Error en la consulta de aplicaciones");
        }
        return application;
    }
    
    public void save(ModelApplications model){
        Conexion conectar = new Conexion();
        String sql = "INSERT INTO aplicaciones(nombre, estado) VALUES ('"+model.getName()+"','"+'A'+"')";
        try {
            if(conectar.ejecutar(sql)){
                JOptionPane.showMessageDialog(null, "Sus datos fueron registrados satisfatoriamente", "Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "Error en el proceso de guardar los datos en aplicaciones, consultar con el administrador.", "Error", JOptionPane.ERROR_MESSAGE);
            }        
        } catch (HeadlessException error) {
            System.out.println("Error en insertar datos en aplicaciones : "+error);
        }
    }
    
    public void update(ModelApplications model){
        Conexion conectar = new Conexion();
        String sql = "UPDATE aplicaciones SET nombre='"+model.getName()
               +"' WHERE id ="+model.getId();
        try {            
            if(conectar.ejecutar(sql)){
                JOptionPane.showMessageDialog(null, "Su registro fue modificado satisfatoriamente", "Modificación exitosa", JOptionPane.INFORMATION_MESSAGE); 
            }else{
                JOptionPane.showMessageDialog(null, "Error en el proceso de modificación de datos de aplicaciones, consultar con el administrador.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException error) {
            System.out.println("Error en modificar datos de aplicaciones : "+error);
        }       
    }
    
    public void delete(ModelApplications model){
        Conexion conectar = new Conexion();
        String sql = "UPDATE aplicaciones SET estado='I' WHERE id =" + model.getId();
        try {            
            if(conectar.ejecutar(sql)){
                JOptionPane.showMessageDialog(null, "Su registro fue eliminado satisfatoriamente", "Eliminación exitosa", JOptionPane.INFORMATION_MESSAGE); 
            }else{
                JOptionPane.showMessageDialog(null, "Error en el proceso de eliminación de datos de aplicaciones, consultar con el administrador.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException error) {
            System.out.println("Error en eliminar datos de aplicaciones : "+error);
        }       
    }
}
