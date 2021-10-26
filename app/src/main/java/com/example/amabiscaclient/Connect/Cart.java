package com.example.amabiscaclient.Connect;

public class Cart {
    private Integer _codigo = 0;
    private String _titulo = "";
    private Double _precio = 0.0;
    private String _imagen = "";
    private Integer _cantidad = 0;

    public Cart(Integer _codigo, String _titulo, String _imagen, Double _precio, Integer _cantidad){
        this._codigo = _codigo;
        this._titulo = _titulo;
        this._imagen = _imagen;
        this._precio = _precio;
        this._cantidad = _cantidad;
    }

    public Integer get_codigo() {return _codigo;}

    public String get_titulo() {
        return _titulo;
    }

    public Double get_precio() {
        return _precio;
    }

    public String get_imagen() {
        return _imagen;
    }

    public Integer get_cantidad() {
        return _cantidad;
    }
    public void set_cantidad(Integer _cantidad) {this._cantidad = _cantidad;}
}
