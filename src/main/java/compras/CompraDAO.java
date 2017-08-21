package compras;

import bd.Conexion;
import compras.model.Compra;
import compras.model.DetalleCompra;
import compras.model.ProductoCompra;
import productos.model.Producto;
import util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by user on 30/06/2017.
 */
public class CompraDAO {

    public Collection<ProductoCompra> getProductosXProveedorConPresentacion(String idProveedor){
        Connection conn = Conexion.getInstance().getConexion();
        final String consulta = String.format(
                "select p.cod_pro,p.nom_pro,p.pre_venta,p.id_prov,p.id_pres,v.nom_pre,p.pre_compra,p.stock,p.fecha_venc from producto p\n" +
                        "inner join presentacion v on v.cod_pre = p.id_pres \n" +
                        "where id_prov=%s;",idProveedor
        );
        final List<ProductoCompra> listaProductos = new ArrayList<>();
        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            ProductoCompra productoCompra;
            while (rs.next()){
                productoCompra = new ProductoCompra(
                        rs.getInt("cod_pro"),
                        rs.getString("nom_pro"),
                        rs.getFloat("pre_venta"),
                        rs.getInt("id_prov"),
                        rs.getInt("id_pres"),
                        rs.getString("nom_pre"),
                        rs.getFloat("pre_compra"),
                        rs.getInt("stock"),
                        rs.getString("fecha_venc")
                );
                listaProductos.add(productoCompra);
            }
        }catch (SQLException ex){
            final String mensaje = "Error en la tabla Productos";
            ex.printStackTrace();
            Util.makeError(mensaje,ex.getLocalizedMessage());
        }
        return listaProductos;
    }

    public Collection<Producto> getProductosXProveedor(String idProveedor){
        Connection conn = Conexion.getInstance().getConexion();
        final String consulta = String.format(
                "select * from producto where id_prov = %s;",idProveedor);
        final List<Producto> listaProductos = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            Producto producto;
            while(rs.next()){
                producto = new Producto(
                        rs.getInt("cod_pro"),
                        rs.getString("nom_pro"),
                        rs.getFloat("pre_compra"),
                        rs.getFloat("pre_venta"),
                        rs.getString("fecha_venc"),
                        rs.getInt("stock"),
                        rs.getInt("id_prov"),
                        rs.getInt("id_pres"),
                        rs.getInt("id_dole"),
                        rs.getInt("cod_col")
                );
                listaProductos.add(producto);
            }

        } catch (SQLException e) {
            final String mensaje = "Error en la tabla Productos";
            e.printStackTrace();
            Util.makeError(mensaje,e.getLocalizedMessage());
        }
        return listaProductos;
    }

    public boolean eliminarDetallesXCompra(String idCompra){
        Connection conn = Conexion.getInstance().getConexion();
        final String procedimiento = String.format("delete from detallecompra where idcompra = %s;",idCompra);
        try {
            CallableStatement cs = conn.prepareCall(procedimiento);
            return cs.execute();
        } catch (SQLException e) {
            final String mensaje = "Error en la tabla Detalle Orden Pedido";
            e.printStackTrace();
            Util.makeError(mensaje,e.getLocalizedMessage());
            return true;
        }
    }

    public int getUltimoIdCompra(){
        int ret = 0;
        Connection conn = Conexion.getInstance().getConexion();
        final String consulta = "select * from mostrarultimoidcompra;";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while(rs.next()){
                ret = rs.getInt("numcompra");
            }
        } catch (SQLException e) {
            final String mensaje = "Error en la tabla Compras";
            e.printStackTrace();
            Util.makeError(mensaje,e.getLocalizedMessage());
        }
        return ret;
    }

    public boolean agregarModificarEliminarCompra(final Compra compra, final String accion){
        Connection conn = Conexion.getInstance().getConexion();
        final String procedimiento = "{ call procesoCompras(?,?,?,?,?,?,?,?,?)}";
        try {
            CallableStatement cs = conn.prepareCall(procedimiento);
            cs.setInt("_numcompra",compra.getNumCompra());
            cs.setString("_num_factura",compra.getNumFactura());
            cs.setString("_serie",compra.getSerie());
            cs.setInt("_idproveedor",compra.getIdProveedor());
            if (compra.getFechaEntrega().equals("")){
                cs.setString("_fechaentrega","2017-01-01");
            }else{
                cs.setString("_fechaentrega", compra.getFechaEntrega());
            }
            cs.setInt("_idempleado",compra.getIdEmpleado());
            cs.setString("_observaciones",compra.getObservaciones());
            cs.setString("_condiciones",compra.getCondiciones());
            cs.setString("accion",accion);
            return cs.execute();
        } catch (SQLException e) {
            final String mensaje = "Error en la tabla Compra";
            e.printStackTrace();
            Util.makeError(mensaje,e.getLocalizedMessage());
            return true;
        }
    }

    public boolean agregarModificarEliminarDetalleCompra(final DetalleCompra detalle, final String accion){
        Connection conn = Conexion.getInstance().getConexion();
        final String procedimiento = "{ call procesodetalleCompras(?,?,?,?,?,?)}";
        try {
            CallableStatement cs = conn.prepareCall(procedimiento);
            cs.setInt("_coddetalle",detalle.getCodDetalle());
            cs.setInt("_idcompra",detalle.getIdCompra());
            cs.setInt("_idproducto",detalle.getIdProducto());
            cs.setInt("_cantidad",detalle.getCantidad());
            if(detalle.getFechaVencimiento().equals("")){
                cs.setString("_fecha_vencimiento", "2017-01-01");
            } else{
                cs.setString("_fecha_vencimiento",detalle.getFechaVencimiento());
            }
            cs.setString("accion",accion);
            return cs.execute();
        } catch (SQLException e) {
            final String mensaje = "Error en la tabla Detalle Compra";
            e.printStackTrace();
            Util.makeError(mensaje,e.getLocalizedMessage());
            return true;
        }
    }



    public Collection<Compra> getCompras(){
        Connection conn = Conexion.getInstance().getConexion();
        final String consulta = "SELECT * FROM farmacia.mostrarcompras;";
        final List<Compra> listaCompras = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            Compra compra;
            while(rs.next()){
                compra = new Compra(
                        rs.getInt("numcompra"),
                        rs.getString("num_factura"),
                        rs.getString("serie"),
                        rs.getInt("idproveedor"),
                        rs.getString("nombreprove"),
                        rs.getString("fechacompra"),
                        rs.getString("fechaentrega"),
                        rs.getInt("idempleado"),
                        rs.getString("nomempleado"),
                        rs.getString("observaciones"),
                        rs.getString("condiciones"),
                        rs.getFloat("total"),
                        rs.getString("estatus")
                );
                listaCompras.add(compra);
            }
        } catch (SQLException e) {
            final String mensaje = "Error en la tabla Compras";
            e.printStackTrace();
            Util.makeError(mensaje,e.getLocalizedMessage());
        }
        return listaCompras;
    }

    public Collection<Compra> getComprasIngresadas(){
        Connection conn = Conexion.getInstance().getConexion();
        final String consulta = "SELECT * FROM farmacia.mostrarcompras where estatus = \'INGRESADO\';";
        final List<Compra> listaCompras = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            Compra compra;
            while(rs.next()){
                compra = new Compra(
                        rs.getInt("numcompra"),
                        rs.getString("num_factura"),
                        rs.getString("serie"),
                        rs.getInt("idproveedor"),
                        rs.getString("nombreprove"),
                        rs.getString("fechacompra"),
                        rs.getString("fechaentrega"),
                        rs.getInt("idempleado"),
                        rs.getString("nomempleado"),
                        rs.getString("observaciones"),
                        rs.getString("condiciones"),
                        rs.getFloat("total"),
                        rs.getString("estatus")
                );
                listaCompras.add(compra);
            }
        } catch (SQLException e) {
            final String mensaje = "Error en la tabla Compras";
            e.printStackTrace();
            Util.makeError(mensaje,e.getLocalizedMessage());
        }
        return listaCompras;
    }

    public Collection<DetalleCompra> getDetalleXIdCompr(String idCompra){
        Connection conn = Conexion.getInstance().getConexion();
        final String consulta = String.format("select * from mostrardetallecompras where idcompra = %s;",idCompra);
        final List<DetalleCompra> listaDetalles = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            DetalleCompra detalleCompra;
            while(rs.next()){
                detalleCompra = new DetalleCompra(
                        rs.getInt("coddetalle"),
                        rs.getInt("idcompra"),
                        rs.getInt("idproducto"),
                        rs.getString("nompro"),
                        rs.getFloat("precio_compra"),
                        rs.getInt("cantidad"),
                        rs.getFloat("subtotal"),
                        rs.getString("fecha_vencimiento")
                );
                listaDetalles.add(detalleCompra);
            }
        } catch (SQLException e) {
            final String mensaje = "Error en la tabla Detalle Compra";
            e.printStackTrace();
            Util.makeError(mensaje,e.getLocalizedMessage());
        }
        return listaDetalles;
    }

    public boolean ingresarProducto(int idCompra){
        Connection conn = Conexion.getInstance().getConexion();
        final String procedimiento = "{ call ingreso_productos(?)};";
        try {
            CallableStatement cs = conn.prepareCall(procedimiento);
            cs.setInt("id",idCompra);
            return cs.execute();
        } catch (SQLException e) {
            final String mensaje = "Error al Ingresar Producto";
            e.printStackTrace();
            Util.makeError(mensaje,e.getLocalizedMessage());
            return true;
        }
    }


}
