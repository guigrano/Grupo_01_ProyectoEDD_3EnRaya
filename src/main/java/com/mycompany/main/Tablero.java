/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.main;

/**
 *
 * @author guill
 */
public class Tablero {
    private Simbolo[][] tablero;
    public static final int[][][] LINEAS_GANADORAS = {
        // 3 Filas
        {{0, 0}, {0, 1}, {0, 2}}, 
        {{1, 0}, {1, 1}, {1, 2}},
        {{2, 0}, {2, 1}, {2, 2}},
        // 3 Columnas
        {{0, 0}, {1, 0}, {2, 0}},
        {{0, 1}, {1, 1}, {2, 1}},
        {{0, 2}, {1, 2}, {2, 2}},
        // 2 Diagonales
        {{0, 0}, {1, 1}, {2, 2}},
        {{0, 2}, {1, 1}, {2, 0}}
    };

    public Tablero() {
        tablero = new Simbolo[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) tablero[i][j] = Simbolo.VACIO;
        }
    }

    public Tablero copiarTablero() {
        Tablero copiaTablero = new Tablero();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Simbolo simbolo = tablero[i][j];
                copiaTablero.marcarCasilla(i, j, simbolo);
            }
        }
        return copiaTablero; 
    }

    public void marcarCasilla(int fila, int col, Simbolo s) {
        tablero[fila][col] = s;
    }
    
    public Simbolo getSimboloEnCasilla(int fila, int col) {
        return tablero[fila][col];
    }


    // metodo temporal para pruebas
    public void mostrarTablero() {
        for (int i = 0; i < 3; i++) {
            System.out.print('[');
            for (int j = 0; j < 3; j++) {
                System.out.print(tablero[i][j]);
                if (j < 2) System.out.print(", ");
            }
            System.out.print(']');
            if (i < 2) System.out.println();
        }
    }
}
