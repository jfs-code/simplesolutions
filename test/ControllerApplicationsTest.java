/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import simplesolutions.applications.controllers.ControllerApplications;
import simplesolutions.applications.models.ModelApplications;
import simplesolutions.conexion.Conexion;

/**
 * @author JFS
 */
public class ControllerApplicationsTest {
    
    private ControllerApplications controller;
    private Conexion mockConexion;
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() throws SQLException {
        controller = new ControllerApplications();
        mockConexion = Mockito.mock(Conexion.class);
        mockResultSet = Mockito.mock(ResultSet.class);

        try {
            var field = ControllerApplications.class.getDeclaredField("conectar");
            field.setAccessible(true);
            field.set(controller, mockConexion);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("No se pudo reemplazar el objeto Conexion en ControllerApplications.");
        }
    }

    @Test
    public void testConsult() throws SQLException {
        when(mockConexion.consultar(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("nombre")).thenReturn("App1");
        when(mockResultSet.getString("estado")).thenReturn("A");

        ArrayList<ModelApplications> result = controller.consult();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("App1", result.get(0).getName());
    }

    @Test
    public void testSave() {
        ModelApplications model = new ModelApplications();
        model.setName("New App");

        when(mockConexion.ejecutar(anyString())).thenReturn(true);

        controller.save(model);

        verify(mockConexion).ejecutar(contains("INSERT INTO aplicaciones(nombre, estado) VALUES ('New App','A')"));        
    }

    @Test
    public void testUpdate() {
        ModelApplications model = new ModelApplications();
        model.setId(1);
        model.setName("Updated App");

        when(mockConexion.ejecutar(anyString())).thenReturn(true);

        controller.update(model);

        verify(mockConexion).ejecutar(contains("UPDATE aplicaciones SET nombre='Updated App' WHERE id =1"));
        
    }

    @Test
    public void testDelete() {
        ModelApplications model = new ModelApplications();
        model.setId(1);

        when(mockConexion.ejecutar(anyString())).thenReturn(true);

        controller.delete(model);

        verify(mockConexion).ejecutar(contains("UPDATE aplicaciones SET estado='I' WHERE id =1"));
        
    }
}
