package ventas.model;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class VentaPorEmpleado {

    private SimpleIntegerProperty idOrdenPedido;
    private SimpleIntegerProperty idDetalleOrden;
    private SimpleIntegerProperty idProducto;
    private SimpleStringProperty nomProducto;
    private SimpleIntegerProperty cantidad;
    private SimpleFloatProperty precioVenta;
    private SimpleStringProperty nomCliente;
    private SimpleStringProperty nomEmpleado;

    public VentaPorEmpleado() {}

    public VentaPorEmpleado(int idOrdenPedido,
                            int idDetalleOrden,
                            int idProducto,
                            String nomProducto,
                            int cantidad,
                            float precioVenta,
                            String nomCliente,
                            String nomEmpleado) {
        this.idOrdenPedido = new SimpleIntegerProperty(idOrdenPedido);
        this.idDetalleOrden = new SimpleIntegerProperty(idDetalleOrden);
        this.idProducto = new SimpleIntegerProperty(idProducto);
        this.nomProducto = new SimpleStringProperty(nomProducto);
        this.cantidad = new SimpleIntegerProperty(cantidad);
        this.precioVenta = new SimpleFloatProperty(precioVenta);
        this.nomCliente = new SimpleStringProperty(nomCliente);
        this.nomEmpleado = new SimpleStringProperty(nomEmpleado);
    }

    public int getIdOrdenPedido() {
        return idOrdenPedido.get();
    }

    public SimpleIntegerProperty idOrdenPedidoProperty() {
        return idOrdenPedido;
    }

    public void setIdOrdenPedido(int idOrdenPedido) {
        this.idOrdenPedido.set(idOrdenPedido);
    }

    public int getIdDetalleOrden() {
        return idDetalleOrden.get();
    }

    public SimpleIntegerProperty idDetalleOrdenProperty() {
        return idDetalleOrden;
    }

    public void setIdDetalleOrden(int idDetalleOrden) {
        this.idDetalleOrden.set(idDetalleOrden);
    }

    public int getIdProducto() {
        return idProducto.get();
    }

    public SimpleIntegerProperty idProductoProperty() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto.set(idProducto);
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

    public int getCantidad() {
        return cantidad.get();
    }

    public SimpleIntegerProperty cantidadProperty() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad.set(cantidad);
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

    public String getNomCliente() {
        return nomCliente.get();
    }

    public SimpleStringProperty nomClienteProperty() {
        return nomCliente;
    }

    public void setNomCliente(String nomCliente) {
        this.nomCliente.set(nomCliente);
    }

    public String getNomEmpleado() {
        return nomEmpleado.get();
    }

    public SimpleStringProperty nomEmpleadoProperty() {
        return nomEmpleado;
    }

    public void setNomEmpleado(String nomEmpleado) {
        this.nomEmpleado.set(nomEmpleado);
    }
}
