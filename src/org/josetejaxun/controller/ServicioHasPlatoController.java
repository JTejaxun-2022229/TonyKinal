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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.josetejaxun.bean.Plato;
import org.josetejaxun.bean.Servicio;
import org.josetejaxun.bean.ServicioHasPlato;
import org.josetejaxun.db.Conexion;
import org.josetejaxun.main.Principal;
public class ServicioHasPlatoController implements Initializable{

    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<ServicioHasPlato> listaServicioHasPlato;
    private ObservableList<Servicio> listaServicio;
    private ObservableList<Plato> listaPlato;
    @FXML private TextField txtServicioCodigoServicioSHP;
    @FXML private TableView tblServicioHasPlatos;
    @FXML private TableColumn colServicioCodigoServicioSHP;
    @FXML private TableColumn colCodigoServicioSHP;
    @FXML private TableColumn colCodigoPlatoSHP;
    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    @FXML private ComboBox cmbCodigoServicioSHP;
    @FXML private ComboBox cmbCodigoPlatoSHP;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        cargarDatos();
        cmbCodigoPlatoSHP.setItems(getPlato());
        cmbCodigoServicioSHP.setItems(getServicio());
    }
    
    public void cargarDatos(){
        
        tblServicioHasPlatos.setItems(getServicioHasPlato());
        colServicioCodigoServicioSHP.setCellValueFactory(new PropertyValueFactory<ServicioHasPlato, Integer>("servicios_codigoServicio"));
        colCodigoPlatoSHP.setCellValueFactory(new PropertyValueFactory<ServicioHasPlato, Integer>("codigoPlato"));
        colCodigoServicioSHP.setCellValueFactory(new PropertyValueFactory<ServicioHasPlato, Integer>("codigoServicio"));
    }
    
    public ObservableList<ServicioHasPlato>getServicioHasPlato(){
        
        ArrayList<ServicioHasPlato> lista = new ArrayList<ServicioHasPlato>();
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarServicios_has_platos");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                
                lista.add(new ServicioHasPlato(resultado.getInt("servicios_codigoServicio"),
                                               resultado.getInt("codigoPlato"),
                                               resultado.getInt("codigoServicio")));
            }
        }catch(Exception e){
            
            e.printStackTrace();
        }
        
        return listaServicioHasPlato = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Plato>getPlato(){
        
        ArrayList<Plato> lista = new ArrayList<Plato>();
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarPlatos");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                
                lista.add(new Plato(resultado.getInt("codigoPlato"),
                                    resultado.getInt("cantidad"),
                                    resultado.getString("nombrePlato"),
                                    resultado.getString("descripcionPlato"),
                                    resultado.getDouble("precioPlato"),
                                    resultado.getInt("codigoTipoPlato")));
            }
        }catch(Exception e){
            
            e.printStackTrace();
        }
        
        return listaPlato = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Servicio>getServicio(){
        
        ArrayList<Servicio> lista = new ArrayList<Servicio>();
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarServicios");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                
                lista.add(new Servicio(resultado.getInt("codigoServicio"),
                                       resultado.getDate("fechaServicio"),
                                       resultado.getString("tipoServicio"),
                                       resultado.getTime("horaServicio"),
                                       resultado.getString("lugarServicio"),
                                       resultado.getString("telefonoContacto"),
                                       resultado.getInt("codigoEmpresa")));
            }
        }catch(Exception e){
            
            e.printStackTrace();
        }
        return listaServicio = FXCollections.observableArrayList(lista);
    }
    
    public void nuevo(){
        
        switch(tipoDeOperacion){
            
            case NINGUNO:
                
                limpiarControles();
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEliminar.setVisible(true);
                btnEliminar.setDisable(false);
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/josetejaxun/image/IconoGuardar.png"));
                imgEliminar.setImage(new Image("/org/josetejaxun/image/IconoCancelar.png"));
                tipoDeOperacion = operaciones.GUARDAR;
            break;
            
            case GUARDAR:
                
                guardar();
                limpiarControles();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEliminar.setVisible(false);
                btnEliminar.setDisable(true);
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("org/josetejaxun/image/IconoAgregar.png"));
                imgEliminar.setImage(new Image("/org/josetejaxun/image/IconoEliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    public void eliminar(){
        
        switch(tipoDeOperacion){
            
            case GUARDAR:
                
                limpiarControles();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEliminar.setVisible(false);
                btnEliminar.setDisable(true);
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/josetejaxun/image/IconoAgregar.png"));
                imgEliminar.setImage(new Image("/org/josetejaxun/image/IconoEliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
            break;
            
            default:
                
                if(tblServicioHasPlatos.getSelectionModel().getSelectedItem() != null){
                    
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?", "Eliminar ServicioHasPlato", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        
                        try{
                            
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_eliminarServicios_has_Platos(?)");
                            procedimiento.setInt(1, ((ServicioHasPlato)tblServicioHasPlatos.getSelectionModel().getSelectedItem()).getServicios_codigoServicio());
                            procedimiento.execute();
                            listaServicioHasPlato.remove(tblServicioHasPlatos.getSelectionModel().getSelectedItem());
                            cargarDatos();
                            limpiarControles();
                        }catch(Exception e){
                            
                            e.printStackTrace();
                        }
                    }
                }else{
                    
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
            break;
        }
    }
    
    public void editar(){
        
        switch(tipoDeOperacion){
            
            case NINGUNO:
                
                if(tblServicioHasPlatos.getSelectionModel().getSelectedItem() != null){
                    
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnReporte.setVisible(true);
                    btnReporte.setDisable(false);
                    imgEditar.setImage(new Image("/org/josetejaxun/image/IconoActualizar.png"));
                    imgReporte.setImage(new Image("/org/josetejaxun/image/IconoCancelar.png"));
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
            break;
            
            case ACTUALIZAR:
                
                actualizar();
                limpiarControles();
                desactivarControles();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnReporte.setVisible(false);
                btnReporte.setDisable(true);
                imgEditar.setImage(new Image("/org/josetejaxun/image/IconoEditar.png"));
                imgEliminar.setImage(new Image("/org/josetejaxun/image/IconoReporte.png"));
                cargarDatos();
                tipoDeOperacion = operaciones.NINGUNO;
            break;
        }
    }
    
    public void reporte(){
        
        switch(tipoDeOperacion){
            
            case ACTUALIZAR:
                
                limpiarControles();
                desactivarControles();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnReporte.setVisible(false);
                btnReporte.setDisable(true);
                imgEditar.setImage(new Image("/org/josetejaxun/image/IconoEditar.png"));
                imgReporte.setImage(new Image("/org/josetejaxun/image/IconoReporte.png"));
                tipoDeOperacion = operaciones.NINGUNO;
            break;
        }
    }
    
    public void guardar(){
        
        ServicioHasPlato registro = new ServicioHasPlato();
        registro.setServicios_codigoServicio(Integer.parseInt(txtServicioCodigoServicioSHP.getText()));
        registro.setCodigoPlato(((Plato)cmbCodigoPlatoSHP.getSelectionModel().getSelectedItem()).getCodigoPlato());
        registro.setCodigoServicio(((Servicio)cmbCodigoServicioSHP.getSelectionModel().getSelectedItem()).getCodigoServicio());
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_agregarServicios_has_platos(?, ?, ?)");
            procedimiento.setInt(1, registro.getServicios_codigoServicio());
            procedimiento.setInt(2, registro.getCodigoPlato());
            procedimiento.setInt(3, registro.getCodigoServicio());
            procedimiento.execute();
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
    
    public void actualizar(){
        
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_editarServicios_has_Platos(?, ?, ?)");
            ServicioHasPlato registro = (ServicioHasPlato)tblServicioHasPlatos.getSelectionModel().getSelectedItem();
            registro.setCodigoPlato(((Plato)cmbCodigoPlatoSHP.getSelectionModel().getSelectedItem()).getCodigoPlato());
            registro.setCodigoServicio(((Servicio)cmbCodigoServicioSHP.getSelectionModel().getSelectedItem()).getCodigoServicio());
            procedimiento.setInt(1, registro.getServicios_codigoServicio());
            procedimiento.setInt(2, registro.getCodigoPlato());
            procedimiento.setInt(3, registro.getCodigoServicio());
            procedimiento.execute();
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
    
    public Plato buscarPlato(int codigoPlato){
        
        Plato resultado = null;
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_buscarPlato(?)");
            procedimiento.setInt(1, codigoPlato);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                
                resultado = new Plato(registro.getInt("codigoPlato"),
                                      registro.getInt("cantidad"),
                                      registro.getString("nombrePlato"),
                                      registro.getString("descripcionPlato"),
                                      registro.getDouble("precioPlato"),
                                      registro.getInt("codigoTipoPlato"));
            }
        }catch(Exception e){
            
            e.printStackTrace();
        }
        
        return resultado;
    }
    
    public Servicio buscarServicio(int codigoServicio){
        
        Servicio resultado = null;
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_buscarServicio(?)");
            procedimiento.setInt(1, codigoServicio);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                
                resultado = new Servicio(registro.getInt("codigoServicio"),
                                         registro.getDate("fechaServicio"),
                                         registro.getString("tipoServicio"),
                                         registro.getTime("horaServicio"),
                                         registro.getString("lugarServicio"),
                                         registro.getString("telefonoContacto"),
                                         registro.getInt("codigoEmpresa"));
            }
        }catch(Exception e){
            
            e.printStackTrace();
        }
        
        return resultado;
    }
    
    public void seleccionarElemento(){
        
        txtServicioCodigoServicioSHP.setText(String.valueOf(((ServicioHasPlato)tblServicioHasPlatos.getSelectionModel().getSelectedItem()).getCodigoPlato()));
        cmbCodigoPlatoSHP.getSelectionModel().select(buscarPlato(((ServicioHasPlato)tblServicioHasPlatos.getSelectionModel().getSelectedItem()).getCodigoPlato()));
        cmbCodigoServicioSHP.getSelectionModel().select(buscarServicio(((ServicioHasPlato)tblServicioHasPlatos.getSelectionModel().getSelectedItem()).getCodigoServicio()));
    }
    
    public void desactivarControles(){
        
        txtServicioCodigoServicioSHP.setEditable(false);
        cmbCodigoPlatoSHP.setDisable(true);
        cmbCodigoServicioSHP.setDisable(true);
    }
    
    public void activarControles(){
        
        txtServicioCodigoServicioSHP.setEditable(true);
        cmbCodigoPlatoSHP.setDisable(false);
        cmbCodigoServicioSHP.setDisable(false);
    }
    
    public void limpiarControles(){
        
        txtServicioCodigoServicioSHP.clear();
        cmbCodigoPlatoSHP.setValue(null);
        cmbCodigoServicioSHP.setValue(null);
    }
    
    public void ventanaServicio(){
    
        escenarioPrincipal.ventanaServicio();
    }
    
    public void ventanaPlato(){
        
        escenarioPrincipal.ventanaPlato();
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
