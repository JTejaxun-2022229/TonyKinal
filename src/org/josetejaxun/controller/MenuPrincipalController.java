package org.josetejaxun.controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.josetejaxun.main.Principal;

public class MenuPrincipalController implements Initializable{

    private Principal escenarioPrincipal;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    } 
    
    public void ventanaProgramador(){
    
        escenarioPrincipal.ventanaProgramador();
    }
    
    public void ventanaEmpresa(){
       
        escenarioPrincipal.ventanaEmpresa();
    }
    
    public void ventanaTipoEmpleado(){
        
        escenarioPrincipal.ventanaTipoEmpleado();
    }
    
    public void ventanaEmpleado(){
        
        escenarioPrincipal.ventanaEmpleado();
    }
    
    public void ventanaTipoPlato(){
        
        escenarioPrincipal.ventanaTipoPlato();
    }
    
    public void ventanaProducto(){
        
        escenarioPrincipal.ventanaProducto();
    }
    
    public void ventanaServicio(){
        
        escenarioPrincipal.ventanaServicio();
    }
    
    public void ventanaPresupuesto(){
        
        escenarioPrincipal.ventanaPresupuesto();
    }
    
    public void ventanaPlato(){
        
        escenarioPrincipal.ventanaPlato();
    }
    
    public void ventanaProductoHasPlato(){
        
        escenarioPrincipal.ventanaProductoHasPlato();
    }
    
    public void ventanaServicioHasPlato(){
        
        escenarioPrincipal.ventanaServicioHasPlato();
    }
    
    public void ventanaServicioHasEmpleado(){
        
        escenarioPrincipal.ventanaServicioHasEmpleado();
    }
    
    public void reporteEmpresa(){
        
        EmpresaController empresa = new EmpresaController();
        empresa.imprimirReporte();
    }
    
    public void ventanaReporteGeneral(){
        
        escenarioPrincipal.ventanaReporteGeneral();
    }
    
    public void ventanaReportePresupuesto(){
        
        escenarioPrincipal.ventanaReportePresupuesto();
    }
    
    public void ventanaInicio(){
        
        escenarioPrincipal.ventanaInicio();
    }
    
    public void cerrarVentana(){
        
        System.exit(0);
    }
        
}
