package com.example.amabiscaclient.Connect;

public class Product {
    private Integer _codigo = 0;
    private String _titulo = "";
    private Double _precio = 0.0;
    private String _descripcion = "";
    private String _imagen = "";
    private Integer _cantidad = 0;
    private String _unidad = "";

    public Product(String _titulo, String _imagen, String _descripcion, Double _precio, String _unidad, Integer _codigo, Integer _cantidad){
        this._titulo = _titulo;
        this._imagen = _imagen;
        this._descripcion = _descripcion;
        this._precio = _precio;
        this._unidad = _unidad;
        this._codigo = _codigo;
        this._cantidad = _cantidad;
    }

    public Integer get_codigo() {
        return _codigo;
    }

    public String get_titulo() {
        return _titulo;
    }

    public Double get_precio() {
        return _precio;
    }

    public String get_descripcion() {
        return _descripcion;
    }

    public String get_imagen() {
        return _imagen;
    }

    public Integer get_cantidad() {
        return _cantidad;
    }

    public String get_unidad() {
        return _unidad;
    }
}
