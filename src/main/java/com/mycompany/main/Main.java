/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.main;

import com.mycompany.main.Modelo.*;
import java.util.Scanner;

/**
 *
 * @author guill
 */
public class Main {
    public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("=====================================");
            System.out.println("   TRES EN RAYA VS COMPUTADORA IA   ");
            System.out.println("=====================================");

            // 1. Configuración de los jugadores
            Simbolo humano = Simbolo.X;
            Simbolo computadora = Simbolo.O;

            // 2. Instanciamos la Inteligencia Artificial
            InteligenciaComputador ia = new InteligenciaComputador(computadora, humano);

            // 3. Creamos la partida
            Partida partida = new Partida(humano, computadora, true, ia, true);

            // 4. Bucle principal del juego
            while (!partida.partidaTerminada()) {
                System.out.println("\nEstado actual del tablero:");
                partida.getTablero().mostrarTablero();

                if (partida.getTurnoActual() == humano) {
                    System.out.println("\n--- TU TURNO (" + humano.getSimbolo() + ") ---");
                    boolean jugadaValida = false;

                    // Pedimos coordenadas hasta que el usuario ingrese una jugada válida
                    while (!jugadaValida) {
                        System.out.print("Ingresa la fila (1, 2, 3): ");
                        int filaInput = scanner.nextInt();
                        System.out.print("Ingresa la columna (1, 2, 3): ");
                        int colInput = scanner.nextInt();

                        // Validamos que el input del usuario esté en el rango 1-3
                        if (filaInput >= 1 && filaInput <= 3 && colInput >= 1 && colInput <= 3) {

                            // Shifting de posición para el backend (0-2)
                            int fila = filaInput - 1;
                            int col = colInput - 1;

                            jugadaValida = partida.jugarTurnoHumano(fila, col);

                            if (!jugadaValida) System.out.println("¡Esa casilla ya está ocupada! Intenta en otra.");
                        } else System.out.println("¡Coordenadas inválidas! Deben estar entre 1 y 3.");
                    }
                } else {
                    System.out.println("\n--- TURNO DE LA COMPUTADORA (" + computadora.getSimbolo() + ") ---");
                    System.out.println("La computadora está pensando su jugada...");
                    partida.jugarTurnoComputador();
                }
            }

            // 5. Pantalla final de resultados
            System.out.println("\n=====================================");
            System.out.println("           FIN DEL JUEGO            ");
            System.out.println("=====================================");
            partida.getTablero().mostrarTablero();

            int resultado = partida.obtenerResultado();

            if (resultado == 1) System.out.println("\n¡Felicidades! Has ganado la partida.");
            else if (resultado == 2) System.out.println("\nLa Inteligencia Artificial te ha vencido.");
            else if (resultado == 0) System.out.println("\n¡Es un Empate perfecto!");

            scanner.close();
        }
}
