package compras.model;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by user on 21/08/2017.
 */
public class ProductoCompra {
    private SimpleIntegerProperty codProducto;
    private SimpleStringProperty nomProducto;
    private SimpleFloatProperty precioVenta;
    private SimpleIntegerProperty idProveedor;
    private SimpleIntegerProperty idPresentacion;
    private SimpleStringProperty nomPresentacion;
    private SimpleFloatProperty precioCompra;
    private SimpleIntegerProperty stock;
    private SimpleStringProperty fechaVencimiento;

    public ProductoCompra(int codProducto,String nomProducto,float precioVenta,int idProveedor,int idPresentacion,
                          String nomPresentacion,float precioCompra, int stock,String fechaVencimiento){
        this.codProducto = new SimpleIntegerProperty(codProducto);
        this.nomProducto = new SimpleStringProperty(nomProducto);
        this.precioVenta = new SimpleFloatProperty(precioVenta);
        this.idProveedor = new SimpleIntegerProperty(idProveedor);
        this.idPresentacion = new SimpleIntegerProperty(idPresentacion);
        this.nomPresentacion = new SimpleStringProperty(nomPresentacion);
        this.precioCompra = new SimpleFloatProperty(precioCompra);
        this.stock = new SimpleIntegerProperty(stock);
        this.fechaVencimiento = new SimpleStringProperty(fechaVencimiento);
    }

    public int getCodProducto() {
        return codProducto.get();
    }

    public SimpleIntegerProperty codProductoProperty() {
        return codProducto;
    }

    public void setCodProducto(int codProducto) {
        this.codProducto.set(codProducto);
    }

    public String getNomProducto() {
        return nomProducto.get();
    }

    public SimpleStringProperty nomProductoProperty() {
        return nomProducto;
    }

    public void setNomProducto(String nomProducto) {
        this.nomProducto.set(nomProducto);
    }

    public float getPrecioVenta() {
        return precioVenta.get();
    }

    public SimpleFloatProperty precioVentaProperty() {
        return precioVenta;
    }

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta.set(precioVenta);
    }

    public int getIdProveedor() {
        return idProveedor.get();
    }

    public SimpleIntegerProperty idProveedorProperty() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor.set(idProveedor);
    }

    public int getIdPresentacion() {
        return idPresentacion.get();
    }

    public SimpleIntegerProperty idPresentacionProperty() {
        return idPresentacion;
    }

    public void setIdPresentacion(int idPresentacion) {
        this.idPresentacion.set(idPresentacion);
    }

    public String getNomPresentacion() {
        return nomPresentacion.get();
    }

    public SimpleStringProperty nomPresentacionProperty() {
        return nomPresentacion;
    }

    public void setNomPresentacion(String nomPresentacion) {
        this.nomPresentacion.set(nomPresentacion);
    }

    public float getPrecioCompra() {
        return precioCompra.get();
    }

    public SimpleFloatProperty precioCompraProperty() {
        return precioCompra;
    }

    public void setPrecioCompra(float precioCompra) {
        this.precioCompra.set(precioCompra);
    }

    public int getStock() {
        return stock.get();
    }

    public SimpleIntegerProperty stockProperty() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock.set(stock);
    }

    public String getFechaVencimiento() {
        return fechaVencimiento.get();
    }

    public SimpleStringProperty fechaVencimientoProperty() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento.set(fechaVencimiento);
    }
}
