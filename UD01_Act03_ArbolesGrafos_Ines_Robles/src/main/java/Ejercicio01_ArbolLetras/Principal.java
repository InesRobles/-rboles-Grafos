package Ejercicio01_ArbolLetras;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        ArbolLetras arbol = new ArbolLetras("ABCDEFGHIJKLMNO");
        System.out.println("~~~ Árbol de letras ~~~");
        arbol.mostrarPorNiveles();

        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print("Introduce una letra: ");
            String entrada = scan.nextLine().trim();
            if (entrada.equalsIgnoreCase("salir")) {
                break;
            }
            //Depuración por si no introduce nada, o más de una letra.
            if (entrada.isEmpty() || entrada.length() != 1 || !Character.isLetter(entrada.charAt(0))) {
                System.out.println("Error, introduce solo una letra.");
                continue;
            }

            char letra = Character.toUpperCase(entrada.charAt(0));
            Ruta ruta = arbol.buscar(letra);
            if (ruta == null) {
                System.out.println("La letra " + letra + "no esta en el árbol.");
            } else {
                System.out.println("Letras visitadas: " + ruta.letras);
                if (ruta.direcciones.size() == 0) {
                    System.out.println("Direcciones: es la raíz");
                } else {
                    System.out.println("Direcciones: " + ruta.direcciones);
                }
            }
        }
    }
}