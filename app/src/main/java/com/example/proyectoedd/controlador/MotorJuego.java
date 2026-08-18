package com.example.proyectoedd.controlador;

import com.example.proyectoedd.modelo.InteligenciaComputador;
import com.example.proyectoedd.modelo.Partida;
import com.example.proyectoedd.modelo.Simbolo;
import com.example.proyectoedd.modelo.Tablero;
import com.example.proyectoedd.modelo.TreeNode;

public class MotorJuego {

    private Partida partida;
    private Simbolo simboloJugador1;
    private Simbolo simboloJugador2;
    private boolean esContraComputador;

    public void iniciarJuego(String simboloHumano, boolean humanoInicia) {

        this.simboloJugador1 = Simbolo.valueOf(simboloHumano);

        if (this.simboloJugador1 == Simbolo.X) simboloJugador2 = Simbolo.O;
        else simboloJugador2 = Simbolo.X;

        InteligenciaComputador ia = new InteligenciaComputador(simboloJugador2, this.simboloJugador1);

        this.esContraComputador = true;

        partida = new Partida(this.simboloJugador1, simboloJugador2, humanoInicia, ia, true);
    }

    public void iniciarJuegoPvP(String simboloJugador1, boolean jugador1Inicia) {

        this.simboloJugador1 = Simbolo.valueOf(simboloJugador1);

        if (this.simboloJugador1 == Simbolo.X) simboloJugador2 = Simbolo.O;
        else simboloJugador2 = Simbolo.X;

        this.esContraComputador = false;

        partida = new Partida(this.simboloJugador1, simboloJugador2, jugador1Inicia, null, false);
    }

    public boolean jugarTurnoHumano(int fila, int col) {
        return partida.jugarTurnoHumano(fila, col);
    }

    public void jugarTurnoComputador() {
        if (!partida.partidaTerminada()) partida.jugarTurnoComputador();
    }

    public boolean verificarFinJuego() {
        return partida.partidaTerminada();
    }

    public int obtenerResultado() {
        return partida.obtenerResultado();
    }

    public Tablero getTablero() {
        return partida.getTablero();
    }

    public Simbolo getTurnoActual() {
        return partida.getTurnoActual();
    }

    public Simbolo getSimboloHumano() {
        return simboloJugador1;
    }

    public Simbolo getSimboloComputador() {
        return simboloJugador2;
    }

    public Simbolo getSimboloJugador1() {
        return simboloJugador1;
    }

    public Simbolo getSimboloJugador2() {
        return simboloJugador2;
    }

    public boolean isEsContraComputador() {
        return esContraComputador;
    }

    public void deshacer() {
        partida.deshacer();
    }

    public void rehacer() {
        partida.rehacer();
    }

    public boolean puedeDeshacer() {
        return partida.puedeDeshacer();
    }

    public boolean puedeRehacer() {
        return partida.puedeRehacer();
    }

    public Tablero getTableroAnterior() { return partida.getTableroAnterior(); }

    public int[] recomendarMovimiento() {
        return partida.recomendarMovimiento();
    }

    public int[] recomendarMovimientoPvP() {
        return partida.recomendarMovimiento();
    }

    public TreeNode<Tablero> generarArbolPensamiento(Tablero tablero) {
        InteligenciaComputador ia = new InteligenciaComputador(simboloJugador2, simboloJugador1);
        return ia.generarArbolMinimax(tablero);
    }
}