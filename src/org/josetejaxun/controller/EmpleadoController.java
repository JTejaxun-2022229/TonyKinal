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
import org.josetejaxun.bean.Empleado;
import org.josetejaxun.bean.TipoEmpleado;
import org.josetejaxun.db.Conexion;
import org.josetejaxun.main.Principal;
public class EmpleadoController implements Initializable{

    private enum operaciones{NUEVO, GUARDAR, ACTUALIZAR, ELIMINAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Empleado> listaEmpleado;
    private ObservableList<TipoEmpleado> listaTipoEmpleado;
    @FXML private TextField txtTelefonoContactoEmpleado;
    @FXML private TextField txtNumeroEmpleado;
    @FXML private TextField txtNombreEmpleado;
    @FXML private TextField txtGradoCocinero;
    @FXML private TextField txtDireccionEmpleado;
    @FXML private TextField txtCodigoEmpleado;
    @FXML private TextField txtApellidoEmpleado;
    @FXML private TableView tblEmpleados;
    @FXML private TableColumn colTelefonoContactoEmpleado;
    @FXML private TableColumn colNumeroEmpleado;
    @FXML private TableColumn colNombreEmpleado;
    @FXML private TableColumn colGradoCocinero;
    @FXML private TableColumn colDireccionEmpleado;
    @FXML private TableColumn colCodigoTipoEmpleadoEmpleado;
    @FXML private TableColumn colCodigoEmpleado;
    @FXML private TableColumn colApellidoEmpleado;
    @FXML private ComboBox cmbCodigoTipoEmpleado;
    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        cargarDatos();
        cmbCodigoTipoEmpleado.setItems(getTipoEmpleado());
    }
    
