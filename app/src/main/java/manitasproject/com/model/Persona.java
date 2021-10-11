package manitasproject.com.model;

public class Persona extends CredencialPersona{

    private String uid;
    private String nombre;
    private String edad;
    private String telefono;
    private String sexo;

    //Constructor
    public Persona() { }

    public String getUid() { return uid; }

    public void setUid(String uid) { this.uid = uid; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEdad() { return edad; }

    public void setEdad(String edad) { this.edad = edad; }

    public String getTelefono() { return telefono; }

    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getSexo() { return sexo; }

    public void setSexo(String sexo) { this.sexo = sexo; }
}
