package reportes;

import compras.model.DetalleCompraReporte;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.collections.map.HashedMap;
import pos.model.ProductoReporte;
import resumen.ventas.model.VentaDiaReporte;
import ventas.model.DetalleOPedidoReporte;
import ventas.model.VentaPorEmpleado;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 10/06/2017.
 */
public class Reporte {

    private final String rut = System.getProperty("user.home") + File.separator + "Reportes" + File.separator + "plantilla" +
            File.separator + "FacturaFarmacia.jrxml";

    private final String rutCompras = System.getProperty("user.home") + File.separator + "Reportes" + File.separator + "plantilla" +
            File.separator + "ReporteCompra.jasper";

    private final String rutVentaDia = System.getProperty("user.home") + File.separator + "Reportes" + File.separator + "plantilla" +
            File.separator + "reporteCierreDia.jasper";

    private final String rutLogo = System.getProperty("user.home") + File.separator + "Reportes" + File.separator + "plantilla" +
            File.separator + "logo.jpg";

    //private final String rutCierre = System.getProperty("user.home") + File.separator + "Reportes" + File.separator + "plantilla" +
      //      File.separator + "CierreDia.jasper";

    private final String rutCierre = System.getProperty("user.home") + File.separator + "Reportes" + File.separator + "plantilla" +
            File.separator + "reporteDetalleVenta.jasper";
    //private final String rutaReporte = "src/main/java/reportes/FacturaFarmacia.jasper";
    Map<String,Object> parametros = new HashMap<>();

