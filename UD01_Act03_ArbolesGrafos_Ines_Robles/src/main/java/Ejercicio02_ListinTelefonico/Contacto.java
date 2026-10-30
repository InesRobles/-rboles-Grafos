package Ejercicio02_ListinTelefonico;

//Clase plantilla de contactos
public class Contacto {
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;


    public Contacto(String nombre, String apellidos, String telefono, String email) {
        this.nombre =nombre;
        this.apellidos =apellidos;
        this.telefono =telefono;
        if (email ==null) {
            this.email = "";
        } else {
            this.email = email;
        }
    }
    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        String textoEmail =email;
        if (email.equals("")) {
            textoEmail = "-";
        }
        return " | Nombre: " + nombre + " | Apellidos: "+apellidos + " | Teléfono: " + telefono + " | Email: " + textoEmail;

    }
}
