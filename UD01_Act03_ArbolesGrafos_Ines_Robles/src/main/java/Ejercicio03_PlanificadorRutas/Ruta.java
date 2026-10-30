package Ejercicio03_PlanificadorRutas;

//Lista de como se llegó a un estado y a qué coste.
public class Ruta implements Comparable<Ruta>{
    String lugar;
    String ultimaLinea; //Ultimo buso metro usado
    Ruta anterior;
    Arista via;// camino que usé para llegar
    int coste;// minutos + penalizaciones por transbordo
    int minutos;// tiempo real
    int transbordos;

    public Ruta(String lugar, String ultimaLinea, Ruta anterior, Arista via, int coste, int minutos, int transbordos) {
        this.lugar =lugar;
        this.ultimaLinea =ultimaLinea;
        this.anterior =anterior;
        this.via =via;
        this.coste =coste;
        this.minutos =minutos;
        this.transbordos =transbordos;
    }

    // La cola de prioridad sacará primero la ruta con menos coste
    public int compareTo(Ruta otra) {
        return this.coste - otra.coste;
    }
}