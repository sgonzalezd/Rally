package logica;

public class Becario{
    private String nombre;
    private int dia;
    public static String[] semana = new String[]{"","Domingo","Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
    public Becario(String nombre, int dia) throws IndexOutOfBoundsException{
        if (7<dia && dia>=0){
            throw new IndexOutOfBoundsException("Fuera de rango");
        }
        this.nombre = nombre;
        this.dia = dia;
    }

    public String getDiaString(){
        return semana[dia];
    }

    public int getDia(){
        return this.dia;
    }

    public String getNombre(){
        return this.nombre;
    }
}