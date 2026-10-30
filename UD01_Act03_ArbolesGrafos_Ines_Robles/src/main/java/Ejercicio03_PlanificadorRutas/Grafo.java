package Ejercicio03_PlanificadorRutas;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

//Grafo.
public class Grafo {
    private TreeMap<String, ArrayList<Arista>> ady = new TreeMap<String, ArrayList<Arista>>();
    //Función que crea el lugar si todavía no existe
    private void crearLugar(String nombre) {
        if (!ady.containsKey(nombre)) {
            ady.put(nombre, new ArrayList<Arista>());
        }
    }
    // Camino de un solo sentido (de a hacia b)
    public void dirigida(String a, String b, Transporte modo, String linea, int minutos) {
        crearLugar(a);
        crearLugar(b);
        ady.get(a).add(new Arista(b, modo, linea, minutos));
    }
    // Camino de doble sentido (ida y vuelta)
    public void doble(String a, String b, Transporte modo, String linea, int minutos) {
        dirigida(a, b, modo, linea, minutos);
        dirigida(b, a, modo, linea, minutos);
    }
    public boolean existe(String lugar) {
        return ady.containsKey(lugar);
    }

    public ArrayList<Arista> getAristas(String lugar) {
        return ady.get(lugar);
    }

    public Set<String> getLugares() {
        return ady.keySet();
    }
}