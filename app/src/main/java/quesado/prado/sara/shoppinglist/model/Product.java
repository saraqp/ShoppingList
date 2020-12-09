package quesado.prado.sara.shoppinglist.model;

public class Product {
    private String nombre;
    private String cantidad;
    private long id;

    public Product() {
    }

    public Product(String nombre, String cantidad, long id) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.id = id;
    }

    public Product(String nombre, String cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

