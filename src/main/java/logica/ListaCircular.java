package logica;
import java.util.NoSuchElementException;
import java.util.Iterator;

// 
// Decompiled by Procyon v0.5.36
// 

public class ListaCircular<T> implements Coleccionable<T>
{
    private Nodo inicio;
    private Nodo fin;
    private int nElementos;
    
    public ListaCircular() {
        final Nodo nodo = null;
        this.fin = nodo;
        this.inicio = nodo;
        this.nElementos = 0;
    }
    
    public ListaCircular(final Coleccionable<T> coleccionable) {
        final Iterator<T> iterator = coleccionable.iterator();
        while (iterator.hasNext()) {
            this.agrega(iterator.next());
        }
    }
    
    @Override
    public boolean esVacio() {
        return this.inicio == null;
    }
    
    @Override
    public void agrega(final T t) {
        final Nodo fin = new Nodo(t);
        if (t == null) {
            throw new IllegalArgumentException("No puedes meter null a una lista circular");
        }
        if (this.inicio != null) {
            fin.siguiente = this.inicio;
            this.fin.siguiente = fin;
            this.fin = fin;
        }
        else {
            fin.siguiente = fin;
            this.inicio = fin;
            this.fin = fin;
        }
        ++this.nElementos;
    }
    
    @Override
    public void elimina(final T t) {
        throw new UnsupportedOperationException("No puedes eliminar elementos de una lista circular. Utiliza el iterador");
    }
    
    @Override
    public boolean contiene(final T obj) {
        int n = 0;
        final Iterator<Object> iterator = (Iterator<Object>)this.iterator();
        while (iterator.hasNext() && n++ < this.nElementos) {
            if (iterator.next().equals(obj)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int getTamanio() {
        return this.nElementos;
    }
    
    @Override
    public Iterator<T> iterator() {
        return new IteradorListaCircular();
    }

    public IteradorListaCircular iteratorCircular() {
        return new IteradorListaCircular();
    }
    
    @Override
    public String toString() {
        String str = "[";
        Nodo nodo = this.inicio;
        for (int n = 0; nodo != null && n++ < this.nElementos; nodo = nodo.siguiente) {
            if (n == this.nElementos) {
                str += nodo.elemento;
            }
            else {
                str = str + nodo.elemento + ",";
            }
        }
        return str + "]";
    }
    
    private class Nodo
    {
        protected T elemento;
        protected Nodo siguiente;
        
        Nodo(final T elemento) {
            this.elemento = elemento;
        }
    }
    
    public class IteradorListaCircular implements Iterator<T>
    {
        private Nodo siguiente;
        private Nodo anterior;
        
        public IteradorListaCircular() {
            this.siguiente = ListaCircular.this.inicio;
            this.anterior = ListaCircular.this.fin;
        }
        
        @Override
        public boolean hasNext() {
            return this.siguiente != null;
        }

        public T elem() {
            if (this.hasNext()) {
                return this.siguiente.elemento;
            }
            throw new NoSuchElementException("Ya no  hay mas elementos que explorar");
        }

        @Override
        public T next() {
            if (this.hasNext()) {
                this.anterior = this.siguiente;
                this.siguiente = this.siguiente.siguiente;
                return this.siguiente.elemento;
            }
            throw new NoSuchElementException("Ya no  hay mas elementos que explorar");
        }
        
        @Override
        public void remove() {
            if (this.siguiente == null) {
                throw new IllegalStateException("No hay mas elementos por eliminar");
            }
            if (ListaCircular.this.nElementos == 1) {
                this.siguiente = null;
                this.anterior = null;
            }
            else {
                this.siguiente = this.siguiente.siguiente;
                this.anterior.siguiente = this.siguiente;
                ListaCircular.this.inicio = this.siguiente;
                ListaCircular.this.fin = this.anterior;
            }
            ListaCircular.this.nElementos--;
        }
    }
}
