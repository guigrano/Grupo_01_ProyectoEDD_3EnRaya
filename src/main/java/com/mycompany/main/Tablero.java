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

        public Tablero() {
            tablero = new Simbolo[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) tablero[i][j] = Simbolo.VACIO;
            }
        }

        public Simbolo[][] copiarTablero() {
            Simbolo[][] copiaTablero = new Simbolo[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) copiaTablero[i][j] = tablero[i][j];
            }
            return copiaTablero;
        }

        public void marcarCasilla(int fila, int col, Simbolo s) {
            tablero[fila][col] = s;
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
