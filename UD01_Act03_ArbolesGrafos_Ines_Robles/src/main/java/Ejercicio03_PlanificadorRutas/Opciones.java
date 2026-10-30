package Ejercicio03_PlanificadorRutas;
import java.util.Set;

//Restricciones elegidas por el usuario.
public class Opciones {
    boolean evitarBici;
    boolean minimizarTransbordos;
    boolean horaPunta;

    public Opciones(boolean evitarBici, boolean minimizarTransbordos, boolean horaPunta) {
        this.evitarBici = evitarBici;
        this.minimizarTransbordos = minimizarTransbordos;
        this.horaPunta = horaPunta;
    }
}
