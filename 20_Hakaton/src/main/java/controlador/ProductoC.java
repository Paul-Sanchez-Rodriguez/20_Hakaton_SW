package controlador;

import dao.ProductoImpl;
import dao.VentaImpl;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;
import modelo.Cliente;
import modelo.Producto;
import modelo.Venta;
import modelo.VentaDetalle;
import reporte.reportesVenta;

@Named(value = "ProductoC")
@SessionScoped
public class ProductoC implements Serializable {

    //variable
    private double subtotal;
    private double total;
    private int cantidad;
    // modelo y dao
    private ProductoImpl dao;
    private Producto pro;
    private VentaDetalle vd;
    private VentaImpl dao2;
    private Venta ven;
    private Cliente cli;
    //listas
    private List<Producto> listadoProd;
    private List<VentaDetalle> listarVentaDealle;
    private List<Venta> listarventa;
    private int caso = 1;

    public ProductoC() {
        dao = new ProductoImpl();
        pro = new Producto();
        vd = new VentaDetalle();
        cli = new Cliente();
        dao2 = new VentaImpl();
        ven = new Venta();
        listarVentaDealle = new ArrayList();
    }

    public void listar() {
        try {
            listadoProd = dao.listar();
        } catch (Exception e) {
            System.out.println("Error en listarC" + e.getMessage());
        }
    }

    public void listarTablaTemporal() {
        total = 0.0;
        subtotal = pro.getPrepro() * pro.getCantidad();

        vd = new VentaDetalle();

        vd.setCodigoPRoducto(pro.getCodpro());
        vd.setCantidad(pro.getCantidad());
        vd.setDescripcion(pro.getDespro());
        vd.setNompro(pro.getNompro());
        vd.setPrcio(pro.getPrepro());
        vd.setStock(pro.getStocpro());
        vd.setSubtotal(subtotal);
        listarVentaDealle.add(vd);
        calcularTotalVenta();
        pro = new Producto();
    }

    public void agregar(String codigo) {
        try {
            dao.buscarProducto(codigo, pro);

        } catch (Exception e) {
            System.out.println("error en buscar ProductoC " + e.getMessage());
        }
    }

    public void calcularTotalVenta() {
        for (int i = 0; i < listarVentaDealle.size(); i++) {
            total = total + listarVentaDealle.get(i).getSubtotal();
        }
    }

    public void registrarVenta() throws Exception {
        try {
            ven.setIdcli(cli.getIdcli());
            dao2.registrar(ven);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Transaccion correcta"));
            registrarDetalle();
            limpiar();

        } catch (Exception e) {
            System.out.println("Error en regiistrarVentaC " + e.getMessage());
        }
    }

    public void limpiar() {
        listarVentaDealle = new ArrayList<>();
        cli = new Cliente();
        total = 0;
    }

    public void registrarDetalle() throws Exception {

        int paul = dao2.ventasMaximas();
//        dao.ventasMaximas(venDet);
        for (int i = 0; i < listarVentaDealle.size(); i++) {
            vd = new VentaDetalle();
            vd.setCantidad(listarVentaDealle.get(i).getCantidad());
            vd.setCodigoVenta(paul);
            vd.setCodigoPRoducto(listarVentaDealle.get(i).getCodigoPRoducto());

            dao2.registrarVentaDetalle(vd);
            dao2.ActualizarStock(vd);

        }
    }

    public void listarVenta() {
        try {
            listarventa = dao2.ListarVentas();

        } catch (Exception e) {
            System.out.println("Error en controlador listarVenta" + e.getMessage());
        }
    }

    public void elimianarProductoAgregado(String Codigo) {
        int i = 0;
        try {
            for (VentaDetalle vent : listarVentaDealle) {
                if (vent.getCodigoPRoducto().equals(Codigo)) {
                    total = total - listarVentaDealle.get(i).getSubtotal();
                    listarVentaDealle.remove(i);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Producto Eliminado con Exito"));
                    break;
                }
                i++;
            }
        } catch (Exception e) {
            System.out.println("Error en Eliminar Producto " + e.getMessage());
        }
    }

    public List<String> autocompleteCliente(String query) throws Exception {
        try {
            return dao2.autocompletarCliente(query);

        } catch (Exception e) {
            System.out.println("Error en autocompletarclienteC " + e.getMessage());
            throw e;
        }
    }

