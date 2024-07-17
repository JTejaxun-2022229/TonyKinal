package org.josetejaxun.controller;
import com.jfoenix.controls.JFXTimePicker;
import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
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
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.josetejaxun.bean.Empleado;
import org.josetejaxun.bean.Servicio;
import org.josetejaxun.bean.ServicioHasEmpleado;
import org.josetejaxun.db.Conexion;
import org.josetejaxun.main.Principal;

public class ServicioHasEmpleadoController implements Initializable{

    private enum operaciones{NUEVO ,GUARDAR, ELIINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<ServicioHasEmpleado> listaSHE;
    private ObservableList<Servicio> listaServicio;
    private ObservableList<Empleado> listaEmpleado;
    private DatePicker fecha;
    @FXML private JFXTimePicker horaSHE;
    @FXML private TextField txtServicioCodigoServicio;
    @FXML private TextField txtLugarEvento;
    @FXML private TableView tblServicioHasEmpleados;
    @FXML private TableColumn colServicioCodigoServicio;
    @FXML private TableColumn colLugarEvento;
    @FXML private TableColumn colHoraEvento;
    @FXML private TableColumn colFechaEvento;
    @FXML private TableColumn colCodigoServicio;
    @FXML private TableColumn colCodigoEmpleado;
    @FXML private GridPane grpServicioHasEmpleado;
    @FXML private ComboBox cmbCodigoServicio;
    @FXML private ComboBox cmbCodigoEmpleado;
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
        fecha = new DatePicker(Locale.ENGLISH);
        fecha.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fecha.getCalendarView().todayButtonTextProperty().set("Today");
        fecha.getCalendarView().showWeeksProperty();
        fecha.getStylesheets().add("/org/josetejaxun/resource/TonnysKinal.css");
        fecha.setDisable(true);
        grpServicioHasEmpleado.add(fecha, 1, 1);
        cmbCodigoServicio.setItems(getServicio());
        cmbCodigoEmpleado.setItems(getEmpleado());
    }
    
