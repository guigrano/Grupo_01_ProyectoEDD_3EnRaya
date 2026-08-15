package com.example.proyectoedd.modelo;

public class PensamientoComputadora {

    private Tablero tablero;
    private int utilidad;

    public PensamientoComputadora(Tablero tablero, int utilidad) {
        this.tablero = tablero;
        this.utilidad = utilidad;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public int getUtilidad() {
        return utilidad;
    }
}