    public void pdfReporte(List<ProductoReporte> list, String nomCliente, String nitCliente, String direccion,
                           String telCliente, String nomEmpleado , String noFactura, boolean imprimir, boolean guardar, String subTotal,
                           String descuento, String total){
        parametros.put("nomCliente",nomCliente);
        parametros.put("nitCliente",nitCliente);
        parametros.put("direccion",direccion);
        parametros.put("telCliente",telCliente);
        parametros.put("nomEmpleado", nomEmpleado);
        parametros.put("noFactura",noFactura);
        parametros.put("subtotal",subTotal);
        parametros.put("descuento",descuento);
        parametros.put("total",total);
        InputStream reporte = null;
        try {
            reporte = getClass().getResourceAsStream("/reportes/FacturaFarmacia.jrxml");
            JasperReport report = JasperCompileManager.compileReport(reporte);
            JasperPrint jPrint = JasperFillManager.fillReport(report, parametros,
                    new JRBeanCollectionDataSource(list));
            if(imprimir){
                JasperPrintManager.printReport(jPrint, false);
            }
            JasperViewer jasperViewer = new JasperViewer(jPrint,false);
            jasperViewer.setTitle("CONSTANCIA");
            jasperViewer.setVisible(true);
            if(guardar){
                String path= System.getProperty("user.home");
                JasperExportManager.exportReportToPdfFile(jPrint,  path + File.separator + "Reportes" + File.separator + "Reporte-" + getFecha() + ".pdf");
            }
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void reporteModificacionVenta(List<ProductoReporte> list, String nomCliente, String nitCliente, String direccion,
                           String telCliente,String nomEmpleado, String noFactura, boolean imprimir, boolean guardar,String subTotal,
                           String descuento, String total,String idVenta){
        parametros.clear();
        parametros.put("nomCliente",nomCliente);
        parametros.put("nitCliente",nitCliente);
        parametros.put("direccion",direccion);
        parametros.put("telCliente",telCliente);
        parametros.put("nomEmpleado", nomEmpleado);
        parametros.put("noFactura",noFactura);
        parametros.put("subtotal",subTotal);
        parametros.put("descuento",descuento);
        parametros.put("total",total);
        InputStream reporte = null;
        try {
            reporte = getClass().getResourceAsStream("/reportes/FacturaFarmacia.jrxml");
            JasperReport report = JasperCompileManager.compileReport(reporte);
            JasperPrint jPrint = JasperFillManager.fillReport(report, parametros,
                    new JRBeanCollectionDataSource(list));
            if(imprimir){
                JasperPrintManager.printReport(jPrint, false);
            }
            JasperViewer jasperViewer = new JasperViewer(jPrint,false);
            jasperViewer.setTitle("CONSTANCIA");
            jasperViewer.setVisible(true);
            if(guardar){
                String path= System.getProperty("user.home");
                JasperExportManager.exportReportToPdfFile(jPrint,  path + File.separator + "Reportes" +File.separator + "ventas" +File.separator + "ReporteModificacionVenta--" + idVenta +"--"+ getFecha() + ".pdf");
            }
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void reporteCompras(List<DetalleCompraReporte> lista,String noCompra,String fechaEntrega,
                               String nomProveedor, String telProveedor,String direccionProveedor,
                               String total,boolean guardar,boolean imprimir, String observaciones,
                               String condiciones){
        Map<String,Object> par = new HashMap<>();
        par.put("noFactora",noCompra);
        par.put("fechaEntrega",fechaEntrega);
        par.put("nomProveedor",nomProveedor);
        par.put("telProveedor",telProveedor);
        par.put("direccionProveedor",direccionProveedor);
        par.put("totalCompra",total);
        par.put("observaciones",observaciones);
        par.put("condiciones",condiciones);
        try {
            JasperPrint jPrint = JasperFillManager.fillReport(rutCompras, par,
                    new JRBeanCollectionDataSource(lista));
            if(imprimir){
                JasperPrintManager.printReport(jPrint, false);
            }
            JasperViewer jasperViewer = new JasperViewer(jPrint,false);
            jasperViewer.setTitle("COMPRA");
            jasperViewer.setVisible(true);
            if(guardar){
                String path= System.getProperty("user.home");
                JasperExportManager.exportReportToPdfFile(jPrint,  path + File.separator + "Reportes" +File.separator +"compras"+ File.separator + "ReporteCompra--" + noCompra + "--" + getFecha() + ".pdf");
            }
        } catch (JRException e) {
            e.printStackTrace();
        }

    }

    public void reporteModificacionCompras(List<DetalleCompraReporte> lista,String noCompra,String fechaEntrega,
                               String nomProveedor, String telProveedor,String direccionProveedor,
                               String total,boolean guardar,boolean imprimir, String observaciones,
                               String condiciones){
        Map<String,Object> par = new HashMap<>();
        par.put("noFactora",noCompra);
        par.put("fechaEntrega",fechaEntrega);
        par.put("nomProveedor",nomProveedor);
        par.put("telProveedor",telProveedor);
        par.put("direccionProveedor",direccionProveedor);
        par.put("totalCompra",total);
        par.put("observaciones",observaciones);
        par.put("condiciones",condiciones);
        try {
            JasperPrint jPrint = JasperFillManager.fillReport(rutCompras, par,
                    new JRBeanCollectionDataSource(lista));
            if(imprimir){
                JasperPrintManager.printReport(jPrint, false);
            }
            JasperViewer jasperViewer = new JasperViewer(jPrint,false);
            jasperViewer.setTitle("COMPRA");
            jasperViewer.setVisible(true);
            if(guardar){
                String path= System.getProperty("user.home");
                JasperExportManager.exportReportToPdfFile(jPrint,  path + File.separator + "Reportes" +File.separator +"compras"+ File.separator + "ReporteModificacionCompra--" + noCompra +"--" + getFecha() + ".pdf");
            }
        } catch (JRException e) {
            e.printStackTrace();
        }

    }

    public void reporteProductosPorVendedor(List<VentaPorEmpleado> lista,String nomEmpleado,
                                            boolean guardar,boolean imprimir){
        Map<String,Object> par = new HashMap<>();
        par.put("nomEmpleado",nomEmpleado);
        InputStream reporte = null;
        try {
            reporte = getClass().getResourceAsStream("/reportes/VentasPorEmpleado.jrxml");
            JasperReport report = JasperCompileManager.compileReport(reporte);
            JasperPrint jPrint = JasperFillManager.fillReport(report, par,
                    new JRBeanCollectionDataSource(lista));
            if(imprimir){
                JasperPrintManager.printReport(jPrint, false);
            }
            JasperViewer jasperViewer = new JasperViewer(jPrint,false);
            jasperViewer.setTitle("CONSOLIDADO");
            jasperViewer.setVisible(true);
            if(guardar){
                String path= System.getProperty("user.home");
                JasperExportManager.exportReportToPdfFile(jPrint,  path + File.separator + "Reportes" +File.separator +"consolidados"+ File.separator + "Con--" + nomEmpleado + "--" + getFecha() + ".pdf");
            }
        } catch (JRException e) {
            e.printStackTrace();
        }

    }

    public void reporteVentasDia(List<VentaDiaReporte> lista,String titulo,String total,
                                 boolean guardar, boolean imprimir){
        Map<String,Object> par = new HashedMap();
        par.put("titulo",titulo);
        par.put("total",total);
        try {
            JasperPrint jPrint = JasperFillManager.fillReport(rutVentaDia, par,
                    new JRBeanCollectionDataSource(lista));
            if(imprimir){
                JasperPrintManager.printReport(jPrint, false);
            }
            JasperViewer jasperViewer = new JasperViewer(jPrint,false);
            jasperViewer.setTitle("VENTAS");
            jasperViewer.setVisible(true);
            if(guardar){
                String path= System.getProperty("user.home");
                JasperExportManager.exportReportToPdfFile(jPrint,  path + File.separator + "Reportes" +File.separator +"ventas"+ File.separator + "ReporteVenta-" + getFecha() + ".pdf");
            }
        } catch (JRException e) {
            e.printStackTrace();
        }

    }

    public void reporteCierreDia(List<DetalleOPedidoReporte> lista, boolean imprimir){
        try {
            JasperPrint jPrint = JasperFillManager.fillReport(rutCierre,new HashMap<>(),
                    new JRBeanCollectionDataSource(lista));
            if(imprimir){
                JasperPrintManager.printReport(jPrint, false);
            }
            JasperViewer jasperViewer = new JasperViewer(jPrint,false);
            jasperViewer.setTitle("VENTAS");
            jasperViewer.setVisible(true);
            String path= System.getProperty("user.home");
            JasperExportManager.exportReportToPdfFile(jPrint,path + File.separator + "Reportes" +File.separator +"ventas"+ File.separator + "CierreDia-" + getFecha() + ".pdf");

        } catch (JRException e) {
            e.printStackTrace();
        }

    }

    private String getFecha(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd___HH-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);

    }
}
