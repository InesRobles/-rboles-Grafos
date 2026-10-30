package Ejercicio02_ListinTelefonico;
import java.util.ArrayList;
import java.util.HashMap;


//Lista telefónica. El HashMap por teléfono permite buscar por teléfono en O(1) y garantiza que sea único.
public class ListaTfnos {
    private Nodo raiz;
    private final HashMap<String, Contacto> porTelefono = new HashMap<>();

    //Orden: apellidos, nombre y, si coinciden, teléfono.
    private int comparar(Contacto a, Contacto b) {
        int c =a.getApellidos().compareToIgnoreCase(b.getApellidos());
        if (c !=0) {
            return c;
        }
        c = a.getNombre().compareToIgnoreCase(b.getNombre());
        if (c !=0) {
            return c;
        }
        return a.getTelefono().compareTo(b.getTelefono());
    }

    // Función que da de alta a cada contacto
    public boolean alta(Contacto c) {
        if (c.getNombre().trim().equals("") || c.getApellidos().trim().equals("") || c.getTelefono().trim().equals("")) {
            return false;
        }
        // El teléfono no se puede repetir
        if (porTelefono.containsKey(c.getTelefono())) {
            return false;
        }
        raiz = insertar(raiz, c);
        porTelefono.put(c.getTelefono(), c);
        return true;
    }

    private Nodo insertar(Nodo nodo, Contacto c) {
        if (nodo == null) {
            return new Nodo(c);
        }
        if (comparar(c, nodo.dato) < 0) {
            nodo.izq = insertar(nodo.izq, c);
        } else {
            nodo.der = insertar(nodo.der, c);
        }
        return nodo;
    }

    //Función que da de baja los contactos
    public boolean baja(String telefono) {
        Contacto c = porTelefono.get(telefono);
        if (c == null) {
            return false;
        }
        porTelefono.remove(telefono);
        raiz = eliminar(raiz, c);
        return true;
    }

    private Nodo eliminar(Nodo nodo, Contacto c) {
        if (nodo == null) {
            return null;
        }

        int cmp = comparar(c, nodo.dato);
        if (cmp < 0) {
            nodo.izq = eliminar(nodo.izq, c);
        } else if (cmp > 0) {
            nodo.der = eliminar(nodo.der, c);
        } else {
            //Nodo a borrar
            if (nodo.izq == null) {
                return nodo.der;
            }
            if (nodo.der == null) {
                return nodo.izq;
            }
            //Nodo que se sustituye por el menor de la derecha
            Nodo menor = nodo.der;
            while (menor.izq != null) {
                menor = menor.izq;
            }
            nodo.dato = menor.dato;
            nodo.der = eliminar(nodo.der, menor.dato);
        }
        return nodo;

    }

    //Función para modificar el número de teléfono
    public boolean modificar(String telefonoActual, String nombre, String apellidos, String telefonoNuevo, String email) {
        if (!porTelefono.containsKey(telefonoActual)) {
            return false;
        }
        if (!telefonoNuevo.equals(telefonoActual) && porTelefono.containsKey(telefonoNuevo)) {
            return false; // el teléfono nuevo ya lo tiene otro contacto
        }
        baja(telefonoActual);
        alta(new Contacto(nombre, apellidos, telefonoNuevo, email));
        return true;
    }

    // Función que busca por teléfono: O(1) gracias al índice hash. */
    public Contacto buscarPorTelefono(String telefono) {
        return porTelefono.get(telefono);
    }

    //Función que busca por apellidos y nombre.
    public ArrayList<Contacto> buscarPorNombre(String apellidos, String nombre) {
        ArrayList<Contacto> resultado = new ArrayList<Contacto>();
        buscarNombre(raiz, apellidos, nombre, resultado);
        return resultado;
    }

    private void buscarNombre(Nodo nodo, String apellidos, String nombre, ArrayList<Contacto> resultado) {
        if (nodo == null) {
            return;
        }
        int c =apellidos.compareToIgnoreCase(nodo.dato.getApellidos());
        if (c ==0) {
            c =nombre.compareToIgnoreCase(nodo.dato.getNombre());
        }
        if (c ==0) {
            resultado.add(nodo.dato);
        }
        // Si lo buscado es menor o igual, puede estar a la izquierda
        if (c <= 0) {
            buscarNombre(nodo.izq, apellidos, nombre, resultado);
        }
        // Si lo buscado es mayor o igual, puede estar a la derecha
        if (c >= 0) {
            buscarNombre(nodo.der, apellidos, nombre, resultado);
        }
    }
    public ArrayList<Contacto> buscarPorPrefijo(String prefijo) {
        ArrayList<Contacto> resultado = new ArrayList<Contacto>();
        buscarPorPrefijo(raiz, prefijo.toLowerCase(), resultado);
        return resultado;
    }
    //Por prefijo de apellidos (por ejemplo "gar" encuentra García)
    private void buscarPorPrefijo(Nodo nodo, String prefijo, ArrayList<Contacto> resultado) {
        if (nodo == null) {
            return;
        }
        String apellidos = nodo.dato.getApellidos().toLowerCase();
        boolean empieza = apellidos.startsWith(prefijo);
        // A la izquierda solo hay nodos menores o iguales a este
        if (prefijo.compareTo(apellidos) <= 0) {
            buscarPorPrefijo(nodo.izq, prefijo, resultado);
        }
        if (empieza) {
            resultado.add(nodo.dato);
        }
        //Miro si este nodo es menor que el prefijo o empieza por él
        if (empieza || apellidos.compareTo(prefijo) < 0) {
            buscarPorPrefijo(nodo.der, prefijo, resultado);
        }
    }
        public ArrayList<Contacto> buscarRango(String desde, String hasta) {
            ArrayList<Contacto> resultado = new ArrayList<Contacto>();
            rango(raiz, desde, hasta, resultado);
            return resultado;
        }
    // Inorden podando ramas fuera de rango: el resultado sale ya ordenado
        private void rango(Nodo nodo, String desde, String hasta, ArrayList<Contacto> resultado) {
            if (nodo == null) {
                return;
            }
            String apellidos = nodo.dato.getApellidos();
            if (desde.compareToIgnoreCase(apellidos) <= 0) {
                rango(nodo.izq, desde, hasta, resultado);
            }
            if (desde.compareToIgnoreCase(apellidos) <= 0 && hasta.compareToIgnoreCase(apellidos) >= 0) {
                resultado.add(nodo.dato);
            }
            if (hasta.compareToIgnoreCase(apellidos) >= 0) {
                rango(nodo.der, desde, hasta, resultado);
            }
        }
    // Listado completo ordenado
            private void inorden(Nodo nodo, ArrayList<Contacto> resultado) {
                if (nodo == null) {
                    return;
                }
                inorden(nodo.izq, resultado);
                resultado.add(nodo.dato);
                inorden(nodo.der, resultado);
            }
            public ArrayList<Contacto> listar() {
                ArrayList<Contacto> resultado = new ArrayList<Contacto>();
                inorden(raiz, resultado);
                return resultado;
            }
}