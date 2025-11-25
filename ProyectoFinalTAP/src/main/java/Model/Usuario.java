package Model;

public class Usuario {
    private int id;
    private String nombre;
    private String username;
    private String password;
    private String rol;  // "Administrador" o "Empleado"


    private Usuario(Builder builder) {
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.username = builder.username;
        this.password = builder.password;
        this.rol = builder.rol;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }


    public static class Builder {
        private int id;
        private String nombre;
        private String username;
        private String password;
        private String rol;


        public Builder(String username, String password) {
            this.username = username;
            this.password = password;
        }


        public Builder conId(int id) {
            this.id = id;
            return this;
        }

        public Builder conNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder conRol(String rol) {
            this.rol = rol;
            return this;
        }


        public Usuario build() {
            return new Usuario(this);
        }
    }
}