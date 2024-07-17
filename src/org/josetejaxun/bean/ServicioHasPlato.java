package org.josetejaxun.bean;
public class ServicioHasPlato {
    
    private int servicios_codigoServicio;
    private int codigoPlato;
    private int codigoServicio;

    public ServicioHasPlato() {
    }

    public ServicioHasPlato(int servicios_codigoServicio, int codigoPlato, int codigoServicio) {
        this.servicios_codigoServicio = servicios_codigoServicio;
        this.codigoPlato = codigoPlato;
        this.codigoServicio = codigoServicio;
    }

    public int getServicios_codigoServicio() {
        return servicios_codigoServicio;
    }

    public void setServicios_codigoServicio(int servicios_codigoServicio) {
        this.servicios_codigoServicio = servicios_codigoServicio;
    }

    public int getCodigoPlato() {
        return codigoPlato;
    }

    public void setCodigoPlato(int codigoPlato) {
        this.codigoPlato = codigoPlato;
    }

    public int getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(int codigoServicio) {
        this.codigoServicio = codigoServicio;
    }
    
    
}
