package com.example.examen.entidades;

public class Item {
    private String id;
    private String nombre;
    private String forma;
    private String volumen;
    private String precio;
    private String cantidad;

    public Item(String id, String nombre,String forma,String volumen,String precio,String cantidad) {
        this.id = id;
        this.nombre ="Producto: "+nombre;
        this.forma = "Forma: "+forma;
        this.volumen = "Volumen: "+volumen;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getId() {
        return id;
    }


    public String getNombre() {
        return nombre;
    }


    public String getForma() {
        return forma;
    }


    public String getVolumen() {
        return volumen;
    }


    public String getPrecio() {
        return precio;
    }

    public String getCantidad() {
        return cantidad="1";
    }

}
