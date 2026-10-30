package Ejercicio01_ArbolLetras;

//Nodo del árbol: una letra y dos punteros izquierdo y derecho.
public class Nodo {
    char letra;
    Nodo izq;
    Nodo der;
    public Nodo(char letra) {
        this.letra = letra;
        this.izq=null;
        this.der=null;
    }
}