    public void cargarDatos(){
        
        tblServicioHasEmpleados.setItems(getServicioHasEmpleado());
        colServicioCodigoServicio.setCellValueFactory(new PropertyValueFactory<ServicioHasEmpleado, Integer>("servicios_codigoServicio"));
        colCodigoServicio.setCellValueFactory(new PropertyValueFactory<ServicioHasEmpleado, Integer>("codigoServicio"));
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<ServicioHasEmpleado, Integer>("codigoEmpleado"));
        colFechaEvento.setCellValueFactory(new PropertyValueFactory<ServicioHasEmpleado, Date>("fechaEvento"));
        colHoraEvento.setCellValueFactory(new PropertyValueFactory<ServicioHasEmpleado, Time>("horaEvento"));
        colLugarEvento.setCellValueFactory(new PropertyValueFactory<ServicioHasEmpleado, String>("lugarEvento"));
    }
    
    public ObservableList<ServicioHasEmpleado>getServicioHasEmpleado(){
        
        ArrayList<ServicioHasEmpleado> lista = new ArrayList<ServicioHasEmpleado>();
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarServicios_has_Empleados");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                
                lista.add(new ServicioHasEmpleado(resultado.getInt("servicios_codigoServicio"),
                                                  resultado.getInt("codigoServicio"),
                                                  resultado.getInt("codigoEmpleado"),
                                                  resultado.getDate("fechaEvento"),
                                                  resultado.getTime("horaEvento"),
                                                  resultado.getString("lugarEvento")));
            }
        }catch(Exception e){
            
            e.printStackTrace();
        }
        
        return listaSHE = FXCollections.observableArrayList(lista);
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
                btnEliminar.setDisable(true);
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/josetejaxun/image/IconoAgregar.png"));
                imgEliminar.setImage(new Image("/org/josetejaxun/image/IconoEliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
            break;
        }
    }
    
    public void editar(){
        
        switch(tipoDeOperacion){
            
            case NINGUNO:
                
                if(tblServicioHasEmpleados.getSelectionModel().getSelectedItem() != null){
                    
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
                btnReporte.setDisable(true);
                imgEditar.setImage(new Image("/org/josetejaxun/image/IconoEditar.png"));
                imgReporte.setImage(new Image("/org/josetejaxun/image/IconoReporte.png"));
                tipoDeOperacion = operaciones.NINGUNO;
            break;
        }
    }
    
    public void guardar(){
        
        ServicioHasEmpleado registro = new ServicioHasEmpleado();
        LocalTime select = horaSHE.getValue();
        java.sql.Time tiempo = java.sql.Time.valueOf(select);
        registro.setServicios_codigoServicio(Integer.parseInt(txtServicioCodigoServicio.getText()));
        registro.setCodigoServicio(((Servicio)cmbCodigoServicio.getSelectionModel().getSelectedItem()).getCodigoServicio());
        registro.setCodigoEmpleado(((Empleado)cmbCodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
        registro.setFechaEvento(fecha.getSelectedDate());
        registro.setHoraEvento(tiempo);
        registro.setLugarEvento(txtLugarEvento.getText());
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_agregarServicios_has_Empleados(?, ?, ?, ?, ?, ?)");
            procedimiento.setInt(1, registro.getServicios_codigoServicio());
            procedimiento.setInt(2, registro.getCodigoServicio());
            procedimiento.setInt(3, registro.getCodigoEmpleado());
            procedimiento.setDate(4, new java.sql.Date(registro.getFechaEvento().getTime()));
            procedimiento.setTime(5, tiempo);
            procedimiento.setString(6, registro.getLugarEvento());
            procedimiento.execute();
            listaSHE.add(registro);
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
    
    public void actualizar(){
        
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_editarServicios_has_Empleados(?, ?, ?, ?, ?, ?)");
            ServicioHasEmpleado registro = new ServicioHasEmpleado();
            LocalTime selectTime = horaSHE.getValue();
            java.sql.Time slqTime = java.sql.Time.valueOf(selectTime);
            
            registro.setServicios_codigoServicio(Integer.parseInt(txtServicioCodigoServicio.getText()));
            registro.setCodigoServicio(((Servicio)cmbCodigoServicio.getSelectionModel().getSelectedItem()).getCodigoServicio());
            registro.setCodigoEmpleado(((Empleado)cmbCodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
            registro.setFechaEvento(fecha.getSelectedDate());
            registro.setHoraEvento(slqTime);
            registro.setLugarEvento(txtLugarEvento.getText());
            
            procedimiento.setInt(1, registro.getServicios_codigoServicio());
            procedimiento.setInt(2, registro.getCodigoServicio());
            procedimiento.setInt(3, registro.getCodigoEmpleado());
            procedimiento.setDate(4, new java.sql.Date(registro.getFechaEvento().getTime()));
            procedimiento.setTime(5, slqTime);
            procedimiento.setString(6, registro.getLugarEvento());
            procedimiento.execute();
            listaSHE.add(registro);
            cargarDatos();
        }catch(Exception e){
            
            e.printStackTrace();
        }
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
    
    public Empleado buscarEmpleado(int codigoEmpleado){
        
        Empleado resultado = null;
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_buscarEmpleado(?)");
            procedimiento.setInt(1, codigoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                
                resultado = new Empleado(registro.getInt("codigoEmpleado"),
                                         registro.getInt("numeroEmpleado"),
                                         registro.getString("apellidoEmpleado"),
                                         registro.getString("nombreEmpleado"),
                                         registro.getString("direccionEmpleado"),
                                         registro.getString("telefonoContacto"),
                                         registro.getString("gradoCocinero"),
                                         registro.getInt("codigoTipoEmpleado"));
            }
        }catch(Exception e){
            
            e.printStackTrace();
        }
        return resultado;
    }
    
    public void seleccionarElemento(){
        
        txtServicioCodigoServicio.setText(String.valueOf(((ServicioHasEmpleado)tblServicioHasEmpleados.getSelectionModel().getSelectedItem()).getServicios_codigoServicio()));
        cmbCodigoServicio.getSelectionModel().select(buscarServicio(((ServicioHasEmpleado)tblServicioHasEmpleados.getSelectionModel().getSelectedItem()).getCodigoServicio()));
        cmbCodigoEmpleado.getSelectionModel().select(buscarEmpleado(((ServicioHasEmpleado)tblServicioHasEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        fecha.selectedDateProperty().set(((ServicioHasEmpleado)tblServicioHasEmpleados.getSelectionModel().getSelectedItem()).getFechaEvento());
        horaSHE.promptTextProperty().set(String.valueOf(((ServicioHasEmpleado)tblServicioHasEmpleados.getSelectionModel().getSelectedItem()).getHoraEvento()));
        txtLugarEvento.setText(String.valueOf(((ServicioHasEmpleado)tblServicioHasEmpleados.getSelectionModel().getSelectedItem()).getLugarEvento()));
    }
    
    public void desactivarControles(){
        
        txtServicioCodigoServicio.setEditable(false);
        cmbCodigoServicio.setDisable(true);
        cmbCodigoEmpleado.setDisable(true);
        fecha.setDisable(true);
        horaSHE.setDisable(true);
        txtLugarEvento.setEditable(false);
    }
    
    public void activarControles(){
        
        txtServicioCodigoServicio.setEditable(true);
        cmbCodigoServicio.setDisable(false);
        cmbCodigoEmpleado.setDisable(false);
        fecha.setDisable(false);
        horaSHE.setDisable(false);
        txtLugarEvento.setEditable(true);
    }
    
    public void limpiarControles(){
        
        txtServicioCodigoServicio.clear();
        cmbCodigoServicio.setValue(null);
        cmbCodigoEmpleado.setValue(null);
        fecha.selectedDateProperty().set(null);
        horaSHE.setValue(null);
        txtLugarEvento.clear();
    }
    
    public void ventanaEmpleado(){
    
        escenarioPrincipal.ventanaEmpleado();
    }
    
    public void ventanaServicio(){
        
        escenarioPrincipal.ventanaServicio();
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
