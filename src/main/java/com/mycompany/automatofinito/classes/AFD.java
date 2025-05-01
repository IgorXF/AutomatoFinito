/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.automatofinito.classes;

/**
 *
 * @author igorxf
 */
import java.util.List;

public class AFD {
    private int estadoInicial;
    private List<Integer> estadosFinais;
    private List<Transicao> transicoes;

    public AFD(int estadoInicial, List<Integer> estadosFinais, List<Transicao> transicoes) {
        this.estadoInicial = estadoInicial;
        this.estadosFinais = estadosFinais;
        this.transicoes = transicoes;
    }

    public boolean verifica(String palavra) {
        int estadoAtual = estadoInicial;

        for (char simbolo : palavra.toCharArray()) {
            boolean transicaoEncontrada = false;

            for (Transicao t : transicoes) {
                if (t.origem == estadoAtual && t.simbolo == simbolo) {
                    estadoAtual = t.destino;
                    transicaoEncontrada = true;
                    break;
                }
            }

            if (!transicaoEncontrada) {
                return false;
            }
        }

        return estadosFinais.contains(estadoAtual);
    }
}


