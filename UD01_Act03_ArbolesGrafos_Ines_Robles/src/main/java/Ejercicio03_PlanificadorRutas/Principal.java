package Ejercicio03_PlanificadorRutas;
import java.util.ArrayList;
import java.util.Scanner;
public class Principal {

    static Scanner scan = new Scanner(System.in);
    // Crea la ciudad de ejemplo
    static Grafo crearCiudad() {
        Grafo g = new Grafo();
        //Casa
        g.doble("Casa", "Plaza", Transporte.PIE, "", 10);
        g.doble("Casa", "Plaza", Transporte.BICI, "", 4);
        g.doble("Casa", "Estacion", Transporte.PIE, "", 25);
        //Plaza
        g.doble("Plaza", "Centro", Transporte.PIE, "", 15);
        g.dirigida("Plaza", "Centro", Transporte.BICI, "", 5); // carril bici de un solo sentido
        g.doble("Plaza", "Estacion", Transporte.BUS, "L10", 8);
        //Estación
        g.doble("Estacion", "Universidad", Transporte.METRO, "M1", 6);
        g.doble("Estacion", "Centro", Transporte.METRO, "M2", 5);
        //Centro
        g.doble("Centro", "Puerto", Transporte.METRO, "M2", 8);
        g.dirigida("Centro", "Hospital", Transporte.BUS, "L20", 7);
//Hospital
        g.dirigida("Hospital", "Centro", Transporte.BUS, "L20", 9);
        g.dirigida("Hospital", "Puerto", Transporte.BUS, "L30", 10);
        //Universidad
        g.dirigida("Universidad", "Centro", Transporte.BUS, "L40", 12); // línea de un solo sentido// línea de un solo sentido
        g.doble("Universidad", "Parque", Transporte.PIE, "", 12);
        g.doble("Universidad", "Parque", Transporte.BICI, "", 4);
        //Parque
        g.doble("Parque", "Hospital", Transporte.PIE, "", 20);
        g.doble("Parque", "Hospital", Transporte.BICI, "", 6);
        return g;
    }
    // Hace una pregunta de sí o no
    static boolean preguntar(String pregunta) {
        System.out.print(pregunta + " (sí/no): ");
        String respuesta = scan.nextLine().trim();
        return respuesta.equalsIgnoreCase("s");
    }

    // Muestra la ruta paso a paso
    static void mostrarRuta(Ruta fin, String origen) {
        if (fin == null) {
            System.out.println("No existe ruta con esas restricciones.");
            return;
        }

        // Voy hacia atrás desde el final y meto cada paso al principio de la lista
        ArrayList<Ruta> pasos = new ArrayList<Ruta>();
        for (Ruta p = fin; p.via != null; p = p.anterior) {
            pasos.add(0, p);
        }

        System.out.println();
        System.out.println("Ruta encontrada:");
        String lugar = origen;
        for (Ruta p : pasos) {
            int minutosPaso = p.minutos - p.anterior.minutos;
            String detalle = p.via.modo.toString();
            if (p.via.modo.esTransporte()) {
                detalle = detalle + " " + p.via.linea;
            }
            System.out.println("  " + lugar + " --[" + detalle + ", " + minutosPaso + " min]--> " + p.via.destino);
            lugar = p.via.destino;
        }
        System.out.println("Tiempo total: " + fin.minutos + " min | Transbordos: " + fin.transbordos);
    }

    public static void main(String[] args) {
        Grafo ciudad = crearCiudad();

        System.out.println("~~~ Planificador de rutas ~~~");
        System.out.println("Lugares: " + ciudad.getLugares());

        while (true) {
            System.out.print("\nOrigen: ");
            String origen = scan.nextLine().trim();
            if (origen.equals("")) {
                break;
            }
            System.out.print("Destino: ");
            String destino = scan.nextLine().trim();

            if (!ciudad.existe(origen) || !ciudad.existe(destino)) {
                System.out.println("Lugar desconocido. Escríbelo igual que en la lista.");
                continue;
            }
            boolean evitarBici = preguntar("¿Evitar bicicleta?");
            boolean minTransbordos = preguntar("¿Minimizar transbordos?");
            boolean horaPunta = preguntar("¿Hora punta?");
            Opciones op = new Opciones(evitarBici, minTransbordos, horaPunta);
            Ruta resultado = Planificador.calcular(ciudad, origen, destino, op);
            mostrarRuta(resultado, origen);
        }

        System.out.println("Fin del programa.");
    }
}
