package org.josetejaxun.controller;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.commons.codec.digest.DigestUtils;
import org.josetejaxun.bean.Usuario;
import org.josetejaxun.db.Conexion;
import org.josetejaxun.main.Principal;

public class UsuarioController implements Initializable{

    private Principal escenarioPrincipal;
    private enum operaciones {GUARDAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    @FXML private TextField txtNombreUsuario;
    @FXML private TextField txtApellidoUsuario;
    @FXML private TextField txtUsuarioUsuario;
    @FXML private TextField txtContraseñaUsuario;
    @FXML private Button btnIngresar;
    @FXML private Button btnCancelar;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
       
    }
    
    public void nuevo(){
        
        switch(tipoDeOperacion){
            
            case NINGUNO:
             
                limpiarControles();
                activarControles();
                btnIngresar.setText("Guardar");
                btnCancelar.setDisable(false);
                btnCancelar.setVisible(true);
                tipoDeOperacion = operaciones.GUARDAR;
            break;
            
            case GUARDAR:
                
                guardar();
                limpiarControles();
                desactivarControles();
                btnIngresar.setText("Registrarse");
                btnCancelar.setDisable(true);
                btnCancelar.setVisible(false);
                ventanaLogin();
                tipoDeOperacion = operaciones.NINGUNO;
            break;
        }
    }
    
    public void cancelar(){
        
        switch(tipoDeOperacion){
            
            case GUARDAR:
                
                limpiarControles();
                desactivarControles();
                btnIngresar.setText("Registrarse");
                btnCancelar.setDisable(true);
                btnCancelar.setVisible(false);
                tipoDeOperacion = operaciones.NINGUNO;
            break;
        }
    }
    
    public void guardar(){
        
        Usuario registro = new Usuario();
        registro.setNombreUsuario(txtNombreUsuario.getText());
        registro.setApellidoUsuario(txtApellidoUsuario.getText());
        registro.setUsuarioLogin(txtUsuarioUsuario.getText());
        registro.setContraseña(txtContraseñaUsuario.getText());
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_agregarUsuario(?, ?, ?, ?)");
            procedimiento.setString(1, registro.getNombreUsuario());
            procedimiento.setString(2, registro.getApellidoUsuario());
            procedimiento.setString(3, registro.getUsuarioLogin());
            procedimiento.setString(4, registro.getContraseña());
            procedimiento.execute();
//            String contra = "";
//            String encript = DigestUtils.md5Hex(contra);
//            System.out.print(encript);
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
    
    public void desactivarControles(){
        
        txtNombreUsuario.setEditable(false);
        txtApellidoUsuario.setEditable(false);
        txtUsuarioUsuario.setEditable(false);
        txtContraseñaUsuario.setEditable(false);
    }
    
    public void activarControles(){
        
        txtNombreUsuario.setEditable(true);
        txtApellidoUsuario.setEditable(true);
        txtUsuarioUsuario.setEditable(true);
        txtContraseñaUsuario.setEditable(true);
    }
    
    public void limpiarControles(){
        
        txtNombreUsuario.clear();
        txtApellidoUsuario.clear();
        txtUsuarioUsuario.clear();
        txtContraseñaUsuario.clear();
    }
    
    public void cerrar(){
    
        System.exit(0);
    }
    
    public void ventanaInicio(){
        
        escenarioPrincipal.ventanaInicio();
    }
    
    public void ventanaLogin(){
        
        escenarioPrincipal.ventanaLogin();
    }
    
    public void ventanaMenuPrincipal(){
        
        escenarioPrincipal.ventanaMenuPrincipal();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
}
