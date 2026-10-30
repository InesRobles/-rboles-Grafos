package Ejercicio03_PlanificadorRutas;

import java.util.HashMap;
import java.util.PriorityQueue;
//Calcula la ruta más óptima con Dijkstra.

public class Planificador {
    //Tiempo real de una arista según la hora punta.
    static int tiempo(Arista a, Opciones op) {
        int t = a.minutos;
        if (op.horaPunta) {
            if (a.modo == Transporte.BUS) {
                t = (int) Math.round(a.minutos * 1.5);
            } else if (a.modo == Transporte.METRO) {
                t = (int) Math.round(a.minutos * 1.25);
            }
        }
        return t;
    }
    /*Un mismo lugar puede tener costes distintos según la última línea usada,
    por eso la clave del HashMap junta el lugar y la línea*/
    static String clave(String lugar, String linea) {
        return lugar + "|" + linea;
    }
    //Devuelve el último Paso de la ruta óptima, o null si no hay ruta.
    public static Ruta calcular(Grafo g, String origen, String destino, Opciones op) {
        PriorityQueue<Ruta> cola = new PriorityQueue<Ruta>();
        HashMap<String, Integer> mejor = new HashMap<String, Integer>();

        // Penalización de cada transbordo (si quiere minimizarlos, pesa mucho más)
        int penalizacion = 3;
        if (op.minimizarTransbordos) {
            penalizacion = 30;
        }

        Ruta inicio = new Ruta(origen, "", null, null, 0, 0, 0);
        cola.add(inicio);
        mejor.put(clave(origen, ""), 0);

        while (!cola.isEmpty()) {
            Ruta actual = cola.poll();

            // Si ya había una forma mejor de llegar a este estado, la ignoro
            if (actual.coste > mejor.get(clave(actual.lugar, actual.ultimaLinea))) {
                continue;
            }

            // La primera vez que sale el destino de la cola es la mejor ruta
            if (actual.lugar.equals(destino)) {
                return actual;
            }
            for (Arista a : g.getAristas(actual.lugar)) {
                // Si quiere evitar la bici, no uso ese camino
                if (op.evitarBici && a.modo == Transporte.BICI) {
                    continue;
                }
                String linea = actual.ultimaLinea;
                int transbordos = actual.transbordos;
                int coste = actual.coste + tiempo(a, op);
                if (a.modo.esTransporte()) {
                    // Hay transbordo si subo a una línea distinta de la anterior
                    if (!linea.equals("") && !linea.equals(a.linea)) {
                        transbordos++;
                        coste = coste + penalizacion;
                    }
                    linea = a.linea;
                }
                String claveNueva = clave(a.destino, linea);
                if (!mejor.containsKey(claveNueva) || coste < mejor.get(claveNueva)) {
                    mejor.put(claveNueva, coste);
                    int minutos = actual.minutos + tiempo(a, op);
                    cola.add(new Ruta(a.destino, linea, actual, a, coste, minutos, transbordos));
                }
            }
        }

        return null; // no hay ruta posible con esas restricciones
    }
}