package com.example.amabiscaclient.Connect;

public class GlobalVarLog {
    private  static GlobalVarLog instance;

    private  static  int    _codigo     = 0;
    private  static  String _nombre     = "";
    private  static  String _dni        = "";
    private  static  String _direccion  = "";
    private  static  String _telefono   = "";
    private  static  String _correo     = "";
    private  static  String _fnacimiento= "";
    private  static  String _sexo       = "";

    public  int get_codigo() {return _codigo;}
    public  void set_codigo(int _codigo) {GlobalVarLog._codigo = _codigo;}

    public  String get_nombre() {return _nombre;}
    public  void set_nombre(String _nombre) {GlobalVarLog._nombre = _nombre;}

    public  String get_dni() {return _dni;}
    public  void set_dni(String _dni) {GlobalVarLog._dni = _dni;}

    public  String get_direccion() {return _direccion;}
    public  void set_direccion(String _direccion) {GlobalVarLog._direccion = _direccion;}

    public  String get_telefono() {return _telefono;}
    public  void set_telefono(String _telefono) {GlobalVarLog._telefono = _telefono;}

    public  String get_correo() {return _correo;}
    public  void set_correo(String _correo) {GlobalVarLog._correo = _correo;}

    public  String get_fnacimiento() {return _fnacimiento;}
    public  void set_fnacimiento(String _fnacimiento) {GlobalVarLog._fnacimiento = _fnacimiento;}

    public  String get_sexo() {return _sexo;}
    public  void set_sexo(String _sexo) {GlobalVarLog._sexo = _sexo;}

    public void restart(){
        _codigo     = 0;
        _nombre     = "";
        _dni        = "";
        _direccion  = "";
        _telefono   = "";
        _correo     = "";
        _fnacimiento= "";
        _sexo       = "";
    }


    public static synchronized GlobalVarLog getInstance() {
        if (instance == null) {
            instance = new GlobalVarLog();
        }
        return instance;
    }
}
