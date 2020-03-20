package logica;

import java.util.Iterator;

/**
 * <p>
 * Clase concreta para modelar la estructura de datos Lista
 * </p>
 * <p>
 * Esta clase implementa una Lista genérica, es decir que es homogénea pero
 * puede tener elementos de cualquier tipo.
 * 
 * @author Alejandro Hernández Mora <alejandrohmora@ciencias.unam.mx>
 * @version 1.0
 */
public class Lista<T> implements Listable<T>, Iterable<T> {

    /* Atributos de la lista */
    private Nodo cabeza, cola;
    private int longitud;

    /**
     *  Constructor por omisión de la clase, no recibe parámetros.
     *  Crea una nueva lista con longitud 0.
     **/
    public Lista(){
        longitud =0;
        cabeza=null;
        cola=null;
    }

    /**
     *  Constructor copia de la clase. Recibe una lista con elementos.
     *  Crea una nueva lista exactamente igual a la que recibimos como parámetro.
     **/
    public Lista( Lista<T> l){
        Lista<T> nueva = l.copia();
        this.longitud = nueva.longitud;
        this.cabeza = nueva.cabeza;
        this.cola = nueva.cola;
    }

    /**
     *  Constructor de la clase que recibe parámetros.
     *  Crea una nueva lista con los elementos de la estructura iterable que recibe como parámetro.
     **/
    public Lista(Iterable<T> iterable){
        longitud =0;
        cabeza=null;
        cola=null;
        for(T elem: iterable)
            agregar(elem);
    }

    /* Clase interna para construir la estructura */
    private class Nodo {
        /* Referencias a los nodos anterior y siguiente */
        public Nodo anterior, siguiente;
        /* El elemento que almacena un nodo */
        public T elemento;

        /* Unico constructor de la clase */
        public Nodo(T elemento) {
            this.elemento = elemento;
        }

        public boolean equals(Nodo n) {
            return n.elemento.equals(this.elemento);
        }
    }

    private class IteradorLista implements Iterator {
        /* La lista a recorrer */
        /* Elementos del centinela que recorre la lista */
        private Lista<T>.Nodo siguiente;

        public IteradorLista() {
            this.siguiente = cabeza;
        }

        @Override
        public boolean hasNext() {
            return this.siguiente != null;
        }

        @Override
        public T next() {
            if (this.hasNext()) {
                Lista<T>.Nodo nodo_siguiente = this.siguiente;
                this.siguiente = this.siguiente.siguiente;
                return nodo_siguiente.elemento;
            } else {
                return null;
            }

        }

        @Override
        public void remove() {
            Iterator.super.remove();
        }
    }

    /**
     * Método que nos dice si las lista está vacía.
     * 
     * @return <code>true</code> si el conjunto está vacío, <code>false</code> en
     *         otro caso.
     */
    public boolean esVacia() {
        return longitud == 0;

    }

    /**
     * Método para eliminar todos los elementos de una lista
     */
    public void vaciar() {
        this.cabeza = null;
        this.cola = null;
        this.longitud = 0;
    }

    /**
     * Método para obtener el tamaño de la lista
     * 
     * @return tamanio Número de elementos de la lista.
     **/
    public int longitud() {
        return this.longitud;

    }

    /**
     * Método para agregar un elemento a la lista.
     * 
     * @param elemento Objeto que se agregará a la lista.
     */
    public void agregar(T elemento) {
        this.agregarAlInicio(elemento);
    }

    /**
     * Método para agregar al inicio un elemento a la lista.
     * 
     * @param elemento Objeto que se agregará al inicio de la lista.
     */
    public void agregarAlInicio(T elemento) {
        if (cabeza == null) {
            this.cabeza = new Nodo(elemento);
            this.cola = this.cabeza;
        } else {
            Nodo aux = this.cabeza;
            this.cabeza = new Nodo(elemento);
            this.cabeza.siguiente = aux;
            aux.anterior = this.cabeza;
        }
        this.longitud++;
    }

    /**
     * Método para agregar al final un elemento a la lista.
     * 
     * @param elemento Objeto que se agregará al inicio de la lista.
     */
    public void agregarAlFinal(T elemento) {
        if (cola== null) {
            this.cola = new Nodo(elemento);
            this.cabeza = this.cola;
        } else {
            Nodo aux = this.cola;
            this.cola = new Nodo(elemento);
            this.cola.anterior = aux;
            aux.siguiente = this.cola;
        }
        this.longitud++;
    }

