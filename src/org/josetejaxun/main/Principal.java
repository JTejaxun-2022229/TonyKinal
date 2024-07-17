/*
Nombre: José Daniel Tejaxún Xicón 
Carné: 2022229
Codigo tecnico: IN5BV
Fecha de creación: 14/04/2023
Fecha de modificación: 14/04/2023 - 19:53
                       20/04/2023 - 13:41
                       21/04/2023 - 13:37
                       22/04/2023 - 21:01
                       23/04/2023 - 13:11
                       25/04/2023 - 08:47
                       25/04/2023 - 21:16
                       26/04/2023 - 07:42
                       27/04/2023 - 12:52
                       27/04/2023 - 08:41
                       28/04/2023 - 13:00
                       28/04/2023 - 20:54
                       30/04/2023 - 23:19
                       02/05/2023 - 08:14
                       03/05/2023 - 23:02
                       04/05/2023 - 08:47
                       05/05/2023 - 13:02
                       09/05/2023 - 08:27
                       09/05/2023 - 23:19
                       12/05/2023 - 07:25
                       12/05/2023 - 12:47
                       22/05/2023 - 17:32
                       24/05/2023 - 21:59
                       25/05/2023 - 08:52
                       25/05/2023 - 12:49
                       26/05/2023 - 13:01  
                       27/05/2023 - 12:31
                       27/05/2023 - 15:44
                       28/05/2023 - 16:14
                       30/05/2023 - 16:10
                       30/05/2023 - 23:24
                       31/05/2023 - 08:48
                       31/05/2023 - 22:30
                       01/06/2023 - 16:56
                       01/06/2023 - 20:29
                       02/06/2023 - 12:57
                       02/06/2023 - 23:34
                       03/06/2023 - 09:02
                       08/06/2023 - 14:11
                       08/06/2023 - 23:36
                       09/06/2023 - 09:01
                       09/06/2023 - 13:07                       
Fecha de finalización: 14/04/2023 - 23:03
                       28/04/2023 - 23:10
                       12/05/2023 - 17:09
                       03/06/2023 - 10:47
                       09/06/2023 - 16:42
*/

package org.josetejaxun.main;
import java.lang.Exception;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.josetejaxun.controller.EmpresaController;
import org.josetejaxun.controller.MenuPrincipalController;
import org.josetejaxun.controller.PlatoController;
import org.josetejaxun.controller.PresupuestoController;
import org.josetejaxun.controller.EmpleadoController;
import org.josetejaxun.controller.InicioController;
import org.josetejaxun.controller.LoginController;
import org.josetejaxun.controller.ProductoController;
import org.josetejaxun.controller.ProductoHasPlatoController;
import org.josetejaxun.controller.ProgramadorController;
import org.josetejaxun.controller.UsuarioController;
import org.josetejaxun.controller.ReporteGeneralController;
import org.josetejaxun.controller.ReportePresupuestoController;
import org.josetejaxun.controller.ServicioController;
import org.josetejaxun.controller.ServicioHasEmpleadoController;
import org.josetejaxun.controller.ServicioHasPlatoController;
import org.josetejaxun.controller.TipoEmpleadoController;
import org.josetejaxun.controller.TipoPlatoController;

public class Principal extends Application {
    
    private final String PAQUETE_VISTA = "/org/josetejaxun/view/";
    private Stage escenarioPrincipal;
    private Scene escena;
    