    public void verReportePDFEST(int codigo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        reportesVenta reporte = new reportesVenta();
        FacesContext facescontext = FacesContext.getCurrentInstance();
        ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
        String root = servletcontext.getRealPath("Reportes/boletaVenta.jasper");
        String numeroinformesocial = String.valueOf(codigo);
        reporte.getReportePdf(root, numeroinformesocial);
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void verReporteActual() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        int idvent = dao2.ventasMaximas();
        reportesVenta reporte = new reportesVenta();
        FacesContext facescontext = FacesContext.getCurrentInstance();
        ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
        String root = servletcontext.getRealPath("Reportes/boletaVenta.jasper");
        String numeroinformesocial = String.valueOf(idvent);
        reporte.getReportePdf(root, numeroinformesocial);
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void filtrado() throws Exception {
        try {
            dao2.filtrarCliente(cli);
        } catch (Exception e) {
            System.out.println("Error en filtrar: " + e.getMessage());
        }
    }

    public void validadorRepetido(Producto productos) {
        int indice = 0;
        int cantidad = 0;
        if (pro.getCantidad() > 0) {
            try {

                if (listarVentaDealle.isEmpty()) {
                    if (pro.getCantidad() > pro.getStocpro()) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Cantidad de producto no disponible"));

                    } else {
                        listarTablaTemporal();
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Producto agregado con éxito"));
                    }

                } else {
                    for (VentaDetalle ventaDetalle : listarVentaDealle) {
                        if (ventaDetalle.getCodigoPRoducto().equals(productos.getCodpro())) {
                            cantidad = listarVentaDealle.get(indice).getCantidad() + productos.getCantidad();
                            if (cantidad <= pro.getStocpro()) {

                                subtotal = listarVentaDealle.get(indice).getPrcio() * cantidad;
                                total = 0;
                                ventaDetalle.setCantidad(cantidad);
                                ventaDetalle.setSubtotal(subtotal);

                                listarVentaDealle.set(indice, ventaDetalle);
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Producto agregado con éxito"));

                                calcularTotalVenta();
                                pro = new Producto();
                                break;

                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "OK", "cantidad de producto no disponible"));
                            }

                        } else {
                            indice++;
                            if (indice == listarVentaDealle.size()) {
                                if (pro.getCantidad() > pro.getStocpro()) {
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Cantidad de producto no disponible"));

                                } else {
                                    listarTablaTemporal();
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Producto agregado con éxito"));
                                    break;
                                }
                            }

                        }

                    }

                }

            } catch (Exception e) {
                System.out.println("error en calidarProducto " + e.getMessage());
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Ingrese la la cantidad de venta"));
        }
    }

    public ProductoImpl getDao() {
        return dao;
    }

    public void setDao(ProductoImpl dao) {
        this.dao = dao;
    }

    public Producto getPro() {
        return pro;
    }

    public void setPro(Producto pro) {
        this.pro = pro;
    }

    public List<Producto> getListadoProd() {
        return listadoProd;
    }

    public void setListadoProd(List<Producto> listadoProd) {
        this.listadoProd = listadoProd;
    }

    public int getCaso() {
        return caso;
    }

    public void setCaso(int caso) {
        this.caso = caso;
    }

    public VentaDetalle getVd() {
        return vd;
    }

    public void setVd(VentaDetalle vd) {
        this.vd = vd;
    }

    public List<VentaDetalle> getListarVentaDealle() {
        return listarVentaDealle;
    }

    public void setListarVentaDealle(List<VentaDetalle> listarVentaDealle) {
        this.listarVentaDealle = listarVentaDealle;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public VentaImpl getDao2() {
        return dao2;
    }

    public void setDao2(VentaImpl dao2) {
        this.dao2 = dao2;
    }

    public Venta getVen() {
        return ven;
    }

    public void setVen(Venta ven) {
        this.ven = ven;
    }

    public List<Venta> getListarventa() {
        return listarventa;
    }

    public void setListarventa(List<Venta> listarventa) {
        this.listarventa = listarventa;
    }

    public Cliente getCli() {
        return cli;
    }

    public void setCli(Cliente cli) {
        this.cli = cli;
    }
}
