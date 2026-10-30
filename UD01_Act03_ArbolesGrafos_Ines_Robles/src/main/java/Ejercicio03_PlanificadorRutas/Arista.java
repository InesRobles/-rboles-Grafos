package Ejercicio03_PlanificadorRutas;
//Camino, con su modo, su línea (si es transporte) y su duración.
public class Arista {
    String destino;
    Transporte modo;
    String linea; // vacía si es a pie o en bici
    int minutos;

    public Arista(String destino, Transporte modo, String linea, int minutos) {
        this.destino =destino;
        this.modo =modo;
        this.linea =linea;
        this.minutos =minutos;
    }
}