    public void cargarDatos(){
        
        tblEmpleados.setItems(getEmpleado());
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("codigoEmpleado"));
        colNumeroEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("numeroEmpleado"));
        colApellidoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellidoEmpleado"));
        colNombreEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombreEmpleado"));
        colDireccionEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("direccionEmpleado"));
        colTelefonoContactoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("telefonoContacto"));
        colGradoCocinero.setCellValueFactory(new PropertyValueFactory<Empleado, String>("gradoCocinero"));
        colCodigoTipoEmpleadoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("codigoTipoEmpleado"));
    }
    
    public ObservableList<Empleado>getEmpleado(){
        
        ArrayList<Empleado> lista = new ArrayList<Empleado>();
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarEmpleados");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                
                lista.add(new Empleado(resultado.getInt("codigoEmpleado"),
                                       resultado.getInt("numeroEmpleado"),
                                       resultado.getString("apellidoEmpleado"),
                                       resultado.getString("nombreEmpleado"),
                                       resultado.getString("direccionEmpleado"),
                                       resultado.getString("telefonoContacto"),
                                       resultado.getString("gradoCocinero"),
                                       resultado.getInt("codigoTipoEmpleado")));
            }
        }catch(Exception e){
            
            e.printStackTrace();
        }
        
        return listaEmpleado = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<TipoEmpleado>getTipoEmpleado(){
        
        ArrayList<TipoEmpleado> lista = new ArrayList<TipoEmpleado>();
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarTipoEmpleados");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                
                lista.add(new TipoEmpleado(resultado.getInt("codigoTipoEmpleado"),
                                           resultado.getString("descripcion")));
            }
        }catch(Exception e){
            
            e.printStackTrace();
        }
        return listaTipoEmpleado = FXCollections.observableArrayList(lista);
    }
    
    public TipoEmpleado buscarTipoEmpleado(int codigoTipoEmpleado){
        
        TipoEmpleado resultado = null;
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_buscarTipoEmpleado(?)");
            procedimiento.setInt(1, codigoTipoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                
                resultado = new TipoEmpleado(registro.getInt("codigoTipoEmpleado"),
                                             registro.getString("descripcion"));
            }
        }catch(Exception e){
            
            e.printStackTrace();
        }
        return resultado;
    }
    
    public void nuevo(){
        
        switch(tipoDeOperacion){
            
            case NINGUNO:
                
                limpiarControles();
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
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
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/josetejaxun/image/IconoAgregar.png"));
                imgEliminar.setImage(new Image("/org/josetejaxun/image/IconoEliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
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
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/josetejaxun/image/IconoAgregar.png"));
                imgEliminar.setImage(new Image("/org/josetejaxun/image/IconoEliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
            break;
            
            default:
                
                
                if(tblEmpleados.getSelectionModel().getSelectedItem() != null){
                    
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro, puede haber registros que dependan de este registro?", "Eliminar Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        
                        try{
                            
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_eliminarEmpleado(?)");
                            procedimiento.setInt(1, ((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                            procedimiento.execute();
                            listaEmpleado.remove(tblEmpleados.getSelectionModel().getSelectedItem());
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
                
                if(tblEmpleados.getSelectionModel().getSelectedItem() != null){
                    
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image("/org/josetejaxun/image/IconoActualizar.png"));
                    imgReporte.setImage(new Image("/org/josetejaxun/image/IconoReporte.png"));
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    
                    JOptionPane.showMessageDialog(null,"Dese seleccionar un elemento");
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
                imgEditar.setImage(new Image("/org/josetejaxun/image/IconoEditar.png"));
                imgReporte.setImage(new Image("/org/josetejaxun/image/IconoReporte.png"));
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
                imgEditar.setImage(new Image("/org/josetejaxun/image/IconoEditar.png"));
                imgReporte.setImage(new Image("/org/josetejaxun/image/IconoReporte.png"));
                cargarDatos();
                tipoDeOperacion = operaciones.NINGUNO;
            break;
        }
    }
    
    public void guardar(){
        
        Empleado registro = new Empleado();
        registro.setNumeroEmpleado(Integer.parseInt(txtNumeroEmpleado.getText()));
        registro.setApellidoEmpleado(txtApellidoEmpleado.getText());
        registro.setNombreEmpleado(txtNombreEmpleado.getText());
        registro.setDireccionEmpleado(txtDireccionEmpleado.getText());
        registro.setTelefonoContacto(txtTelefonoContactoEmpleado.getText());
        registro.setGradoCocinero(txtGradoCocinero.getText());
        registro.setCodigoTipoEmpleado(((TipoEmpleado)cmbCodigoTipoEmpleado.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado());
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_agregarEmpleado(?, ?, ?, ?, ?, ?, ?)");
            procedimiento.setInt(1, registro.getNumeroEmpleado());
            procedimiento.setString(2, registro.getApellidoEmpleado());
            procedimiento.setString(3, registro.getNombreEmpleado());
            procedimiento.setString(4, registro.getDireccionEmpleado());
            procedimiento.setString(5, registro.getTelefonoContacto());
            procedimiento.setString(6, registro.getGradoCocinero());
            procedimiento.setInt(7, registro.getCodigoTipoEmpleado());
            procedimiento.execute();
            listaEmpleado.add(registro);
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
    
    public void actualizar(){
        
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_editarEmpleado(?, ?, ?, ?, ?, ?, ?, ?)");
            Empleado registro = (Empleado)tblEmpleados.getSelectionModel().getSelectedItem();
            registro.setNumeroEmpleado(Integer.parseInt(txtNumeroEmpleado.getText()));
            registro.setApellidoEmpleado(txtApellidoEmpleado.getText());
            registro.setNombreEmpleado(txtNombreEmpleado.getText());
            registro.setDireccionEmpleado(txtDireccionEmpleado.getText());
            registro.setTelefonoContacto(txtTelefonoContactoEmpleado.getText());
            registro.setGradoCocinero(txtGradoCocinero.getText());
            registro.setCodigoTipoEmpleado(((TipoEmpleado)cmbCodigoTipoEmpleado.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado());
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setInt(2, registro.getNumeroEmpleado());
            procedimiento.setString(3, registro.getApellidoEmpleado());
            procedimiento.setString(4, registro.getNombreEmpleado());
            procedimiento.setString(5, registro.getDireccionEmpleado());
            procedimiento.setString(6, registro.getTelefonoContacto());
            procedimiento.setString(7, registro.getGradoCocinero());
            procedimiento.setInt(8, registro.getCodigoTipoEmpleado());
            procedimiento.execute();
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
    
    public void seleccionarElemento(){
        
        txtCodigoEmpleado.setText(String.valueOf(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        txtNumeroEmpleado.setText(String.valueOf(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getNumeroEmpleado()));
        txtApellidoEmpleado.setText(String.valueOf(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getApellidoEmpleado()));
        txtNombreEmpleado.setText(String.valueOf(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getNombreEmpleado()));
        txtDireccionEmpleado.setText(String.valueOf(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getDireccionEmpleado()));
        txtTelefonoContactoEmpleado.setText(String.valueOf(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getTelefonoContacto()));
        txtGradoCocinero.setText(String.valueOf(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getGradoCocinero()));
        cmbCodigoTipoEmpleado.getSelectionModel().select(buscarTipoEmpleado(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado()));
    }
    
    public void desactivarControles(){
        
        txtCodigoEmpleado.setEditable(false);
        txtNumeroEmpleado.setEditable(false);
        txtApellidoEmpleado.setEditable(false);
        txtNombreEmpleado.setEditable(false);
        txtDireccionEmpleado.setEditable(false);
        txtTelefonoContactoEmpleado.setEditable(false);
        txtGradoCocinero.setEditable(false);
        cmbCodigoTipoEmpleado.setDisable(true);
    }
    
    public void activarControles(){
        
        txtCodigoEmpleado.setEditable(false);
        txtNumeroEmpleado.setEditable(true);
        txtApellidoEmpleado.setEditable(true);
        txtNombreEmpleado.setEditable(true);
        txtDireccionEmpleado.setEditable(true);
        txtTelefonoContactoEmpleado.setEditable(true);
        txtGradoCocinero.setEditable(true);
        cmbCodigoTipoEmpleado.setDisable(false);
    }
    
    public void limpiarControles(){
       
        txtCodigoEmpleado.clear();
        txtNumeroEmpleado.clear();
        txtApellidoEmpleado.clear();
        txtNombreEmpleado.clear();
        txtDireccionEmpleado.clear();
        txtTelefonoContactoEmpleado.clear();
        txtGradoCocinero.clear();
        cmbCodigoTipoEmpleado.setValue(null);
    }
    
    public void ventanaServicioHasEmpleado(){
        
        escenarioPrincipal.ventanaServicioHasEmpleado();
    }
    
    public void ventanaTipoEmpleado(){
        
        escenarioPrincipal.ventanaTipoEmpleado();
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
