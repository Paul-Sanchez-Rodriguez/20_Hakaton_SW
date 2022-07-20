
package modelo;

import java.util.Date;
import java.util.GregorianCalendar;


public class Venta {
    private int idvent;
    private Date fecha = GregorianCalendar.getInstance().getTime();
    private int idcli;
    private int idemp;
    private String cliente;
    private String empleado;

    public int getIdvent() {
        return idvent;
    }

    public void setIdvent(int idvent) {
        this.idvent = idvent;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdcli() {
        return idcli;
    }

    public void setIdcli(int idcli) {
        this.idcli = idcli;
    }

    public int getIdemp() {
        return idemp;
    }

    public void setIdemp(int idemp) {
        this.idemp = idemp;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }
}
