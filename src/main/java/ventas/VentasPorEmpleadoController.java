package ventas;

import bd.Conexion;
import clientes.model.Cliente;
import compras.model.Compra;
import empleados.model.Empleado;
import empleados.model.EmpleadoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;
import pos.model.ProductoReporte;
import proveedores.model.ProveedorCodNom;
import reportes.Reporte;
import resumen.ventas.model.Venta;
import util.Util;
import ventas.model.VentaPorEmpleado;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class VentasPorEmpleadoController implements Initializable {

    @FXML
    private ComboBox<Empleado> comboEmpleados;

    @FXML
    private RadioButton rdbHoy;

    @FXML
    private ToggleGroup tablaVentas;

    @FXML
    private DatePicker txtDeste;

    @FXML
    private DatePicker txtHasta;

    @FXML
    private Button btnVer;

    @FXML
    private TextField txtFiltrar;

    @FXML
    private TableView<VentaPorEmpleado> tblDatos;

    @FXML
    private TableColumn<VentaPorEmpleado, Integer> colIdProducto;

    @FXML
    private TableColumn<VentaPorEmpleado, String> colNombreProducto;

    @FXML
    private TableColumn<VentaPorEmpleado, Integer> colCantidad;

    @FXML
    private TableColumn<VentaPorEmpleado, String> colCliente;

    @FXML
    private Button btnReporte;

    @FXML
    private CheckBox checkGuardar;

    @FXML
    private CheckBox checkImprimir;

    private ObservableList<Empleado> listaEmpleados = FXCollections.observableArrayList();
    private ObservableList<VentaPorEmpleado> listaVentas = FXCollections.observableArrayList();

    EmpleadoDAO empleadoDAO = new EmpleadoDAO();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        llenarComboEmpleado();
        convertComboEmpleadoToString();
        matchDataWithTable();
        configFiltrado();


        btnVer.setDisable(true);
        comboEmpleados.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (comboEmpleados.getSelectionModel().getSelectedItem() != null){
                    btnVer.setDisable(false);
                }
            }
        });
        btnVer.setOnAction((event -> {
            setDataToTable();
        }));

        btnReporte.setOnAction((event -> {
            verReporte();
        }));
    }

    private void llenarComboEmpleado(){
        listaEmpleados.setAll(empleadoDAO.getEmpleados());
        comboEmpleados.setItems(listaEmpleados);
    }

    private void setDataToTable(){
        listaVentas.setAll(getVentasPorEmpleado());
    }

    private void matchDataWithTable(){
        colIdProducto.setCellValueFactory(new PropertyValueFactory<VentaPorEmpleado, Integer>("idProducto"));
        colNombreProducto.setCellValueFactory(new PropertyValueFactory<VentaPorEmpleado, String>("nomProducto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<VentaPorEmpleado, Integer>("cantidad"));
        colCliente.setCellValueFactory(new PropertyValueFactory<VentaPorEmpleado, String>("nomCliente"));
        colIdProducto.prefWidthProperty().bind(tblDatos.widthProperty().multiply(0.10));
        colNombreProducto.prefWidthProperty().bind(tblDatos.widthProperty().multiply(0.40));
        colCantidad.prefWidthProperty().bind(tblDatos.widthProperty().multiply(0.10));
        colCliente.prefWidthProperty().bind(tblDatos.widthProperty().multiply(0.40));
        tblDatos.setItems(listaVentas);
    }

    private void verReporte(){
//        List<VentaPorEmpleado> listaVentas = new ArrayList<>();
//        for(int i = 0; i<listaCompras.size(); i++){
//            listaReporte.add(new ProductoReporte(String.valueOf(listaCompras.get(i).getCantidad()),
//                    listaCompras.get(i).getNombre(),
//                    String.valueOf(listaCompras.get(i).getPrecio()),
//                    "%" + String.valueOf(listaCompras.get(i).getDescuento()),
//                    String.valueOf(listaCompras.get(i).getSubtotal())));
//        }
        Reporte reporte = new Reporte();
        Empleado empl = comboEmpleados.getSelectionModel().getSelectedItem();
        reporte.reporteProductosPorVendedor(listaVentas,empl.getNombre() + " " + empl.getApellidos(),checkGuardar.isSelected(),checkImprimir.isSelected());
    }

    private void configFiltrado(){
        FilteredList<VentaPorEmpleado> filteredList = new FilteredList<VentaPorEmpleado>(listaVentas, c -> true);
        txtFiltrar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(venta -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(venta.getIdProducto()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (venta.getNomProducto().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (venta.getNomCliente().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                return false; // Does not match.
            });
        });
        SortedList<VentaPorEmpleado> sortedList = new SortedList<VentaPorEmpleado>(filteredList);
        sortedList.comparatorProperty().bind(tblDatos.comparatorProperty());
        tblDatos.setItems(sortedList);
    }

    private String consulta(String nomEmpleado, String fecha){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(dateFormat.parse(fecha));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 1);
        return String.format("select id_orpedido, num_ordenp, id_producto, nom_pro, sum(cantidad) as cantidad, precio_venta, nom_cli, nom_empleado, total from detalleordenpedido inner join ordenpedido on id_orpedido = num_ordenpedido where fecha >='%s 00:00:00' and fecha < '%s 00:00:00' and nom_empleado LIKE '%s' group by id_producto",fecha,dateFormat.format(c.getTime()),nomEmpleado);
    }

    public List<VentaPorEmpleado> getVentasPorEmpleado(){
        VentaPorEmpleado ventaPorEmpleado;
        List<VentaPorEmpleado> listaVentas = new ArrayList<>();
        Connection conn = Conexion.getInstance().getConexion();
        Empleado empl = comboEmpleados.getSelectionModel().getSelectedItem();
        final String consulta = consulta(empl.getNombre() +  " " + empl.getApellidos(), new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        System.out.println("consulta: " + consulta);
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while(rs.next()){
                ventaPorEmpleado = new VentaPorEmpleado(
                        rs.getInt("id_orpedido"),
                        rs.getInt("num_ordenp"),
                        rs.getInt("id_producto"),
                        rs.getString("nom_pro"),
                        rs.getInt("cantidad"),
                        rs.getFloat("precio_venta"),
                        rs.getString("nom_cli"),
                        rs.getString("nom_empleado")
                );
                listaVentas.add(ventaPorEmpleado);
            }
        } catch (SQLException e) {
            final String mensaje = "Error en la Consulta Ventas Por Empleado";
            e.printStackTrace();
            Util.makeError(mensaje,e.getLocalizedMessage());
        }
        return listaVentas;
    }

    private void convertComboEmpleadoToString(){
        comboEmpleados.setCellFactory(new Callback<ListView<Empleado>, ListCell<Empleado>>() {
            @Override
            public ListCell<Empleado> call(ListView<Empleado> param) {
                ListCell<Empleado> cell = new ListCell<Empleado>(){
                    @Override
                    protected void updateItem(Empleado item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText("");
                        }else {
                            setText(getStringForEmpleado(item));
                        }
                    }
                };
                return cell;
            }
        });

        comboEmpleados.setButtonCell(new ListCell<Empleado>() {
            @Override
            protected void updateItem(Empleado item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText("");
                }else {
                    setText(getStringForEmpleado(item));
                }
            }
        });

        comboEmpleados.setConverter(new StringConverter<Empleado>() {
            private Map<String,Empleado> map = new HashMap<String, Empleado>();
            @Override
            public String toString(Empleado object) {
                if(object != null){
                    String str = getStringForEmpleado(object);
                    map.put(str,object);
                    return str;
                }else{
                    return "";
                }
            }

            @Override
            public Empleado fromString(String string) {
                if(!map.containsKey(string)){
                    comboEmpleados.setValue(null);
                    comboEmpleados.getEditor().clear();
                    return null;
                }
                return map.get(string);
            }
        });
    }

    private String getStringForEmpleado(Empleado empleado){
        return empleado.getCodigo() + ". " + empleado.getNombre() + " " + empleado.getApellidos();
    }

}
