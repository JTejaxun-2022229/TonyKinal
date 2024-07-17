package org.josetejaxun.controller;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import org.josetejaxun.bean.Login;
import org.josetejaxun.bean.Usuario;
import org.josetejaxun.db.Conexion;
import org.josetejaxun.main.Principal;

public class LoginController implements Initializable{

    private Principal escenarioPrincipal;
    private ObservableList<Usuario> listaUsuario;
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
        
    }
    
    public ObservableList<Usuario>getUsuario(){
        
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarUsuarios}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                
                lista.add(new Usuario(resultado.getInt("codigoUsuario"),
                                      resultado.getString("nombreUsuario"),
                                      resultado.getString("apellidoUsuario"),
                                      resultado.getString("usuarioLogin"),
                                      resultado.getString("contraseña")));
            }
        }catch(Exception e){
            
            e.printStackTrace();
        }
        
        return listaUsuario = FXCollections.observableArrayList(lista);
    }
    
    @FXML
    private void Login(){
        
        Login login = new Login();
        int x = 0;
        boolean bandera = false;
        login.setUsuarioMaster(txtUsuario.getText());
        login.setPasswordLogin(txtPassword.getText());
        while(x < getUsuario().size()){
            
            String user = getUsuario().get(x).getUsuarioLogin();
            String pass = getUsuario().get(x).getContraseña();
            if(user.equals(login.getUsuarioMaster()) && pass.equals(login.getPasswordLogin())){
                
                JOptionPane.showMessageDialog(null, "Seccion Inciada\n" + 
                                                     getUsuario().get(x).getNombreUsuario() + " " + 
                                                     getUsuario().get(x).getApellidoUsuario() + "\n" + 
                                                    "Bienvenido");
                                                     escenarioPrincipal.ventanaMenuPrincipal();
                                                     x = getUsuario().size();
                                                     bandera = true;
            } 
            x++;
            
            if(bandera == false){
                
                JOptionPane.showMessageDialog(null, "Credenciales invalidas");
            }
        }
    } 
    
    public void cerrar(){
        
        System.exit(0);
    }
    
    public void ventanaInicio(){
        
        escenarioPrincipal.ventanaInicio();
    }
    
    public void ventanaRegistro(){
        
        escenarioPrincipal.ventanaRegistro();
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
