package com.example.tresenraya.Modelo;

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
