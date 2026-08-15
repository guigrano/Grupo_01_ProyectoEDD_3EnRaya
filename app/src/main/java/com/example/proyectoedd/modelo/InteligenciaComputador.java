/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.proyectoedd.modelo;

import java.util.List;
import java.util.ArrayList;

public class InteligenciaComputador {
    private Simbolo simboloComputador;
    private Simbolo simboloHumano;
    
    public InteligenciaComputador(Simbolo simboloComputador, Simbolo simboloHumano) {
        this.simboloComputador = simboloComputador;
        this.simboloHumano = simboloHumano;
    }
    
    public Tablero decidirMejorMovimiento(Tablero tablero) {
        TreeNode<Tablero> arbolMinimax = generarArbolMinimax(tablero);
        
        int utilidadMaxima = -100; // Este valor de utilidad nunca podrá ser obtenido por lo que es un buen valor como cota mínima.
        Tablero nuevoTablero = tablero;
        
        List<TreeNode<Tablero>> children = arbolMinimax.getChildren();
        for (TreeNode<Tablero> child : children) {
            int utilidadHijo = child.getUtilidad();
            
            if (utilidadHijo > utilidadMaxima) {
                utilidadMaxima = utilidadHijo;
                nuevoTablero = child.getContent();
            }
        }
        
        return nuevoTablero;
    }
    
    public int calcularUtilidad(Tablero tablero) {
        int lineasComputador = calcularLineasDisponibles(tablero, this.simboloHumano);
        int lineasHumano = calcularLineasDisponibles(tablero, this.simboloComputador);
        
        return lineasComputador - lineasHumano; // fórmula de utilidad especificada en la guía del proyecto.
    }
    
    public int calcularLineasDisponibles(Tablero tablero, Simbolo simboloOponente) {
        int lineasDisponibles = 0;
        
        for (int[][] linea : Tablero.LINEAS_GANADORAS) {
            boolean lineaBloqueada = false;
            
            for (int[] coordenada : linea) {
                int fila = coordenada[0];
                int col = coordenada[1];
                
                if (tablero.getCasilla(fila, col) == simboloOponente) {
                    lineaBloqueada = true;
                    break;
                }
            }
            
            if (!lineaBloqueada) lineasDisponibles++;
        }
        
        return lineasDisponibles;
    }
    
    public TreeNode<Tablero> generarArbolMinimax(Tablero tablero) {
        TreeNode<Tablero> root = new TreeNode<>(tablero.copiarTablero()); // Padre (tablero original).
        
        // Primera generación de hijos (Tableros con la casilla a marcar de la computadora).
        generarPosiblesTableros(tablero, simboloComputador, root);
        
        
        // Segunda generación de hijos (Nodos hoja y tableros con la casilla a marcar del jugador).
        List<TreeNode<Tablero>> children = root.getChildren();
        
        for (TreeNode<Tablero> child : children) {
            Tablero tableroHijo = child.getContent();
            generarPosiblesTableros(tableroHijo, simboloHumano, child);
            
            child.setUtilidad(calcularUtilidadMinima(child));
        } 
        return root;
    }
    
    public void generarPosiblesTableros(Tablero tablero, Simbolo simbolo, TreeNode<Tablero> root) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero.getCasilla(i, j) == Simbolo.VACIO) {
                    Tablero copiaTablero = tablero.copiarTablero();
                    copiaTablero.marcarCasilla(i, j, simbolo);
                    
                    TreeNode<Tablero> child = new TreeNode<>(copiaTablero);
                    root.addChild(child);
                }
            }
        }
    }
    
    public int calcularUtilidadMinima(TreeNode<Tablero> root) {
        List<TreeNode<Tablero>> children = root.getChildren();
        if (children.isEmpty()) return calcularUtilidad(root.getContent());
        
        int utilidadMinima = 100; // Este valor de utilidad nunca podrá ser obtenido por lo que es un buen valor como cota máxima.
        
        for (TreeNode<Tablero> node : children) {
            Tablero tablero = node.getContent();
            
            int utilidadTablero = calcularUtilidad(tablero);
            node.setUtilidad(utilidadTablero);
            if (utilidadTablero < utilidadMinima) utilidadMinima = utilidadTablero;
        }
        
        return utilidadMinima;
    }

    //Primer metodo de funcionalidades extras
    public int[] recomendarMovimiento(Tablero tablero) {

        int mejorUtilidad = Integer.MAX_VALUE;
        int[] mejorMovimiento = {-1, -1};

        for (int fila = 0; fila < 3; fila++) {

            for (int col = 0; col < 3; col++) {

                // Solo se pueden recomendar casillas vacías
                if (tablero.getCasilla(fila, col) != Simbolo.VACIO) {
                    continue;
                }

                Tablero tableroCandidato = tablero.copiarTablero();

                // Simulamos la jugada del humano
                tableroCandidato.marcarCasilla(
                        fila,
                        col,
                        simboloHumano
                );

                // Si el humano gana inmediatamente,
                // esta es automáticamente una buena recomendación.
                if (tableroCandidato.esGanador(simboloHumano)) {
                    return new int[]{fila, col};
                }

                // Simulamos la mejor respuesta de la computadora.
                Tablero respuestaComputador =
                        decidirMejorMovimiento(tableroCandidato);

                int utilidad =
                        calcularUtilidad(respuestaComputador);

                if (utilidad < mejorUtilidad) {

                    mejorUtilidad = utilidad;

                    mejorMovimiento = new int[]{fila, col};
                }
            }
        }

        // Verificación final:
        // nunca devolver una casilla que esté ocupada.
        if (mejorMovimiento[0] != -1
                && mejorMovimiento[1] != -1) {

            if (tablero.getCasilla(
                    mejorMovimiento[0],
                    mejorMovimiento[1]
            ) != Simbolo.VACIO) {

                return buscarCasillaDisponible(tablero);
            }
        }

        return mejorMovimiento;
    }

    private int[] buscarCasillaDisponible(Tablero tablero) {

        for (int fila = 0; fila < 3; fila++) {

            for (int col = 0; col < 3; col++) {

                if (tablero.getCasilla(fila, col) == Simbolo.VACIO) {
                    return new int[]{fila, col};
                }
            }
        }

        return new int[]{-1, -1};
    }

    //SEGUNDA FUNCIONALIDAD
    public List<PensamientoComputadora> obtenerPensamiento(Tablero tablero) {

        List<PensamientoComputadora> pensamientos = new ArrayList<>();

        TreeNode<Tablero> arbolMinimax = generarArbolMinimax(tablero);

        for (TreeNode<Tablero> hijo : arbolMinimax.getChildren()) {

            pensamientos.add(
                    new PensamientoComputadora(
                            hijo.getContent(),
                            hijo.getUtilidad()
                    )
            );
        }

        return pensamientos;
    }

}
