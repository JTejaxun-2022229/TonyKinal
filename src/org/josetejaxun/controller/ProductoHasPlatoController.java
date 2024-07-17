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
import org.josetejaxun.bean.Producto;
import org.josetejaxun.bean.ProductoHasPlato;
import org.josetejaxun.db.Conexion;
import org.josetejaxun.main.Principal;
public class ProductoHasPlatoController implements Initializable{

    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<ProductoHasPlato> listaProductoHasPlato;
    private ObservableList<Plato> listaPlato;
    private ObservableList<Producto> listaProducto;
    @FXML private TextField txtProductoCodigoProductoPHP;
    @FXML private TableView tblProductoHasPlatos;
    @FXML private TableColumn colProductoCodigoProductoPHP;
    @FXML private TableColumn colCodigoProductoPHP;
    @FXML private TableColumn colCodigoPlatoPHP;
    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    @FXML private ComboBox cmbCodigoProductoPHP;
    @FXML private ComboBox cmbCodigoPlatoPHP;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        cargarDatos();
        cmbCodigoPlatoPHP.setItems(getPlato());
        cmbCodigoProductoPHP.setItems(getProducto());
    }
    
    public void cargarDatos(){
        
        tblProductoHasPlatos.setItems(getProductoHasPlato());
        colProductoCodigoProductoPHP.setCellValueFactory(new PropertyValueFactory<ProductoHasPlato, Integer>("productos_codigoProducto"));
        colCodigoPlatoPHP.setCellValueFactory(new PropertyValueFactory<ProductoHasPlato, Integer>("codigoPlato"));
        colCodigoProductoPHP.setCellValueFactory(new PropertyValueFactory<ProductoHasPlato, Integer>("codigoProducto"));
    }
    
    public ObservableList<ProductoHasPlato>getProductoHasPlato(){
        
        ArrayList<ProductoHasPlato> lista = new ArrayList<ProductoHasPlato>();
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarProductos_has_platos");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                
                lista.add(new ProductoHasPlato(resultado.getInt("productos_codigoProducto"),
                                               resultado.getInt("codigoPlato"),
                                               resultado.getInt("codigoProducto")));
            }
        }catch(Exception e){
            
            e.printStackTrace();
        }
        
        return listaProductoHasPlato = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Plato>getPlato(){
        
        ArrayList<Plato> lista = new  ArrayList<Plato>();
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
    
    public ObservableList<Producto>getProducto(){
        
        ArrayList<Producto> lista = new ArrayList<Producto>();
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarProductos");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                
                lista.add(new Producto(resultado.getInt("codigoProducto"),
                                       resultado.getString("nombreProducto"),
                                       resultado.getInt("cantidad")));
            }
        }catch(Exception e){
            
            e.printStackTrace();
        }
        return listaProducto = FXCollections.observableArrayList(lista);
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
                imgAgregar.setImage(new Image("/org/josetejaxun/image/IconoAgregar.png"));
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
                btnEliminar.setDisable(false);
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/josetejaxun/image/IconoAgregar.png"));
                imgEliminar.setImage(new Image("/org/josetejaxun/image/IconoEliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
            break;
            
            default:
                
                if(tblProductoHasPlatos.getSelectionModel().getSelectedItem() != null){
                    
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?", "Eliminar ProductoHasPlato", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        
                        try{
                            
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_eliminarProductos_has_platos(?)");
                            procedimiento.setInt(1, ((ProductoHasPlato)tblProductoHasPlatos.getSelectionModel().getSelectedItem()).getProductos_codigoProducto());
                            procedimiento.execute();
                            listaProductoHasPlato.remove(tblProductoHasPlatos.getSelectionModel().getSelectedItem());
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
                
                if(tblProductoHasPlatos.getSelectionModel().getSelectedItem() != null){
                    
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnReporte.setVisible(true);
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
                btnReporte.setVisible(false);
                imgEditar.setImage(new Image("/org/josetejaxun/image/IconoEditar.png"));
                imgReporte.setImage(new Image("/org/josetejaxun/image/IconoReporte.png"));
                tipoDeOperacion = operaciones.NINGUNO;
            break;
        }
    }
    
    public void guardar(){
        
        ProductoHasPlato registro = new ProductoHasPlato();
        registro.setProductos_codigoProducto(Integer.parseInt(txtProductoCodigoProductoPHP.getText()));
        registro.setCodigoPlato(((Plato)cmbCodigoPlatoPHP.getSelectionModel().getSelectedItem()).getCodigoPlato());
        registro.setCodigoProducto(((Producto)cmbCodigoProductoPHP.getSelectionModel().getSelectedItem()).getCodigoProducto());
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_agregarProductos_has_platos(?, ?, ?)");
            procedimiento.setInt(1, registro.getProductos_codigoProducto());
            procedimiento.setInt(2, registro.getCodigoPlato());
            procedimiento.setInt(3, registro.getCodigoProducto());
            procedimiento.execute();
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
    
    public void actualizar(){
        
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_editarProductos_has_platos(?, ?, ?)");
            ProductoHasPlato registro = (ProductoHasPlato)tblProductoHasPlatos.getSelectionModel().getSelectedItem();
            registro.setCodigoPlato(((Plato)cmbCodigoPlatoPHP.getSelectionModel().getSelectedItem()).getCodigoPlato());
            registro.setCodigoProducto(((Producto)cmbCodigoProductoPHP.getSelectionModel().getSelectedItem()).getCodigoProducto());
            procedimiento.setInt(1, registro.getProductos_codigoProducto());
            procedimiento.setInt(2, registro.getCodigoPlato());
            procedimiento.setInt(3, registro.getCodigoProducto());
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
    
    public Producto buscarProducto(int codigoProducto){
        
        Producto resultado = null;
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_buscarProducto(?)");
            procedimiento.setInt(1, codigoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                
                resultado = new Producto(registro.getInt("codigoProducto"),
                                         registro.getString("nombreProducto"),
                                         registro.getInt("cantidad"));
            }
        }catch(Exception e){
            
            e.printStackTrace();
        }
        return resultado;
    }
    
    public void seleccionarElemento(){
        
        txtProductoCodigoProductoPHP.setText(String.valueOf((((ProductoHasPlato)tblProductoHasPlatos.getSelectionModel().getSelectedItem()).getProductos_codigoProducto())));
        cmbCodigoPlatoPHP.getSelectionModel().select(buscarPlato(((ProductoHasPlato)tblProductoHasPlatos.getSelectionModel().getSelectedItem()).getCodigoPlato()));
        cmbCodigoProductoPHP.getSelectionModel().select(buscarProducto(((ProductoHasPlato)tblProductoHasPlatos.getSelectionModel().getSelectedItem()).getCodigoProducto()));
    }

    public void desactivarControles(){
        
        txtProductoCodigoProductoPHP.setEditable(false);
        cmbCodigoPlatoPHP.setDisable(true);
        cmbCodigoProductoPHP.setDisable(true);
    }
    
    public void activarControles(){
        
        txtProductoCodigoProductoPHP.setEditable(true);
        cmbCodigoPlatoPHP.setDisable(false);
        cmbCodigoProductoPHP.setDisable(false);
    }
    
    public void limpiarControles(){
        
        txtProductoCodigoProductoPHP.clear();
        cmbCodigoPlatoPHP.setValue(null);
        cmbCodigoProductoPHP.setValue(null);
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaMenuPlato(){
        
        escenarioPrincipal.ventanaPlato();
    }
    
    public void ventanaMenuProducto(){
        
        escenarioPrincipal.ventanaProducto();
    }
    
    public void ventanaMenuPrincipal(){
        
        escenarioPrincipal.ventanaMenuPrincipal();
    }
}
