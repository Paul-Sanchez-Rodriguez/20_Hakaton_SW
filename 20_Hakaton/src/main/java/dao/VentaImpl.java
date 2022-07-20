package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import modelo.Cliente;
import modelo.Venta;
import modelo.VentaDetalle;

public class VentaImpl extends Conexion {

    DateFormat formato = new SimpleDateFormat("dd-MM-YYYY");

    public void registrar(Venta ven) throws Exception {
        try {
            String sql = "insert into VENTA"
                    + "(FECVENT,IDCLI,IDEMP)"
                    + "VALUES (?,?,?)";

            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, formato.format(ven.getFecha()));
            ps.setInt(2, ven.getIdcli());
            ps.setInt(3, 2);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en registrar Venta DAO" + e.getMessage());
        }
    }

    public int ventasMaximas() {
        int nroVentas = 0;
        String sql = "SELECT MAX(IDVENT) FROM VENTA";
        try {
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                nroVentas = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error en ventas Maximas" + e.getMessage());
        }
        return nroVentas;

    }

    public void registrarVentaDetalle(VentaDetalle Vendet) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String sql = "INSERT INTO VENTA_DETALLE"
                + "(CANVENTDET,CODPRO,IDVENT)"
                + "VALUES (?,?,?)";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, Vendet.getCantidad());
            ps.setString(2, Vendet.getCodigoPRoducto());
            ps.setInt(3, Vendet.getCodigoVenta());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en registrar VentasDetalle " + e.getMessage());
        }
    }

    public void ActualizarStock(VentaDetalle vd) {
        int cant = vd.getCantidad();
        String codpro = vd.getCodigoPRoducto();
        String sql = "UPDATE producto SET stocpro = stocpro - '" + cant + "' where codpro = '" + codpro + "'";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en Actualizar Stock" + e.getMessage());
        }
    }

    public List<Venta> ListarVentas() throws Exception {
        List<Venta> listadoVenta = null;
        Venta vd;
        String sql = "select * from VistaVentas";
        try {

            listadoVenta = new ArrayList<>();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                vd = new Venta();
                vd.setIdvent(rs.getInt("Venta"));
                vd.setFecha(rs.getDate("Fecha"));
                vd.setCliente(rs.getString("cliente"));
                vd.setEmpleado(rs.getString("empleado"));
                listadoVenta.add(vd);
            }
            rs.close();
            st.close();

        } catch (Exception e) {
            System.out.println("Error en kistar Producto DAO" + e.getMessage());
        }
        return listadoVenta;

    }

    public List<String> autocompletarCliente(String consulta) throws Exception {

        Cliente cli = new Cliente();
        List<String> listado = new ArrayList<>();
        String sql = "Select * from Cliente_Venta WHERE NOMBRE_APELLIDO LIKE ?";
        PreparedStatement ps = dao.Conexion.conectar().prepareCall(sql);
        try {

            ps.setString(1, consulta + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                listado.add(rs.getString("NOMBRE_APELLIDO"));
            }

            rs.close();

        } catch (Exception e) {
            System.out.println("Error en ventaImpl " + e.getMessage());
        } finally {
            ps.close();
        }
        return listado;
    }

    public void filtrarCliente(Cliente cli) throws Exception {
        String sql = "select * from Cliente_Venta where NOMBRE_APELLIDO=?";
        PreparedStatement ps = dao.Conexion.conectar().prepareStatement(sql);
        try {
            ps.setString(1, cli.getDatos());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cli.setIdcli(rs.getInt("IDCLI"));
                cli.setNomcli(rs.getString("NOMBRE_APELLIDO"));
                cli.setCelcli(rs.getString("CELCLI"));
                cli.setDircli(rs.getString("DIRCLI"));  
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error en ventaImpl " + e.getMessage());
        }

    }
}
