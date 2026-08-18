package com.example.proyectoedd.modelo;

public class EstadoJuego {
    private Tablero tablero;
    private Simbolo turnoActual;

    public EstadoJuego(Tablero tablero, Simbolo turnoActual) {
        this.tablero = tablero.copiarTablero();
        this.turnoActual = turnoActual;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public Simbolo getTurnoActual() {
        return turnoActual;
    }
}
