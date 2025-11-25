package Model;

public class Vehiculo {
    private String placa;
    private String tipo; // "Auto", "Moto", "Camioneta"
    private String propietario;

    public Vehiculo(String placa, String tipo, String propietario) {
        this.placa = placa;
        this.tipo = tipo;
        this.propietario = propietario;
    }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getPropietario() { return propietario; }
    public void setPropietario(String propietario) { this.propietario = propietario; }

    public static class Builder {
        private String placa;
        private String tipo;
        private String propietario;
        public Builder conPlaca(String placa) {
            this.placa = placa;
            return this;
        }

        public Builder deTipo(String tipo) {
            this.tipo = tipo;
            return this;
        }

        public Builder delPropietario(String propietario) {
            this.propietario = propietario;
            return this;
        }

        public Vehiculo build() {
            return new Vehiculo(this.placa, this.tipo, this.propietario);
        }
    }
}
