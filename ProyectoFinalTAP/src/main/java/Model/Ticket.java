package Model;

import java.time.LocalDateTime;

public class Ticket {
    private int numeroTicket;
    private String placaVehiculo;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;
    private double montoTotal;

    private Ticket(Builder builder) {
        this.numeroTicket = builder.numeroTicket;
        this.placaVehiculo = builder.placaVehiculo;
        this.fechaSalida = builder.fechaSalida;
        this.montoTotal = builder.montoTotal;


        if (builder.fechaEntrada == null) {
            this.fechaEntrada = LocalDateTime.now();
        } else {
            this.fechaEntrada = builder.fechaEntrada;
        }
    }


    public int getNumeroTicket() { return numeroTicket; }
    public void setNumeroTicket(int id) { this.numeroTicket = id; }

    public String getPlacaVehiculo() { return placaVehiculo; }


    public LocalDateTime getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(LocalDateTime fecha) { this.fechaEntrada = fecha; }

    public LocalDateTime getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(LocalDateTime fechaSalida) { this.fechaSalida = fechaSalida; }

    public double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(double montoTotal) { this.montoTotal = montoTotal; }

    public static class Builder {
        private int numeroTicket;
        private String placaVehiculo;
        private LocalDateTime fechaEntrada;
        private LocalDateTime fechaSalida;
        private double montoTotal;


        public Builder(String placaVehiculo) {
            this.placaVehiculo = placaVehiculo;
        }

        public Builder conId(int id) {
            this.numeroTicket = id;
            return this;
        }

        public Builder conFechaEntrada(LocalDateTime fecha) {
            this.fechaEntrada = fecha;
            return this;
        }

        public Builder conFechaSalida(LocalDateTime fecha) {
            this.fechaSalida = fecha;
            return this;
        }

        public Builder conMonto(double monto) {
            this.montoTotal = monto;
            return this;
        }

        public Ticket build() {
            return new Ticket(this);
        }
    }
}