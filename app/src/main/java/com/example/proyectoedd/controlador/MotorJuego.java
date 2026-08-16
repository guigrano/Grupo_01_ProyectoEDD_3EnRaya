package com.example.proyectoedd.controlador;

import com.example.proyectoedd.modelo.InteligenciaComputador;
import com.example.proyectoedd.modelo.Partida;
import com.example.proyectoedd.modelo.Simbolo;
import com.example.proyectoedd.modelo.Tablero;
import com.example.proyectoedd.modelo.TreeNode;

public class MotorJuego {

    private Partida partida;
    private Simbolo simboloHumano;
    private Simbolo simboloComputador;

    public void iniciarJuego(String simboloHumano, boolean humanoInicia) {

        this.simboloHumano = Simbolo.valueOf(simboloHumano);

        if (this.simboloHumano == Simbolo.X) {
            simboloComputador = Simbolo.O;
        } else {
            simboloComputador = Simbolo.X;
        }

        InteligenciaComputador ia =
                new InteligenciaComputador(
                        simboloComputador,
                        this.simboloHumano
                );

        partida = new Partida(
                this.simboloHumano,
                simboloComputador,
                humanoInicia,
                ia,
                true
        );
    }

    public boolean jugarTurnoHumano(int fila, int col) {

        return partida.jugarTurnoHumano(fila, col);
    }

    public void jugarTurnoComputador() {

        if (!partida.partidaTerminada()) {
            partida.jugarTurnoComputador();
        }
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
        return simboloHumano;
    }

    public Simbolo getSimboloComputador() {
        return simboloComputador;
    }

    public int[] recomendarMovimientoHumano() {
        return partida.recomendarMovimientoHumano();
    }

    public TreeNode<Tablero> obtenerArbolPensamiento() {
        return partida.obtenerArbolPensamiento();
    }
    public TreeNode<Tablero> generarArbolPensamiento(
            Tablero tablero) {

        InteligenciaComputador ia =
                new InteligenciaComputador(
                        simboloComputador,
                        simboloHumano
                );

        return ia.obtenerArbolPensamiento(tablero);
    }


}