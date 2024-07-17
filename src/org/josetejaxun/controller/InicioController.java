package org.josetejaxun.controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.josetejaxun.main.Principal;

public class InicioController implements Initializable{

    private Principal escenarioPrincipal;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        
    }
    
    public void cerrar(){
        
        System.exit(0);
    }
    
    public void ventanaLogin(){
        
        escenarioPrincipal.ventanaLogin();
    }
    
    public void ventanaRegistro(){
        
        escenarioPrincipal.ventanaRegistro();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
}
