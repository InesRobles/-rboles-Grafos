package Ejercicio03_PlanificadorRutas;

//Transportes de la ciudad.
public enum Transporte {
    PIE, BICI, BUS, METRO;

    // Bus y metro son transporte público (tienen líneas y pueden dar transbordos)
    public boolean esTransporte() {
        return this == BUS || this == METRO;
    }
}