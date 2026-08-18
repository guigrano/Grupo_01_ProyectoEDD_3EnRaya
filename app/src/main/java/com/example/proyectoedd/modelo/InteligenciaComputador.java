package com.example.proyectoedd.modelo;

import java.util.List;

public class InteligenciaComputador {
    private Simbolo simboloComputador;
    private Simbolo simboloHumano;
    
    public InteligenciaComputador(Simbolo simboloComputador, Simbolo simboloHumano) {
        this.simboloComputador = simboloComputador;
        this.simboloHumano = simboloHumano;
    }
    
    public Tablero decidirMejorMovimiento(Tablero tablero) {
        TreeNode<Tablero> arbolMinimax = generarArbolMinimax(tablero);
        
        int utilidadMaxima = Integer.MIN_VALUE; // Cota mínima.
        Tablero nuevoTablero = null;
        
        List<TreeNode<Tablero>> children = arbolMinimax.getChildren();
        if (!children.isEmpty()) nuevoTablero = children.get(0).getContent();
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
        if (tablero.esGanador(this.simboloComputador)) return 1000;
        if (tablero.esGanador(this.simboloHumano)) return -1000;

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
        if (tablero.esGanador(simboloComputador) || tablero.esGanador(simboloHumano) || tablero.esEmpate()) {
            return;
        }

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
        if (root.isLeaf()) return calcularUtilidad(root.getContent());
        
        int utilidadMinima = Integer.MAX_VALUE; // Cota máxima.
        
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
        Tablero tableroRecomendado = decidirMejorMovimiento(tablero);

        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 3; col++) {
                if (tablero.getCasilla(fila, col) == Simbolo.VACIO && tableroRecomendado.getCasilla(fila, col) != Simbolo.VACIO) {
                    return new int[]{fila, col};
                }
            }
        }
        
        return new int[]{-1, -1};
    }
}
