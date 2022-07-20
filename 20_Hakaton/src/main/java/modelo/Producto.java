/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Paul
 */
public class Producto {
    private String Codpro;
    private String nompro;
    private String despro;
    private double prepro;
    private int stocpro;
    private String estpro;
    private int Cantidad;

    public String getCodpro() {
        return Codpro;
    }

    public void setCodpro(String Codpro) {
        this.Codpro = Codpro;
    }

    public String getNompro() {
        return nompro;
    }

    public void setNompro(String nompro) {
        this.nompro = nompro;
    }

    public String getDespro() {
        return despro;
    }

    public void setDespro(String despro) {
        this.despro = despro;
    }

    public double getPrepro() {
        return prepro;
    }

    public void setPrepro(double prepro) {
        this.prepro = prepro;
    }

    public int getStocpro() {
        return stocpro;
    }

    public void setStocpro(int stocpro) {
        this.stocpro = stocpro;
    }

    public String getEstpro() {
        return estpro;
    }

    public void setEstpro(String estpro) {
        this.estpro = estpro;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }
}
