package compras;

import compras.model.DetalleCompra;
import compras.model.ProductoCompra;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import pos.PasarNoCompra;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by user on 13/07/2017.
 */
public class AgregarProductoCompraController implements Initializable,PasarIdProveedor, PasarNoCompra {

    @FXML
    private GridPane rootPos;

    @FXML
    private TableView<ProductoCompra> tblDatos;

    @FXML
    private TableColumn<ProductoCompra, Integer> colNoProducto;

    @FXML
    private TableColumn<ProductoCompra, String> colNombre;

    @FXML
    private TableColumn<ProductoCompra,String> colPresentacion;

    @FXML
    private TableColumn<ProductoCompra, Integer> colStock;

    @FXML
    private TableColumn<ProductoCompra,Float> colPrecioCompra;

    @FXML
    private TableColumn<ProductoCompra, String> colFechaVencimiento;

    @FXML
    private Label lblNoCompra;

    @FXML
    private TextField txtBuscar;

    private String mIdProveedor = "";
    private String mNumCompra = "";

    CompraDAO compraDAO = new CompraDAO();

    public static ObservableList<ProductoCompra> listaProductos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        matchDataWithTable();
        configClickListenerTableView();
    }

    private void addDataToList(){
        listaProductos.setAll(compraDAO.getProductosXProveedorConPresentacion(mIdProveedor));
    }

    private void configClickListenerTableView(){
        tblDatos.setOnMouseClicked((event -> {
            if(event.getClickCount() == 1){
                if(tblDatos.getSelectionModel().getSelectedItem() != null){
                    addItemToList(tblDatos.getSelectionModel().getSelectedItem());
                }
            }
        }));
    }

    private void addItemToList(ProductoCompra producto){
        if(ModificarCompraController.listaCodigos.contains(producto.getCodProducto())){
            for(int i = 0; i<ModificarCompraController.listaDetalleCompra.size(); i++){
                DetalleCompra dc = ModificarCompraController.listaDetalleCompra.get(i);
                if(dc.getIdProducto() == producto.getCodProducto()){
                    dc.setCantidad(dc.getCantidad()+1);
                    dc.setSubTotal(dc.getCantidad()*dc.getPrecioCompra());
                    ModificarCompraController.listaDetalleCompra.set(i,dc);
                    break;
                }
            }
        }else{
            ModificarCompraController.listaCodigos.add(producto.getCodProducto());
            DetalleCompra dc = new DetalleCompra(
                    0,
                    Integer.parseInt(mNumCompra),
                    producto.getCodProducto(),
                    producto.getNomProducto(),
                    producto.getPrecioCompra(),
                    1,
                    producto.getPrecioCompra(),
                    "");
            ModificarCompraController.listaDetalleCompra.add(dc);
        }
    }



    private void matchDataWithTable(){
        colNoProducto.setCellValueFactory(new PropertyValueFactory<ProductoCompra, Integer>("codProducto"));
        colNombre.setCellValueFactory(new PropertyValueFactory<ProductoCompra, String>("nomProducto"));
        colPresentacion.setCellValueFactory(new PropertyValueFactory<ProductoCompra, String>("nomPresentacion"));
        colPrecioCompra.setCellValueFactory(new PropertyValueFactory<ProductoCompra, Float>("precioCompra"));
        colStock.setCellValueFactory(new PropertyValueFactory<ProductoCompra, Integer>("stock"));
        colFechaVencimiento.setCellValueFactory(new PropertyValueFactory<ProductoCompra, String>("fechaVencimiento"));
        colNoProducto.prefWidthProperty().bind(tblDatos.widthProperty().multiply(0.10));
        colNombre.prefWidthProperty().bind(tblDatos.widthProperty().multiply(0.30));
        colPresentacion.prefWidthProperty().bind(tblDatos.widthProperty().multiply(0.15));
        colPrecioCompra.prefWidthProperty().bind(tblDatos.widthProperty().multiply(0.15));
        colStock.prefWidthProperty().bind(tblDatos.widthProperty().multiply(0.10));
        colFechaVencimiento.prefWidthProperty().bind(tblDatos.widthProperty().multiply(0.20));
        tblDatos.setItems(listaProductos);
    }

    @Override
    public void pasarId(String idProveedor) {
        mIdProveedor = idProveedor;
        addDataToList();
    }

    @Override
    public void pasarNoCompra(int noCompra) {
        mNumCompra = String.valueOf(noCompra);
    }
}
