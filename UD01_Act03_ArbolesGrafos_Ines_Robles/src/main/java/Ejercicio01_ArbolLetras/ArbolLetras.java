package Ejercicio01_ArbolLetras;
import java.util.LinkedList;
import java.util.Queue;

public class ArbolLetras {
    private final Nodo raiz;
    // Construcción del árbol rellenando de izquierda a derecha y de nivel en nivel.
    public ArbolLetras(String letras) {
        if (letras == null || letras.length() == 0) {
            raiz = null;
            return;
        }
        raiz =new Nodo(letras.charAt(0));
        Queue<Nodo> cola =new LinkedList<>();
        cola.add(raiz);
        int i =1;
        while (i < letras.length()) {
            Nodo actual = cola.poll();
            actual.izq = new Nodo(letras.charAt(i));
            cola.add(actual.izq);
            i=i+1;
            if (i < letras.length()) {
                actual.der = new Nodo(letras.charAt(i));
                cola.add(actual.der);
                i=i+1;
            }
        }
    }

    //Función que devuelve la ruta desde la raíz hasta la letra, o null si no existe esa letra.
    public Ruta buscar(char objetivo) {
        Ruta ruta = new Ruta();
        boolean encontrada = dfs(raiz, objetivo, ruta);
        if (encontrada) {
            return ruta;
        } else {
            return null;
        }
    }
// Función que recorre el árbol.
    private boolean dfs(Nodo nodo, char objetivo, Ruta ruta) {
        if (nodo == null) {
            return false;
        }
        // Visito el nodo
        ruta.letras.add(nodo.letra);
        // Si es la letra que busco, termino
        if (nodo.letra == objetivo) {
            return true;
        }
        // Pruebo por la izquierda
        ruta.direcciones.add('L');
        if (dfs(nodo.izq, objetivo, ruta)) {
            return true;
        }
        ruta.direcciones.remove(ruta.direcciones.size() - 1);
        // Si no estaba, pruebo por la derecha
        ruta.direcciones.add('R');
        if (dfs(nodo.der, objetivo, ruta)) {
            return true;
        }
        ruta.direcciones.remove(ruta.direcciones.size() - 1);
        // No estaba en ninguna rama: borro este nodo de la ruta y vuelvo atrás
        ruta.letras.remove(ruta.letras.size() - 1);
        return false;
    }

    // Función que muestra el árbol por niveles.
    public void mostrarPorNiveles() {
        //Depuración por si estuviera vacio.
        if (raiz == null) {
            System.out.println("Árbol vacío");
            return;
        }
        Queue<Nodo> cola = new LinkedList<Nodo>();
        cola.add(raiz);
        int nivel = 0;
        while (!cola.isEmpty()) {
            int cantidad = cola.size();//Cantidad de nodos que hay en este nivil
            String linea = "Nivel " + nivel + ": ";
            for (int i = 0; i < cantidad; i++) {
                Nodo n = cola.poll();
                linea=linea+n.letra+ " ";
                if (n.izq != null){
                    cola.add(n.izq);
                }
                if (n.der != null){
                    cola.add(n.der);
                }
            }
            System.out.println(linea);
            nivel++;
        }
    }
}