    /**
     * Método para verificar si un elemento pertenece a la lista.
     * 
     * @param elemento Objeto que se va a buscar en la lista.
     * @return <code>true</code> si el elemento esta en el lista y false en otro
     *         caso.
     */
    public boolean contiene(T elemento) {
        for (T elem : this) {
            if (elem.equals(elemento)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método para eliminar un elemento de la lista.
     * 
     * @param elemento Objeto que se eliminara de la lista.
     */
    public void eliminar(T elemento) {
        Nodo aux = this.cabeza;
        while (aux != null) {
            if (aux.elemento.equals(elemento)) {
                if(aux.anterior != null){
                    aux.anterior.siguiente = aux.siguiente;
                }
                if(aux.siguiente != null){
                    aux.siguiente.anterior = aux.anterior;
                }
                if(aux == this.cabeza){
                    this.cabeza = aux.siguiente;
                }
                if(aux == this.cola){
                    this.cola = aux.anterior;
                }
                this.longitud--;
            }
            aux = aux.siguiente;
        }
    }

    /**
     * Método que devuelve la posición en la lista que tiene la primera aparición
     * del <code> elemento</code>.
     * 
     * @param elemento El elemnto del cuál queremos saber su posición.
     * @return i la posición del elemento en la lista, -1, si no se encuentra en
     *         ésta.
     */
    public int indiceDe(T elemento) {
        int i = 0;
        Nodo aux = this.cabeza;
        while (aux != null) {
            if (aux.elemento.equals(elemento)) {
                return i;
            }
            aux = aux.siguiente;
            i++;
        }
        return -1;
    }

    /**
     * Método que nos dice en qué posición está un elemento en la lista
     * 
     * @param i La posición cuyo elemento deseamos conocer.
     * @return <code> elemento </code> El elemento que contiene la lista,
     *         <code>null</code> si no se encuentra
     * @throws IndexOutOfBoundsException Si el índice es < 0 o >longitud()
     */
    public T getElemento(int i) throws IndexOutOfBoundsException {
        Nodo aux = this.cabeza;
        if (i < 0 || i > this.longitud) {
            throw new IndexOutOfBoundsException();
        }
        while (aux != null) {
            if (i == 0) {
                return aux.elemento;
            }
            i--;
            aux = aux.siguiente;
        }
        return null;
    }

    /**
     * Método que devuelve una copia de la lista, pero en orden inverso
     * 
     * @return Una copia con la lista al revés.
     */
    public Lista<T> reversa() {
        Lista<T> lista = new Lista<>();
        Nodo aux = this.cola;
        while (aux != null) {
            lista.agregar(aux.elemento);
            aux = aux.anterior;
        }
        return lista;
    }

    /**
     * Método que devuelve una copia exacta de la lista
     * 
     * @return la copia de la lista.
     */
    public Lista<T> copia() {
        Lista<T> lista = new Lista<>();
        for (T t : this) {
            lista.agregar(t);
        }
        return lista;
    }

    /**
     * Método que nos dice si una lista es igual que otra.
     * 
     * @param o objeto a comparar con la lista.
     * @return <code>true</code> si son iguales, <code>false</code> en otro caso.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Listable)) {
            return false;
        }
        if(o.getClass() == this.getClass()){
            Nodo aux = this.cabeza;
            Lista<?> lista = (Lista<?>) o;
            if(lista.longitud != this.longitud){
                return false;
            }
            for (Object ol : lista) {
                T elem = null;
                try {
                    elem = (T) ol;
                } catch (Exception e) {
                    return false;
                }
                if(!aux.elemento.equals(elem)){
                    return false;
                }
                aux = aux.siguiente;
            }
            return true;
        }
        return false;
    }

    /**
     * Método que devuelve un iterador sobre la lista
     * 
     * @return java.util.Iterador -- iterador sobre la lista
     */
    @Override
    public java.util.Iterator<T> iterator() {
        return new IteradorLista();

    }

    /**
     * Método que devuelve una copia de la lista.
     * 
     * @param <T> Debe ser un tipo que extienda Comparable, para poder distinguir el
     *            orden de los elementos en la lista.
     * @param l   La lista de elementos comparables.
     * @return copia de la lista ordenada.
     */
    public static <T extends Comparable<T>> Lista<T> mergesort(Lista<T> l) {
        if(l.longitud == 1){
            return l;
        }
        int i = 0;
        Lista<T>.Nodo aux = l.cabeza;
        Lista<T> l1 = new Lista<T>();
        Lista<T> l2 = new Lista<T>();
        while(aux != null){
            if(i < l.longitud/2){
                l1.agregarAlFinal(aux.elemento);
            }else{
                l2.agregarAlFinal(aux.elemento);
            }
            i++;
            aux = aux.siguiente;
        }
        return merge(mergesort(l1), mergesort(l2));
    }

    private static <T extends Comparable<T>> Lista<T> merge(Lista<T> l1, Lista<T> l2) {
        Lista<T> lista = new Lista<>();
        Lista<T>.Nodo aux1 = l1.cabeza;
        Lista<T>.Nodo aux2 = l2.cabeza;
        while(aux1!= null && aux2 != null){
            if(aux1.elemento.compareTo(aux2.elemento) < 0){
                lista.agregarAlFinal(aux1.elemento);
                aux1 = aux1.siguiente;
            }else{
                lista.agregarAlFinal(aux2.elemento);
                aux2 = aux2.siguiente;
            }
        }
        if(lista.longitud>0){
            if(aux1 != null){
                lista.cola.siguiente = aux1;
                lista.cola = l1.cola;
            }
            if(aux2 != null){
                lista.cola.siguiente = aux2;
                lista.cola = l2.cola;
            }
        }else{
            lista.cabeza = aux1;
            lista.cola = l1.cola;
        }
        return lista;
    }

    /**
     * 
     */
    @Override
    public String toString() {
        String cadena = "[";
        Nodo aux = this.cabeza;
        while (aux != null) {
            if(aux != this.cola){
                cadena += aux.elemento + ", ";
            }else{
                cadena += aux.elemento;
            }
            aux = aux.siguiente;
        }
        cadena += "]";
        //cadena += "\ncabeza: "+ this.cabeza;
        //cadena += "\ncola: "+ this.cola;
        //cadena += "\nlongitud: "+ this.longitud;
        return cadena;
    }
}
