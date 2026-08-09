/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.main;

/**
 *
 * @author guill
 */
public enum Simbolo {
    X('X'),
    O('O'),
    VACIO(' ');
    
    private final char simbolo;
    
    private Simbolo(char simbolo) {
        this.simbolo = simbolo;
    }
    
    public char getSimbolo() {
        return this.simbolo;
    }
    
    @Override 
    public String toString() {
        return String.valueOf(this.simbolo);
    }
    
}
