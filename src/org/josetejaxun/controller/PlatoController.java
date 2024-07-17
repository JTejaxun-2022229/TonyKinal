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
import org.josetejaxun.bean.TipoPlato;
import org.josetejaxun.db.Conexion;
import org.josetejaxun.main.Principal;
public class PlatoController implements Initializable{

    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Plato> listaPlato;
    private ObservableList<TipoPlato> listaTipoPlato;
    @FXML private TextField txtPrecioPlato;
    @FXML private TextField txtNombrePlato;
    @FXML private TextField txtDescripcionPlato;
    @FXML private TextField txtCodigoPlato;
    @FXML private TextField txtCantidadPlato;
    @FXML private TableView tblPlatos;
    @FXML private TableColumn colPrecioPlato;
    @FXML private TableColumn colNombrePlato;
    @FXML private TableColumn colDescripcionPlato;
    @FXML private TableColumn colCodigoTipoPlatoPlato;
    @FXML private TableColumn colCodigoPlato;
    @FXML private TableColumn colCantidadPlato;
    @FXML private ComboBox cmbCodigoTipoPlato;
    @FXML private Button btnNuevo; 
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        cargarDatos();
        cmbCodigoTipoPlato.setItems(getTipoPlato());
    }
    
    public void cargarDatos(){
        
        tblPlatos.setItems(getPlato());
        colCodigoPlato.setCellValueFactory(new PropertyValueFactory<Plato, Integer>("codigoPlato"));
        colCantidadPlato.setCellValueFactory(new PropertyValueFactory<Plato, Integer>("cantidad"));
        colNombrePlato.setCellValueFactory(new PropertyValueFactory<Plato, String>("nombrePlato"));
        colDescripcionPlato.setCellValueFactory(new PropertyValueFactory<Plato, String>("descripcionPlato"));
        colPrecioPlato.setCellValueFactory(new PropertyValueFactory<Plato, Double>("precioPlato"));
        colCodigoTipoPlatoPlato.setCellValueFactory(new PropertyValueFactory<Plato, Integer>("codigoTipoPlato"));
        
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
    
    public ObservableList<TipoPlato>getTipoPlato(){
        
        ArrayList<TipoPlato> lista = new ArrayList<TipoPlato>();
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarTipoPlatos");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                
                lista.add(new TipoPlato(resultado.getInt("codigoTipoPlato"),
                                        resultado.getString("descripcionTipo")));
            }
        }catch(Exception e){
            
            e.printStackTrace();
        }

        return listaTipoPlato = FXCollections.observableArrayList(lista);
    }
    
    public void nuevo(){
        
        switch(tipoDeOperaciones){
            
            case NINGUNO:
                
                limpiarControles();
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/josetejaxun/image/IconoGuardar.png"));
                imgEliminar.setImage(new Image("/org/josetejaxun/image/IconoCancelar.png"));
                tipoDeOperaciones = operaciones.GUARDAR;
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
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    public void eliminar(){
        
        switch(tipoDeOperaciones){
            
            case GUARDAR:
                
                limpiarControles();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/josetejaxun/image/IconoAgregar.png"));
                imgEliminar.setImage(new Image("/org/josetejaxun/image/IconoEliminar.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
            break;
            
            default:
                
                if(tblPlatos.getSelectionModel().getSelectedItem() != null){
                    
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro, puede que haya registros que dependan de este registro?", "Eliminar Plato", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        
                        try{
                            
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_eliminarPlato(?)");
                            procedimiento.setInt(1, ((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getCodigoPlato());
                            procedimiento.execute();
                            listaPlato.remove(tblPlatos.getSelectionModel().getSelectedItem());
                            cargarDatos();
                            limpiarControles();
                        }catch(Exception e ){
                            
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
        
        switch(tipoDeOperaciones){
            
            case NINGUNO:
                
                if(tblPlatos.getSelectionModel().getSelectedItem() != null){
                    
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image("/org/josetejaxun/image/IconoActualizar.png"));
                    imgReporte.setImage(new Image("/org/josetejaxun/image/IconoCancelar.png"));
                    activarControles();
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
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
                imgEditar.setImage(new Image("/org/josetejaxun/image/IconoEditar.png"));
                imgReporte.setImage(new Image("/org/josetejaxun/image/IconoReporte.png"));
                cargarDatos();
                tipoDeOperaciones = operaciones.NINGUNO;
            break;
        }
    }
    
    public void reporte(){
        
        switch(tipoDeOperaciones){
            
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
                tipoDeOperaciones = operaciones.NINGUNO;
            break;
        }
    }
    
    public void guardar(){
        
        Plato registro = new Plato();
        registro.setCantidad(Integer.parseInt(txtCantidadPlato.getText()));
        registro.setNombrePlato(txtNombrePlato.getText());
        registro.setDescripcionPlato(txtDescripcionPlato.getText());
        registro.setPrecioPlato(Double.parseDouble(txtPrecioPlato.getText()));
        registro.setCodigoTipoPlato(((TipoPlato)cmbCodigoTipoPlato.getSelectionModel().getSelectedItem()).getCodigoTipoPlato());
        try{
           
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_agregarPlato(?, ?, ?, ?, ?)");
            procedimiento.setInt(1, registro.getCantidad());
            procedimiento.setString(2, registro.getNombrePlato());
            procedimiento.setString(3, registro.getDescripcionPlato());
            procedimiento.setDouble(4, registro.getPrecioPlato());
            procedimiento.setInt(5, registro.getCodigoTipoPlato());
            procedimiento.execute();
            listaPlato.add(registro);
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
    
    public void actualizar(){
        
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_editarPlato(?, ?, ?, ?, ?, ?)");
            Plato registro = (Plato)tblPlatos.getSelectionModel().getSelectedItem();
            registro.setCantidad(Integer.parseInt(txtCantidadPlato.getText()));
            registro.setNombrePlato(txtNombrePlato.getText());
            registro.setDescripcionPlato(txtDescripcionPlato.getText());
            registro.setPrecioPlato(Double.parseDouble(txtPrecioPlato.getText()));
            registro.setCodigoTipoPlato(((TipoPlato)cmbCodigoTipoPlato.getSelectionModel().getSelectedItem()).getCodigoTipoPlato());
            procedimiento.setInt(1, registro.getCodigoPlato());
            procedimiento.setInt(2, registro.getCantidad());
            procedimiento.setString(3, registro.getNombrePlato());
            procedimiento.setString(4, registro.getDescripcionPlato());
            procedimiento.setDouble(5, registro.getPrecioPlato());
            procedimiento.setInt(6, registro.getCodigoTipoPlato());
            procedimiento.execute();
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
    
    public TipoPlato buscarTipoPlato(int codigoTipoPlato){
        
        TipoPlato resultado = null;
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_buscarTipoPlato(?)");
            procedimiento.setInt(1, codigoTipoPlato);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                
                resultado = new TipoPlato(registro.getInt("codigoTipoPlato"),
                                          registro.getString("descripcionTipo"));
            }
        }catch(Exception e){
            
            e.printStackTrace();
        }
        return resultado;
    }

    
    public void seleccionarElemento(){
        
        txtCodigoPlato.setText(String.valueOf(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getCodigoPlato()));
        txtCantidadPlato.setText(String.valueOf(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getCantidad()));
        txtNombrePlato.setText(String.valueOf(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getNombrePlato()));
        txtDescripcionPlato.setText(String.valueOf(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getDescripcionPlato()));
        txtPrecioPlato.setText(String.valueOf(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getPrecioPlato()));
        cmbCodigoTipoPlato.getSelectionModel().select(buscarTipoPlato(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getCodigoTipoPlato()));
    }
    
    public void desactivarControles(){
        
        txtCodigoPlato.setEditable(false);
        txtCantidadPlato.setEditable(false);
        txtNombrePlato.setEditable(false);
        txtDescripcionPlato.setEditable(false);
        txtPrecioPlato.setEditable(false);
        cmbCodigoTipoPlato.setDisable(true);
    }
    
    public void activarControles(){
        
        txtCodigoPlato.setEditable(false);
        txtCantidadPlato.setEditable(true);
        txtNombrePlato.setEditable(true);
        txtDescripcionPlato.setEditable(true);
        txtPrecioPlato.setEditable(true);
        cmbCodigoTipoPlato.setDisable(false);
    }
    
    public void limpiarControles(){
        
        txtCodigoPlato.clear();
        txtCantidadPlato.clear();
        txtNombrePlato.clear();
        txtDescripcionPlato.clear();
        txtPrecioPlato.clear();
        cmbCodigoTipoPlato.setValue(null);
    }
    
    public void ventanaProductoHasPlato(){
        
        escenarioPrincipal.ventanaProductoHasPlato();
    }
    
    public void ventanaServicioHasPlato(){
        
        escenarioPrincipal.ventanaServicioHasPlato();
    }
    
    public void ventanaTipoPlato(){
        
        escenarioPrincipal.ventanaTipoPlato();
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
