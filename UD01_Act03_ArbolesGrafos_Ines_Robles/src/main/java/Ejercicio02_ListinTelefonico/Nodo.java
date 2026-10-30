package Ejercicio02_ListinTelefonico;

//Nodo del árbol de búsqueda binaria: un contacto y sus dos hijos.
public class Nodo {
    Contacto dato;
    Nodo izq;
    Nodo der;

    public Nodo(Contacto dato) {
        this.dato = dato;
        this.izq = null;
        this.der = null;
    }
}