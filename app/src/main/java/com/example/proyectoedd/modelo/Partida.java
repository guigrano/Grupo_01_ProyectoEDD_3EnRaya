package com.example.proyectoedd.modelo;

import java.util.Stack;

public class Partida {
    private Tablero tablero;
    private Simbolo turnoActual;
    private Simbolo simboloJugador1;
    private Simbolo simboloJugador2;
    private InteligenciaComputador ia;
    private boolean esContraComputador;
    private Stack<EstadoJuego> pilaDeshacer;
    private Stack<EstadoJuego> pilaRehacer;

    public Partida(Simbolo simboloJugador1, Simbolo simboloJugador2, boolean iniciaJugador1, InteligenciaComputador ia, boolean esContraComputador) {
        this.tablero = new Tablero();
        this.simboloJugador1 = simboloJugador1;
        this.simboloJugador2 = simboloJugador2;
        this.esContraComputador = esContraComputador;
        if (esContraComputador) this.ia = ia;
        this.pilaDeshacer = new Stack<>();
        this.pilaRehacer = new Stack<>();

        if (iniciaJugador1) turnoActual = simboloJugador1;
        else turnoActual = simboloJugador2;
    }

    public boolean jugarTurnoHumano(int fila, int col) {
        if (tablero.getCasilla(fila, col) != Simbolo.VACIO) return false;
        guardarEstado();
        this.tablero.marcarCasilla(fila, col, turnoActual);
        cambiarTurno();
        return true;
    }

    public void jugarTurnoComputador() {
        if (partidaTerminada()) return;
        guardarEstado();
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

    private void guardarEstado() {
        pilaDeshacer.push(new EstadoJuego(tablero, turnoActual));
        pilaRehacer.clear();
    }

    public void deshacer() {
        if (pilaDeshacer.isEmpty()) return;
        pilaRehacer.push(new EstadoJuego(tablero, turnoActual));
        EstadoJuego estado = pilaDeshacer.pop();
        this.tablero = estado.getTablero().copiarTablero();
        this.turnoActual = estado.getTurnoActual();
    }

    public void rehacer() {
        if (pilaRehacer.isEmpty()) return;
        pilaDeshacer.push(new EstadoJuego(tablero, turnoActual));
        EstadoJuego estado = pilaRehacer.pop();
        this.tablero = estado.getTablero().copiarTablero();
        this.turnoActual = estado.getTurnoActual();
    }

    public boolean puedeDeshacer() {
        return !pilaDeshacer.isEmpty();
    }

    public boolean puedeRehacer() {
        return !pilaRehacer.isEmpty();
    }

    public Tablero getTablero() {
        return tablero.copiarTablero();
    }

    public Tablero getTableroAnterior() {
        if (!pilaDeshacer.isEmpty()) {
            return pilaDeshacer.peek().getTablero().copiarTablero();
        }
        return tablero.copiarTablero();
    }

    public Simbolo getTurnoActual() {
        return this.turnoActual;
    }

    public boolean esContraComputador() {
        return this.esContraComputador;
    }

    public int[] recomendarMovimiento() {
        if (partidaTerminada()) return new int[]{-1, -1};
        Simbolo oponente = (turnoActual == simboloJugador1) ? simboloJugador2 : simboloJugador1;
        InteligenciaComputador iaTemp = new InteligenciaComputador(turnoActual, oponente); // Se pasan valores invertidos para que tome como "computador" al turno actual.
        return iaTemp.recomendarMovimiento(tablero);
    }
}
