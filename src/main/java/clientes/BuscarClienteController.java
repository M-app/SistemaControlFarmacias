package clientes;

import clientes.model.Cliente;
import clientes.model.ClienteDAO;
import domain.PasarParametros;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import pos.PosController;
import pos.model.Pos;
import productos.model.ProductoMostrar;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuscarClienteController implements Initializable {

    @FXML
    private GridPane rootClientes;

    @FXML
    private TableView<Cliente> tblDatos;

    @FXML
    private TableColumn<Cliente, String> colCodgo;

    @FXML
    private TableColumn<Cliente, String> colNombre;

    @FXML
    private TableColumn<Cliente, String> colApellidos;

    @FXML
    private TableColumn<Cliente, String> colDireccion;

    @FXML
    private TableColumn<Cliente, String> colTelefono;

    @FXML
    private TableColumn<Cliente, String> colCelular;

    @FXML
    private TextField txtBuscar;

    public static ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    ClienteDAO clienteDAO = new ClienteDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDataToTable();
        configHeightsOfColums();
        matchDataWithTable();
        configClickTable();
        configFiltrado();
    }

    private void setDataToTable(){
        listaClientes.setAll(clienteDAO.getClientes());
    }

    private void configClickTable(){
        tblDatos.setOnMouseClicked((event -> {
            if(event.getClickCount() == 1){
                if (tblDatos.getSelectionModel().getSelectedItem() != null){
                    Cliente c = tblDatos.getSelectionModel().getSelectedItem();
                    PosController.setmCliente(c);
                    PosController.setmStringCliente(c.getCodigo() + ". " + c.getNombre() + " " + c.getApellidos());
                    close();
                }
            }
        }));
    }

    private void configHeightsOfColums(){

        colNombre.setCellFactory(new Callback<TableColumn<Cliente, String>, TableCell<Cliente, String>>() {
            @Override
            public TableCell<Cliente, String> call(TableColumn<Cliente, String> param) {
                TableCell<Cliente,String> cell = new TableCell<>();
                Text text = new Text();
                cell.setGraphic(text);
                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                text.wrappingWidthProperty().bind(colNombre.widthProperty());
                text.textProperty().bind(cell.itemProperty());
                return cell;
            }
        });

        colApellidos.setCellFactory(new Callback<TableColumn<Cliente, String>, TableCell<Cliente, String>>() {
            @Override
            public TableCell<Cliente, String> call(TableColumn<Cliente, String> param) {
                TableCell<Cliente,String> cell = new TableCell<>();
                Text text = new Text();
                cell.setGraphic(text);
                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                text.wrappingWidthProperty().bind(colApellidos.widthProperty());
                text.textProperty().bind(cell.itemProperty());
                return cell;
            }
        });

        colDireccion.setCellFactory(new Callback<TableColumn<Cliente, String>, TableCell<Cliente, String>>() {
            @Override
            public TableCell<Cliente, String> call(TableColumn<Cliente, String> param) {
                TableCell<Cliente,String> cell = new TableCell<>();
                Text text = new Text();
                cell.setGraphic(text);
                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                text.wrappingWidthProperty().bind(colDireccion.widthProperty());
                text.textProperty().bind(cell.itemProperty());
                return cell;
            }
        });
    }

    private void configFiltrado(){
        FilteredList<Cliente> filteredList = new FilteredList<Cliente>(listaClientes, p -> true);
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(cliente -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(cliente.getNombre()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (cliente.getApellidos().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (cliente.getDireccion().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                } else if (String.valueOf(cliente.getTelefono()).toLowerCase().contains(lowerCaseFilter)){
                    return true;
                } else if (String.valueOf(cliente.getCelular()).toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                return false; // Does not match.
            });
        });
        SortedList<Cliente> sortedList = new SortedList<Cliente>(filteredList);
        sortedList.comparatorProperty().bind(tblDatos.comparatorProperty());
        tblDatos.setItems(sortedList);
    }

    private void matchDataWithTable(){
        colCodgo.setCellValueFactory(new PropertyValueFactory<Cliente, String>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellidos"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Cliente, String>("direccion"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefono"));
        colCelular.setCellValueFactory(new PropertyValueFactory<Cliente, String>("celular"));
        tblDatos.setItems(listaClientes);
    }

    private void close(){
        Stage thisStage =  (Stage) rootClientes.getScene().getWindow();
        thisStage.close();
    }
}
