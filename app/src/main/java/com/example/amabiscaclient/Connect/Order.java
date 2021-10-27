package com.example.amabiscaclient.Connect;

import java.util.Date;

public class Order {
    private Integer _codigo = 0;
    private String _fecha= "";
    private String _detalle = "";
    private String _proveedor = "";
    private String _telefono = "";


    public Order(Integer _codigo, String _fecha, String _detalle, String _proveedor, String _telefono){
        this._codigo = _codigo;
        this._fecha = _fecha;
        this._detalle = _detalle;
        this._proveedor = _proveedor;
        this._telefono = _telefono;
    }

    public String get_telefono() {
        return _telefono;
    }

    public void set_telefono(String _telefono) {
        this._telefono = _telefono;
    }

    public Integer get_codigo() {
        return _codigo;
    }

    public void set_codigo(Integer _codigo) {
        this._codigo = _codigo;
    }

    public String get_fecha() {
        return _fecha;
    }

    public void set_fecha(String _fecha) {
        this._fecha = _fecha;
    }

    public String get_detalle() {
        return _detalle;
    }

    public void set_detalle(String _detalle) {
        this._detalle = _detalle;
    }

    public String get_proveedor() {
        return _proveedor;
    }

    public void set_proveedor(String _proveedor) {
        this._proveedor = _proveedor;
    }
}
