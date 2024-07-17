package org.josetejaxun.bean;
public class ProductoHasPlato {
    
    private int productos_codigoProducto;
    private int codigoPlato;
    private int codigoProducto;

    public ProductoHasPlato() {
    }

    public ProductoHasPlato(int productos_codigoProducto, int codigoPlato, int codigoProducto) {
        this.productos_codigoProducto = productos_codigoProducto;
        this.codigoPlato = codigoPlato;
        this.codigoProducto = codigoProducto;
    }

    public int getProductos_codigoProducto() {
        return productos_codigoProducto;
    }

    public void setProductos_codigoProducto(int productos_codigoProducto) {
        this.productos_codigoProducto = productos_codigoProducto;
    }

    public int getCodigoPlato() {
        return codigoPlato;
    }

    public void setCodigoPlato(int codigoPlato) {
        this.codigoPlato = codigoPlato;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }
    
}
