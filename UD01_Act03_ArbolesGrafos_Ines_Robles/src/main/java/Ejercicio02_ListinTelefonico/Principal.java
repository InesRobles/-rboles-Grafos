package Ejercicio02_ListinTelefonico;
import java.util.ArrayList;
import java.util.Scanner;

public class Principal {
    static Scanner scan = new Scanner(System.in);
    static ListaTfnos listin = new ListaTfnos();

    // Pide un texto por pantalla
    static String leer(String mensaje) {
        System.out.print(mensaje);
        return scan.nextLine().trim();
    }

    // Muestra una lista de contactos
    static void mostrar(ArrayList<Contacto> lista) {
        if (lista.size() == 0) {
            System.out.println("Sin resultados");
        } else {
            for (Contacto c : lista) {
                System.out.println("  " + c);
            }
        }
    }

    static void altaContacto() {
        String nombre = leer("Nombre: ");
        String apellidos = leer("Apellidos: ");
        String telefono = leer("Teléfono: ");
        String email = leer("Email (opcional): ");

        Contacto c = new Contacto(nombre, apellidos, telefono, email);
        if (listin.alta(c)) {
            System.out.println("Contacto añadido.");
        } else {
            System.out.println("No se pudo añadir (faltan datos o el teléfono está repetido).");
        }
    }

    static void bajaContacto() {
        String telefono = leer("Teléfono del contacto a borrar: ");
        if (listin.baja(telefono)) {
            System.out.println("Contacto eliminado.");
        } else {
            System.out.println("No existe ese teléfono.");
        }
    }

    static void modificarContacto() {
        String telefono = leer("Teléfono del contacto a modificar: ");
        Contacto actual = listin.buscarPorTelefono(telefono);
        if (actual == null) {
            System.out.println("No existe ese teléfono.");
            return;
        }

        System.out.println("Contacto actual: " + actual);
        System.out.println("(deja vacío lo que no quieras cambiar)");

        String nombre = leer("Nombre: ");
        if (nombre.equals("")) {
            nombre = actual.getNombre();
        }
        String apellidos = leer("Apellidos: ");
        if (apellidos.equals("")) {
            apellidos = actual.getApellidos();
        }
        String telefonoNuevo = leer("Teléfono: ");
        if (telefonoNuevo.equals("")) {
            telefonoNuevo = actual.getTelefono();
        }
        String email = leer("Email: ");
        if (email.equals("")) {
            email = actual.getEmail();
        }

        if (listin.modificar(telefono, nombre, apellidos, telefonoNuevo, email)) {
            System.out.println("Contacto modificado.");
        } else {
            System.out.println("No se pudo modificar (el teléfono nuevo ya existe).");
        }
    }

    static void buscarTelefono() {
        String telefono = leer("Teléfono: ");
        Contacto c = listin.buscarPorTelefono(telefono);
        if (c == null) {
            System.out.println("No encontrado");
        } else {
            System.out.println("  " + c);
        }
    }

    public static void main(String[] args) {
        // Contactos de ejemplo
        listin.alta(new Contacto("Ana", "García López", "600111222", "ana@mail.com"));
        listin.alta(new Contacto("Luis", "García Pérez", "600333444", ""));
        listin.alta(new Contacto("María", "Martínez Ruiz", "600555666", "maria@mail.com"));
        listin.alta(new Contacto("Pedro", "Alonso Gil", "600777888", ""));
        listin.alta(new Contacto("Sara", "Zapata Mora", "600999000", "sara@mail.com"));

        String opcion = "";
        while (!opcion.equals("0")) {
            System.out.println();
            System.out.println("--- LISTÍN TELEFÓNICO ---");
            System.out.println("1) Alta");
            System.out.println("2) Baja");
            System.out.println("3) Modificar");
            System.out.println("4) Buscar por teléfono");
            System.out.println("5) Buscar por apellidos y nombre");
            System.out.println("6) Buscar por prefijo de apellidos");
            System.out.println("7) Buscar por rango de apellidos");
            System.out.println("8) Mostrar todos los contactos");
            System.out.println("0) Salir");
            opcion = leer("Opción: ");

            switch (opcion) {
                case "1":
                    altaContacto();
                    break;
                case "2":
                    bajaContacto();
                    break;
                case "3":
                    modificarContacto();
                    break;
                case "4":
                    buscarTelefono();
                    break;
                case "5":
                    String apellidos = leer("Apellidos: ");
                    String nombre = leer("Nombre: ");
                    mostrar(listin.buscarPorNombre(apellidos, nombre));
                    break;
                case "6":
                    String prefijo = leer("Prefijo: ");
                    mostrar(listin.buscarPorPrefijo(prefijo));
                    break;
                case "7":
                    String desde = leer("Desde: ");
                    String hasta = leer("Hasta: ");
                    mostrar(listin.buscarRango(desde, hasta));
                    break;
                case "8":
                    mostrar(listin.listar());
                    break;
                case "0":
                    System.out.println("Hasta pronto.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