    @Override
    public void start(Stage escenarioPrincipal) throws Exception{
        
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Tonny's Kinal 2023");
        escenarioPrincipal.getIcons().add(new Image("/org/josetejaxun/image/Icono.png"));
        ventanaInicio();
        escenarioPrincipal.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws Exception{
        
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new   FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
        escena = new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        return resultado;
    }
    
    public void ventanaInicio(){
        
        try{
            
            InicioController inicio = (InicioController)cambiarEscena("InicioView.fxml",1200,700);
            inicio.setEscenarioPrincipal(this);
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
    
    public void ventanaMenuPrincipal(){
        
        try{
            
            MenuPrincipalController menu =(MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml",1200,700);
            menu.setEscenarioPrincipal(this);
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }

    public void ventanaProgramador(){
        
        try{
            
            ProgramadorController programador = (ProgramadorController)cambiarEscena("ProgramadorView.fxml",1200,700);
            programador.setEscenarioPrincipal(this);
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
    
    public void ventanaEmpresa(){
        
        try{
            
            EmpresaController empresa = (EmpresaController)cambiarEscena("EmpresaView.fxml",1200,700);
            empresa.setEscenarioPrincipal(this);
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
    
    public void ventanaTipoEmpleado(){
        
        try{
            TipoEmpleadoController tipoEmpleado = (TipoEmpleadoController)cambiarEscena("TipoEmpleadoView.fxml",1200,700);
            tipoEmpleado.setEscenarioPrincipal(this);
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
    
    public void ventanaEmpleado(){
        
        try{
            
            EmpleadoController empleado = (EmpleadoController)cambiarEscena("EmpleadoView.fxml",1498,700);
            empleado.setEscenarioPrincipal(this);
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
    
    public void ventanaTipoPlato(){
        
        try{
            
            TipoPlatoController tipoPlato = (TipoPlatoController)cambiarEscena("TipoPlatoView.fxml",1200,700);
            tipoPlato.setEscenarioPrincipal(this);
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
    
    public void ventanaProducto(){
        
        try{
            
            ProductoController producto = (ProductoController)cambiarEscena("ProductoView.fxml",1200,700);
            producto.setEscenarioPrincipal(this);
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
    
    public void ventanaServicio(){
        
        try{
            
            ServicioController servicio = (ServicioController)cambiarEscena("ServicioView.fxml",1498,700);
            servicio.setEscenarioPrincipal(this);
        }catch(Exception e){
        
            e.printStackTrace();
        }        
    }
    
    public void ventanaPresupuesto(){
        
        try{
            
            PresupuestoController presupuesto = (PresupuestoController)cambiarEscena("PresupuestoView.fxml",1200,700);
            presupuesto.setEscenarioPrincipal(this);
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
    
    public void ventanaPlato(){
        
        try{
            
            PlatoController plato = (PlatoController)cambiarEscena("PlatoView.fxml",1498,700);
            plato.setEscenarioPrincipal(this);
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
    
    public void ventanaProductoHasPlato(){
        
        try{
            
            ProductoHasPlatoController php = (ProductoHasPlatoController)cambiarEscena("ProductoHasPlatoView.fxml",1200,700);
            php.setEscenarioPrincipal(this);
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
    
    public void ventanaServicioHasPlato(){
        
        try{
            
            ServicioHasPlatoController shp = (ServicioHasPlatoController)cambiarEscena("ServicioHasPlatoView.fxml",1200,700);
            shp.setEscenarioPrincipal(this);
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
    
    public void ventanaServicioHasEmpleado(){
        
        try{
            
            ServicioHasEmpleadoController she = (ServicioHasEmpleadoController)cambiarEscena("ServicioHasEmpleadoView.fxml",1498,700);
            she.setEscenarioPrincipal(this);
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
    
    public void ventanaReporteGeneral(){
        
        try{
            
            ReporteGeneralController reporteGeneral = (ReporteGeneralController)cambiarEscena("ReporteGeneralView.fxml",1200,700);
            reporteGeneral.setEscenarioPrincipal(this);
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
    
    public void ventanaReportePresupuesto(){
        
        try{
            
            ReportePresupuestoController reportePresupuesto = (ReportePresupuestoController)cambiarEscena("ReportePresupuestoView.fxml",1200,700);
            reportePresupuesto.setEscenarioPrincipal(this);
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
    
    public void ventanaLogin(){
        
        try{
            
            LoginController login = (LoginController)cambiarEscena("LoginView.fxml",1200,700);
            login.setEscenarioPrincipal(this);
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
    
    public void ventanaRegistro(){
        
        try{
            
            UsuarioController registro = (UsuarioController)cambiarEscena("RegistroView.fxml",1200,700);
            registro.setEscenarioPrincipal(this);
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
}
