/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.automatofinito.classes;

/**
 *
 * @author igorxf
 */
public class ResultadoVerificacao {
    public boolean aceita;
    public int indiceFalha;
    public char simboloFalha;
    public int estadoAtual;

    public ResultadoVerificacao(boolean aceita, int indiceFalha, char simboloFalha, int estadoAtual) {
        this.aceita = aceita;
        this.indiceFalha = indiceFalha;
        this.simboloFalha = simboloFalha;
        this.estadoAtual = estadoAtual;
    }
}
