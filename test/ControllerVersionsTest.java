/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

import java.sql.ResultSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;
import java.util.ArrayList;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import simplesolutions.applications.controllers.ControllerApplications;
import simplesolutions.applications.models.ModelApplications;
import simplesolutions.conexion.Conexion;
import simplesolutions.versions.controllers.ControllerVersions;
import simplesolutions.versions.models.ModelVersions;

/**
 * @author JFS
 */
public class ControllerVersionsTest {

    private ControllerVersions controller;
    private Conexion mockConexion;
    private ResultSet mockResultSet;
    private ControllerApplications mockControllerApplications;

    @BeforeEach
    public void setUp() {
        controller = new ControllerVersions();
        mockConexion = mock(Conexion.class);
        mockResultSet = mock(ResultSet.class);
        mockControllerApplications = mock(ControllerApplications.class);

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
        when(mockResultSet.next()).thenReturn(true, false); 
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("version")).thenReturn("1.0");
        when(mockResultSet.getInt("aplicacion_id")).thenReturn(1);

        when(mockControllerApplications.consultApplication(anyInt())).thenReturn(new ModelApplications());

        ArrayList<ModelVersions> result = controller.consult();
        assertNotNull(result, "El resultado no debería ser nulo.");
        assertEquals(1, result.size(), "Debería haber un elemento en el resultado.");
        assertEquals("1.0", result.get(0).getVersion(), "La versión debería ser 1.0.");
    }

    @Test
    public void testConsultForId() throws SQLException {
        when(mockConexion.consultar(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false); 
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("version")).thenReturn("1.0");
        when(mockResultSet.getInt("aplicacion_id")).thenReturn(1);

        when(mockControllerApplications.consultApplication(anyInt())).thenReturn(new ModelApplications());

        ArrayList<ModelVersions> result = controller.consultForId(1);
        assertNotNull(result, "El resultado no debería ser nulo.");
        assertEquals(1, result.size(), "Debería haber un elemento en el resultado.");
        assertEquals("1.0", result.get(0).getVersion(), "La versión debería ser 1.0.");
    }

    @Test
    public void testConsultVersion() throws SQLException {
        when(mockConexion.consultar(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false); 
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("version")).thenReturn("1.0");
        when(mockResultSet.getInt("aplicacion_id")).thenReturn(1);

        when(mockControllerApplications.consultApplication(anyInt())).thenReturn(new ModelApplications());

        ModelVersions result = controller.consultVersion(1);
        assertNotNull(result, "El resultado no debería ser nulo.");
        assertEquals("1.0", result.getVersion(), "La versión debería ser 1.0.");
    }

    @Test
    public void testSave() {
        when(mockConexion.ejecutar(anyString())).thenReturn(true);

        ModelVersions version = new ModelVersions();
        version.setApplications(new ModelApplications());
        version.getApplications().setId(1);
        version.setVersion("1.0");

        controller.save(version);

        verify(mockConexion).ejecutar("INSERT INTO versiones(aplicacion_id, version) VALUES ('1','1.0')");
    }

    @Test
    public void testUpdate() {
        when(mockConexion.ejecutar(anyString())).thenReturn(true);

        ModelVersions version = new ModelVersions();
        version.setId(1);
        version.setApplications(new ModelApplications());
        version.getApplications().setId(1);
        version.setVersion("1.0");

        controller.update(version);

        verify(mockConexion).ejecutar("UPDATE versiones SET version='1.0', aplicacion_id ='1' WHERE id =1");
    }

    @Test
    public void testDelete() {
        when(mockConexion.ejecutar(anyString())).thenReturn(true);

        ModelVersions version = new ModelVersions();
        version.setId(1);

        controller.delete(version);

        verify(mockConexion).ejecutar("DELETE FROM versiones WHERE id =1");
    }
}