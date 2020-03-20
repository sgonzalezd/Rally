package logica;

import javafx.scene.shape.Rectangle;

public class Soldado {
    private String nombre;
    private int numero;
    private Rectangle rect;

    public Soldado(String nombre, int numero, Rectangle rect){
        this.nombre = nombre;
        this.numero = numero;
        this.rect = rect;
    }

    public Rectangle getRect(){
        return this.rect;
    }

    public String getNombre(){
        return this.nombre;
    }
    public int getNumero(){
        return this.numero;
    }
    @Override
    public String toString(){
        return Integer.toString(this.numero)+':'+this.nombre + '\n';
    } 
}
