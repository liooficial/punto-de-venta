package com.example.examen.entidades;

public class PRODUTOS {
    private String id;
    private String nombre;
    private String forma;
    private String volumen;
    private String precio;
    private String cantidad;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = "Id: "+id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre =  "Nombre: "+nombre;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma =  "Forma: "+forma;
    }

    public String getVolumen() {
        return volumen;
    }

    public void setVolumen(String volumen) {
        this.volumen =  "Volumen: "+volumen;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio =  "Precio: "+precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad =  "Cantidad: "+cantidad;
    }
}