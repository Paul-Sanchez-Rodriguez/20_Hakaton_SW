/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Producto;

/**
 *
 * @author Paul
 */
public class ProductoImpl extends Conexion {

    public List listar() throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        List<Producto> listado = null;
        String sql = "";
        Producto prov;
        sql = "select * from PRODUCTO where ESTPRO = 'A'";
               
        try {
            listado = new ArrayList();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                prov = new Producto();
                prov.setCodpro(rs.getString("CODPRO"));
                prov.setNompro(rs.getString("NOMPRO"));
                prov.setDespro(rs.getString("DESPRO"));
                prov.setPrepro(rs.getDouble("PREPRO"));
                prov.setEstpro(rs.getString("ESTPRO"));
                prov.setStocpro(rs.getInt("STOCPRO"));

                listado.add(prov);

            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("Error en lsistarImpl" + e.getMessage());
        }
        return listado;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void buscarProducto(String codigo,Producto pro) throws Exception {
        String sql = "Select * from Producto where CODPRO=?";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                pro.setCodpro(rs.getString("CODPRO"));
                pro.setNompro(rs.getString("NOMPRO"));
                pro.setDespro(rs.getString("DESPRO"));
                pro.setPrepro(rs.getDouble("PREPRO"));
                pro.setStocpro(rs.getInt("STOCPRO"));
                pro.setEstpro(rs.getString("ESTPRO"));
            }

        } catch (Exception e) {
            System.out.println("Error en buscar ProductoIpml " + e.getMessage());
        }
    }
}
