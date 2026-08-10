/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.main.Modelo;

/**
 *
 * @author guill
 */
public class Partida {
    private Tablero tablero;
    private Simbolo turnoActual;
    private Simbolo simboloJugador1;
    private Simbolo simboloJugador2;
    private InteligenciaComputador ia;
    private boolean esContraComputador;
    
    public Partida(Simbolo simboloJugador1, Simbolo simboloJugador2, boolean iniciaJugador1, InteligenciaComputador ia, boolean esContraComputador) {
        this.tablero = new Tablero();
        this.simboloJugador1 = simboloJugador1;
        this.simboloJugador2 = simboloJugador2;
        this.esContraComputador = esContraComputador;
        if (esContraComputador) this.ia = ia;
        
        if (iniciaJugador1) turnoActual = simboloJugador1;
        else turnoActual = simboloJugador2;
    }
    
    public boolean jugarTurnoHumano(int fila, int col) {
        if (tablero.getCasilla(fila, col) != Simbolo.VACIO) return false;
        this.tablero.marcarCasilla(fila, col, turnoActual);
        cambiarTurno();
        return true;
    }
    
    public void jugarTurnoComputador() {
        if (partidaTerminada()) return;
        this.tablero = ia.decidirMejorMovimiento(this.tablero);
        cambiarTurno();
    }
    
    public boolean partidaTerminada() {
        return tablero.esGanador(simboloJugador1) || tablero.esGanador(simboloJugador2) || tablero.esEmpate();
    }
    
    /*
    Podriamos implementar un Enum luego para no retornar estos valores enteros.
    */
    public int obtenerResultado() {
        if (tablero.esGanador(simboloJugador1)) return 1;
        else if (tablero.esGanador(simboloJugador2)) return 2;
        else if (tablero.esEmpate()) return 0;
        else return -1;
    }
    
    private void cambiarTurno(){
        if (this.turnoActual == this.simboloJugador1) this.turnoActual = this.simboloJugador2;
        else this.turnoActual = this.simboloJugador1;
    }
    
    public Tablero getTablero() {
        return this.tablero;
    }
    
    public Simbolo getTurnoActual() {
        return this.turnoActual;
    }
}
